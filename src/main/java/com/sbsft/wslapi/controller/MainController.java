package com.sbsft.wslapi.controller;

import com.sbsft.wslapi.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        surlService.getTotalLinkCount(req,model);
        return "index";
    }

    @CrossOrigin
    @GetMapping("/lmt")
    public String limit(HttpServletRequest req, Model model){
        surlService.getTotalLinkCount(req,model);
        return "limit";
    }

    @CrossOrigin
    @GetMapping("/{surl}")
    public String redirect(@PathVariable("surl") String surl){
        return "redirect:"+surlService.getOriginUrl(surl);
    }
}
