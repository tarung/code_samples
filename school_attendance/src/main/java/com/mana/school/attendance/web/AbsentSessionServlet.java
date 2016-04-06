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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/8/2015 12:36 AM. This class is ${NAME}
 *
 * @author tarung
 * @Copyright
 */
public class AbsentSessionServlet extends HttpServlet {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( SessionServlet.class.getCanonicalName( ) );

    /**
     * The Session service.
     */
    private SessionService sessionService;

    /**
     * Init void.
     */
    @Override
    public void init( ) {
        EntityFactoryManagerInitializer.initializeEntityFactoryManager( );
        sessionService = new SessionServiceImpl( );
    }

    /**
     * Service void.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException the iO exception
     */
    public void service( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        System.out.println( request.getRequestURL( ) );
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
    private void performGet( final HttpServletRequest request, final HttpServletResponse response ) throws IOException {

        String location = this.getClass( ).getCanonicalName( ) + "#performGet()";
        logger.log( Level.INFO, "Starting " + location );

        List< Session > sessions = null;
        try {

                String specificDate = request.getParameter( "AbsentBySpecificDay" );
                if ( specificDate != null && !specificDate.trim( ).isEmpty( ) )
                sessions = sessionService.getAbsentBySpecificDay( specificDate );

                String moduleTitle = request.getParameter( "AbsentByModule" );
                if ( moduleTitle != null && !moduleTitle.trim( ).isEmpty( ) )
                sessions = sessionService.getAbsentByModule( moduleTitle );

                String week = request.getParameter( "AbsentByWeek" );
                if ( week != null && !week.trim( ).isEmpty( ) ) {
                    int weekNumber = Integer.parseInt(week);
                    sessions = sessionService.getAbsentInWeekGreaterThanThree(weekNumber);
                }
            if(specificDate == null && moduleTitle == null && week == null){
                throw new NullPointerException("At least one absent criteria must be provided");
            }
            Gson gson = new GsonBuilder( ).excludeFieldsWithoutExposeAnnotation( ).create();

            if(sessions != null) {
                response.setContentType( "application/json" );
                response.setStatus(HttpServletResponse.SC_OK);

                response.getOutputStream().print(gson.toJson( sessions ));
                response.getOutputStream().flush();

            } else {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);

                response.getOutputStream().print(gson.toJson( new ArrayList()));
                response.getOutputStream().flush();
            }
        } catch ( NullPointerException exception ) {
            logger.log( Level.INFO, exception.getMessage( ), exception );
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, exception.getMessage( ) );
        } catch ( NumberFormatException exception ) {

            logger.log( Level.INFO, exception.getMessage( ), exception );
            response.sendError( HttpServletResponse.SC_BAD_REQUEST, exception.getMessage( ) );
        } catch ( Exception exception ) {

            logger.log( Level.SEVERE, exception.getMessage( ), exception );
            response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage( ) );
        }
        logger.log( Level.INFO, "Finishing " + location );
    }
}
