package com.mana.school.attendance.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

/**
 * Created by tarung on 8/4/2015 9:01 PM. This class is UserCredential
 *
 * @author tarung
 * @Copyright
 */
@Entity
@Table( name = "user_credentials", uniqueConstraints =
@UniqueConstraint( name = "Unique", columnNames = {"user_name"}))
public class UserCredential {

    /**
     * The User credential id.
     */
    @Id
    @Column( name = "user_credential_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private long userCredentialId;

    /**
     * The User name.
     */
    @Column( name = "user_name" )
    @Expose
    private String userName;
    /**
     * The Password.
     */
    @Column( name = "password" )
    private String password;

    /**
     * The User.
     */
    @OneToOne( cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn( name = "lecturer_id" )
    private Lecturer lecturer;

    /**
     * Gets user credential id.
     *
     * @return the user credential id
     */
    public long getUserCredentialId( ) {
        return userCredentialId;
    }

    /**
     * Sets user credential id.
     *
     * @param userCredentialId the user credential id
     */
    public void setUserCredentialId( final long userCredentialId ) {
        this.userCredentialId = userCredentialId;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName( ) {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName( final String userName ) {
        this.userName = userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword( ) {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword( final String password ) {
        this.password = password;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public Lecturer getLecturer( ) {
        return lecturer;
    }

    /**
     * Sets user.
     *
     * @param lecturer the user
     */
    public void setLecturer( final Lecturer lecturer ) {
        this.lecturer = lecturer;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof UserCredential ) ) return false;
        UserCredential that = ( UserCredential ) o;
        return Objects.equals( getUserCredentialId( ), that.getUserCredentialId( ) ) &&
                Objects.equals( getUserName( ), that.getUserName( ) ) &&
                Objects.equals( getPassword( ), that.getPassword( ) );
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode( ) {
        return Objects.hash( getUserCredentialId( ), getUserName( ), getPassword( ));
    }


    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString( ) {
        return "UserCredential{" +
                "userCredentialId=" + userCredentialId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
