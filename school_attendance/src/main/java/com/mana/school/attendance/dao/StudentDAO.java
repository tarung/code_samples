package com.mana.school.attendance.dao;

import com.mana.school.attendance.domain.Student;

import java.util.List;

/**
 * Created by tarung on 8/4/2015 9:41 PM. This class is StudentRecordDAO
 *
 * @author tarung
 * @Copyright
 */
public interface StudentDAO {

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
     * Gets student by id.
     *
     * @param studentId the student id
     * @return the student by id
     */
    Student getStudentByStudentId( long studentId );

    /**
     * Gets student by id.
     *
     * @return the student by id
     */
    List<Student> getStudents( );

}
