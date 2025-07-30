package com.example.springcore;

import com.example.springcore.config.AppConfig;
import com.example.springcore.facade.GymFacade;
import com.example.springcore.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        GymFacade facade = context.getBean(GymFacade.class);

        Trainee newTrainee = facade.createTrainee(
                "Carlos", "Zamora",
                LocalDate.of(2025, 7, 30),
                "123 Main Street", true);

        Trainer newTrainer = facade.createTrainer(
                "Lucia", "Fernandez",
                true, "Strength");

        Training newTraining = facade.createTraining(
                newTrainee.getId(),
                newTrainer.getId(),
                TrainingType.STRENGTH,
                "Strength Session",
                LocalDateTime.of(2025, 7, 31, 17, 0),
                Duration.ofMinutes(60));

        facade.updateTraining(
                newTraining.getId(),
                TrainingType.STRENGTH,
                "Cardio class",
                LocalDateTime.of(2025, 8, 4, 17, 0),
                Duration.ofMinutes(75));

        List<Training> trainings = facade.getAllTrainings();
        System.out.println("\n=== All Trainings Before Deleting ===");
        for (Training training : trainings) {
            System.out.println(training);
        }

        facade.deleteTrainingById(newTraining.getId());

        trainings = facade.getAllTrainings();
        System.out.println("\n=== All Trainings After Deleting ===");
        for (Training training : trainings) {
            System.out.println(training);
        }
    }
}
