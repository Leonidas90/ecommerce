package com.example.ecommerce.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String LOGGED_IN_ATTRIBUTE = "loggedIn";

    public void bindSessionToUser(final String userId, final HttpSession session) {
        session.setAttribute(USER_ID_ATTRIBUTE, userId);
        session.setAttribute(LOGGED_IN_ATTRIBUTE, Boolean.TRUE);
    }

    public boolean isUserLoggedIn(final HttpSession httpSession) {
        final Object isLoggedInObj = httpSession.getAttribute(LOGGED_IN_ATTRIBUTE);
        return isLoggedInObj instanceof Boolean isLoggedIn ? isLoggedIn : false;
    }

    public void invalidateSession(final HttpSession httpSession) {
        httpSession.removeAttribute(USER_ID_ATTRIBUTE);
        httpSession.removeAttribute(LOGGED_IN_ATTRIBUTE);
        httpSession.invalidate();
    }

}
