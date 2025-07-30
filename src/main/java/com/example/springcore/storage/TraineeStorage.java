package com.example.springcore.storage;

import com.example.springcore.model.Trainee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Objects;

@Component
public class TraineeStorage extends InMemoryStorageImpl<Trainee> {

    @Value("${trainee.storage.init.file}")
    private String filePath;

    @PostConstruct
    public void init() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass()
                        .getResourceAsStream(filePath.replace("classpath:", "/")))))) {

            reader.lines().forEach(line -> {
                String[] tokens = line.split(",");

                if (tokens.length >= 7) {
                    Trainee t = Trainee.builder()
                            .firstName(tokens[0])
                            .lastName(tokens[1])
                            .username(tokens[2])
                            .password(tokens[3])
                            .active(Boolean.parseBoolean(tokens[4]))
                            .dateOfBirth(LocalDate.parse(tokens[5]))
                            .address(tokens[6])
                            .build();

                    save(t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
