package com.mana.school.attendance.dao.impl;

import com.mana.school.attendance.dao.EntityFactoryManagerInitializer;
import com.mana.school.attendance.dao.ModuleDAO;
import com.mana.school.attendance.domain.Module;
import com.mana.school.attendance.domain.Module;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/7/2015 6:29 AM. This class is ModuleDAOImpl
 *
 * @author tarung
 * @Copyright
 */
public class ModuleDAOImpl implements ModuleDAO {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( ModuleDAOImpl.class.getCanonicalName( ) );

    /**
     * The Entity manager factory.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * Instantiates a new Lecturer dAO impl.
     */
    public ModuleDAOImpl( ) {
        entityManagerFactory = EntityFactoryManagerInitializer.getEntityFactoryManager( );
    }


    /**
     * Gets module by module id.
     *
     * @param moduleId the module id
     * @return the module by module id
     */
    @Override
    public Module getModuleByModuleId( final long moduleId ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getModuleByModuleId()";
        logger.log( Level.INFO, "Starting " + location );
        Module module = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            module = entityManager.find( Module.class, moduleId );
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to find specific module with id=" + moduleId, exception );
            return module;
        }
        logger.log( Level.INFO, "Finishing " + location );
        return module;
    }

    /**
     * Gets modules.
     *
     * @return the modules
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public List< Module > getModules( ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getModules()";
        logger.log( Level.INFO, "Starting " + location );
        List< Module > modules = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager( );
            EntityTransaction entityTransaction = entityManager.getTransaction( );
            entityTransaction.begin( );
            Query query = entityManager
                    .createQuery( "Select module from Module module order by module.moduleTitle ASC " );
            modules = query.getResultList();
            entityTransaction.commit( );
            entityManager.close( );
        } catch ( PersistenceException exception ) {
            logger.log( Level.SEVERE, "Failed to all modules", exception );
            return modules;
        }
        logger.log( Level.INFO, "Finishing " + location );
        return modules;
    }
}
