package com.sbsft.mlnlmal.controller;

import com.sbsft.mlnlmal.domain.Ourlink;
import com.sbsft.mlnlmal.domain.ShortUrl;
import com.sbsft.mlnlmal.domain.UrlUser;
import com.sbsft.mlnlmal.service.OurlinkService;
import com.sbsft.mlnlmal.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/ourlnk")
public class UrlinkController {

    private final OurlinkService ourlService;

    public UrlinkController(OurlinkService ourlService) {
        this.ourlService = ourlService;
    }

    @CrossOrigin
    @PostMapping("/list")
    @ResponseBody
    public List<Ourlink> shareUrl(HttpServletRequest req){
        return ourlService.shareUrl(req);
    }

}
