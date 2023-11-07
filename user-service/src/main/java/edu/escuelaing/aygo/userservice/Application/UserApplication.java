package edu.escuelaing.aygo.userservice.Application;

import java.util.List;

import edu.escuelaing.aygo.userservice.Application.Contracts.IUserApplication;
import edu.escuelaing.aygo.userservice.DTOs.RequestCreateUser;
import edu.escuelaing.aygo.userservice.DTOs.RequestUpdateUser;
import edu.escuelaing.aygo.userservice.Domain.User;
import edu.escuelaing.aygo.userservice.Repository.UserRepository;
import edu.escuelaing.aygo.userservice.Repository.Contracts.IUserRepository;

public class UserApplication implements IUserApplication{

    private IUserRepository userRepository = new UserRepository();
    public UserApplication() {}
    
    @Override
    public User getUserById(String userId) throws Exception {
        try {
            return userRepository.getUserById(userId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        try {
            return userRepository.getAllUsers();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User createUser(RequestCreateUser newUser) throws Exception {
        try {
            return userRepository.createUser(newUser);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String deleteUserById(String userId) throws Exception {
        try {
            return userRepository.deleteUserById(userId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User updateUser(RequestUpdateUser updateUser) throws Exception {
        try {
            return userRepository.updateUser(updateUser);
        } catch (Exception e) {
            throw e;
        }
    }
    
}
