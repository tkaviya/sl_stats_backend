package com.tkaviya.slstats.config;

import com.tkaviya.slstats.security.SLStatsSecurityUtil;
import com.tkaviya.slstats.security.SLUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***************************************************************************
 * Created:     01 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Component
public class SLRequestFilter extends OncePerRequestFilter {

    private final SLUserDetailsService SLUserDetailsService;

    private final SLStatsSecurityUtil slStatsSecurityUtil;

    public SLRequestFilter(SLUserDetailsService SLUserDetailsService, SLStatsSecurityUtil slStatsSecurityUtil) {
        this.SLUserDetailsService = SLUserDetailsService;
        this.slStatsSecurityUtil = slStatsSecurityUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && slStatsSecurityUtil.validateJwtToken(jwt)) {
                String username = slStatsSecurityUtil.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = SLUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = slStatsSecurityUtil.getJwtFromCookies(request);
        return jwt;
    }
}
