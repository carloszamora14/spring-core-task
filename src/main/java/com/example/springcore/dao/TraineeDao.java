package com.example.springcore.dao;

import com.example.springcore.model.Trainee;
import com.example.springcore.storage.TraineeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TraineeDao {

    private TraineeStorage traineeStorage;

    @Autowired
    public void setTraineeStorage(TraineeStorage traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    public Trainee create(Trainee trainee) {
        return traineeStorage.save(trainee);
    }

    public Trainee update(Trainee trainee) {
        return traineeStorage.save(trainee);
    }

    public Trainee deleteById(Long id) {
        return traineeStorage.deleteById(id);
    }

    public Optional<Trainee> getById(Long id) {
        return traineeStorage.findById(id);
    }

    public List<Trainee> getAll() {
        return traineeStorage.findAll();
    }
}
