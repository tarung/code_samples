package com.mana.school.attendance.dao;

import com.mana.school.attendance.domain.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by tarung on 8/4/2015 9:29 PM. This class is AttendanceDAO
 *
 * @author tarung
 * @Copyright
 */
public interface SessionDAO {

    /**
     * Gets attendance view.
     *
     * @param sessionParam the session param
     * @return the attendance view
     */
    List< Session > getSessions( final String sessionParam );

    /**
     * Add attendance.
     *
     * @param session the attendance
     * @return the attendance
     */
    Session addSession( Session session );

    /**
     * Gets absent by specific day.
     *
     * @param absent the absent
     * @param startTime the start time
     * @param endTime the end time
     * @return the absent by specific day
     */
    List<Session> getAbsentBySpecificDay( String absent, Date startTime, Date endTime );

    /**
     * Gets absent by module.
     *
     * @param absent the absent
     * @param moduleTitle the module title
     * @return the absent by module
     */
    List<Session> getAbsentByModule( String absent, String moduleTitle );

    /**
     * Gets absent in week greater than three.
     *
     * @param absent the absent
     * @param startTime the start time
     * @param endTime the end time
     * @return the absent in week greater than three
     */
    List<Session> getAbsentInWeekGreaterThanThree( String absent, Date startTime, Date endTime );
}
