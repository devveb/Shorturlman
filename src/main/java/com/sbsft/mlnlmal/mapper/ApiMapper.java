package com.sbsft.mlnlmal.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApiMapper {

    void insertToWordList(String word);

    List<Map<String, Object>> selectRecentList();
}
