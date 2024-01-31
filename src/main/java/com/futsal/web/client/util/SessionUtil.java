package com.futsal.web.client.util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    @Autowired
    private HttpServletRequest request;

    public String getUserNameFromSession() {
        HttpSession session = request.getSession(false); // Do not create a new session if it doesn't exist
        if (session != null) {
            return (String) session.getAttribute("userName");
        }
        return null;
    }
    

    public String getIdFromSession() {
        HttpSession session = request.getSession(false); // Do not create a new session if it doesn't exist
        if (session != null) {
            return (String) session.getAttribute("futsalId");
        }
        return null;
    }
}