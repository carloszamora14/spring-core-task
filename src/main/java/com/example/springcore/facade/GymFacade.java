package com.example.springcore.facade;

import com.example.springcore.model.*;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(
            TraineeService traineeService,
            TrainerService trainerService,
            TrainingService trainingService) {

        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.addAll(traineeService.getAll());
        users.addAll(trainerService.getAll());
        return users;
    }

    // Trainee methods

    public Trainee createTrainee(
            String firstName, String lastName,
            LocalDate dateOfBirth, String address, boolean active) {

        Trainee trainee = Trainee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .active(active)
                .build();
        return traineeService.create(trainee);
    }

    public Trainee updateTrainee(
            long id, String firstName, String lastName,
            LocalDate dateOfBirth, String address, boolean active) {

        Trainee existing = traineeService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found with ID: " + id));

        existing.setFirstName(firstName);
        existing.setLastName(lastName);
        existing.setDateOfBirth(dateOfBirth);
        existing.setAddress(address);
        existing.setActive(active);

        return traineeService.update(existing);
    }

    public void deleteTraineeById(Long id) {
        traineeService.deleteById(id);
    }

    public Optional<Trainee> getTraineeById(Long id) {
        return traineeService.getById(id);
    }

    public List<Trainee> getAllTrainees() {
        return traineeService.getAll();
    }

    // Trainer methods

    public Trainer createTrainer(
            String firstName, String lastName,
            boolean active, String specialization) {

        Trainer trainer = Trainer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .active(active)
                .specialization(specialization)
                .build();

        return trainerService.create(trainer);
    }

    public Trainer updateTrainer(
            Long trainerId, String firstName, String lastName,
            boolean active, String specialization) {

        Trainer existing = trainerService.getById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found with ID: " + trainerId));

        existing.setFirstName(firstName);
        existing.setLastName(lastName);
        existing.setActive(active);
        existing.setSpecialization(specialization);

        return trainerService.update(existing);
    }

    public Optional<Trainer> getTrainerById(Long trainerId) {
        return trainerService.getById(trainerId);
    }

    public List<Trainer> getAllTrainers() {
        return trainerService.getAll();
    }

    public void deleteTrainerById(Long trainerId) {
        trainerService.deleteById(trainerId);
    }

    // Training methods

    public Training createTraining(
            long traineeId, long trainerId, TrainingType trainingType,
            String trainingName, LocalDateTime dateTime, Duration duration) {

        Trainee trainee = traineeService.getById(traineeId).orElse(null);
        Trainer trainer = trainerService.getById(trainerId).orElse(null);

        if (trainee == null || trainer == null) {
            throw new IllegalArgumentException("Trainee or Trainer not found");
        }

        Training training = Training.builder()
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingType(trainingType)
                .trainingName(trainingName)
                .date(dateTime)
                .duration(duration)
                .build();

        return trainingService.create(training);
    }

    public Optional<Training> getTrainingById(Long id) {
        return trainingService.getById(id);
    }

    public List<Training> getTrainingsByTraineeId(long traineeId) {
        Trainee trainee = traineeService.getById(traineeId).orElse(null);
        if (trainee == null) {
            throw new IllegalArgumentException("Trainee not found with ID: " + traineeId);
        }
        return trainingService.getByTraineeId(traineeId);
    }

    public List<Training> getTrainingsByTrainerId(long trainerId) {
        Trainer trainer = trainerService.getById(trainerId).orElse(null);
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer not found with ID: " + trainerId);
        }
        return trainingService.getByTrainerId(trainerId);
    }

    public List<Training> getAllTrainings() {
        return trainingService.getAll();
    }

    public Training updateTraining(
            long trainingId, TrainingType newType, String newName,
            LocalDateTime newDate, Duration newDuration) {

        Training existing = trainingService.getById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found with ID: " + trainingId));

        existing.setTrainingType(newType);
        existing.setTrainingName(newName);
        existing.setDate(newDate);
        existing.setDuration(newDuration);

        return trainingService.update(existing);
    }

    public void deleteTrainingById(Long id) {
        trainingService.deleteById(id);
    }
}
