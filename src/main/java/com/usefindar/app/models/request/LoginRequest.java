package com.usefindar.app.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Password should not be blank")
    private String password;
    @NotBlank(message = "Email should not be blank")
    private String email;
}
