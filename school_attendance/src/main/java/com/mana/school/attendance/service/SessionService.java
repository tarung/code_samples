package com.mana.school.attendance.service;

import com.mana.school.attendance.domain.Session;
import com.mana.school.attendance.domain.Student;

import java.util.List;

/**
 * Created by tarung on 8/4/2015 8:59 PM. This class is AttendanceService
 *
 * @author tarung
 * @Copyright
 */
public interface SessionService {

    /**
     * Add attendance.
     *
     * @param session the attendance
     * @return the attendance
     */
    Session addSession( Session session );

    /**
     * View attendance.
     *
     * @param sessionParam the session param
     * @return the list
     */
    List< Session > viewSessions( final String sessionParam );


    /**
     * Gets absent by specific day.
     *
     * @param specificDate the specific date
     * @return the absent by specific day
     */
    List<Session> getAbsentBySpecificDay( String specificDate );

    /**
     * Gets absent by module.
     *
     * @param moduleTitle the module title
     * @return the absent by module
     */
    List<Session> getAbsentByModule( String moduleTitle );

    /**
     * Gets absent in week greater than three.
     *
     * @param weekNumber the week number
     * @return the absent in week greater than three
     */
    List<Session> getAbsentInWeekGreaterThanThree( int weekNumber );
}
