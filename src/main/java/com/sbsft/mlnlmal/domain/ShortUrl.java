package com.sbsft.mlnlmal.domain;

import lombok.Data;

@Data
public class ShortUrl extends Common {
    private int idx;
    private String originUrl;
    private String shortUrl;
    private int hitCnt;
    private String shareFlag;
    private String regDate;
    private String domain;
    private boolean isBan;

}