package com.mana.school.attendance.dao.impl;

import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.dao.LecturerDAO;
import com.mana.school.attendance.domain.Lecturer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/5/2015 5:46 PM. This class is LecturerDAOImpl
 *
 * @author tarung
 * @Copyright
 */
public class LecturerDAOImpl implements LecturerDAO {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( LecturerDAOImpl.class.getCanonicalName( ) );

    /**
     * The Entity manager factory.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new Lecturer dAO impl.
     */
    public LecturerDAOImpl( ) {
        entityManagerFactory = EntityFactoryManagerInitializer.getEntityFactoryManager( );
    }

    /**
     * Add lecturer.
     *
     * @param lecturer the lecturer
     * @return the lecturer
     */
    @Override
    public Lecturer add( final Lecturer lecturer ) {
        String location = this.getClass( ).getCanonicalName( ) + "#add()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            lecturer.getUserCredential( ).setUserName( lecturer.getUserCredential( ).getUserName( ).toLowerCase( ) );
            lecturer.getUserCredential( ).setLecturer( lecturer );
            entityManager.persist( lecturer );
            entityTransaction.commit( );
            entityManager.close( );
            logger.log( Level.INFO, "Finishing " + location );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to create lecturer", exception );
            return null;
        }
        return lecturer;
//        return Lecturer.getDummyLecturer( );
    }

    /**
     * Modify lecturer.
     *
     * @param lecturer the lecturer
     * @return the lecturer
     */
    @Override
    public Lecturer modify( Lecturer lecturer ) {
        String location = this.getClass( ).getCanonicalName( ) + "#modify()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );

            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            Query query = entityManager
                    .createQuery( "Select lecturer from Lecturer lecturer where lecturer.lecturerId=:lecturerId" );
            query.setParameter( "lecturerId", lecturer.getLecturerId( ) );
            Lecturer domainLecturer = ( Lecturer ) query.getResultList( ).get( 0 );
            entityTransaction.commit( );
            entityManager.close( );


            entityManager = entityManagerFactory.createEntityManager( );
            entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            lecturer.setUserCredential( domainLecturer.getUserCredential( ) );
            lecturer.getUserCredential( ).setLecturer( lecturer );
            lecturer = entityManager.merge( lecturer );
            entityTransaction.commit( );

            entityManager.close( );
            logger.log( Level.INFO, "Finishing " + location );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to modify lecturer", exception );
            return null;
        }
        return lecturer;
        //   return lecturer;
    }

    /**
     * Delete boolean.
     *
     * @param lecturerId the lecturer id
     * @return the boolean
     */
    @Override
    public Boolean delete( final long lecturerId ) {
        String location = this.getClass( ).getCanonicalName( ) + "#delete()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            Lecturer lecturer = entityManager.find( Lecturer.class, lecturerId );
            if ( lecturer != null ) {
                entityManager.remove( lecturer );
            }
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to delete lecturer", exception );
            return false;
        }
        logger.log( Level.INFO, "Finishing " + location );
        return true;//Lecturer.getDummyLecturer( ) != null;
    }

    /**
     * Gets lecturer by id.
     *
     * @param lecturerId the lecturer id
     * @return the lecturer by id
     */
    @Override
    public Lecturer getLecturerByLecturerId( final long lecturerId ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getLecturerByLecturerId()";
        logger.log( Level.INFO, "Starting " + location );
        Lecturer lecturer = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            lecturer = entityManager.find( Lecturer.class, lecturerId );
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to find specific lecturer with id=" + lecturerId, exception );
            return lecturer;
        }
        logger.log( Level.INFO, "Finishing " + location );
        return lecturer;//Lecturer.getDummyLecturer( );
    }

    /**
     * Gets lecturers.
     *
     * @return the lecturers
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public List< Lecturer > getLecturers( ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getLecturers()";
        logger.log( Level.INFO, "Starting " + location );
        List< Lecturer > lecturers = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            Query query = entityManager
                    .createQuery( "Select lecturer from Lecturer lecturer order by lecturer.lastName ASC " );
            lecturers = query.getResultList( );
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to all lecturers", exception );
            return lecturers;
        }

        logger.log( Level.INFO, "Finishing " + location );
        return lecturers;
    }
}
