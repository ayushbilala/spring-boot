package com.paypalinterview.problem4.service;

import com.paypalinterview.problem4.model.User;

import java.time.LocalTime;


public interface UserService {

	boolean addUser(User user, LocalTime loginTime, String ipAddress);

	User getUser(String userId);

	boolean isValid(User user);

	boolean detectAnomaly(String userId, String ipAddress);
}