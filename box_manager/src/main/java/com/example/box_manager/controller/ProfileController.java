package com.example.box_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.box_manager.model.Company;
import com.example.box_manager.viewModel.ProfileViewModel;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private ProfileViewModel profileViewModel;

    @GetMapping("/profile")
    public String viewProfile(HttpServletRequest httpRequest, Model model) {
        Company user = (Company) httpRequest.getSession().getAttribute("user");

        if (user == null || user.getUid() == null) {
            return "redirect:/login"; 
        }

        Company companyData = profileViewModel.getCompanyData(user);

        if (companyData != null) {
            model.addAttribute("aziendaNome", companyData.getNome());
            model.addAttribute("aziendaEmail", companyData.getEmail());
            model.addAttribute("aziendaTelefono", companyData.getTelefono());
            model.addAttribute("aziendaIndirizzo", companyData.getIndirizzo());
        } else {
            model.addAttribute("messaggioErrore", "Errore durante il recupero dei dati aziendali.");
        }

        return "profilepage";
    }
    
    @PostMapping("/logout")
    public String logout(HttpServletRequest httpRequest) {
        profileViewModel.logout(httpRequest);
        return "loginpage";
    }

    
}
