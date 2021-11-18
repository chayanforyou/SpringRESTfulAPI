package me.chayan.springrestapi.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
}
