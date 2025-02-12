package com.example.box_manager.viewModel;

import com.example.box_manager.model.Company;
import com.example.box_manager.service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileViewModel {

    private final CompanyService companyService;

    @Autowired
    public ProfileViewModel(CompanyService companyService) {
        this.companyService = companyService;
    }

    public Company getCompanyData(Company user) {
        if (user == null || user.getUid() == null) {
            return null;
        }
        try {
            return companyService.getUpdatedCompany(user.getUid());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Errore durante il recupero dei dati aziendali: " + e.getMessage());
            return null;
        }
    }

    public void logout(HttpServletRequest request) {
        companyService.logout(request);
    }
}
