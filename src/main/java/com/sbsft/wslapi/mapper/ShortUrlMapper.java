package com.sbsft.wslapi.mapper;

import com.sbsft.wslapi.domain.ShortUrl;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShortUrlMapper {

    int insertOriginUrl(ShortUrl su);

    void updateshortUrl(ShortUrl su);

    ShortUrl getOriginUrl(ShortUrl su);

    int cntOriginUrl(ShortUrl su);


    ShortUrl getShortUrlByOriginUrl(ShortUrl su);

    int getTotalLinkCount();
}
