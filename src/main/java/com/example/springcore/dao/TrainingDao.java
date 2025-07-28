package com.example.springcore.dao;

import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.storage.TrainingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingDao {

    private TrainingStorage trainingStorage;

    @Autowired
    public void setTrainingStorage(TrainingStorage trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    public Training create(Training training) {
        return trainingStorage.save(training);
    }

    public Training update(Training training) {
        return trainingStorage.save(training);
    }

    public Training deleteById(Long id) {
        return trainingStorage.deleteById(id);
    }

    public Optional<Training> getById(Long id) {
        return trainingStorage.findById(id);
    }

    public List<Training> getAll() {
        return trainingStorage.findAll();
    }

    public List<Training> getByTrainerId(Long trainerId) {
        return trainingStorage.findAll().stream()
                .filter(t -> t.getTrainerId() != null &&
                        t.getTrainerId().equals(trainerId))
                .toList();
    }

    public List<Training> getByTraineeId(Long traineeId) {
        return trainingStorage.findAll().stream()
                .filter(t -> t.getTraineeId() != null &&
                        t.getTraineeId().equals(traineeId))
                .toList();
    }

    public List<Training> getByTrainingType(TrainingType trainingType) {
        return trainingStorage.findAll().stream()
                .filter(t -> t.getTrainingType() != null &&
                        t.getTrainingType().equals(trainingType))
                .toList();
    }
}
