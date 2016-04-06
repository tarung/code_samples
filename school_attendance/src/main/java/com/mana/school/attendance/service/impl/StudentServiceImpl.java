package com.mana.school.attendance.service.impl;

import com.mana.school.attendance.dao.StudentDAO;
import com.mana.school.attendance.dao.impl.StudentDAOImpl;
import com.mana.school.attendance.domain.Module;
import com.mana.school.attendance.domain.Program;
import com.mana.school.attendance.domain.Session;
import com.mana.school.attendance.domain.Student;
import com.mana.school.attendance.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/8/2015 1:54 AM. This class is StudentServiceImpl
 *
 * @author tarung
 * @Copyright
 */
public class StudentServiceImpl implements StudentService {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( StudentServiceImpl.class.getCanonicalName( ) );

    /**
     * The Student dAO.
     */
    private StudentDAO studentDAO;

    /**
     * Instantiates a new Student service impl.
     */
    public StudentServiceImpl( ) {
        studentDAO = new StudentDAOImpl( );
    }

    /**
     * Add student.
     *
     * @param student the student
     * @return the student
     */
    @Override
    public Student add( Student student ) {
        String location = this.getClass( ).getCanonicalName( ) + "#add()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            student = studentDAO.add( student );
            List< Student > students = new ArrayList< Student >( );
            students.add( student );
            modifyStudentForJSONResponse( students );
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while creating student " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return student;
    }

    /**
     * Modify student.
     *
     * @param student the student
     * @return the student
     */
    @Override
    public Student modify( Student student ) {

        String location = this.getClass( ).getCanonicalName( ) + "#modify()";

        logger.log( Level.INFO, "Starting " + location );
        try {
            student = studentDAO.modify( student );
            List< Student > students = new ArrayList< Student >( );
            students.add( student );
            modifyStudentForJSONResponse( students );
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while modifying student " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return student != null ? student : null;
    }

    /**
     * Delete boolean.
     *
     * @param studentId the student id
     * @return the boolean
     */
    @Override
    public Boolean delete( final long studentId ) {
        String location = this.getClass( ).getCanonicalName( ) + "#delete()";
        logger.log( Level.INFO, "Starting " + location );
        boolean isStudentDeleted = false;
        try {
            isStudentDeleted = studentDAO.delete( studentId );
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while creating student " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return isStudentDeleted;
    }

    /**
     * Gets students.
     *
     * @return the students
     */
    @Override
    public List< Student > getStudents( ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getStudents()";
        logger.log( Level.INFO, "Starting " + location );
        List< Student > students = null;
        try {
            students = studentDAO.getStudents( );
            modifyStudentForJSONResponse( students );

        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while getting student " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return students;
    }

    /**
     * Gets student by id.
     *
     * @param studentId the student id
     * @return the student by id
     */
    @Override
    public Student getStudentById( final long studentId ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getStudentByStudentId()";
        logger.log( Level.INFO, "Starting " + location );
        Student student = null;
        try {
            student = studentDAO.getStudentByStudentId( studentId );
            List< Student > students = new ArrayList< Student >( );
            students.add( student );
            modifyStudentForJSONResponse( students );
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while creating student " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return student != null ? student : null;
    }

    /**
     * Modify student for jSON response.
     *
     * @param students the students
     */
    private void modifyStudentForJSONResponse( List< Student > students ) {

        if ( students != null ) {
            for ( Student student : students ) {
                if ( student != null ) {
                    student.setSessions( new ArrayList< Session >( ) );
                    Program program = student.getProgram( );

                    if ( program != null ) {
                        List< Module > modules = program.getModules( );
                        if ( modules != null ) {
                            for ( Module module : modules ) {
                                if ( module != null ) {
                                    module.setLecturer( null );
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
