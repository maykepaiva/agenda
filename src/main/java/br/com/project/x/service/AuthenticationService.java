package br.com.project.x.service;

import br.com.project.x.domain.dto.AuthRequest;
import br.com.project.x.domain.dto.TokenResponse;
import br.com.project.x.domain.entity.User;
import br.com.project.x.exception.UnauthorizedException;
import br.com.project.x.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticationService implements UserDetailsService {

    @Value("${auth.jwt.token.secret}")
    private String secretKey;

    @Value("${auth.jwt.token.expiration}")
    private Integer horaExpiracaoToken;

    @Value("${auth.jwt.refresh-token.expiration}")
    private Integer horaExpiracaoRefreshToken ;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public String obterToken(AuthRequest authRequest){
        User user = userRepository.findByEmail(authRequest.getEmail());

        return geraTokenJwt(user);
    }
    public  String geraTokenJwt(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(geraDataExpiracao(horaExpiracaoRefreshToken))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao tentar gerar o token! " +exception.getMessage());
        }
    }
    public String validaTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao tentar validar token" + exception.getMessage());
        }
    }
//    public TokenResponse obterRefreshToken(String refreshToken) throws UnauthorizedException {
//
//        String email = validaTokenJwt(refreshToken);
//        User user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            throw new UnauthorizedException("UnauthorizedException");
//        }
//
//        var autentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(autentication);
//
//        return TokenResponse
//                .builder()
//                .token(geraTokenJwt(user,horaExpiracaoToken))
//                .refreshToken(geraTokenJwt(user,horaExpiracaoRefreshToken))
//                .build();
//    }
    private Instant geraDataExpiracao(Integer expiration) {
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
