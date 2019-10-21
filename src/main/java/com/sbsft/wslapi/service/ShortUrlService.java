package com.sbsft.wslapi.service;

import com.sbsft.wslapi.domain.ShortUrl;
import com.sbsft.wslapi.mapper.ShortUrlMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public ShortUrl shrk(HttpServletRequest req) {
        ShortUrl su = new ShortUrl();
        String origin = req.getParameter("orl").trim();
        su.setOriginUrl(origin);
        return shinkProcess(su);
    }

    public List<ShortUrl> mkmshrt(HttpServletRequest req) {
        List<ShortUrl> suList = new ArrayList<>();

        for (String originUrl : urlSeparator(req)){
            if(originUrl.length() > 0){
                ShortUrl su = new ShortUrl();
                su.setOriginUrl(originUrl);
                suList.add(shinkProcess(su));
            }
        }
        return suList;

    }

    private String[] urlSeparator(HttpServletRequest req) {
        String urls = ","+req.getParameter("murl").trim().replace("\n",",");
        return urls.split(",");
    }


    private ShortUrl shinkProcess(ShortUrl su){
        su.setMessage(null);
        if(!su.getOriginUrl().startsWith("http://")&&!su.getOriginUrl().startsWith("https://")){
            su.setMessage("can not find 'http://' or 'https://' 를 찾을수 없습니다.");
            su.setCode(506);
            return su;
        }

        if (su.getOriginUrl().contains("http://mlnlmal.ml") ||su.getOriginUrl().contains("mlnlmal.ml")||su.getOriginUrl().equals("")){
            su.setMessage("'mlnlmal.ml'은 URL에 포함될 수 없습니다.");
            su.setCode(507);
            return su;
        }
        su.setOriginUrl(su.getOriginUrl());

        if(surlMapper.cntOriginUrl(su) < 1){
            surlMapper.insertOriginUrl(su);
            su.setShortUrl(shrinkUrl(su.getIdx()));
            surlMapper.updateshortUrl(su);
            su.setMessage("succ");
            su.setCode(200);
            return su;
        }else{
            su = surlMapper.getShortUrlByOriginUrl(su);
            su.setMessage("reuse");
            su.setCode(201);
            return su;
        }

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
        su = surlMapper.getOriginUrl(su);
        if(su.getHitCnt() <= 255){
            return su.getOriginUrl();
        }else{
            return "/lmt";
        }

    }

    public void getTotalLinkCount(HttpServletRequest req, Model model) {
        int cnt = surlMapper.getTotalLinkCount();
        model.addAttribute("totalCnt",cnt);
    }


}



	
