package com.example.securitywithhttpsession.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Objects;

/**
 * 
 * @TableName users
 */
public class User {
    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private Integer isAccountnonexpired;

    /**
     * 
     */
    private Integer isAccountnonlocked;

    /**
     * 
     */
    private Integer isCredentialsnonexpired;

    /**
     * 
     */
    private Integer isEnabled;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String phoneNumber;
    private List<SimpleGrantedAuthority> authorities;

    /**
     * 
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     */
    public Integer getIsAccountnonexpired() {
        return isAccountnonexpired;
    }

    /**
     * 
     */
    public void setIsAccountnonexpired(Integer isAccountnonexpired) {
        this.isAccountnonexpired = isAccountnonexpired;
    }

    public Integer getIsAccountnonlocked() {
        return isAccountnonlocked;
    }

    public void setIsAccountnonlocked(Integer isAccountnonlocked) {
        this.isAccountnonlocked = isAccountnonlocked;
    }

    public Integer getIsCredentialsnonexpired() {
        return isCredentialsnonexpired;
    }

    public void setIsCredentialsnonexpired(Integer isCredentialsnonexpired) {
        this.isCredentialsnonexpired = isCredentialsnonexpired;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(Integer userId, String username, String password, Integer isAccountnonexpired, Integer isAccountnonlocked, Integer isCredentialsnonexpired, Integer isEnabled, String email, String phoneNumber, List<SimpleGrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isAccountnonexpired = isAccountnonexpired;
        this.isAccountnonlocked = isAccountnonlocked;
        this.isCredentialsnonexpired = isCredentialsnonexpired;
        this.isEnabled = isEnabled;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }

    public User() {
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(isAccountnonexpired, user.isAccountnonexpired) && Objects.equals(isAccountnonlocked, user.isAccountnonlocked) && Objects.equals(isCredentialsnonexpired, user.isCredentialsnonexpired) && Objects.equals(isEnabled, user.isEnabled) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, isAccountnonexpired, isAccountnonlocked, isCredentialsnonexpired, isEnabled, email, phoneNumber, authorities);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isAccountnonexpired=" + isAccountnonexpired +
                ", isAccountnonlocked=" + isAccountnonlocked +
                ", isCredentialsnonexpired=" + isCredentialsnonexpired +
                ", isEnabled=" + isEnabled +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}