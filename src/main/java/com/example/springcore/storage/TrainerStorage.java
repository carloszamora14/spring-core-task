package com.example.springcore.storage;

import com.example.springcore.model.Trainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

@Component
public class TrainerStorage extends InMemoryStorageImpl<Trainer> {

    @Value("${trainer.storage.init.file}")
    private String filePath;

    @PostConstruct
    public void init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(filePath.replace("classpath:", "/")))))) {
            reader.lines().forEach(line -> {
                String[] tokens = line.split(",");
                if (tokens.length >= 7) {
                    Trainer t = Trainer.builder()
                            .id(Long.parseLong(tokens[0]))
                            .firstName(tokens[1])
                            .lastName(tokens[2])
                            .username(tokens[3])
                            .password(tokens[4])
                            .active(Boolean.parseBoolean(tokens[5]))
                            .specialization(tokens[6])
                            .build();
                    save(t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

