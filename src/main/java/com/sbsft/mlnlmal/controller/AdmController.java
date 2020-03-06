package com.sbsft.mlnlmal.controller;

import com.sbsft.mlnlmal.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/adm")
public class AdmController {


    private final ShortUrlService surlService;

    public AdmController(ShortUrlService surlService) {
        this.surlService = surlService;
    }

    @PostMapping("/gmp")
    public String goToMyPage(HttpServletRequest req,Model model){
        surlService.getLinkStatByUser(req,model);
        return "mypage";
    }


}
