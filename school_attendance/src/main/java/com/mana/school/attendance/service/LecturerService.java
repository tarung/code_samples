package com.mana.school.attendance.service;

import com.mana.school.attendance.domain.Lecturer;

import java.util.List;

/**
 * Created by tarung on 8/4/2015 8:59 PM. This class is UserService
 *
 * @author tarung
 * @Copyright
 */
public interface LecturerService {

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
    Lecturer getLecturerById( long lecturerId );

    /**
     * Gets lecturers.
     *
     * @return the lecturers
     */
    List<Lecturer> getLecturers();
}
