package me.chayan.springrestapi.controllers;


import me.chayan.springrestapi.models.User;
import me.chayan.springrestapi.payload.response.BaseResponse;
import me.chayan.springrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public BaseResponse create(@RequestBody User user){
       return userService.create(user);
    }
}
