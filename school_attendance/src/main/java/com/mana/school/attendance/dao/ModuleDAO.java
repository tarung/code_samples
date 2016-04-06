package com.mana.school.attendance.dao;

import com.mana.school.attendance.domain.Module;

import java.util.List;

/**
 * Created on 8/7/2015 6:22 AM. This class is ModuleDAO
 *
 * @author tarung
 * @Copyright
 */
public interface ModuleDAO {

    /**
     * Gets module by module id.
     *
     * @param ModuleId the module id
     * @return the module by module id
     */
    Module getModuleByModuleId(long ModuleId);

    /**
     * Gets modules.
     *
     * @return the modules
     */
    List<Module> getModules();
}
