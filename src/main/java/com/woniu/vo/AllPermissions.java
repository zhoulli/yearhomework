package com.woniu.vo;

import com.woniu.domain.Permission;
import lombok.Data;

import java.util.Set;

@Data
public class AllPermissions {

    private Set<Permission> permissions;

    private Set<Integer> rids3;
}
