package com.sbsft.wslapi.mapper;

import com.sbsft.wslapi.domain.ShortUrl;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShortUrlMapper {

    int insertOriginUrl(ShortUrl su);

    void updateShoutUrl(ShortUrl su);

    String getOriginUrl(ShortUrl su);
}
