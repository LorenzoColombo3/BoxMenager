package com.example.box_manager.service;

import com.example.box_manager.model.Company; 
import com.example.box_manager.model.Unit;
import com.example.box_manager.repository.CompanyRepository;
import com.example.box_manager.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class BoxService {

    private final UnitRepository unitRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public BoxService(UnitRepository unitRepository, CompanyRepository companyRepository) {
        this.unitRepository = unitRepository;
        this.companyRepository = companyRepository;
    }

    public Company getUpdatedCompany(String uid) throws ExecutionException, InterruptedException {
        return companyRepository.getCompanyDataByUserUid(uid).get();
    }

    public CompletableFuture<List<Unit>> getAllBoxes(String uid) {
        return unitRepository.getDepositoUnits(uid);
    }

    public void addNewBox(String uid, Unit newUnit) {
        unitRepository.addNewBox(uid, newUnit);
    }
}
