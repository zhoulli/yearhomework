package com.woniu.vo;

import com.woniu.domain.User;
import lombok.Data;

@Data
public class Tadd {
    private String jwtToken;
    private User user;
}
