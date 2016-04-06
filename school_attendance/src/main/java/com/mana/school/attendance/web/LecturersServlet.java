package com.mana.school.attendance.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.domain.Lecturer;
import com.mana.school.attendance.service.LecturerService;
import com.mana.school.attendance.service.impl.LecturerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/5/2015 5:56 PM. This class is LecturerServlet
 *
 * @author tarung
 * @Copyright
 */
public class LecturersServlet extends HttpServlet {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( LecturersServlet.class.getCanonicalName( ) );


    /**
     * The Lecturer service.
     */
//    private LoginService loginService;
    private LecturerService lecturerService;
    /**
     * The Lecturer validator.
     */
    private Validator lecturerValidator;

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
        } else if ( methodType.equalsIgnoreCase( "put" ) ) {
            logger.log( Level.INFO, "MethodType is " + methodType );
            performPut( request, response );
        } else {
            logger.log( Level.INFO, "MethodType is " + methodType );
            performDelete( request, response );
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
            List< Lecturer > lecturers;
            Lecturer lecturer;
            String stringLecturerId = request.getParameter( "lecturerId" );

            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_ACCEPTED );

            Gson gson = new GsonBuilder( ).excludeFieldsWithoutExposeAnnotation( ).create( );
            Object category = request.getSession( ).getAttribute( "lectureCategory" );
            if ( category != null && category.toString( ).equalsIgnoreCase( "Admin" ) ) {
                if ( stringLecturerId == null ) {
                    lecturers = lecturerService.getLecturers( );
                    response.getWriter( ).print( gson.toJson( lecturers ) );
                    logger.log( Level.INFO, "Output lecturer is " + lecturers );
                } else {
                    long lecturerId = Long.parseLong( stringLecturerId );

                    if ( lecturerId < 1 ) {
                        throw new IllegalArgumentException( "Invalid lecturer provided" );
                    }
                    lecturer = lecturerService.getLecturerById( lecturerId );
                    response.getWriter( ).print( gson.toJson( lecturer ) );
                    logger.log( Level.INFO, "Output lecturer is " + lecturer );
                }
                response.getWriter( ).flush( );
            } else {
                response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Not Allowed to View Other staff member" );
            }

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
            Gson gson = new GsonBuilder( ).create( );
            Lecturer lecturer = gson.fromJson( sb.toString( ), Lecturer.class );
            if ( lecturer == null || lecturer.getUserCredential( ) == null ) {
                throw new NullPointerException( "Staff member or members login credentials cannot be null" );
            }

            message = lecturerValidator.validateLecturer( lecturer );
            message += lecturerValidator.validatePassword( lecturer.getUserCredential( ).getPassword( ) );
            if ( message.toLowerCase( ).equals( "true" ) ) {
                lecturer = lecturerService.add( lecturer );
            } else {
                throw new IllegalArgumentException( message );
            }
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_CREATED );
            response.getWriter( ).print( gson.toJson( lecturer ) );
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
     * Perform put.
     *
     * @param request the request
     * @param response the response
     * @throws IOException the iO exception
     */
    void performPut( final HttpServletRequest request, final HttpServletResponse response ) throws IOException {

        try {

            StringBuilder sb = new StringBuilder( );
            String s;

            while ( ( s = request.getReader( ).readLine( ) ) != null ) {
                sb.append( s );
            }
            Gson gson = new GsonBuilder( ).excludeFieldsWithoutExposeAnnotation( ).create( );
            Lecturer lecturer = gson.fromJson( sb.toString( ), Lecturer.class );
            String message = lecturerValidator.validateLecturer( lecturer );


            logger.log( Level.INFO, "lecturer to be modified " + lecturer );

            if ( message.toLowerCase( ).equals( "true" ) || lecturer.getUserCredential( ).getPassword( ) == null ) {
                if ( lecturer.getLecturerId( ) < 1 ) {
                    throw new IllegalArgumentException( "Lecturer Id needs to be valid &" + message
                    );
                }
                lecturer = lecturerService.modify( lecturer );
            } else {
                throw new IllegalArgumentException( message );
            }
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_OK );
            response.getOutputStream( ).print( gson.toJson( lecturer ) );
            response.getOutputStream( ).flush( );
        } catch ( IOException exception ) {

            logger.log( Level.SEVERE, "Exception occurred while processing request", exception );
            response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Error" );
        }
    }

    /**
     * Perform delete.
     *
     * @param request the request
     * @param response the response
     * @throws IOException the iO exception
     */
    void performDelete( final HttpServletRequest request, final HttpServletResponse response ) throws IOException {
        try {
            long lecturerId = Long.parseLong( request.getParameter( "lecturerId" ) );
            if ( lecturerId < 1 ) {
                throw new IllegalArgumentException( "Invalid lecturerId provided" );
            }
            logger.log( Level.INFO, "lecturer to be deleted ", lecturerId );
            Boolean isLecturerDeleted = lecturerService.delete( lecturerId );

            Gson gson = new GsonBuilder( ).create( );
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_ACCEPTED );
            response.getWriter( ).print( gson.toJson( isLecturerDeleted ) );//isLecturerDeleted ) );
            response.getWriter( ).flush( );
        } catch ( Exception exception ) {
            logger.log( Level.SEVERE, "Exception occurred while processing request", exception );
            response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage( ) );
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
        lecturerService = new LecturerServiceImpl( );
        lecturerValidator = new Validator( );
    }

}