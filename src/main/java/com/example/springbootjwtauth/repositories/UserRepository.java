package com.example.springbootjwtauth.repositories;

import com.example.springbootjwtauth.models.User;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private static final Map<String, User> db = new HashMap<>();

    public User findByEmail(String email) {
        return db.get(email);
    }

    public void addUser(User user) {
        db.put(user.getEmail(), user);
    }
}
