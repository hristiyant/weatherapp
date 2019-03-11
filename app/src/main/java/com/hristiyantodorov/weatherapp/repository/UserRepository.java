package com.hristiyantodorov.weatherapp.repository;

import com.hristiyantodorov.weatherapp.persistence.user.UserDbModel;
import com.hristiyantodorov.weatherapp.util.AsyncResponse;

import java.util.List;

public interface UserRepository {

    List<UserDbModel> getAllUsers(AsyncResponse response);

    UserDbModel getUserByEmail(String email, AsyncResponse response);

    UserDbModel getUserById(int id, AsyncResponse response);

    void insertUsers(UserDbModel... users);

    void deleteUsers(UserDbModel... users);

    void deleteUserById(int id);

}
