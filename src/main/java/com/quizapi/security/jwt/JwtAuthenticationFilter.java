package com.quizapi.security.jwt;


import java.io.IOException;

import com.quizapi.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException,ExpiredJwtException,IllegalArgumentException,MalformedJwtException {
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header : {}",requestHeader);
        String username = null;
        String token = null;
        if(requestHeader!=null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error("Illegal Argument while fetching the username !!");
                e.printStackTrace();
                throw new ApiException("Illegal Argument while fetching the username !!");
//				throw e;
            } catch (ExpiredJwtException e) {
                logger.error("Given jwt token is expired !!");
                e.printStackTrace();
                throw new ApiException("Given jwt token is expired !!");
//				throw e;
            } catch (MalformedJwtException e) {
                logger.error("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
                throw new ApiException("Some changed has done in token !! Invalid Token");
//				throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            logger.info("Invalid Header Value !! ");
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean validateToken = jwtTokenHelper.validateToken(token, userDetails);
            if(validateToken) {
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                logger.info("Validation fails !!");
            }
        }
        filterChain.doFilter(request, response);
    }

}
