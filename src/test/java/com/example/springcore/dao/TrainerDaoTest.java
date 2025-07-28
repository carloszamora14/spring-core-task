package com.example.springcore.dao;

import com.example.springcore.model.Trainer;
import com.example.springcore.storage.TrainerStorage;
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
public class TrainerDaoTest {

    @Mock
    private TrainerStorage trainerStorage;

    @InjectMocks
    private TrainerDao trainerDao;

    @Test
    void testCreate() {
        Trainer trainer = Trainer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .specialization("Aerobics")
                .build();

        when(trainerStorage.save(trainer)).thenReturn(trainer);

        Trainer result = trainerDao.create(trainer);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(trainerStorage, times(1)).save(trainer);
    }

    @Test
    void testUpdate() {
        Trainer trainer = Trainer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .specialization("Aerobics")
                .build();

        when(trainerStorage.save(trainer)).thenReturn(trainer);

        Trainer result = trainerDao.update(trainer);

        assertNotNull(result);
        assertEquals("Aerobics", result.getSpecialization());
        verify(trainerStorage, times(1)).save(trainer);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        Trainer trainer = Trainer.builder().id(id).build();

        when(trainerStorage.deleteById(id)).thenReturn(trainer);

        Trainer result = trainerDao.deleteById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(trainerStorage, times(1)).deleteById(id);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Trainer trainer = Trainer.builder()
                .id(id)
                .firstName("John")
                .build();

        when(trainerStorage.findById(id)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerDao.getById(id);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        verify(trainerStorage, times(1)).findById(id);
    }

    @Test
    void testGetAll() {
        Trainer trainer1 = Trainer.builder().id(1L).firstName("John").build();
        Trainer trainer2 = Trainer.builder().id(2L).firstName("Jane").build();

        when(trainerStorage.findAll()).thenReturn(List.of(trainer1, trainer2));

        List<Trainer> result = trainerDao.getAll();

        assertEquals(2, result.size());
        verify(trainerStorage, times(1)).findAll();
    }
}
