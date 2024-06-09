package br.com.project.x.util;

import lombok.Getter;

@Getter
public enum RoleName {

    ADMIN("admin"),
    USER("user");

    private String role;

    RoleName(String role) {
        this.role = role;
    }
}