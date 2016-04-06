package com.mana.school.attendance.dao.impl;

import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.dao.StudentDAO;
import com.mana.school.attendance.domain.Module;
import com.mana.school.attendance.domain.Program;
import com.mana.school.attendance.domain.Student;
import com.mana.school.attendance.domain.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/5/2015 5:45 PM. This class is StudentRecordDAOImpl
 *
 * @author tarung
 * @Copyright
 */
public class StudentDAOImpl implements StudentDAO {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( StudentDAOImpl.class.getCanonicalName( ) );

    /**
     * The Entity manager factory.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new Student record dAO impl.
     */
    public StudentDAOImpl( ) {
        entityManagerFactory = EntityFactoryManagerInitializer.getEntityFactoryManager( );
    }

    /**
     * Add student.
     *
     * @param student the student
     * @return the student
     */
    @Override
    public Student add( final Student student ) {
        String location = this.getClass( ).getCanonicalName( ) + "#add()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            // Todo see which fields of student are to be created
            entityManager.persist( student );
            entityTransaction.commit( );
            entityManager.close( );
            logger.log( Level.INFO, "Finishing " + location );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to create student", exception );
            return null;
        }
        return student;
//        return Student.getDummyStudent( );
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
            EntityManager entityManager = entityManagerFactory.createEntityManager( );

            EntityTransaction entityTransaction = entityManager.getTransaction( );
//            entityTransaction.begin( );
//            Query query = entityManager
//                    .createQuery( "Select student from Student student where student.studentId=:studentId" );
//            query.setParameter( "studentId", student.getStudentId() );
//            Student domainStudent = ( Student ) query.getResultList().get( 0 );
//            entityTransaction.commit( );
//            entityManager.close( );


            entityManager = entityManagerFactory.createEntityManager( );
            entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            // Todo see which fields of student are to be updated
            student = entityManager.merge( student );
            entityTransaction.commit();

            entityManager.close( );
            logger.log( Level.INFO, "Finishing " + location );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to modify student", exception );
            return null;
        }
        return student;
        //   return student;
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
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            Student student = entityManager.find( Student.class, studentId );
            if( student != null){
                entityManager.remove( student );
            }
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to delete student", exception );
            return false;
        }
        logger.log( Level.INFO, "Finishing " + location );
        return true;//Student.getDummyStudent( ) != null;
    }

    /**
     * Gets student by id.
     *
     * @param studentId the student id
     * @return the student by id
     */
    @Override
    public Student getStudentByStudentId( final long studentId ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getStudentByStudentId()";
        logger.log( Level.INFO, "Starting " + location );
        Student student = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            student = entityManager.find( Student.class, studentId );
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to find specific student with id=" + studentId, exception );
            return student;
        }
        logger.log( Level.INFO, "Finishing " + location );
        return student;//Student.getDummyStudent( );
    }

    /**
     * Gets students.
     *
     * @return the students
     */
    @SuppressWarnings( "unchecked" )
    public List< Student > getStudents( ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getStudents()";
        logger.log( Level.INFO, "Starting " + location );
        List< Student > students = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            Query query = entityManager
                    .createQuery( "Select student from Student student order by student.lastName ASC " );
            students = query.getResultList();
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to all students", exception );
            return students;
        }

        logger.log( Level.INFO, "Finishing " + location );
        return students;
    }
}
