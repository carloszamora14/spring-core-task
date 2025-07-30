package com.example.springcore.service;

import com.example.springcore.dao.TraineeDao;
import com.example.springcore.model.Trainee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeService {

    private static final Logger logger = LogManager.getLogger(TraineeService.class);

    @Autowired
    private TraineeDao traineeDao;

    @Autowired
    private UserCredentialService credentialService;

    public Trainee create(Trainee trainee) {
        if (trainee == null) {
            logger.error("create() called with null Trainee");
            throw new IllegalArgumentException("Trainee cannot be null");
        }

        logger.info("Creating trainee: {}", trainee);

        String username = credentialService.generateUniqueUsername(
                trainee.getFirstName(), trainee.getLastName());
        trainee.setUsername(username);

        String password = credentialService.generateRandomPassword(10);
        trainee.setPassword(password);

        Trainee created = traineeDao.create(trainee);
        logger.info("Trainee created successfully: {}", created);
        return created;
    }

    public Trainee update(Trainee trainee) {
        if (trainee == null) {
            logger.error("update() called with null Trainee");
            throw new IllegalArgumentException("Trainee cannot be null");
        }

        logger.info("Updating trainee: {}", trainee);

        Optional<Trainee> existingOpt = traineeDao.getById(trainee.getId());
        if (existingOpt.isEmpty()) {
            logger.warn("Trainee update failed. Trainee not found: {}", trainee);
            return null;
        }

        credentialService.updateUsername(trainee, existingOpt.get());
        Trainee updated = traineeDao.update(trainee);
        logger.info("Trainee updated successfully: {}", updated);

        return updated;
    }

    public void deleteById(long id) {
        logger.info("Deleting trainee with id: {}", id);
        Trainee deleted = traineeDao.deleteById(id);

        if (deleted == null) {
            logger.warn("Trainee with id {} not found. Nothing was deleted.", id);
        } else {
            logger.info("Deleted trainee: {}", deleted);
        }
    }

    public Optional<Trainee> getById(long id) {
        logger.info("Getting trainee with id: {}", id);
        return traineeDao.getById(id);
    }

    public List<Trainee> getAll() {
        logger.info("Getting all trainees");
        return traineeDao.getAll();
    }
}
