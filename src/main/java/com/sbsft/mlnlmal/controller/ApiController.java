package com.sbsft.mlnlmal.controller;

import com.sbsft.mlnlmal.domain.ShortUrl;
import com.sbsft.mlnlmal.domain.UrlUser;
import com.sbsft.mlnlmal.service.ShortUrlService;
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
    @PostMapping("/shlk")
    @ResponseBody
    public UrlUser shrinkUrl(HttpServletRequest req){
        return surlService.shrinkUrl(req);
    }

    @CrossOrigin
    @PostMapping("/shlks")
    @ResponseBody
    public List<UrlUser> shrinkUrlList(HttpServletRequest req){
        return surlService.shrinkUrlList(req);
    }

    @CrossOrigin
    @GetMapping("/cntlink")
    @ResponseBody
    public ShortUrl cntLinks(HttpServletRequest req){
        return surlService.getTotalLinkCount();
    }

    @CrossOrigin
    @GetMapping("/cntrd")
    @ResponseBody
    public ShortUrl cntRedirection(HttpServletRequest req){
        return surlService.getTotalRedirectionCount();
    }

    @CrossOrigin
    @PostMapping("/usreg")
    @ResponseBody
    public int userRegist(HttpServletRequest req){
        return surlService.registUser(req);
    }

    @CrossOrigin
    @PostMapping("/usrog")
    @ResponseBody
    public int userLogin(HttpServletRequest req){
        return surlService.loginUser(req);
    }
    @CrossOrigin
    @GetMapping("/usrgo")
    @ResponseBody
    public int userLogout(HttpServletRequest req){
        return surlService.logoutUser(req);
    }

}
