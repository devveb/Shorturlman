package com.sbsft.mlnlmal.controller;

import com.sbsft.mlnlmal.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller

public class MainController {


    private final ShortUrlService surlService;

    public MainController(ShortUrlService surlService) {
        this.surlService = surlService;
    }

    @CrossOrigin
    @GetMapping("/")
    public String main(HttpServletRequest req, Model model){
        return "index";
    }

    @CrossOrigin
    @GetMapping("/ourlink")
    public String ourlink(HttpServletRequest req, Model model){
        return "index";
    }

    @CrossOrigin
    @GetMapping("/lmt")
    public String limit(HttpServletRequest req, Model model){
        return "limit";
    }

    @CrossOrigin
    @GetMapping("/{surl}")
    public String redirect(@PathVariable("surl") String surl, Model model){
        surlService.getOriginUrl(surl,model);
        return "redirect";
//        return "redirect:"+surlService.getOriginUrl(surl,model);
    }

    @GetMapping("/policy")
    public String getPolicy(){
        return "policy";
    }

    @GetMapping("/notfound")
    public String getNotfound(){
        return "notfound";
    }


}
