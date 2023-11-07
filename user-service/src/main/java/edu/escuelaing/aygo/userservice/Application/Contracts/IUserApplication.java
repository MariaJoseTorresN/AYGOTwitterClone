package edu.escuelaing.aygo.userservice.Application.Contracts;

import java.util.List;

import edu.escuelaing.aygo.userservice.DTOs.RequestCreateUser;
import edu.escuelaing.aygo.userservice.DTOs.RequestUpdateUser;
import edu.escuelaing.aygo.userservice.Domain.User;

public interface IUserApplication {
    
    public User getUserById(String userId) throws Exception;

    public List<User> getAllUsers() throws Exception;

    public User createUser(RequestCreateUser newUser) throws Exception;

    public String deleteUserById(String userId) throws Exception;

    public User updateUser(RequestUpdateUser updateUser) throws Exception;
}
