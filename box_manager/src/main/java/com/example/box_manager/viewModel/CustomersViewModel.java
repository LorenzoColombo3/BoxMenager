package com.example.box_manager.viewModel;

import com.example.box_manager.model.Company;
import com.example.box_manager.model.Customer;
import com.example.box_manager.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.concurrent.ExecutionException;

@Component
public class CustomersViewModel {

    private final CustomerService customerService;

    @Autowired
    public CustomersViewModel(CustomerService customerService) {
        this.customerService = customerService;
    }

    public String getCustomersPage(HttpServletRequest httpRequest, Model model) {
        try {
            Company user = (Company) httpRequest.getSession().getAttribute("user");
            if (user == null || user.getUid() == null) {
                return "redirect:/login";
            }

            Company updatedUser = customerService.getUpdatedCompany(user.getUid());
            httpRequest.getSession().setAttribute("user", updatedUser);

            model.addAttribute("aziendaNome", user.getNome());
            model.addAttribute("clienti", customerService.getCustomerList(user));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return "customerpage";
    }

    public String addCustomer(String nome, String cognome, String citta, String email, HttpServletRequest httpRequest) {
        Company user = (Company) httpRequest.getSession().getAttribute("user");
        if (user == null || user.getUid() == null) {
            return "redirect:/login";
        }

        Customer newCustomer = new Customer();
        newCustomer.setNome(nome);
        newCustomer.setCognome(cognome);
        newCustomer.setCitta(citta);
        newCustomer.setEmail(email);

        customerService.addCustomer(user.getUid(), newCustomer);

        try {
            Company updatedUser = customerService.getUpdatedCompany(user.getUid());
            httpRequest.getSession().setAttribute("user", updatedUser);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return "redirect:/customers";
    }
}
