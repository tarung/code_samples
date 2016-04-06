package com.mana.school.attendance.dao;

import com.mana.school.attendance.domain.Program;

import java.util.List;

/**
 * Created by tarung on 8/7/2015 4:05 PM. This class is ProgramModuleDAO
 *
 * @author tarung
 * @Copyright
 */
public interface ProgramModuleDAO {

    /**
     * Gets programs.
     *
     * @return the programs
     */
    List< Program > getPrograms( );

}