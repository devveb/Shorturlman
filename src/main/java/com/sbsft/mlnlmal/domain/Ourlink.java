package com.sbsft.mlnlmal.domain;

import lombok.Data;

@Data
public class Ourlink extends ShortUrl {
    private int oidx;
    private int linkIdx;
    private String title;
    private String desc;
    private int shareYN;
    private int likeCnt;
    private String rdate;
    private String mdate;
}