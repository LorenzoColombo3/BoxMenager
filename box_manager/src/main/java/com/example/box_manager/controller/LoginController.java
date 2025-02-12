package com.example.box_manager.controller;

import com.example.box_manager.model.Company;
import com.example.box_manager.viewModel.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private LoginViewModel loginViewModel;
    
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        String token = request.get("token");
        try {
            Company company = loginViewModel.authenticate(token);
            if (company != null) {
                loginViewModel.saveUserInSession(company);
                return ResponseEntity.ok(Map.of("success", true));
            } else {
                return ResponseEntity.status(401).body(Map.of("success", false, "message", "Utente non trovato."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("success", false, "message", "Token invalido."));
        }
    }
}

