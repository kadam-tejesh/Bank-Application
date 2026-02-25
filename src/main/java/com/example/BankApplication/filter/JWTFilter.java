package com.example.BankApplication.filter;

import com.example.BankApplication.service.JWTService;
import com.example.BankApplication.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private MyUserDetailsService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader=request.getHeader("Authorization");
        String email=null;
        String token=null;
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            token=authorizationHeader.substring(7);
            try {
                email = jwtService.extractUsername(token);
            } catch (Exception e) {
                // Invalid token — do not authenticate
                filterChain.doFilter(request, response);
                return;
            }
        }
        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= service.loadUserByUsername(email);
            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken token1=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                token1.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token1);

            }
        }
        System.out.println("Extracted Email: " + email);
        filterChain.doFilter(request,response);
    }

}
