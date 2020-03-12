package com.sbsft.mlnlmal.service;

import com.sbsft.mlnlmal.domain.Ourlink;
import com.sbsft.mlnlmal.domain.ShortUrl;
import com.sbsft.mlnlmal.domain.UrlUser;
import com.sbsft.mlnlmal.mapper.ShortUrlMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        List<ShortUrl> banList = surlMapper.getBanList();
        UrlUser uu = null;
        if(req.getSession().getAttribute("user") == null){
            uu = new UrlUser();
        }else{
            uu = (UrlUser) req.getSession().getAttribute("user");
        }
        //ShortUrl su = new ShortUrl();

        String origin = req.getParameter("orl").trim();
        uu.setOriginUrl(origin);
        uu.setChannel(req.getParameter("v"));
        uu = shinkProcess(uu,banList);
        if(req.getParameter("s").equals("1")) linkShareProcess(uu);
        return uu;
    }

    public List<UrlUser> shrinkUrlList(HttpServletRequest req) {
        List<UrlUser> uuList = new ArrayList<>();
        List<ShortUrl> banList = surlMapper.getBanList();


        UrlUser user = (UrlUser) req.getSession().getAttribute("user");

        for (String originUrl : urlSeparator(req)){
                UrlUser uu = new UrlUser();

                if(uuList.size() >=1){
                    if(user == null){
                        uu.setUidx(0);
                        uu.setMessage("Multi link job 위해서 Sign in 해주세요.");
                        uuList.add(uu);
                    }else{
                        uu.setUidx(user.getUidx());
                        uu.setOriginUrl(originUrl);
                        uu.setChannel(req.getParameter("v"));
                        uu = shinkProcess(uu,banList);
                        uuList.add(uu);

                        if(req.getParameter("s").equals("1"))
                            linkShareProcess(uu);
                    }
                }else{
                    if(user == null){
                        uu.setUidx(0);
                    }else{
                        uu.setUidx(user.getUidx());
                    }

                    uu.setOriginUrl(originUrl);
                    uu.setChannel(req.getParameter("v"));
                    uu = shinkProcess(uu,banList);
                    uuList.add(uu);

                    if(req.getParameter("s").equals("1"))
                        linkShareProcess(uu);
                }
        }
        return uuList;

    }

    private UrlUser banProcess(UrlUser uu, List<ShortUrl> banList) {
        for(ShortUrl banDom : banList){
            if(uu.getOriginUrl().contains(banDom.getDomain())) {
                uu.setBan(false);
            }
        }
        return uu;
    }

    private void linkShareProcess(ShortUrl uu) {

        Ourlink ourlink = new Ourlink();
        ourlink.setLinkIdx(uu.getIdx());

        Document doc = null;
        try {
            doc = Jsoup.connect(uu.getOriginUrl()).get();
            Elements metaOgDesc = doc.select("meta[og:description]");

            if(doc.title() != ""){
                ourlink.setTitle(doc.title());
            }else{
                ourlink.setTitle("Pandora Box(Unknown)");
            }
            ourlink.setDesc(metaOgDesc.toString());
        } catch (IOException e) {
            e.printStackTrace();
            ourlink.setTitle("Untitle");
            ourlink.setDesc("Unknown");
        }

        surlMapper.insertLinkShareInfo(ourlink);
    }

    private String[] urlSeparator(HttpServletRequest req) {
        String urls = req.getParameter("murl").trim().replace("\n",",");
        return urls.split(",");
    }


    private UrlUser shinkProcess(UrlUser su,List<ShortUrl> banList){

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

        if(su.getUidx() == 0){
            for(ShortUrl ban : banList){
                if(su.getOriginUrl().contains(ban.getDomain())){
                    surlMapper.insertBanTryList(su);
                    su.setMessage(ban.getReason());
                    su.setCode(508);
                    return su;
                }
            }
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
            su.setIdx(su.getIdx());
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

    public String getOriginUrl(String surl, Model model) {

        int nonloginLimitation = 128;
        UrlUser uu = new UrlUser();

        uu.setIdx(getUrlIdx(surl));
        uu = surlMapper.getOriginUrl(uu);

        if(uu != null && uu.getUidx() == 0 && uu.getHitCnt() > nonloginLimitation){
            surlMapper.writeTryLog(surl);
            return "/lmt";
        }else if((uu != null && uu.getUidx() == 0 && uu.getHitCnt() <= nonloginLimitation)||(uu != null && uu.getUidx() != 0)){
            surlMapper.writeRedirectionLog(uu);
            return uu.getOriginUrl();
        }else{
            surlMapper.writeTryLog(surl);
            return "/notfound";
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
            uu.setEmail(req.getParameter("eml"));
            uu.setPassword(req.getParameter("pw"));
            uu.setNick(req.getParameter("nn"));
            //uu.setType(Integer.parseInt(req.getParameter("usrtyp")));
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

    public int userDupCheck(HttpServletRequest req) {
        String eml = req.getParameter("eml");
        return surlMapper.checkEmailDup(eml);
    }

    public void getLinkStatByUser(HttpServletRequest req, Model model) {
        int userIdx = Integer.parseInt(req.getParameter("dbwjdkdlel"));
        List<ShortUrl> urlList = surlMapper.getUrlListByUser(userIdx);
        HashMap<String,Object> linkStat = surlMapper.getLinkStatByUser(userIdx);

        model.addAttribute("urlList",urlList);
        model.addAttribute("linkStat",linkStat);

    }

    public ShortUrl getLastRedirectLink() {
        ShortUrl url = null;
        try{
            url=surlMapper.getLastRedirectLink();
            url.setCode(200);
        }catch (Exception e){
            e.printStackTrace();
            url.setCode(999);
            url.setMessage(e.getMessage());
            url.setShortUrl("Ask Admin");
        }

        return url;
    }
}
