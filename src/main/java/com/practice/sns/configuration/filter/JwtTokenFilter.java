package com.practice.sns.configuration.filter;

import com.practice.sns.Service.UserService;
import com.practice.sns.model.User;
import com.practice.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String key;
    private final UserService userService;

    private final static List<String> TOKEN_IN_PARAM_URLS = List.of("/api/v1/users/alarm/subscribe");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token;
        log.info(request.getRequestURI());
        log.info(request.getHeader(HttpHeaders.AUTHORIZATION));

        try {
            if(TOKEN_IN_PARAM_URLS.contains(request.getRequestURI())) {
                log.info("Request with {} check the query param", request.getRequestURI());
                token = request.getQueryString().split("=")[1].trim();
            } else {
                final String header = request.getHeader(HttpHeaders.AUTHORIZATION);


                if (header == null || !header.startsWith("Bearer ")) {
                    log.error("Error occurs while getting header. header is invalid {}",request.getRequestURL());
                    filterChain.doFilter(request, response);
                    return;
                }
                token = header.split(" ")[1].trim();
            }
            if (JwtTokenUtils.isExpired(token, key)) {
                log.error("Key is expired");
                filterChain.doFilter(request, response);
                return;
            };

            // TODO: 토큰에서 유저네임 가져오기
            String userName = JwtTokenUtils.getUserName(token, key);

            // TODO: 유저이름이 valid한지
            User user = userService.loadUserByUserName(userName);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    // TODO
                    user,null, user.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException e) {
            filterChain.doFilter(request, response);
            log.error(e.toString());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
