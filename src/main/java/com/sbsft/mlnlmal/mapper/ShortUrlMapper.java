package com.sbsft.mlnlmal.mapper;

import com.sbsft.mlnlmal.domain.Ourlink;
import com.sbsft.mlnlmal.domain.ShortUrl;
import com.sbsft.mlnlmal.domain.UrlUser;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface ShortUrlMapper {

    int insertOriginUrl(ShortUrl su);

    void updateshortUrl(ShortUrl su);

    UrlUser getOriginUrl(UrlUser su);

    int cntOriginUrl(ShortUrl su);


    UrlUser getShortUrlByOriginUrl(ShortUrl su);

    int getTotalLinkCount();

    void registUser(UrlUser uu);

    UrlUser getUserInfo(UrlUser uu);

    void writeRedirectionLog(ShortUrl su);

    int getTotalRedirectionCount();

    void writeTryLog(String tryUrl);

    void insertLinkShareInfo(Ourlink ourlink);

    List<ShortUrl> getBanList();

    void insertBanTryList(UrlUser su);

    int checkEmailDup(String req);

    List<ShortUrl> getUrlListByUser(int userIdx);

    HashMap<String, Object> getLinkStatByUser(int userIdx);

    ShortUrl getLastRedirectLink();
}
