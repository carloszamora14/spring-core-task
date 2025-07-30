package com.example.springcore.service;

import com.example.springcore.dao.TrainerDao;
import com.example.springcore.model.Trainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {

    private static final Logger logger = LogManager.getLogger(TrainerService.class);

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private UserCredentialService credentialService;

    public Trainer create(Trainer trainer) {
        if (trainer == null) {
            logger.error("create() called with null Trainer");
            throw new IllegalArgumentException("Trainer cannot be null");
        }

        logger.info("Creating trainer: {}", trainer);

        String username = credentialService.generateUniqueUsername(
                trainer.getFirstName(), trainer.getLastName());
        trainer.setUsername(username);

        String password = credentialService.generateRandomPassword(10);
        trainer.setPassword(password);

        Trainer created = trainerDao.create(trainer);
        logger.info("Trainer created successfully: {}", created);
        return created;
    }

    public Trainer update(Trainer trainer) {
        if (trainer == null) {
            logger.error("update() called with null Trainer");
            throw new IllegalArgumentException("Trainer cannot be null");
        }

        logger.info("Updating trainer: {}", trainer);

        Optional<Trainer> existingOpt = trainerDao.getById(trainer.getId());
        if (existingOpt.isEmpty()) {
            logger.warn("Trainer update failed. Trainee not found: {}", trainer);
            return null;
        }

        credentialService.updateUsername(trainer, existingOpt.get());
        Trainer updated = trainerDao.update(trainer);
        logger.info("Trainee updated successfully: {}", updated);

        return updated;
    }

    public void deleteById(long id) {
        logger.info("Deleting trainer with id: {}", id);
        Trainer deleted = trainerDao.deleteById(id);

        if (deleted == null) {
            logger.warn("Trainer with id {} not found. Nothing was deleted.", id);
        } else {
            logger.info("Deleted trainer: {}", deleted);
        }
    }

    public Optional<Trainer> getById(long id) {
        logger.info("Getting trainer with id: {}", id);
        return trainerDao.getById(id);
    }

    public List<Trainer> getAll() {
        logger.info("Getting all trainers");
        return trainerDao.getAll();
    }
}
