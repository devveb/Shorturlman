package com.sbsft.wslapi.controller;

import com.sbsft.wslapi.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final ShortUrlService surlService;

    public ApiController(ShortUrlService surlService) {

        this.surlService = surlService;
    }

    @CrossOrigin
    @GetMapping("/mkshrt")
    @ResponseBody
    public String mkshrt(HttpServletRequest req){
        return surlService.shrk(req);
    }
}


