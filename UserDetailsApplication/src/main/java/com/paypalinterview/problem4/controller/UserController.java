package com.paypalinterview.problem4.controller;


import com.paypalinterview.problem4.model.User;
import com.paypalinterview.problem4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.time.ZoneId;

@RestController
public class UserController {

	@Autowired
	private UserService userService;


	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody User user, HttpServletRequest request) {
		if (userService.isValid(user)) {
			LocalTime loginTime = LocalTime.now(ZoneId.of("GMT+02:30"));
			String ipAddress = request.getRemoteAddr();
			userService.addUser(user, loginTime, ipAddress);
		} else {
			System.out.println("Invalid user");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		System.out.println("User " + user.getUserId() + " successfully added/updated");
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/getUser/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable("userId") String userId) {
		User user =  userService.getUser(userId);
		if(user == null) {
			System.out.println("User not found");
		} else {
			System.out.println(user);
		}
		return user;
	}

	@RequestMapping(value = "/detectAnomaly/{userId}/{ipAddress}", method = RequestMethod.GET)
	public boolean detectAnomaly(@PathVariable("userId") String userId, @PathVariable("ipAddress") String ipAddress) {
		boolean isAnomalyFound = userService.detectAnomaly(userId, ipAddress);
		System.out.println(isAnomalyFound);
		return isAnomalyFound;
	}

}
