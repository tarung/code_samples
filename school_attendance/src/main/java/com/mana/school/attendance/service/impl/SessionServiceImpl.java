package com.mana.school.attendance.service.impl;

import com.mana.school.attendance.dao.SessionDAO;
import com.mana.school.attendance.dao.impl.SessionDAOImpl;
import com.mana.school.attendance.domain.Module;
import com.mana.school.attendance.domain.Session;
import com.mana.school.attendance.domain.Student;
import com.mana.school.attendance.service.SessionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/4/2015 9:23 PM. This class is AttendanceServiceImpl
 *
 * @author tarung
 * @Copyright
 */
public class SessionServiceImpl implements SessionService {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( SessionServiceImpl.class.getCanonicalName( ) );

    /**
     * The Attendance dAO.
     */
    private SessionDAO sessionDAO;

    /**
     * Instantiates a new Attendance service impl.
     */
    public SessionServiceImpl( ) {
        sessionDAO = new SessionDAOImpl( );
    }

    /**
     * Add attendance.
     *
     * @param session the attendance
     * @return the attendance
     */
    @Override
    public Session addSession( Session session ) {
        String location = this.getClass( ).getCanonicalName( ) + "#()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            session = sessionDAO.addSession( session );

            if ( session != null ) {
                session.getStudent( ).setSessions( null );
            }
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while modifying lecturer " + location, exception );
        }

        logger.log( Level.INFO, "Finishing " + location );
        return session != null ? session : null;
    }

    /**
     * View attendance.
     *
     * @param sessionParam the session param
     * @return the list
     */
    @Override
    public List< Session > viewSessions( final String sessionParam ) {
        String location = this.getClass( ).getCanonicalName( ) + "#viewSessions()";
        logger.log( Level.INFO, "Starting " + location );
        List< Session > sessions = null;

        try {
            sessions = sessionDAO.getSessions( sessionParam );
            this.modifySessionForJSONResponse( sessions );
        } catch ( Exception exception ) {

            logger.log( Level.SEVERE, "Exception has occurred " + exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return sessions;
    }

    /**
     * Gets absent by specific day.
     *
     * @param specificDate the specific date
     * @return the absent by specific day
     */
    @Override
    public List< Session > getAbsentBySpecificDay( final String specificDate ) {
        String location = this.getClass( ).getCanonicalName( ) + "#()";
        logger.log( Level.INFO, "Starting " + location );
        List< Session > sessions = null;

        try {
            Calendar start = Calendar.getInstance( );
            start.setTime( new SimpleDateFormat( "MM-dd-yyyy" ).parse( specificDate ) );
            Calendar end = ( Calendar ) start.clone( );
            start.set( Calendar.HOUR_OF_DAY, 0 );
            start.set( Calendar.MINUTE, 0 );
            start.set( Calendar.SECOND, 0 );

            end.set( Calendar.HOUR_OF_DAY, 23 );
            end.set( Calendar.MINUTE, 59 );
            end.set( Calendar.SECOND, 59 );

            sessions = sessionDAO.getAbsentBySpecificDay( "Absent", start.getTime( ), end.getTime( ) );
            this.modifySessionForJSONResponse( sessions );

        } catch ( ParseException exception ) {
            logger.log( Level.SEVERE, "Date ParseException has occurred, issue with DateFormat " + exception );
        } catch ( Exception exception ) {

            logger.log( Level.SEVERE, "Exception has occurred " + exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return sessions;
    }

    /**
     * Gets absent by module.
     *
     * @param moduleTitle the module title
     * @return the absent by module
     */
    @Override
    public List< Session > getAbsentByModule( final String moduleTitle ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getAbsentByModule()";
        logger.log( Level.INFO, "Starting " + location );
        List< Session > sessions = null;
        try {

            sessions = sessionDAO.getAbsentByModule( "Absent", moduleTitle );
            this.modifySessionForJSONResponse( sessions );

        } catch ( Exception exception ) {

            logger.log( Level.SEVERE, "Exception has occurred " + exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return sessions;
    }

    /**
     * Gets absent in week greater than three.
     *
     * @param weekNumber the week number
     * @return the absent in week greater than three
     */
    @Override
    public List< Session > getAbsentInWeekGreaterThanThree( final int weekNumber ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getAbsentInWeekGreaterThanThree()";
        logger.log( Level.INFO, "Starting " + location );
        List< Session > sessions = null;

        try {
            Calendar start = Calendar.getInstance( );
            int year = start.get( Calendar.YEAR );
            start.clear( );
            start.set( Calendar.WEEK_OF_YEAR, weekNumber );
            start.set( Calendar.YEAR, year );

            Calendar end = ( Calendar ) start.clone( );
            end.add( Calendar.DATE, 7 );

            start.set( Calendar.HOUR_OF_DAY, 0 );
            start.set( Calendar.MINUTE, 0 );
            start.set( Calendar.SECOND, 0 );

            end.set( Calendar.HOUR_OF_DAY, 23 );
            end.set( Calendar.MINUTE, 59 );
            end.set( Calendar.SECOND, 59 );

            sessions = sessionDAO.getAbsentInWeekGreaterThanThree( "Absent", start.getTime( ), end.getTime( ) );
            this.modifySessionForJSONResponse( sessions );

        } catch ( Exception exception ) {

            logger.log( Level.SEVERE, "Exception has occurred " + exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return sessions;
    }

    /**
     * Modify session for jSON response.
     *
     * @param sessions the sessions
     */
    private void modifySessionForJSONResponse( List< Session > sessions ) {

        if ( sessions != null ) {
            for ( Session session : sessions ) {
                if ( session != null ) {
                    Student student = session.getStudent( );
//                    session.setModule(null);
                    if ( student != null ) {
                        // set sessions null for each student
                        student.setSessions( null );
                        student.getProgram( ).setModules( null );

//                        List< Module > modules = student.getProgram( ).getModules( );
//                        if ( modules != null ) {
//                            // Set Lecturer null
//                            for ( Module module : modules ) {
//                                if ( module != null ) {
//                                    module.setLecturer( null );
//                                }
//                            }
//                        }
                    }
                }
            }
        }
    }
}
