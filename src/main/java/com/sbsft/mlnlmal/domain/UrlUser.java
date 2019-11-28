package com.sbsft.mlnlmal.domain;

import lombok.Data;

@Data
public class UrlUser extends ShortUrl {
    private int uidx;
    private String email;
    private String password;
    private int type;
    private String regDate;

}
