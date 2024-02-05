package com.futsal.web.client.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionUtilFutsal {

    @Autowired
    private HttpServletRequest requestFutsal;

//  ... (other methods)

    public String getIdFromSession() {
        HttpSession session = requestFutsal.getSession(false); // Do not create a new session if it doesn't exist
        if (session != null) {
            Object futsalId = session.getAttribute("futsalId");
            if (futsalId != null) {
                if (futsalId instanceof String) {
                    return (String) futsalId;
                } else {
                    return String.valueOf(futsalId);
                }
            }
        }
        return null;
    }
}

