package com.example.box_manager.service;

import com.example.box_manager.model.Company;
import com.example.box_manager.model.Customer;
import com.example.box_manager.model.CustomerUnit;
import com.example.box_manager.model.Unit;
import com.example.box_manager.repository.CompanyRepository;
import com.example.box_manager.repository.CustomerRepository;
import com.example.box_manager.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final UnitRepository unitRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CompanyRepository companyRepository, UnitRepository unitRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.unitRepository = unitRepository;
    }

    public Company getUpdatedCompany(String uid) throws ExecutionException, InterruptedException {
        return companyRepository.getCompanyDataByUserUid(uid).get();
    }

    public Customer getCustomer(String uid, String customerId) throws ExecutionException, InterruptedException {
        return customerRepository.getCustomersDataByUserUid(uid, customerId).get();
    }

    public void deleteCustomer(String uid, String customerId) {
        customerRepository.removeCustomer(uid, customerId);
    }

    public List<Unit> getAvailableBoxes(String uid) throws ExecutionException, InterruptedException {
        List<Unit> availableUnits = unitRepository.getDepositoUnits(uid)
                .thenApply(boxes -> boxes.stream()
                        .filter(box -> !box.isOccupato())
                        .toList())
                .get();

        return availableUnits;
    }

    public List<Customer> getCustomerList(Company user) {
        List<Customer> customersList = new ArrayList<>();
        for (String id : user.getCliente()) {
            try {
                customersList.add(getCustomer(user.getUid(), id));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return customersList;
    }

    public void addCustomer(String uid, Customer newCustomer) {
        customerRepository.addNewCustomer(uid, newCustomer);
    }
    
    public boolean assignUnitToCustomer(String uid, String customerId, String unitId, int dataInizio, int dataFine, int codice) {
        try {
            Customer customer = customerRepository.getCustomersDataByUserUid(uid, customerId).get();
            if (customer == null) {
                System.out.println("Cliente non trovato.");
                return false;
            }
            
            CustomerUnit unit = new CustomerUnit(unitId, codice, dataInizio, dataFine);
            unitRepository.assegnaCliente(uid, unitId, Integer.parseInt(customerId));
            customerRepository.addCustomerUnit(uid, customerId, unit);
                        
            System.out.println("Unità assegnata con successo.");
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean removeUnitToCustomer(String uid, String customerId, String unitId) {
    	try {
            Customer customer = customerRepository.getCustomersDataByUserUid(uid, customerId).get();
            if (customer == null) {
                System.out.println("Cliente non trovato.");
                return false;
            }
            
            unitRepository.liberaBox(uid, unitId);
            customerRepository.removeCustomerUnit(uid, customerId, unitId);
                        
            System.out.println("Unità assegnata con successo.");
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

}
