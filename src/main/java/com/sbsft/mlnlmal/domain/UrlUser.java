package com.sbsft.mlnlmal.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UrlUser extends ShortUrl {

    private int uidx;
    private String email;
    private String nick;
    private String password;
    private int type;
    private String regDate;

}