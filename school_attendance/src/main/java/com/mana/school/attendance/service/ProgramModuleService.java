package com.mana.school.attendance.service;

import com.mana.school.attendance.domain.Program;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/7/2015 4:03 PM. This class is ProgramModuleService
 *
 * @author tarung
 * @Copyright
 */
public interface ProgramModuleService {

    /**
     * Gets programs.
     *
     * @return the programs
     */
    List<Program > getPrograms( );
}
