package com.example.springcore.service;

import com.example.springcore.dao.TraineeDao;
import com.example.springcore.dao.TrainerDao;
import com.example.springcore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserCredentialService {

    @Autowired
    private TraineeDao traineeDao;

    @Autowired
    private TrainerDao trainerDao;

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateUniqueUsername(String firstName, String lastName) {
        String baseUsername = firstName.trim() + "." + lastName.trim();
        baseUsername = baseUsername.toLowerCase();
        Set<String> existingUsernames = new HashSet<>();

        traineeDao.getAll().forEach(t -> existingUsernames.add(t.getUsername()));
        trainerDao.getAll().forEach(t -> existingUsernames.add(t.getUsername()));

        if (!existingUsernames.contains(baseUsername)) {
            return baseUsername;
        }

        int suffix = 1;
        String newUsername;
        do {
            newUsername = baseUsername + suffix;
            suffix += 1;
        } while (existingUsernames.contains(newUsername));

        return newUsername;
    }

    public <T extends User> void updateUsername(T newUserData, T existingUser) {
        boolean nameChanged =
                !newUserData.getFirstName().trim().equalsIgnoreCase(existingUser.getFirstName().trim()) ||
                !newUserData.getLastName().trim().equalsIgnoreCase(existingUser.getLastName().trim());

        if (nameChanged) {
            String newUsername = generateUniqueUsername(
                    newUserData.getFirstName(), newUserData.getLastName());
            newUserData.setUsername(newUsername);
        } else {
            newUserData.setUsername(existingUser.getUsername());
        }
    }

    public String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARS.length());
            builder.append(CHARS.charAt(index));
        }

        return builder.toString();
    }
}

