package com.mana.school.attendance.dao;

import com.mana.school.attendance.domain.Lecturer;

import java.util.List;

/**
 * This class is LecturerDAO
 *
 * @author tarung
 * @Copyright
 */
public interface LecturerDAO {

    /**
     * Add lecturer.
     *
     * @param lecturer the lecturer
     * @return the lecturer
     */
    Lecturer add( Lecturer lecturer );

    /**
     * Modify lecturer.
     *
     * @param lecturer the lecturer
     * @return the lecturer
     */
    Lecturer modify( Lecturer lecturer );

    /**
     * Delete boolean.
     *
     * @param lecturerId the lecturer id
     * @return the boolean
     */
    Boolean delete( long lecturerId );

    /**
     * Gets lecturer by id.
     *
     * @param lecturerId the lecturer id
     * @return the lecturer by id
     */
    Lecturer getLecturerByLecturerId( long lecturerId );

    /**
     * Gets lecturers.
     *
     * @return the lecturers
     */
    @SuppressWarnings( "unchecked" )
    List< Lecturer > getLecturers( );
}
