package com.egresso.ufma.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{
    
    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) 
                throws IOException, ServletException {
        //pega o token
        String header = request.getHeader(SecurityConstants.HEADER_NAME);
        if (header == null) {
            chain.doFilter(request, response);
            return;
        }
        //tenta autenticar
        UsernamePasswordAuthenticationToken authentication = authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        //pega o toke
        String token = request.getHeader(SecurityConstants.HEADER_NAME);
        if (token != null) {
            // faz parse do token
            String user = Jwts.parser()
                .setSigningKey(SecurityConstants.KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }else{
                return  null;
            }
        }
        return null;
    }
}
