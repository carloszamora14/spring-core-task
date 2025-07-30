package com.example.springcore.service;

import com.example.springcore.dao.TraineeDao;
import com.example.springcore.dao.TrainerDao;
import com.example.springcore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserCredentialService {

    private static final Logger logger = LogManager.getLogger(UserCredentialService.class);

    @Autowired
    private TraineeDao traineeDao;

    @Autowired
    private TrainerDao trainerDao;

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateUniqueUsername(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            logger.error("generateUniqueUsername() called with null arguments: firstName={}, lastName={}", firstName, lastName);
            throw new IllegalArgumentException("First name and last name cannot be null");
        }

        String baseUsername = formatBaseUsername(firstName, lastName);
        Set<String> existingUsernames = getExistingUsernames();
        return generateAvailableUsername(baseUsername, existingUsernames);
    }

    private String formatBaseUsername(String firstName, String lastName) {
        return (firstName.trim() + "." + lastName.trim()).toLowerCase();
    }

    private Set<String> getExistingUsernames() {
        Set<String> usernames = new HashSet<>();

        traineeDao.getAll().forEach(t -> usernames.add(t.getUsername()));
        trainerDao.getAll().forEach(t -> usernames.add(t.getUsername()));

        return usernames;
    }

    private String generateAvailableUsername(String baseUsername, Set<String> existingUsernames) {
        if (!existingUsernames.contains(baseUsername)) {
            return baseUsername;
        }

        int suffix = 1;
        String candidate;
        do {
            candidate = baseUsername + suffix;
            suffix++;
        } while (existingUsernames.contains(candidate));

        return candidate;
    }

    public <T extends User> void updateUsername(T newUserData, T existingUser) {
        if (newUserData == null || existingUser == null) {
            logger.error("updateUsername() called with null arguments: newUserData={}, existingUser={}", newUserData, existingUser);
            throw new IllegalArgumentException("User data cannot be null");
        }

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
        if (length <= 0) {
            logger.error("generateRandomPassword() called with non-positive length: {}", length);
            throw new IllegalArgumentException("Password length must be greater than 0");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARS.length());
            builder.append(CHARS.charAt(index));
        }

        return builder.toString();
    }
}

