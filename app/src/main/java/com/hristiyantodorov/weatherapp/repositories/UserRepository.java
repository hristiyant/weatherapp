package com.hristiyantodorov.weatherapp.repositories;

import com.hristiyantodorov.weatherapp.model.database.user.UserDbModel;

public interface UserRepository {
    void createUser(String email);

    UserDbModel getUserByEmail(String email);

    UserDbModel getUserById(int id);
}
