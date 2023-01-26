package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 5);
        userService.saveUser("Petr", "Petrov", (byte) 6);
        userService.saveUser("Oleg", "Olegov", (byte) 7);
        userService.saveUser("Alexander", "Alexandrov", (byte) 8);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}