package com.usefindar.app.models.response;

import com.usefindar.app.entities.user.User;
import lombok.Data;

@Data
public class LoginResponse extends User {
    private String token;
}
