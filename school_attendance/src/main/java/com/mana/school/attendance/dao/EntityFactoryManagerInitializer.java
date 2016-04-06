package com.mana.school.attendance.dao;

import com.google.appengine.api.utils.SystemProperty;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/5/2015 5:22 PM. This class is EntityFactoryManagerInitializer
 *
 * @author tarung
 */
public class EntityFactoryManagerInitializer {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( EntityFactoryManagerInitializer.class.getCanonicalName( ) );

    /**
     * The constant entityManagerFactory.
     */
    private static EntityManagerFactory entityManagerFactory;

    /**
     * Initialize entity factory manager.
     */
    public static void initializeEntityFactoryManager( ) {

        String location = "EntityFactoryManagerInitializer#initializeEntityFactoryManager()";
        logger.log( Level.INFO, "Starting " + location );

        Map< String, String > properties = new HashMap< String, String >( );
        if ( SystemProperty.environment.value( ) ==
                SystemProperty.Environment.Value.Production ) {
            properties.put( "javax.persistence.jdbc.driver",
                    "com.mysql.jdbc.GoogleDriver" );
            properties.put( "javax.persistence.jdbc.url",
                    System.getProperty( "cloudsql.url" ) );
        } else {
            properties.put( "javax.persistence.jdbc.driver",
                    "com.mysql.jdbc.Driver" );
            properties.put( "javax.persistence.jdbc.url",
                    System.getProperty( "cloudsql.url.dev" ) );
        }

        if ( entityManagerFactory == null ) {
            entityManagerFactory = Persistence.createEntityManagerFactory( "transactions-optional", properties );
        }

        logger.log( Level.INFO, "Finishing " + location );
    }

    /**
     * Gets entity factory manager.
     *
     * @return the entity factory manager
     */
    public static EntityManagerFactory getEntityFactoryManager( ) {

        String location = "EntityFactoryManagerInitializer#getEntityFactoryManager()";
        logger.log( Level.INFO, "Starting " + location );

        if ( entityManagerFactory == null ) {
            throw new RuntimeException( "EntityManagerFactory is not initialized" );
        }

        logger.log( Level.INFO, "Finishing " + location );
        return entityManagerFactory;
    }
}
