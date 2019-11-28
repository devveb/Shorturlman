package com.sbsft.mlnlmal.service;

import com.sbsft.mlnlmal.domain.ShortUrl;
import com.sbsft.mlnlmal.domain.UrlUser;
import com.sbsft.mlnlmal.mapper.ShortUrlMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public UrlUser shrinkUrl(HttpServletRequest req) {
        UrlUser uu = null;
        if(req.getSession().getAttribute("user") == null){
            uu = new UrlUser();
        }else{
            uu = (UrlUser) req.getSession().getAttribute("user");
        }


        String origin = req.getParameter("orl").trim();
        uu.setOriginUrl(origin);
        uu.setChannel(req.getParameter("v"));
        return shinkProcess(uu);
    }

    public List<UrlUser> shrinkUrlList(HttpServletRequest req) {
        List<UrlUser> uuList = new ArrayList<>();
        UrlUser user = (UrlUser) req.getSession().getAttribute("user");
        for (String originUrl : urlSeparator(req)){
            if(originUrl.length() > 0){
                //ShortUrl su = new ShortUrl();
                UrlUser uu = new UrlUser();
                //uu.setUidx(user.getUidx());
                uu.setChannel(req.getParameter("v"));
                uu.setOriginUrl(originUrl);
                uuList.add(shinkProcess(uu));
            }
        }
        return uuList;

    }

    private String[] urlSeparator(HttpServletRequest req) {
        String urls = ","+req.getParameter("murl").trim().replace("\n",",");
        return urls.split(",");
    }


    private UrlUser shinkProcess(UrlUser su){
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
        //10진번 인덱스를 52진법으로
        String str = "";

        while (idx >= list.size()) {
            str = list.get(idx % list.size()) + str;
            idx = idx / list.size();
        }
        return "http://mlnlmal.ml/"+list.get(idx) + str;
    }

    private int getUrlIdx(String surl){
        //hash to idx
        //52진법에서 10진번 인덱스로
        int idx = 0;

        for (int i = 0; i < surl.length(); i++) {
            idx = idx + ((int) Math.pow(list.size(), i) * list.indexOf(String.valueOf(surl.charAt(surl.length() - i - 1))));
        }
        return idx;

    }

    public void getOriginUrl(String surl, Model model) {
        ShortUrl su = new ShortUrl();


        su.setIdx(getUrlIdx(surl));
        su = surlMapper.getOriginUrl(su);

//        if(su.getHitCnt() <= 255){
//            surlMapper.writeRedirectionLog(su);
//            return su.getOriginUrl();
//        }else{
//            return "/lmt";
//        }
        if(su != null){
            surlMapper.writeRedirectionLog(su);
            model.addAttribute("rurl",su.getOriginUrl());
            //return su.getOriginUrl();
        }else{

            surlMapper.writeTryLog(surl);
            model.addAttribute("rurl","http://mlnlmal.ml/notfound");
            //return "/notfound";
        }
    }

    public ShortUrl getTotalLinkCount() {
        ShortUrl su = new ShortUrl();
        su.setCount(surlMapper.getTotalLinkCount());
        su.setCode(200);
        return su;

    }

    public int registUser(HttpServletRequest req) {
        System.out.println(req.getParameterNames());
        UrlUser uu = new UrlUser();
        int code = 999;

        try {
            uu.setEmail(req.getParameter("usrmail"));
            uu.setPassword(req.getParameter("usrpw"));
            uu.setType(Integer.parseInt(req.getParameter("usrtyp")));
            surlMapper.registUser(uu);
            uu.setCode(200);
            code = 200;
        }catch (Exception e){
            e.printStackTrace();
            uu.setCode(400);
        }

        return code;
    }

    public int loginUser(HttpServletRequest req) {
        int code = 999;
        UrlUser uu = new UrlUser();

        try{
            uu.setEmail(req.getParameter("werewp"));
            uu.setPassword(req.getParameter("nwebvie"));
            uu = surlMapper.getUserInfo(uu);
            code = 200;
            if(uu != null){
                System.out.println("login");
                HttpSession session = req.getSession();
                session.setAttribute("user",uu);
            }else{
                System.out.println("no user");
            }
        }catch (Exception e){
            e.printStackTrace();
            code = 404;
        }

        return code;
    }

    public int logoutUser(HttpServletRequest req) {
        int code = 999;
        HttpSession session = req.getSession();
        session.invalidate();
        return 200;
    }

    public ShortUrl getTotalRedirectionCount() {
        ShortUrl su = new ShortUrl();
        su.setCount(surlMapper.getTotalRedirectionCount());
        su.setCode(200);
        return su;
    }
}
