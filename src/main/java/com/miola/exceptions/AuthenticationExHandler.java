package com.miola.exceptions;

import com.miola.responseMessages.ExceptionMessages;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationExHandler implements AuthenticationEntryPoint {

    public static final String ROOT_ENDPOINT = "/authentication-exception/";
    public static final String ENDPOINT_1 = "not-logged-in";
    public static final String ENDPOINT_2 = "corrupt-token";
    public static final String ENDPOINT_3 = "invalid-token";

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {

        String exReqAtt = (String) req.getAttribute("customException");
        Optional<String> exOp = Optional.ofNullable(exReqAtt);

        if (exOp.isPresent()) {

            String ex = exOp.get();

            if (ex.equals(ExceptionMessages.NOT_LOGGED_IN_OR_INVALID_AUTH_HEADER)) {
                req.getRequestDispatcher(ROOT_ENDPOINT + ENDPOINT_1).forward(req, res);
            }
            else if (ex.equals(ExceptionMessages.CORRUPT_TOKEN)) {
                req.getRequestDispatcher(ROOT_ENDPOINT + ENDPOINT_2).forward(req, res);
            }
            else if (ex.equals(ExceptionMessages.INVALID_TOKEN)) {
                req.getRequestDispatcher(ROOT_ENDPOINT + ENDPOINT_3).forward(req, res);
            } else if (ex.equals("corrupt JWT token")) {
                req.getRequestDispatcher(ROOT_ENDPOINT + "jwt").forward(req, res);
            }
        }
        else {
            req.getRequestDispatcher(ROOT_ENDPOINT).forward(req, res);
        }
    }
}
