package com.sbsft.wslapi.controller;

import com.sbsft.wslapi.domain.ShortUrl;
import com.sbsft.wslapi.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final ShortUrlService surlService;

    public ApiController(ShortUrlService surlService) {
        this.surlService = surlService;
    }

    @CrossOrigin
    @PostMapping("/mkshrt")
    @ResponseBody
    public ShortUrl mkshrt(HttpServletRequest req){
        return surlService.shrk(req);
    }

    @CrossOrigin
    @PostMapping("/mkmshrt")
    @ResponseBody
    public List<ShortUrl> mkmshrt(HttpServletRequest req){
        return surlService.mkmshrt(req);
    }

    @CrossOrigin
    @GetMapping("/cntlink")
    @ResponseBody
    public ShortUrl cntlink(HttpServletRequest req){
        return surlService.getTotalLinkCount();
    }

}
