package com.mana.school.attendance.service;

import com.mana.school.attendance.domain.UserCredential;

import javax.servlet.http.HttpSession;

/**
 * Created by tarung on 8/4/2015 9:00 PM. This class is LoginService
 *
 * @author tarung
 * @Copyright
 */
public interface LoginService {

    /**
     * Do login.
     *
     * @param userCredential the user credential
     * @return the boolean
     */
    UserCredential doLogin( UserCredential userCredential );

    /**
     * Do logout.
     *
     * @param session the session
     * @return the boolean
     */
    boolean doLogout( HttpSession session );
}
