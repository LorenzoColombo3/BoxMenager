package com.example.box_manager.viewModel;

import com.example.box_manager.model.Company;  
import com.example.box_manager.model.Customer;
import com.example.box_manager.model.Unit;
import com.example.box_manager.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class CustomerDetailViewModel {

    private final CustomerService customerService;

    @Autowired
    public CustomerDetailViewModel(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Model getCustomerDetail(String uid, String customerId, HttpServletRequest httpRequest, Model model) {
        try {
            Customer customer = customerService.getCustomer(uid, customerId);
            model.addAttribute("cliente", customer);
            
            model.addAttribute("boxOccupati", customer.getUnita());

            Company user = (Company) httpRequest.getSession().getAttribute("user");
            model.addAttribute("aziendaNome", user.getNome());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return model;
    }

    public String deleteById(String uid, String customerId) {
        customerService.deleteCustomer(uid, customerId);
        return "redirect:/customers";
    }

    public List<Unit> getAvailableBoxes(String uid) throws InterruptedException, ExecutionException {
        List<Unit> availableUnits = customerService.getAvailableBoxes(uid);
        
        availableUnits.forEach(box -> {
            double altezzaInMetri = box.getProfondita() / 100.0;
            double larghezzaInMetri = box.getLarghezza() / 100.0;
            box.setSuperficie(altezzaInMetri * larghezzaInMetri);
        });
    	
        return availableUnits;
    }
    
    public boolean assignUnit(String uid, String customerId, String unitId, int dataInizio, int dataFine, int codice) {
        return customerService.assignUnitToCustomer(uid, customerId, unitId, dataInizio, dataFine, codice);
    }
    
    public boolean removeUnit(String uid, String customerId, String unitId) {
    	return customerService.removeUnitToCustomer(uid, customerId, unitId);
    }

}
