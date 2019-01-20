package com.paypalinterview.problem4.repository;


import com.paypalinterview.problem4.model.User;

public interface UserRepository {

    User getUser(String userId);

    boolean addUser(User user);

}
