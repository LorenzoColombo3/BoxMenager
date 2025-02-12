package com.example.box_manager.viewModel;

import com.example.box_manager.model.Company; 
import com.example.box_manager.model.Unit;
import com.example.box_manager.service.BoxService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.concurrent.ExecutionException;

@Component
public class BoxPageViewModel {

    private final BoxService boxService;

    @Autowired
    public BoxPageViewModel(BoxService boxService) {
        this.boxService = boxService;
    }

    public Model getBoxPage(HttpServletRequest httpRequest, Model model) throws InterruptedException, ExecutionException {
        Company user = (Company) httpRequest.getSession().getAttribute("user");
        Company updatedUser = boxService.getUpdatedCompany(user.getUid());

        httpRequest.getSession().setAttribute("user", updatedUser);

        model.addAttribute("boxList", boxService.getAllBoxes(user.getUid()).get());
        model.addAttribute("aziendaNome", user.getNome());

        return model;
    }

    public void addNewBox(String uid, Unit newUnit) {
        boxService.addNewBox(uid, newUnit);
    }
}
