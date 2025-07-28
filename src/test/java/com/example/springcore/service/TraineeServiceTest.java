package com.example.springcore.service;

import com.example.springcore.dao.TraineeDao;
import com.example.springcore.model.Trainee;
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
public class TraineeServiceTest {

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private UserCredentialService credentialService;

    @InjectMocks
    private TraineeService traineeService;

    @Test
    void testCreate() {
        Trainee trainee = Trainee.builder()
                .firstName("Alice")
                .lastName("Smith")
                .build();

        when(credentialService.generateUniqueUsername("Alice", "Smith")).thenReturn("alice.smith");
        when(credentialService.generateRandomPassword(10)).thenReturn("password10");

        Trainee savedTrainee = Trainee.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .username("alice.smith")
                .password("password10")
                .build();

        when(traineeDao.create(any())).thenReturn(savedTrainee);

        Trainee result = traineeService.create(trainee);

        assertNotNull(result);
        assertEquals("alice.smith", result.getUsername());
        assertEquals("password10", result.getPassword());
        assertEquals(1L, result.getId());

        verify(credentialService, times(1)).generateUniqueUsername("Alice", "Smith");
        verify(credentialService, times(1)).generateRandomPassword(10);
        verify(traineeDao, times(1)).create(any(Trainee.class));
    }

    @Test
    void testUpdate_existingTrainee() {
        Trainee existing = Trainee.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .username("alice.old")
                .build();

        Trainee updated = Trainee.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Smith")
                .username("alice.new")
                .build();

        when(traineeDao.getById(1L)).thenReturn(Optional.of(existing));
        when(traineeDao.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        traineeService.update(updated);

        verify(traineeDao).getById(1L);
        verify(credentialService).updateUsername(updated, existing);
        verify(traineeDao).update(updated);
    }

    @Test
    void testUpdate_nonExistingTrainee() {
        Trainee trainee = Trainee.builder()
                .id(1L)
                .build();

        when(traineeDao.getById(1L)).thenReturn(Optional.empty());

        Trainee result = traineeService.update(trainee);

        assertNull(result);
        verify(traineeDao).getById(1L);
        verify(credentialService, never()).updateUsername(any(), any());
        verify(traineeDao, never()).update(any());
    }

    @Test
    void testDeleteById_found() {
        Trainee trainee = Trainee.builder().id(1L).build();

        when(traineeDao.deleteById(1L)).thenReturn(trainee);

        traineeService.deleteById(1L);

        verify(traineeDao).deleteById(1L);
    }

    @Test
    void testDeleteById_notFound() {
        when(traineeDao.deleteById(1L)).thenReturn(null);

        traineeService.deleteById(1L);

        verify(traineeDao).deleteById(1L);
    }

    @Test
    void testGetById_found() {
        Trainee trainee = Trainee.builder().id(1L).build();

        when(traineeDao.getById(1L)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(traineeDao).getById(1L);
    }

    @Test
    void testGetById_notFound() {
        when(traineeDao.getById(1L)).thenReturn(Optional.empty());

        Optional<Trainee> result = traineeService.getById(1L);

        assertFalse(result.isPresent());
        verify(traineeDao).getById(1L);
    }

    @Test
    void testGetAll() {
        Trainee t1 = Trainee.builder().id(1L).build();
        Trainee t2 = Trainee.builder().id(2L).build();

        when(traineeDao.getAll()).thenReturn(List.of(t1, t2));

        List<Trainee> result = traineeService.getAll();

        assertEquals(2, result.size());
        verify(traineeDao).getAll();
    }
}
