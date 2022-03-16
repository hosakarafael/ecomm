package com.rafaelhosaka.ecomm.advice;

import com.rafaelhosaka.ecomm.exception.BuyerNotFoundException;
import com.rafaelhosaka.ecomm.log.Log;
import com.rafaelhosaka.ecomm.security.RequestCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String DEFAULT_TITLE = "Error Page"; 
    public static final String DEFAULT_ERROR_VIEW = "error";

    @Autowired
    @Qualifier("customRequestCache")
    private RequestCache requestCache;

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception exception) throws Exception {
        if (AnnotationUtils.findAnnotation
                (exception.getClass(), ResponseStatus.class) != null)
            throw exception;

        ModelAndView mav = createModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("message", exception.getMessage());
        mav.addObject("url", request.getRequestURL());
//        registerLog(exception.toString());
        return mav;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public void accessDeniedExceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                                     AccessDeniedException exception) throws IOException, ServletException {
        requestCache.saveRequest(request, response);

        //Forward to the login page
        request.getRequestDispatcher("/login").forward(request, response);
    }


    private ModelAndView createModelAndView() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title",DEFAULT_TITLE);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    private void registerLog(String text) throws IOException {
        Log log = new Log("log.txt");
        log.logger.setLevel(Level.WARNING);
        log.logger.warning(text);
    }
}
