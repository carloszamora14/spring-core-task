package com.example.springcore.dao;

import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.storage.TrainingStorage;
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
public class TrainingDaoTest {

    @Mock
    private TrainingStorage trainingStorage;

    @InjectMocks
    private TrainingDao trainingDao;

    @Test
    void testCreate() {
        Training training = Training.builder()
                .id(1L)
                .trainerId(10L)
                .traineeId(20L)
                .trainingType(TrainingType.YOGA)
                .build();

        when(trainingStorage.save(training)).thenReturn(training);

        Training result = trainingDao.create(training);

        assertNotNull(result);
        assertEquals(10L, result.getTrainerId());
        verify(trainingStorage, times(1)).save(training);
    }

    @Test
    void testUpdate() {
        Training training = Training.builder()
                .id(1L)
                .trainerId(10L)
                .traineeId(20L)
                .trainingType(TrainingType.YOGA)
                .build();

        when(trainingStorage.save(training)).thenReturn(training);

        Training result = trainingDao.update(training);

        assertNotNull(result);
        assertEquals(TrainingType.YOGA, result.getTrainingType());
        verify(trainingStorage, times(1)).save(training);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        Training training = Training.builder().id(id).build();

        when(trainingStorage.deleteById(id)).thenReturn(training);

        Training result = trainingDao.deleteById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(trainingStorage, times(1)).deleteById(id);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Training training = Training.builder()
                .id(id)
                .trainerId(10L)
                .build();

        when(trainingStorage.findById(id)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingDao.getById(id);

        assertTrue(result.isPresent());
        assertEquals(10L, result.get().getTrainerId());
        verify(trainingStorage, times(1)).findById(id);
    }

    @Test
    void testGetAll() {
        Training t1 = Training.builder().id(1L).trainerId(10L).build();
        Training t2 = Training.builder().id(2L).trainerId(11L).build();

        when(trainingStorage.findAll()).thenReturn(List.of(t1, t2));

        List<Training> result = trainingDao.getAll();

        assertEquals(2, result.size());
        verify(trainingStorage, times(1)).findAll();
    }

    @Test
    void testGetByTrainerId() {
        Training t1 = Training.builder().trainerId(1L).build();
        Training t2 = Training.builder().trainerId(2L).build();
        Training t3 = Training.builder().trainerId(1L).build();

        when(trainingStorage.findAll()).thenReturn(List.of(t1, t2, t3));

        List<Training> result = trainingDao.getByTrainerId(1L);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getTrainerId().equals(1L)));
        verify(trainingStorage, times(1)).findAll();
    }

    @Test
    void testGetByTraineeId() {
        Training t1 = Training.builder().traineeId(5L).build();
        Training t2 = Training.builder().traineeId(6L).build();
        Training t3 = Training.builder().traineeId(5L).build();

        when(trainingStorage.findAll()).thenReturn(List.of(t1, t2, t3));

        List<Training> result = trainingDao.getByTraineeId(5L);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getTraineeId().equals(5L)));
        verify(trainingStorage, times(1)).findAll();
    }

    @Test
    void testGetByTrainingType() {
        Training t1 = Training.builder().trainingType(TrainingType.CARDIO).build();
        Training t2 = Training.builder().trainingType(TrainingType.STRENGTH).build();
        Training t3 = Training.builder().trainingType(TrainingType.CARDIO).build();
        Training t4 = Training.builder().trainingType(TrainingType.FLEXIBILITY).build();

        when(trainingStorage.findAll()).thenReturn(List.of(t1, t2, t3, t4));

        List<Training> result = trainingDao.getByTrainingType(TrainingType.CARDIO);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getTrainingType().equals(TrainingType.CARDIO)));
        verify(trainingStorage, times(1)).findAll();
    }
}
