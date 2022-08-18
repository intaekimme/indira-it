package com.troupe.backend.filter;

import com.troupe.backend.exception.InvalidAccessTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidAccessTokenException invalidAccessTokenException) {
            log.error("유효하지 않은 액세스 토큰");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(invalidAccessTokenException.getMessage());
        } catch (Exception exception) {
            log.error("기타 예외");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(exception.getMessage());
        }
    }
}
