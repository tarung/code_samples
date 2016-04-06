package com.mana.school.attendance.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.domain.Lecturer;
import com.mana.school.attendance.domain.Program;
import com.mana.school.attendance.service.LecturerService;
import com.mana.school.attendance.service.ProgramModuleService;
import com.mana.school.attendance.service.impl.LecturerServiceImpl;
import com.mana.school.attendance.service.impl.ProgramModuleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/7/2015 3:54 PM. This class is ProgramModuleServlet
 *
 * @author tarung
 * @Copyright
 */
public class ProgramModuleServlet extends HttpServlet {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( ProgramModuleServlet.class.getCanonicalName( ) );


    /**
     * The Lecturer service.
     */
//    private LoginService loginService;
    private ProgramModuleService programModuleService;

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

            List<Program > programs= programModuleService.getPrograms(  );

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();;
            response.setContentType( "application/json" );
            response.setStatus( HttpServletResponse.SC_ACCEPTED );
            response.getWriter( ).print( gson.toJson( programs ) );
            response.getWriter( ).flush( );
            logger.log( Level.INFO, "Output lecturer is " + programs );
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
        programModuleService = new ProgramModuleServiceImpl( );
    }
}
