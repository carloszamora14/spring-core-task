package com.example.springcore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.springcore.dao.TraineeDao;
import com.example.springcore.dao.TrainerDao;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserCredentialServiceTest {

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private TrainerDao trainerDao;

    @InjectMocks
    private UserCredentialService credentialService;

    @Test
    public void testGenerateUniqueUsername() {
        when(traineeDao.getAll()).thenReturn(List.of());
        when(trainerDao.getAll()).thenReturn(List.of());

        String username = credentialService.generateUniqueUsername("Juan", "Sanchez");

        assertEquals("juan.sanchez", username);
    }

    @Test
    public void testGenerateUniqueUsername_usernameTaken() {
        Trainee existingTrainee = new Trainee();
        existingTrainee.setUsername("pedro.diaz");

        Trainer existingTrainer1 = new Trainer();
        existingTrainer1.setUsername("pedro.diaz1");

        Trainer existingTrainer2 = new Trainer();
        existingTrainer2.setUsername("pedro.diaz3");

        when(traineeDao.getAll()).thenReturn(List.of(existingTrainee));
        when(trainerDao.getAll()).thenReturn(List.of(existingTrainer1, existingTrainer2));

        String username = credentialService.generateUniqueUsername("Pedro", "Diaz");

        assertEquals("pedro.diaz2", username);
    }

    @Test
    public void testUpdateUsername_nameChanged() {
        User newUser = new Trainee();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");

        User existingUser = new Trainee();
        existingUser.setFirstName("John");
        existingUser.setLastName("Jones");
        existingUser.setUsername("john.jones");

        when(traineeDao.getAll()).thenReturn(List.of());
        when(trainerDao.getAll()).thenReturn(List.of());

        credentialService.updateUsername(newUser, existingUser);

        assertEquals("john.doe", newUser.getUsername());
    }

    @Test
    public void testUpdateUsername_nameNotChanged() {
        User newUser = new Trainee();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");

        User existingUser = new Trainee();
        existingUser.setFirstName("john");
        existingUser.setLastName("Doe");
        existingUser.setUsername("john.doe");

        credentialService.updateUsername(newUser, existingUser);

        assertEquals("john.doe", newUser.getUsername());
    }

    @Test
    public void testGenerateRandomPassword_lengthAndChars() {
        String password = credentialService.generateRandomPassword(10);

        assertNotNull(password);
        assertEquals(10, password.length());
        assertTrue(password.matches("[A-Za-z0-9]+"));
    }
}
