package com.example.springcore.service;

import com.example.springcore.dao.TrainerDao;
import com.example.springcore.model.Trainer;
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
public class TrainerServiceTest {

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private UserCredentialService credentialService;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    public void testCreate() {
        Trainer trainer = Trainer.builder()
                .firstName("Bob")
                .lastName("Brown")
                .build();

        when(credentialService.generateUniqueUsername("Bob", "Brown")).thenReturn("bob.brown");
        when(credentialService.generateRandomPassword(10)).thenReturn("password10");

        Trainer savedTrainer = Trainer.builder()
                .id(1L)
                .firstName("Bob")
                .lastName("Brown")
                .username("bob.brown")
                .password("password10")
                .build();

        when(trainerDao.create(any())).thenReturn(savedTrainer);

        Trainer result = trainerService.create(trainer);

        assertNotNull(result);
        assertEquals("bob.brown", result.getUsername());
        assertEquals("password10", result.getPassword());
        assertEquals(1L, result.getId());

        verify(credentialService, times(1)).generateUniqueUsername("Bob", "Brown");
        verify(credentialService, times(1)).generateRandomPassword(10);
        verify(trainerDao, times(1)).create(any(Trainer.class));
    }

    @Test
    public void testCreate_withNullTrainer() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            trainerService.create(null);
        });
        assertEquals("Trainer cannot be null", exception.getMessage());
    }

    @Test
    public void testUpdate_existingTrainer() {
        Trainer existing = Trainer.builder()
                .id(1L)
                .firstName("Bob")
                .lastName("Brown")
                .username("bob.old")
                .build();

        Trainer updated = Trainer.builder()
                .id(1L)
                .firstName("Bob")
                .lastName("Brown")
                .username("bob.new")
                .build();

        when(trainerDao.getById(1L)).thenReturn(Optional.of(existing));
        when(trainerDao.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        trainerService.update(updated);

        verify(trainerDao).getById(1L);
        verify(credentialService).updateUsername(updated, existing);
        verify(trainerDao).update(updated);
    }

    @Test
    public void testUpdate_nonExistingTrainer() {
        Trainer trainer = Trainer.builder()
                .id(1L)
                .build();

        when(trainerDao.getById(1L)).thenReturn(Optional.empty());

        Trainer result = trainerService.update(trainer);

        assertNull(result);
        verify(trainerDao).getById(1L);
        verify(credentialService, never()).updateUsername(any(), any());
        verify(trainerDao, never()).update(any());
    }

    @Test
    public void testUpdate_withNullTrainer() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            trainerService.update(null);
        });
        assertEquals("Trainer cannot be null", exception.getMessage());
    }

    @Test
    public void testDeleteById_found() {
        Trainer trainer = Trainer.builder().id(1L).build();

        when(trainerDao.deleteById(1L)).thenReturn(trainer);

        trainerService.deleteById(1L);

        verify(trainerDao).deleteById(1L);
    }

    @Test
    public void testDeleteById_notFound() {
        when(trainerDao.deleteById(1L)).thenReturn(null);

        trainerService.deleteById(1L);

        verify(trainerDao).deleteById(1L);
    }

    @Test
    public void testGetById_found() {
        Trainer trainer = Trainer.builder().id(1L).build();

        when(trainerDao.getById(1L)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(trainerDao).getById(1L);
    }

    @Test
    public void testGetById_notFound() {
        when(trainerDao.getById(1L)).thenReturn(Optional.empty());

        Optional<Trainer> result = trainerService.getById(1L);

        assertFalse(result.isPresent());
        verify(trainerDao).getById(1L);
    }

    @Test
    public void testGetAll() {
        Trainer t1 = Trainer.builder().id(1L).build();
        Trainer t2 = Trainer.builder().id(2L).build();

        when(trainerDao.getAll()).thenReturn(List.of(t1, t2));

        List<Trainer> result = trainerService.getAll();

        assertEquals(2, result.size());
        verify(trainerDao).getAll();
    }
}
