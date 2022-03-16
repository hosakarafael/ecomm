package com.rafaelhosaka.ecomm.security;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("customAccessDeniedHandler")
@NoArgsConstructor
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Autowired
    @Qualifier("customRequestCache")
    private RequestCache requestCache;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        if (!httpServletResponse.isCommitted()) {

            //Save Target-Request
            requestCache.saveRequest(httpServletRequest, httpServletResponse);

            //Forward to the login page
            httpServletRequest.getRequestDispatcher("/login").forward(httpServletRequest, httpServletResponse);
        }
    }
}
