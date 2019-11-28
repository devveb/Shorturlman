package com.sbsft.mlnlmal.mapper;

import com.sbsft.mlnlmal.domain.ShortUrl;
import com.sbsft.mlnlmal.domain.UrlUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShortUrlMapper {

    int insertOriginUrl(ShortUrl su);

    void updateshortUrl(ShortUrl su);

    ShortUrl getOriginUrl(ShortUrl su);

    int cntOriginUrl(ShortUrl su);


    UrlUser getShortUrlByOriginUrl(ShortUrl su);

    int getTotalLinkCount();

    void registUser(UrlUser uu);

    UrlUser getUserInfo(UrlUser uu);

    void writeRedirectionLog(ShortUrl su);

    int getTotalRedirectionCount();

    void writeTryLog(String tryUrl);
}