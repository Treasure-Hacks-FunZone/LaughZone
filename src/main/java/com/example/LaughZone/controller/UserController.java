package com.example.LaughZone.controller;

import com.example.LaughZone.service.YouTubeService;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    YouTubeService youTubeService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) throws IOException, GeneralSecurityException{
        final List<SearchResult> searchResults = youTubeService.search(query);
        model.addAttribute("searchResults", searchResults);
        return "index";
    }
}
