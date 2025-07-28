package com.example.springcore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training implements Identifiable {
    private Long id;
    private Long traineeId;
    private Long trainerId;
    private TrainingType trainingType;
    private String trainingName;
    private LocalDateTime date;
    private Duration duration;
}
