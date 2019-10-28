package com.sbsft.wslapi.domain;

import lombok.Data;

@Data
public class UrlUser extends Common {
    private int idx;
    private String email;
    private String password;
    private int type;
    private String regDate;

}
