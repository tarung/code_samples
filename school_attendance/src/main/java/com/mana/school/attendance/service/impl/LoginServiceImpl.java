package com.mana.school.attendance.service.impl;

import com.mana.school.attendance.dao.UserCredentialDAO;
import com.mana.school.attendance.dao.impl.UserCredentialDAOImpl;
import com.mana.school.attendance.domain.UserCredential;
import com.mana.school.attendance.service.LoginService;

import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/5/2015 5:18 PM. This class is LoginServiceImpl
 *
 * @author tarung
 * @Copyright
 */
public class LoginServiceImpl implements LoginService {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( LoginServiceImpl.class.getCanonicalName( ) );

    /**
     * The User credential dAO.
     */
    private UserCredentialDAO userCredentialDAO;

    /**
     * Instantiates a new Login service impl.
     */
    public LoginServiceImpl( ) {

        userCredentialDAO = new UserCredentialDAOImpl( );
    }

    /**
     * Do login.
     *
     * @param userCredential the user credential
     * @return the boolean
     */
    @Override
    public UserCredential doLogin(  UserCredential userCredential ) {
        String location = this.getClass( ).getCanonicalName( ) + "#doLogin()";
        logger.log( Level.INFO, "Starting " + location );
        try{
            userCredential = userCredentialDAO.validateLogin( userCredential );
        } catch ( Exception exception ) {
            logger.log( Level.SEVERE, "Exception while validating login", exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return userCredential;
    }

    /**
     * Do logout.
     *
     * @param session the session
     * @return the boolean
     */
    @Override
    public boolean doLogout( HttpSession session ) {
        String location = this.getClass( ).getCanonicalName( ) + "#doLogout()";
        logger.log( Level.INFO, "Starting " + location );
        session.setAttribute( "loggedUserName", null );
        logger.log( Level.INFO, "Finishing " + location );
        return session.getAttribute( "loggedUserName" ) == null;
    }
}
