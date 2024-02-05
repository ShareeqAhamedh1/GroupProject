package com.futsal.web.client.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SessionUtilUser {

    @Autowired
    private HttpServletRequest requestUser;

    public String getUserIdFromSession() {
        HttpSession session = requestUser.getSession(false); // Do not create a new session if it doesn't exist
        if (session != null) {
            return (String) session.getAttribute("userId");
        }
        return null;
    }
}
