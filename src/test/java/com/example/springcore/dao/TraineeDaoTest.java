package com.example.springcore.dao;

import com.example.springcore.model.Trainee;
import com.example.springcore.storage.TraineeStorage;
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
public class TraineeDaoTest {

    @Mock
    private TraineeStorage traineeStorage;

    @InjectMocks
    private TraineeDao traineeDao;

    @Test
    void testCreate() {
        Trainee trainee = Trainee.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .build();

        when(traineeStorage.save(trainee)).thenReturn(trainee);

        Trainee result = traineeDao.create(trainee);

        assertNotNull(result);
        assertEquals("Alice", result.getFirstName());
        verify(traineeStorage, times(1)).save(trainee);
    }

    @Test
    void testUpdate() {
        Trainee trainee = Trainee.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .build();

        when(traineeStorage.save(trainee)).thenReturn(trainee);

        Trainee result = traineeDao.update(trainee);

        assertNotNull(result);
        assertEquals("Smith", result.getLastName());
        verify(traineeStorage, times(1)).save(trainee);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        Trainee trainee = Trainee.builder().id(id).build();

        when(traineeStorage.deleteById(id)).thenReturn(trainee);

        Trainee result = traineeDao.deleteById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(traineeStorage, times(1)).deleteById(id);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Trainee trainee = Trainee.builder()
                .id(id)
                .firstName("Alice")
                .build();

        when(traineeStorage.findById(id)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeDao.getById(id);

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getFirstName());
        verify(traineeStorage, times(1)).findById(id);
    }

    @Test
    void testGetAll() {
        Trainee trainee1 = Trainee.builder().id(1L).firstName("Alice").build();
        Trainee trainee2 = Trainee.builder().id(2L).firstName("Bob").build();

        when(traineeStorage.findAll()).thenReturn(List.of(trainee1, trainee2));

        List<Trainee> result = traineeDao.getAll();

        assertEquals(2, result.size());
        verify(traineeStorage, times(1)).findAll();
    }
}
