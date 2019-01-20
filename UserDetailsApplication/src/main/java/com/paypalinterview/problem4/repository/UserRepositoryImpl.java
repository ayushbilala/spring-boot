package com.paypalinterview.problem4.repository;

import com.paypalinterview.problem4.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserRepositoryImpl implements UserRepository {

    Map<String, User> userMap = new HashMap<>();

    @Override
    public User getUser(String userId) {
        return userMap.get(userId);
    }

    @Override
    public boolean addUser(User user) {
        if(userMap.containsKey(user.getUserId())) {
            User existingUser = userMap.get(user.getUserId());

            existingUser.setUserAsset(user.getUserAsset());
            existingUser.setLoginTime(user.getLoginTime());

            Set<String> ipAddress = existingUser.getIpAddress();
            ipAddress.addAll(user.getIpAddress());
            existingUser.setIpAddress(ipAddress);

            userMap.put(user.getUserId(), existingUser);
        } else {
            userMap.put(user.getUserId(), user);
        }
        return true;
    }
}
