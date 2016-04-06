package com.mana.school.attendance.service.impl;

import com.mana.school.attendance.dao.ProgramModuleDAO;
import com.mana.school.attendance.dao.impl.ProgramModuleDAOImpl;
import com.mana.school.attendance.domain.Program;
import com.mana.school.attendance.service.ProgramModuleService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/7/2015 4:04 PM. This class is ProgramModuleServiceImpl
 *
 * @author tarung
 * @Copyright
 */
public class ProgramModuleServiceImpl implements ProgramModuleService {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( ProgramModuleServiceImpl.class.getCanonicalName( ) );

    /**
     * The Program module dAO.
     */
    private ProgramModuleDAO programModuleDAO;

    /**
     * Instantiates a new Program module service impl.
     */
    public ProgramModuleServiceImpl( ) {
        programModuleDAO = new ProgramModuleDAOImpl( );
    }


    /**
     * Gets programs.
     *
     * @return the programs
     */
    @Override
    public List< Program > getPrograms( ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getPrograms()";
        List< Program > programs = null;
        logger.log( Level.INFO, "Starting " + location );
        try {
            programs = programModuleDAO.getPrograms( );
        } catch ( Exception exception ) {
            logger.log( Level.SEVERE, "An exception occurred", exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return programs != null ? programs : null;
    }
}
