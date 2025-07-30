package com.example.springcore.storage;

import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class TrainingStorage extends InMemoryStorageImpl<Training> {

    @Value("${training.storage.init.file}")
    private String filePath;

    @PostConstruct
    public void init() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass()
                        .getResourceAsStream(filePath.replace("classpath:", "/")))))) {

            reader.lines().forEach(line -> {
                String[] tokens = line.split(",");

                if (tokens.length >= 6) {
                    Training training = Training.builder()
                            .traineeId(Long.parseLong(tokens[0]))
                            .trainerId(Long.parseLong(tokens[1]))
                            .trainingType(TrainingType.valueOf(tokens[2]))
                            .trainingName(tokens[3])
                            .date(LocalDateTime.parse(tokens[4]))
                            .duration(Duration.parse(tokens[5]))
                            .build();

                    save(training);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
