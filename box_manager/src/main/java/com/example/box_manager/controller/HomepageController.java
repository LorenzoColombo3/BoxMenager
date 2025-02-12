package com.example.box_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.box_manager.viewModel.HomepageViewModel;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/homepage")
public class HomepageController {

    private final HomepageViewModel homepageViewModel;

    @Autowired
    public HomepageController(HomepageViewModel homepageViewModel) {
        this.homepageViewModel = homepageViewModel;
    }

    @GetMapping
    public String homepage(HttpServletRequest httpRequest, Model model) {
        return homepageViewModel.getHomepageData(httpRequest, model);
    }
}
