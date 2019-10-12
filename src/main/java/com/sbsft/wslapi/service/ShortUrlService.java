package com.sbsft.wslapi.service;

import com.sbsft.wslapi.domain.ShortUrl;
import com.sbsft.wslapi.mapper.ShortUrlMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class ShortUrlService {

    private ShortUrlMapper surlMapper;

    List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    public ShortUrlService(ShortUrlMapper surlMapper) {
        this.surlMapper = surlMapper;
    }

    //String[] exs = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @Transactional
    public String shrk(HttpServletRequest req) {
        ShortUrl su = new ShortUrl();
        su.setOriginUrl(req.getParameter("orl"));
        surlMapper.insertOriginUrl(su);
        su.setShoutUrl(shrinkUrl(su.getIdx()));
        surlMapper.updateShoutUrl(su);
        return su.getShoutUrl();
    }

    private String shrinkUrl(int idx){

        String str = "";


        while (idx >= list.size()) {
            str = list.get(idx % list.size()) + str;
            idx = idx / list.size();
        }
        return "http://mlnlmal.ml/"+list.get(idx) + str;
    }

    private int getUrlIdx(String surl){
        int idx = 0;

        for (int i = 0; i < surl.length(); i++) {
            idx = idx + ((int) Math.pow(list.size(), i) * list.indexOf(String.valueOf(surl.charAt(surl.length() - i - 1))));
        }
        return idx;

    }

    public String getOriginUrl(String surl) {
        ShortUrl su = new ShortUrl();
        su.setIdx(getUrlIdx(surl));
        String orul = surlMapper.getOriginUrl(su);
        return orul;
    }
}



	
