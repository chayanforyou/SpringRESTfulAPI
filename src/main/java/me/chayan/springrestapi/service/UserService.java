package me.chayan.springrestapi.service;


import me.chayan.springrestapi.models.User;
import me.chayan.springrestapi.payload.response.BaseResponse;
import me.chayan.springrestapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public BaseResponse create(User user) {
        try {
            userRepository.save(user);
            return new BaseResponse(true, "Successfully registered");
        } catch (Exception e) {
            return new BaseResponse(false, "Something went wrong");
        }
    }
}
