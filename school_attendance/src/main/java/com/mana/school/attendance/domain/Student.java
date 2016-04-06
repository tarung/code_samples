package com.mana.school.attendance.domain;


import com.google.gson.annotations.Expose;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * The type Student.

 * Created by tarung on $date $time.
 * @author tarung
 * @Copyright
 */
@Entity
@Table( name = "students" )
public class Student {

    /**
     * The Student id.
     */
    @Id
    @Column( name = "student_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Expose
    private long studentId;

    /**
     * The First name.
     */
    @Column( name = "first_name" )
    @Expose
    private String firstName;
    /**
     * The Last name.
     */
    @Column( name = "last_name" )
    @Expose
    private String lastName;
    /**
     * The Gender.
     */
    @Column( name = "gender" )
    @Expose
    private String gender;

//    @Column( name = "email" )
//    private String email;
//    @Column( name = "phone_number" )
//    private String phoneNumber;
//    @Column( name = "mobile_number" )
//    private String mobileNumber;
//    @Column( name = "date_of_birth" )
//    private String dateOfBirth;

    /**
     * The Program.
     */
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "program_id", nullable = false)
    @Expose
    private Program program;

    /**
     * The Sessions.
     */
    @OneToMany( cascade = CascadeType.ALL )
    @JoinColumn( name = "session_id" )
    private List< Session > sessions;

    /**
     * Gets student id.
     *
     * @return the student id
     */
    public long getStudentId( ) {
        return studentId;
    }

    /**
     * Sets student id.
     *
     * @param studentId the student id
     */
    public void setStudentId( final long studentId ) {
        this.studentId = studentId;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName( ) {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName( ) {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender( ) {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender( final String gender ) {
        this.gender = gender;
    }

    /**
     * Gets program.
     *
     * @return the program
     */
    public Program getProgram( ) {
        return program;
    }

    /**
     * Sets program.
     *
     * @param program the program
     */
    public void setProgram( final Program program ) {
        this.program = program;
    }

    /**
     * Gets sessions.
     *
     * @return the sessions
     */
    public List< Session > getSessions( ) {
        return sessions;
    }

    /**
     * Sets sessions.
     *
     * @param sessions the sessions
     */
    public void setSessions( final List< Session > sessions ) {
        this.sessions = sessions;
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode( ) {
        return Objects.hash( getStudentId( ), getFirstName( ), getLastName( ), getGender( ), getProgram( ), sessions );
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
        if ( !( o instanceof Student ) ) return false;
        Student that = ( Student ) o;
        return Objects.equals( getStudentId( ), that.getStudentId( ) ) &&
                Objects.equals( getFirstName( ), that.getFirstName( ) ) &&
                Objects.equals( getLastName( ), that.getLastName( ) ) &&
                Objects.equals( getGender( ), that.getGender( ) ) &&
                Objects.equals( getProgram( ), that.getProgram( ) );
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString( ) {
        return "StudentRecord {" +
                " studentId=" + studentId +
                ", firstName= " + firstName +
                ", lastName= " + lastName +
                ", gender= " + gender +
                ", program=" + program +
                ", sessions=" + sessions +
                '}';
    }
}
