package com.myrestfulprojects.moviehub.config;

import com.myrestfulprojects.moviehub.config.auth.JwtConfig;
import com.myrestfulprojects.moviehub.config.auth.JwtSecretKey;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        if (authorizationHeader == null || authorizationHeader.isEmpty() ||
                !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authorizationHeader.replace("Bearer ", "");

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build().parseClaimsJws(token);
            var body = claimsJws.getBody();
            String username = body.getSubject();
            var authorities = (List<Map<String, String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(a -> new SimpleGrantedAuthority(a.get("authority")))
                    .collect(Collectors.toSet());
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            // The JWT token has expired
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The JWT token has expired");
            return;
        } catch (UnsupportedJwtException e) {
            // The JWT token is not supported
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The JWT token is not supported");
            return;
        } catch (MalformedJwtException e) {
            // The JWT token is malformed
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The JWT token is malformed");
            return;
        } catch (SignatureException e) {
            // The JWT signature is invalid
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The JWT signature is invalid");
            return;
        } catch (IllegalArgumentException e) {
            // The JWT token is empty or null
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The JWT token is empty or null");
        }

        filterChain.doFilter(request, response);
    }
}
