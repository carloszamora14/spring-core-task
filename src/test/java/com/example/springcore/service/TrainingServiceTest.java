package com.example.springcore.service;

import com.example.springcore.dao.TrainingDao;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingDao trainingDao;

    @InjectMocks
    private TrainingService trainingService;

    @Test
    void testCreate() {
        Training training = Training.builder()
                .trainingName("Yoga")
                .build();

        Training savedTraining = Training.builder()
                .id(1L)
                .trainingName("Yoga")
                .build();

        when(trainingDao.create(any())).thenReturn(savedTraining);

        Training result = trainingService.create(training);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Yoga", result.getTrainingName());

        verify(trainingDao, times(1)).create(any(Training.class));
    }

    @Test
    void testUpdate_found() {
        Training training = Training.builder()
                .id(1L)
                .trainingName("Pilates")
                .build();

        when(trainingDao.update(training)).thenReturn(training);

        Training result = trainingService.update(training);

        assertNotNull(result);
        assertEquals("Pilates", result.getTrainingName());

        verify(trainingDao, times(1)).update(training);
    }

    @Test
    void testUpdate_notFound() {
        Training training = Training.builder()
                .id(1L)
                .trainingName("Pilates")
                .build();

        when(trainingDao.update(training)).thenReturn(null);

        Training result = trainingService.update(training);

        assertNull(result);
        verify(trainingDao, times(1)).update(training);
    }

    @Test
    void testDeleteById_found() {
        Training training = Training.builder().id(1L).build();

        when(trainingDao.deleteById(1L)).thenReturn(training);

        trainingService.deleteById(1L);

        verify(trainingDao, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_notFound() {
        when(trainingDao.deleteById(1L)).thenReturn(null);

        trainingService.deleteById(1L);

        verify(trainingDao, times(1)).deleteById(1L);
    }

    @Test
    void testGetById_found() {
        Training training = Training.builder().id(1L).build();

        when(trainingDao.getById(1L)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());

        verify(trainingDao, times(1)).getById(1L);
    }

    @Test
    void testGetById_notFound() {
        when(trainingDao.getById(1L)).thenReturn(Optional.empty());

        Optional<Training> result = trainingService.getById(1L);

        assertFalse(result.isPresent());
        verify(trainingDao, times(1)).getById(1L);
    }

    @Test
    void testGetAll() {
        Training t1 = Training.builder().id(1L).trainingName("Yoga").build();
        Training t2 = Training.builder().id(2L).trainingName("Crossfit").build();

        when(trainingDao.getAll()).thenReturn(List.of(t1, t2));

        List<Training> result = trainingService.getAll();

        assertEquals(2, result.size());
        verify(trainingDao, times(1)).getAll();
    }

    @Test
    void testGetByTrainerId() {
        Long trainerId = 10L;
        Training t1 = Training.builder().id(1L).trainerId(trainerId).build();
        Training t2 = Training.builder().id(2L).trainerId(trainerId).build();

        when(trainingDao.getByTrainerId(trainerId)).thenReturn(List.of(t1, t2));

        List<Training> result = trainingService.getByTrainerId(trainerId);

        assertEquals(2, result.size());
        verify(trainingDao, times(1)).getByTrainerId(trainerId);
    }

    @Test
    void testGetByTraineeId() {
        Long traineeId = 20L;
        Training t1 = Training.builder().id(1L).traineeId(traineeId).build();
        Training t2 = Training.builder().id(2L).traineeId(traineeId).build();

        when(trainingDao.getByTraineeId(traineeId)).thenReturn(List.of(t1, t2));

        List<Training> result = trainingService.getByTraineeId(traineeId);

        assertEquals(2, result.size());
        verify(trainingDao, times(1)).getByTraineeId(traineeId);
    }

    @Test
    void testGetByTrainingType() {
        Training t1 = Training.builder().id(1L).trainingType(TrainingType.STRENGTH).build();
        Training t2 = Training.builder().id(2L).trainingType(TrainingType.STRENGTH).build();

        when(trainingDao.getByTrainingType(TrainingType.STRENGTH)).thenReturn(List.of(t1, t2));

        List<Training> result = trainingService.getByTrainingType(TrainingType.STRENGTH);

        assertEquals(2, result.size());
        verify(trainingDao, times(1)).getByTrainingType(TrainingType.STRENGTH);
    }
}
