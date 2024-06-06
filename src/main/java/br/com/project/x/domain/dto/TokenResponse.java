package br.com.project.x.domain.dto;

import lombok.Builder;

@Builder
public class TokenResponse {
    String token;
    String refreshToken;
}
