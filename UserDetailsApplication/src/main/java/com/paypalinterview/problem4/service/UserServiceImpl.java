package com.paypalinterview.problem4.service;


import com.paypalinterview.problem4.model.User;
import com.paypalinterview.problem4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean addUser(User user, LocalTime loginTime, String ipAddress) {
        user.setLoginTime(loginTime);
        user.getIpAddress().add(ipAddress);
        userRepository.addUser(user);
        return true;
    }

    @Override
    public User getUser(String userId) {
        return userRepository.getUser(userId);
    }

    @Override
    public boolean isValid(User user) {
        if(user.getUserId() == null || user.getUserAsset() == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean detectAnomaly(String userId, String ipAddress) {
        User existingUser = userRepository.getUser(userId);
        if(existingUser != null && existingUser.getIpAddress().contains(ipAddress)) {
            return true;
        }
        return false;
    }

}
