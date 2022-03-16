package com.rafaelhosaka.ecomm.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;

@Component("customRequestCache")
public class RequestCache extends HttpSessionRequestCache {
    public RequestCache(){
        super();
    }
}
