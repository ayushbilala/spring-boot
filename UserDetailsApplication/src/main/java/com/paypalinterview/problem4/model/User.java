package com.paypalinterview.problem4.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class User {

	private String userId;
	private String userAsset;
	private LocalTime loginTime;
	private Set<String> ipAddress;

    public User() {
        this.ipAddress = new HashSet<>();
    }

    public User(String userId, String userAsset) {
        this.userId = userId;
        this.userAsset = userAsset;
        this.ipAddress = new HashSet<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAsset() {
        return userAsset;
    }

    public void setUserAsset(String userAsset) {
        this.userAsset = userAsset;
    }

    public LocalTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalTime loginTime) {
        this.loginTime = loginTime;
    }

    public Set<String> getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Set<String> ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userAsset='" + userAsset + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", ipAddress=" + ipAddress +
                '}';
    }
}