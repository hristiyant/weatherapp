package com.hristiyantodorov.weatherapp.service;

import com.hristiyantodorov.weatherapp.model.user.User;

public interface UserService {
    void createUser(String email);

    User getUserByEmail(String email);

    User getUserById(int id);
}
