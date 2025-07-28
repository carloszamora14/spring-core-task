package com.example.springcore;

import com.example.springcore.config.AppConfig;
import com.example.springcore.facade.GymFacade;
import com.example.springcore.model.Training;
import com.example.springcore.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        GymFacade facade = context.getBean(GymFacade.class);

        List<User> users = facade.getAllUsers();
        System.out.println("=== All Users ===");
        for (User user : users) {
            System.out.println(user);
        }

        List<Training> trainings = facade.getAllTrainings();
        System.out.println("\n=== All Trainings ===");
        for (Training training : trainings) {
            System.out.println(training);
        }
    }
}
