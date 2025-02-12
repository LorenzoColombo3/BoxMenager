package com.example.box_manager.viewModel;

import com.example.box_manager.model.Company;
import com.example.box_manager.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginViewModel {

    private final CompanyService companyService;

    @Autowired
    public LoginViewModel(CompanyService companyService) {
        this.companyService = companyService;
    }

    public Company authenticate(String token) {
        return companyService.authenticate(token);
    }

    public void saveUserInSession(Company company) {
        companyService.saveUserInSession(company);
    }
}
