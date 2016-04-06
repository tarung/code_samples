package com.mana.school.attendance.dao.impl;

import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.dao.ProgramModuleDAO;
import com.mana.school.attendance.domain.Lecturer;
import com.mana.school.attendance.domain.Program;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/7/2015 4:08 PM. This class is ProgramModuleDAOImpl
 *
 * @author tarung
 * @Copyright
 */
public class ProgramModuleDAOImpl implements ProgramModuleDAO {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( ProgramModuleDAOImpl.class.getCanonicalName( ) );

    /**
     * The Entity manager factory.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new Program module dAO impl.
     */
    public ProgramModuleDAOImpl(){
        {
            entityManagerFactory = EntityFactoryManagerInitializer.getEntityFactoryManager( );
        }
    }

    /**
     * Gets programs.
     *
     * @return the programs
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public List< Program > getPrograms( ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getPrograms()";
        logger.log( Level.INFO, "Starting " + location );
        List< Program > programs = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            Query query = entityManager
                    .createQuery( "Select program from Program program order by program.programName ASC " );
            programs = query.getResultList();
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to all programs", exception );
            return programs;
        }

        logger.log( Level.INFO, "Finishing " + location );
        return programs;
    }
}
