package com.mana.school.attendance.web;

import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.domain.UserCredential;
import com.mana.school.attendance.service.LoginService;
import com.mana.school.attendance.service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/7/2015 8:30 PM. This class is LoginServlet
 *
 * @author tarung
 * @Copyright
 */
public class LoginServlet extends HttpServlet {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( LoginServlet.class.getCanonicalName( ) );
    /**
     * The Login service.
     */
    private LoginService loginService;

    /**
     * Init void.
     */
    @Override
    public void init( ) {
        String location = this.getClass( ).getCanonicalName( ) + "#init()";
        logger.log( Level.INFO, "Starting " + location );

        EntityFactoryManagerInitializer.initializeEntityFactoryManager( );
        loginService = new LoginServiceImpl( );

        logger.log( Level.INFO, "Finishing " + location );
    }

    /**
     * Service void.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException the iO exception
     */
    @Override
    public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        if ( request.getMethod( ).toLowerCase( ).equals( "post" ) ) {
            if ( request.getRequestURI( ).contains( "login" ) ) {
                performLogin( request, response );
            }
        }
        if ( request.getMethod( ).toLowerCase( ).equals( "get" ) ) {
            if ( request.getRequestURI( ).contains( "logout" ) ) {
                performLogout( request, response );
            }
        }
    }

    /**
     * Perform logout.
     *
     * @param request the request
     * @param response the response
     */
    private void performLogout( final HttpServletRequest request, final HttpServletResponse response ) {

        String location = this.getClass( ).getCanonicalName( ) + "#performLogout()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            response.setHeader( "Cache-Control", "no-cache, no-store" );
            response.setHeader( "Pragma", "no-cache" );

            request.getSession( ).invalidate( );
            response.setContentType( "text/html" );
            response.getOutputStream( ).print( request.getRequestURL( ).toString( ).replace( "/logout", "/html/login.html" ) );
            response.getOutputStream( ).flush( );
            response.setStatus( HttpServletResponse.SC_OK );
//            response.sendRedirect(request.getContextPath() + "/login.html");
        } catch ( Exception exception ) {
            logger.log( Level.INFO, "Error occurred during logout " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
    }

    /**
     * Perform login.
     *
     * @param request the request
     * @param response the response
     * @return the boolean
     * @throws IOException the iO exception
     * @throws ServletException the servlet exception
     */
    public boolean performLogin( HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {

        String location = this.getClass( ).getCanonicalName( ) + "#performLogin()";
        logger.log( Level.INFO, "Starting " + location );
        String userName = request.getParameter( "userName" );
        String password = request.getParameter( "password" );
        HttpSession session = request.getSession( );
        Object loggedUserName = session.getAttribute( "userName" );
        if ( loggedUserName != null && !loggedUserName.toString( ).trim( ).isEmpty( ) ) {
            String url = request.getRequestURI( );
            url = url.replace( "/login", "/html/home.jsp" );
            request.getRequestDispatcher( url ).forward( request, response );
            return true;
        } else {
            if ( userName != null && password != null && !userName.isEmpty( ) && !password.isEmpty( ) ) {
                UserCredential userCredential = new UserCredential( ), validatedStaff;
                userCredential.setPassword( password );
                userCredential.setUserName( userName.toLowerCase( ) );

                try {
                    validatedStaff = loginService.doLogin( userCredential );
                } catch ( Exception exception ) {
                    logger.log( Level.SEVERE, "Exception occurred while trying to login", exception );
                    response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Could Not Log User in" );
                    return false;
                }
                if ( validatedStaff != null ) {
                    session.setAttribute( "userName", userName );
                    session.setAttribute( "lecturerId", validatedStaff.getLecturer( ).getLecturerId( ) );
                    session.setAttribute( "lecturerCategory", validatedStaff.getLecturer( ).getCategory( ) );
                    String url = request.getRequestURI( );
                    url = url.replace( "/login", "/html/home.jsp" );
                    response.sendRedirect( url );
                    return true;
                } else {
                    displayInvalidCredentials(request, response);
                }
            } else {
                displayInvalidCredentials(request, response);
            }
        }

        logger.log( Level.INFO, "Finishing " + location );
        return false;
    }

    /**
     * Display invalid credentials.
     *
     * @param request the request
     * @param response the response
     * @throws IOException the iO exception
     */
    void displayInvalidCredentials(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletOutputStream printWriter = response.getOutputStream( );
        response.setContentType( "text/html" );
        response.setStatus( 401 );
        printWriter.print( "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Admin Home</title>\n" +
                "</head>\n" );
        printWriter.print( "<body>" );
        printWriter.print( "<div> Login Failure No Credentials Provided </div>" );
        printWriter.print( "<div> <a href='html/login.html'>Click to retry login</a> </div>" );
        printWriter.print( "</body>\n" + "</html>" );
        printWriter.flush( );
    }
}
