package com.mana.school.attendance.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.domain.Session;
import com.mana.school.attendance.service.SessionService;
import com.mana.school.attendance.service.impl.SessionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/7/2015 11:15 PM. This class is SessionServlet
 *
 * @author tarung
 * @Copyright
 */
public class SessionServlet extends HttpServlet {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( SessionServlet.class.getCanonicalName( ) );


    /**
     * The Session service.
     */
    private SessionService sessionService;
    /**
     * The Session validator.
     */
    private Validator sessionValidator;

    /**
     * Service void.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws ServletException the servlet exception
     */
    @Override
    public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        String methodType = request.getMethod( ).trim( );
        if ( methodType.equalsIgnoreCase( "get" ) ) {
            logger.log( Level.INFO, "MethodType is " + methodType );
            performGet( request, response );

        } else if ( methodType.equalsIgnoreCase( "post" ) ) {
            logger.log( Level.INFO, "MethodType is " + methodType );
            performPost( request, response );
        }
    }

    /**
     * Perform get.
     *
     * @param request the request
     * @param response the response
     * @throws IOException the iO exception
     */
    void performGet( final HttpServletRequest request, final HttpServletResponse response ) throws IOException {

        try {
            String sessionParam = request.getParameter( "sessionOption" );
            if ( sessionParam == null ) {
                throw new IllegalArgumentException( "Invalid session option provided" );
            }
            List< Session > sessions = sessionService.viewSessions( sessionParam );

            Gson gson = new GsonBuilder( ).excludeFieldsWithoutExposeAnnotation( ).create( );

            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_ACCEPTED );
            response.getWriter( ).print( gson.toJson( sessions ) );
            response.getWriter( ).flush( );
            logger.log( Level.INFO, "Output session is " + sessions);
        } catch ( Exception exception ) {
            logger.log( Level.SEVERE, "Exception occurred while processing request", exception );
            response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage( ) );
        }
    }

    /**
     * Perform post.
     *
     * @param request the request
     * @param response the response
     * @throws IOException the iO exception
     */
    void performPost( final HttpServletRequest request, final HttpServletResponse response ) throws IOException {

        String message = null;
        try {
            StringBuilder sb = new StringBuilder( );
            String s;
            while ( ( s = request.getReader( ).readLine( ) ) != null ) {
                sb.append( s );
            }
            Gson gson = new GsonBuilder( ).setDateFormat("MM-dd-yyyy HH:mm:ss").create( );
            Session session = gson.fromJson( sb.toString( ), Session.class );
            if(session == null){
                throw new NullPointerException( "Session cannot be null" );
            }
            logger.log( Level.INFO, "session to be created ", session );
            message = sessionValidator.validateSession( session );
            logger.log( Level.INFO, "session created " + session );
            if ( message.toLowerCase( ).equals( "true" ) ) {
                session = sessionService.addSession( session );
            } else {
                throw new IllegalArgumentException( message );
            }
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_CREATED );
            response.getWriter( ).print( gson.toJson( session ) );
            response.getWriter( ).flush( );
        } catch ( NullPointerException exception ) {
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, exception.getMessage( ) );
            logger.log( Level.WARNING, "Exception occurred while processing request due to Null Data", exception );

        } catch ( IOException exception ) {

            if ( message == null ) {
                response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Error" );
                logger.log( Level.SEVERE, "Exception occurred while processing request", exception );
            } else {
                response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Invalid Data" );
                logger.log( Level.WARNING, "Exception occurred while processing request due to Invalid Data", exception );
            }
        }
    }

    /**
     * Destroy void.
     */
    @Override
    public void destroy( ) {

    }

    /**
     * Init void.
     */
    @Override
    public void init( ) {
        EntityFactoryManagerInitializer.initializeEntityFactoryManager( );
        sessionService = new SessionServiceImpl( );
        sessionValidator = new Validator( );
    }
}
