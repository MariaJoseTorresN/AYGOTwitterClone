package edu.escuelaing.aygo.userservice.Repository.Contracts;

import java.util.List;

import edu.escuelaing.aygo.userservice.DTOs.RequestCreateUser;
import edu.escuelaing.aygo.userservice.DTOs.RequestUpdateUser;
import edu.escuelaing.aygo.userservice.Domain.User;

public interface IUserRepository {
    public User getUserById(String userId);

    public List<User> getAllUsers();

    public User createUser(RequestCreateUser newUser);

    public String deleteUserById(String userId);

    public User updateUser(RequestUpdateUser updateUser);
}
