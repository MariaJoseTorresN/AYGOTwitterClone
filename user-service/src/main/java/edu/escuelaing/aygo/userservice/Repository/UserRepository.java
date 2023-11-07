package edu.escuelaing.aygo.userservice.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import edu.escuelaing.aygo.userservice.DTOs.RequestCreateUser;
import edu.escuelaing.aygo.userservice.DTOs.RequestUpdateUser;
import edu.escuelaing.aygo.userservice.Domain.User;
import edu.escuelaing.aygo.userservice.Exception.ExistingUserException;
import edu.escuelaing.aygo.userservice.Exception.UserNotFoundException;
import edu.escuelaing.aygo.userservice.Repository.Contracts.IUserRepository;

public class UserRepository implements IUserRepository {

    private DynamoDBMapper dynamoDBMapper;

    private void initDynamoDB() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }

    @Override
    public User getUserById(String userId) {
        initDynamoDB();
        User user = dynamoDBMapper.load(User.class, userId);
        if (user == null) {
            throw new UserNotFoundException("No se encontró un usuario con el Id: " + userId);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        initDynamoDB();
        List<User> users = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
        return users;
    }

    @Override
    public User createUser(RequestCreateUser newUser) {
        initDynamoDB();
        User user = new User();
        LocalDate actualDate = LocalDate.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("ddMMyy");
        String dateString = actualDate.format(formatDate);
        String userId = newUser.getUserName() + dateString;
        User searchUser = dynamoDBMapper.load(User.class, userId);
        if (searchUser != null) {
            throw new ExistingUserException("Ya existe un usuario con el id: " + userId);
        }
        user.setUserId(userId);
        user.setUserName(newUser.getUserName());
        user.setUserEmail(newUser.getUserEmail());
        dynamoDBMapper.save(user);
        return user;
    }

    @Override
    public String deleteUserById(String userId) {
        initDynamoDB();
        User user = dynamoDBMapper.load(User.class, userId);
        if (user != null) {
            dynamoDBMapper.delete(user);
            return "Usuario eliminado correctamente.";
        } else {
            throw new UserNotFoundException("No se encontró un usuario con el ID: " + userId);
        }
    }

    @Override
    public User updateUser(RequestUpdateUser updateUser) {
        initDynamoDB();
        User modifiedUser = dynamoDBMapper.load(User.class, updateUser.getUserId());
        if (modifiedUser == null) {
            throw new UserNotFoundException("No se encontró un usuario con el ID: " + updateUser.getUserId());
        }
        dynamoDBMapper.delete(modifiedUser);
        User user = new User();
        user.setUserId(updateUser.getUserId());
        user.setUserName(updateUser.getUserName());
        user.setUserEmail(updateUser.getUserEmail());
        dynamoDBMapper.save(user);
        return user;
    }

}
