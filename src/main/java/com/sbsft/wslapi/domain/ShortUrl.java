package com.sbsft.wslapi.domain;

import lombok.Data;

@Data
public class ShortUrl {
    private int idx;
    private String originUrl;
    private String shoutUrl;
    private String hitCnt;
    private String regDate;


}
