package com.example.box_manager.viewModel;

import com.example.box_manager.model.Company;
import com.example.box_manager.service.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.concurrent.ExecutionException;

@Component
public class HomepageViewModel {

    private final CompanyService companyService;

    @Autowired
    public HomepageViewModel(CompanyService companyService) {
        this.companyService = companyService;
    }

    public String getHomepageData(HttpServletRequest httpRequest, Model model) {
        Company user = (Company) httpRequest.getSession().getAttribute("user");
        if (user == null || user.getUid() == null) {
            return "redirect:/login";
        }

        try {
            Company companyData = companyService.getUpdatedCompany(user.getUid());
            model.addAttribute("aziendaNome", companyData.getNome());
        } catch (InterruptedException | ExecutionException e) {
            model.addAttribute("messaggioErrore", "Errore nel recupero dei dati.");
        }

        return "homepage";
    }
}
