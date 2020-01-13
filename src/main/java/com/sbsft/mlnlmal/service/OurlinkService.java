package com.sbsft.mlnlmal.service;

import com.sbsft.mlnlmal.domain.Ourlink;
import com.sbsft.mlnlmal.domain.ShortUrl;
import com.sbsft.mlnlmal.domain.UrlUser;
import com.sbsft.mlnlmal.mapper.OUrlMapper;
import com.sbsft.mlnlmal.mapper.ShortUrlMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OurlinkService {

    private OUrlMapper ourlMapper;
    
    public OurlinkService(OUrlMapper ourlMapper) {
        this.ourlMapper = ourlMapper;
    }


    public List<Ourlink> shareUrl(HttpServletRequest req) {
        return ourlMapper.getOurlList();

    }
}
