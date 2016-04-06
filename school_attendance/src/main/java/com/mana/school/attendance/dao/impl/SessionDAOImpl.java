package com.mana.school.attendance.dao.impl;

import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.dao.SessionDAO;
import com.mana.school.attendance.domain.Module;
import com.mana.school.attendance.domain.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Session dAO impl.
 * <p/>
 * Created by tarung on $date $time.
 *
 * @author tarung
 * @Copyright
 */
public class SessionDAOImpl implements SessionDAO {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( SessionDAOImpl.class.getCanonicalName( ) );
    /**
     * The Entity manager factory.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new Session dAO impl.
     */
    public SessionDAOImpl( ) {
        entityManagerFactory = EntityFactoryManagerInitializer.getEntityFactoryManager( );
    }

    /**
     * Gets sessions.
     *
     * @param sessionParam the session param
     * @return the sessions
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public List< Session > getSessions( final String sessionParam ) {

        String location = this.getClass( ).getCanonicalName( ) + "#getSessions()";
        logger.log( Level.INFO, "Starting " + location );

        List< Session > sessions = null;
        try {
            EntityManager manager = entityManagerFactory.createEntityManager( );
            String queryStudent = "select session from Session session" +
                    " ORDER BY session." + querySortParamString( sessionParam ) + " ASC";
            manager.createQuery( queryStudent );
            Query query = manager.createQuery( queryStudent );
            sessions = query.getResultList( );
            manager.close( );
            logger.log( Level.INFO, "successful output is\n" + sessions );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Error fetching data", exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return sessions;
    }

    /**
     * Add session.
     *
     * @param session the session
     * @return the session
     */
    @Override
    public Session addSession( final Session session ) {
        String location = this.getClass( ).getCanonicalName( ) + "#addSession()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            EntityManager manager = entityManagerFactory.createEntityManager( );

            EntityTransaction entityTransaction = manager.getTransaction( );

            entityTransaction.begin( );
//            Query query = manager.createQuery( "select module from Module module where module.moduleId=:moduleId" );
//            query.setParameter( "moduleId", session.getModule().getModuleId() );
//            Module module = ( Module ) query.getSingleResult();
//
//            session.setModule( module );
            manager.persist( session );
            entityTransaction.commit( );

            manager.close( );
            logger.log( Level.INFO, "successful output is\n" + session );

        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Error add data to session table ", exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return session;
    }

    /**
     * Gets absent by specific day.
     *
     * @param absent the absent
     * @param startTime the start time
     * @param endTime the end time
     * @return the absent by specific day
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public List< Session > getAbsentBySpecificDay( final String absent, final Date startTime, final Date endTime ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getAbsentBySpecificDay()";
        logger.log( Level.INFO, "Starting " + location );

        List< Session > sessions = null;
        try {
            EntityManager manager = entityManagerFactory.createEntityManager( );
            String querySession = "select session from Session session" +
                    " where session.status=:status and session.dateTaken between :startTime and :endTime";

            Query query = manager.createQuery( querySession );
            query.setParameter( "status", absent );
            query.setParameter( "startTime", startTime );
            query.setParameter( "endTime", endTime );

            sessions = query.getResultList( );
            manager.close( );
            logger.log( Level.INFO, "successful output is\n" + sessions );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Error fetching data", exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return sessions;
    }

    /**
     * Gets absent by module.
     *
     * @param absent the absent
     * @param moduleTitle the module title
     * @return the absent by module
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public List< Session > getAbsentByModule( final String absent, final String moduleTitle ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getAbsentByModule()";
        logger.log( Level.INFO, "Starting " + location );
        List< Session > sessions = null;

        try {
            EntityManager manager = entityManagerFactory.createEntityManager( );
            String querySession = "select session from Session session" +
                    " where session.status=:status and session.module.moduleTitle=:moduleTitle " +
                    " order by session.student.studentId asc ";

            Query query = manager.createQuery( querySession );
            query.setParameter( "status", absent );
            query.setParameter( "moduleTitle", moduleTitle );

            sessions = query.getResultList( );
            manager.close( );
            logger.log( Level.INFO, "successful output is\n" + sessions );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Error fetching data", exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return sessions;
    }

    /**
     * Gets absent in week greater than three.
     *
     * @param absent the absent
     * @param startTime the start time
     * @param endTime the end time
     * @return the absent in week greater than three
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public List< Session > getAbsentInWeekGreaterThanThree( final String absent, final Date startTime, final Date endTime ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getAbsentInWeekGreaterThanThree()";
        logger.log( Level.INFO, "Starting " + location );
        List< Session > sessions = null;

        try {
            EntityManager manager = entityManagerFactory.createEntityManager( );
            String querySession = "select session from Session session" +
                    " where session.status=:status and session.dateTaken between :startTime and :endTime " +
                    " order by session.student.studentId asc";

            Query query = manager.createQuery( querySession );
            query.setParameter( "status", absent );
            query.setParameter( "startTime", startTime );
            query.setParameter( "endTime", endTime );

            sessions = query.getResultList( );
            manager.close( );
            logger.log( Level.INFO, "successful output is\n" + sessions );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Error fetching data", exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return sessions;
    }

    /**
     * Query sort param string.
     *
     * @param sessionParam the session param
     * @return the string
     */
    String querySortParamString( String sessionParam ) {
        if ( sessionParam.contains( "module" ) ) {
            return "module.moduleTitle";
        } else if ( sessionParam.contains( "semester" ) ) {
            return "module.semester";
        } else if ( sessionParam.contains( "dateTaken" ) ) {
              return "dateTaken";
        }
        return null;
    }
}
