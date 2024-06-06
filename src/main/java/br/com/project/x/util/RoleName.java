package br.com.project.x.util;

import lombok.Getter;

@Getter
public enum RoleName {

    ROLE_ADMIN("admin"),
    ROLE_USER("usuario");

    private String role;

    RoleName(String role) {
        this.role = role;
    }
}