package com.example.springcore.dao;

import com.example.springcore.model.Trainer;
import com.example.springcore.storage.TrainerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerDao {

    private TrainerStorage trainerStorage;

    @Autowired
    public void setTrainerStorage(TrainerStorage trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    public Trainer create(Trainer trainer) {
        return trainerStorage.save(trainer);
    }

    public Trainer update(Trainer trainer) {
        return trainerStorage.save(trainer);
    }

    public Trainer deleteById(Long id) {
        return trainerStorage.deleteById(id);
    }

    public Optional<Trainer> getById(Long id) {
        return trainerStorage.findById(id);
    }

    public List<Trainer> getAll() {
        return trainerStorage.findAll();
    }
}
