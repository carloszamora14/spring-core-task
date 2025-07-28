package com.example.springcore.facade;

import com.example.springcore.model.Training;
import com.example.springcore.model.User;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(TraineeService traineeService,
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

    public List<Training> getAllTrainings() {
        return trainingService.getAll();
    }
}
