package com.hristiyantodorov.weatherapp.persistence.user;

import java.util.List;

public interface UserRepository {
    List<UserDbModel> getAllUsers();

    UserDbModel getUserByEmail(String email);

    UserDbModel getUserById(int id);

    void insertUsers(UserDbModel... users);

    void deleteUsers(UserDbModel... users);

    void deleteUserById(int id);
}
