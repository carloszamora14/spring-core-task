package com.example.springcore.service;

import com.example.springcore.dao.TrainingDao;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    private static final Logger logger = LogManager.getLogger(TrainingService.class);

    @Autowired
    private TrainingDao trainingDao;

    public Training create(Training training) {
        if (training == null) {
            logger.error("create() called with null Training");
            throw new IllegalArgumentException("Training cannot be null");
        }

        logger.info("Creating training: {}", training);
        Training created = trainingDao.create(training);
        logger.info("Training created successfully: {}", created);
        return created;
    }

    public Training update(Training training) {
        if (training == null) {
            logger.error("update() called with null Training");
            throw new IllegalArgumentException("Training cannot be null");
        }

        logger.info("Updating training: {}", training);
        Training updated = trainingDao.update(training);

        if (updated != null) {
            logger.info("Training updated successfully: {}", updated);
        } else {
            logger.warn("Training update failed. Training not found: {}", training);
        }

        return updated;
    }

    public void deleteById(long id) {
        logger.info("Deleting training with id: {}", id);
        Training deleted = trainingDao.deleteById(id);

        if (deleted == null) {
            logger.warn("Training with id {} not found. Nothing was deleted.", id);
        } else {
            logger.info("Deleted training: {}", deleted);
        }
    }

    public Optional<Training> getById(long id) {
        logger.info("Getting training with id: {}", id);
        return trainingDao.getById(id);
    }

    public List<Training> getAll() {
        logger.info("Getting all trainings");
        return trainingDao.getAll();
    }

    public List<Training> getByTrainerId(long trainerId) {
        logger.info("Getting trainings by trainerId: {}", trainerId);
        return trainingDao.getByTrainerId(trainerId);
    }

    public List<Training> getByTraineeId(long traineeId) {
        logger.info("Getting trainings by traineeId: {}", traineeId);
        return trainingDao.getByTraineeId(traineeId);
    }

    public List<Training> getByTrainingType(TrainingType trainingType) {
        if (trainingType == null) {
            logger.error("getByTrainingType() called with null trainingType");
            throw new IllegalArgumentException("TrainingType cannot be null");
        }

        logger.info("Getting trainings by trainingType: {}", trainingType);
        return trainingDao.getByTrainingType(trainingType);
    }
}
