package com.sbsft.wslapi.domain;

import lombok.Data;

@Data
public class ShortUrl extends Common {
    private int idx;
    private String originUrl;
    private String shortUrl;
    private int hitCnt;
    private String regDate;

}
