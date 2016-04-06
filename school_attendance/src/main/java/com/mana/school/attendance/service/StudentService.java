package com.mana.school.attendance.service;

import com.mana.school.attendance.domain.Student;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/8/2015 1:53 AM. This class is StudentService
 *
 * @author tarung
 * @Copyright
 */
public interface StudentService {


    /**
     * Gets student by id.
     *
     * @param studentId the student id
     * @return the student by id
     */
    Student getStudentById( long studentId );

    /**
     * Add student.
     *
     * @param student the student
     * @return the student
     */
    Student add( Student student );

    /**
     * Modify student.
     *
     * @param student the student
     * @return the student
     */
    Student modify( Student student );

    /**
     * Delete boolean.
     *
     * @param studentId the student id
     * @return the boolean
     */
    Boolean delete( long studentId );

    /**
     * Gets students.
     *
     * @return the students
     */
    List<Student> getStudents( );
}
