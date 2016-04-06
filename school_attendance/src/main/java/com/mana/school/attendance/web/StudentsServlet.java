package com.mana.school.attendance.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.domain.Student;
import com.mana.school.attendance.service.StudentService;
import com.mana.school.attendance.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/4/2015 9:45 PM. This class is StudentRecordsServlet
 *
 * @author tarung
 * @Copyright
 */
public class StudentsServlet extends HttpServlet {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( StudentsServlet.class.getCanonicalName( ) );

    /**
     * The Student service.
     */
    private StudentService studentService;
    /**
     * The Student validator.
     */
    private Validator studentValidator;

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

            String studentIdString = request.getParameter( "studentId" );
            Student student;
            List< Student > students;
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_ACCEPTED );
            Gson gson = new GsonBuilder( ).excludeFieldsWithoutExposeAnnotation( ).create( );

            if ( studentIdString == null ) {
                students = studentService.getStudents( );
                response.getWriter( ).print( gson.toJson( students ) );
                logger.log( Level.INFO, "Output students is " + students );

            } else {
                long studentId = Long.parseLong( studentIdString );
                if ( studentId < 1 ) {
                    throw new IllegalArgumentException( "Invalid student provided" );
                }
                student = studentService.getStudentById( studentId );
                response.getWriter( ).print( gson.toJson( student ) );
                logger.log( Level.INFO, "Output student is " + student );
            }
            response.getWriter( ).flush( );

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
            Student student = gson.fromJson( sb.toString( ), Student.class );
            logger.log( Level.INFO, "student to be created ", student );
            message = studentValidator.validateStudent( student );
            logger.log( Level.INFO, "student created " + student );
            if ( message.toLowerCase( ).equals( "true" ) ) {
                student = studentService.add( student );
            } else {
                throw new IllegalArgumentException( message );
            }
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_CREATED );
            response.getWriter( ).print( gson.toJson( student ) );
            response.getWriter( ).flush( );
        } catch ( IOException exception ) {

            logger.log( Level.SEVERE, "Exception occurred while processing request", exception );
            if ( message == null )
                response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Error" );
            else response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Invalid Data" );
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
            Student student = gson.fromJson( sb.toString( ), Student.class );
            String message = studentValidator.validateStudent( student );


            logger.log( Level.INFO, "student to be modified " + student );

            if ( message.toLowerCase( ).equals( "true" ) ) {
                if ( student.getStudentId( ) < 1 ) {
                    throw new IllegalArgumentException( "Student Id needs to be valid &" + message
                    );
                }
                student = studentService.modify( student );
            } else {
                throw new IllegalArgumentException( message );
            }
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_OK );
            response.getOutputStream( ).print( gson.toJson( student ) );
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
            long studentId = Long.parseLong( request.getParameter( "studentId" ) );
            if ( studentId < 1 ) {
                throw new IllegalArgumentException( "Invalid studentId provided" );
            }
            logger.log( Level.INFO, "student to be deleted ", studentId );
            Boolean isStudentDeleted = studentService.delete( studentId );

            Gson gson = new GsonBuilder( ).create( );
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_ACCEPTED );
            response.getWriter( ).print( gson.toJson( isStudentDeleted ) );//isStudentDeleted ) );
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
        studentService = new StudentServiceImpl( );
        studentValidator = new Validator( );
    }
}
