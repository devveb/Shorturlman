package com.sbsft.mlnlmal.mapper;

import com.sbsft.mlnlmal.domain.Ourlink;
import com.sbsft.mlnlmal.domain.ShortUrl;
import com.sbsft.mlnlmal.domain.UrlUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OUrlMapper {

    List<Ourlink> getOurlList();
}
