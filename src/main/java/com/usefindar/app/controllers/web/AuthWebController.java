package com.usefindar.app.controllers.web;

import com.usefindar.app.entities.user.User;
import com.usefindar.app.models.request.LoginRequest;
import com.usefindar.app.models.request.SignupRequest;
import com.usefindar.app.models.response.system.ApiResponse;
import com.usefindar.app.service.authentication.AuthService;
import com.usefindar.app.service.books.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;

/**
 * UserController
 */
@Controller
@RequestMapping("/web")
public class AuthWebController {

    @Autowired
    private AuthService authService;
    @Autowired
    private BookService bookService;

    @GetMapping("/register")
    public String getSignupPage(Model model) {
        model.addAttribute("registerRequest", new SignupRequest());
        return "register_page";
    }

    @GetMapping("/signIn")
    public String getSignInPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute SignupRequest request) {
        var newUser = authService.createNewUser(request);
        return newUser == null ? "error_page" : "redirect:/web/signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@ModelAttribute LoginRequest request, Model model) {
        var loginUser = authService.loginUser(request);
        if(!isNull(loginUser)){
            var books =  bookService.getAllBooks();
            model.addAttribute("userLogin", loginUser.getEmail());
            model.addAttribute("books", books);
            return "books_page";
        }else{
            return "error_page";
        }
    }
}