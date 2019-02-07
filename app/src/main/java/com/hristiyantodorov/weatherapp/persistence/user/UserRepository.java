package com.hristiyantodorov.weatherapp.persistence.user;

public interface UserRepository {
    void createUser(String email);

    UserDbModel getUserByEmail(String email);

    UserDbModel getUserById(int id);
}
