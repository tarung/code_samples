package com.mana.school.attendance.dao;

import com.mana.school.attendance.domain.UserCredential;

/**
 * Created by tarung on 8/5/2015 5:19 PM. This class is UserCredentialDAO
 *
 * @author tarung
 * @Copyright
 */
public interface UserCredentialDAO {

    /**
     * Validate login.
     *
     * @param userCredential the user credential
     * @return the boolean
     */
    UserCredential validateLogin( final UserCredential userCredential );
}
