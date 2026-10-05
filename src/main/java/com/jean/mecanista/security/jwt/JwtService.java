package com.jean.mecanista.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.jean.mecanista.security.UsuarioAutenticado;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private static final String ISSUER = "MecanistaAPI";
    private static final String PREFIX = "Bearer ";
    private static final Long TEMPO_EM_SEGUNDOS_EXPIRACAO = 900L;
    private static final String SECRET_KEY = "B6q7K7YCsPOygNhyDvm28SLR0c31u/ti94XWgSXMI1c=";


    public String gerarToken(UsuarioAutenticado usuario) {
        var aogra = Instant.now();

        return Jwts.builder()
                .issuer(ISSUER)
                .issuedAt(Date.from(aogra))
                .expiration(Date.from(aogra.plusSeconds(TEMPO_EM_SEGUNDOS_EXPIRACAO)))
                .subject(usuario.getUsername())
                .claim("roles", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(secretKey())
                .compact();
    }

    public String extrairSubject(String token) {
        try {
            return getClaims(token).getSubject();
        }catch (JwtException e){
            throw new JwtServiceException(e.getMessage());
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
