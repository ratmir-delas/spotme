package com.example.spotme_mvp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.spotme_mvp.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id = :id")
    User getById(int id);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User login(String email, String password);

    @Insert
    void insert(User user);

    @Update
    void update(User user);
    @Query("UPDATE users SET username = :username WHERE id = :id")
    void updateNome(String username, int id);

    @Query("UPDATE users SET email = :email WHERE id = :id")
    void updateEmail(String email, int id);

    @Query("UPDATE users SET password = :password WHERE id = :id")
    void updatePassword(String password, int id);

    @Query("UPDATE users SET profileImage = :profileImage WHERE id = :id")
    void updateProfileImage(String profileImage, int id);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User findByEmail(String email);
}