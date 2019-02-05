package com.hristiyantodorov.weatherapp.repository;

import com.hristiyantodorov.weatherapp.model.user.UserDbModel;

public interface UserRepository {
    void createUser(String email);

    UserDbModel getUserByEmail(String email);

    UserDbModel getUserById(int id);
}
