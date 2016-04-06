package com.mana.school.attendance.dao.impl;

import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.dao.UserCredentialDAO;
import com.mana.school.attendance.domain.UserCredential;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/5/2015 5:21 PM. This class is UserCredentialDAOImpl
 *
 * @author tarung
 * @Copyright
 */
public class UserCredentialDAOImpl implements UserCredentialDAO {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( UserCredentialDAOImpl.class.getCanonicalName( ) );

    /**
     * The Entity manager factory.
     */
    EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new User credential dAO impl.
     */
    public UserCredentialDAOImpl( ) {

        entityManagerFactory = EntityFactoryManagerInitializer.getEntityFactoryManager( );
    }

    /**
     * Validate login.
     *
     * @param userCredential the user credential
     * @return the boolean
     */
    @Override
    public UserCredential validateLogin( UserCredential userCredential ) {
        String location = this.getClass( ).getCanonicalName( ) + "#validateLogin()";
        logger.log( Level.INFO, "Starting " + location );

        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            Query query = entityManager.createQuery( "select userCredential from UserCredential  userCredential where " +
                    " userCredential.userName=:userName and userCredential.password=:password " );
            query.setParameter( "userName", userCredential.getUserName( ).toLowerCase( ) );
            query.setParameter( "password", userCredential.getPassword( ) );
            userCredential = ( UserCredential ) query.getSingleResult( );
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Invalid credentials", exception );
            return null;
        }
        logger.log( Level.INFO, "Finishing " + location );
        return userCredential;
    }
}
