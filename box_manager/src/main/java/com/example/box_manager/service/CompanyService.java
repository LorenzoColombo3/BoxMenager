package com.example.box_manager.service;

import com.example.box_manager.model.Company;
import com.example.box_manager.repository.CompanyRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company getUpdatedCompany(String uid) throws ExecutionException, InterruptedException {
        return companyRepository.getCompanyDataByUserUid(uid).get();
    }

    public Company authenticate(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String id = decodedToken.getUid();
            Company company = companyRepository.getCompanyDataByUserUid(id).get();
            if (company != null) {
                company.setUid(id);
            }
            return company;
        } catch (FirebaseAuthException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveUserInSession(Company company) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            request.getSession().setAttribute("user", company);
        }
    }

    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }
}
