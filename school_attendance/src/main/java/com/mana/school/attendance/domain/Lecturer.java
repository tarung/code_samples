package com.mana.school.attendance.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by tarung on 8/4/2015 7:16 PM. This class is User
 *
 * @author tarung
 * @Copyright
 */
@Entity
@Table( name = "lecturers" )
public class Lecturer {

    /**
     * The User credential.
     */
    @OneToOne( cascade = CascadeType.ALL, mappedBy = "lecturer" )
    @Expose
    public UserCredential userCredential;
    /**
     * The User id.
     */
    @Id
    @Column( name = "lecturer_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Expose
    private long lecturerId;
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
    /**
     * The Category.
     */
    @Column( name = "category" )
    @Expose
    private String category;
    /**
     * The Email.
     */
    @Column( name = "email" )
    @Expose
    private String email;
    /**
     * The Phone number.
     */
    @Column( name = "phone_number" )
    @Expose
    private String phoneNumber;
    /**
     * The Mobile number.
     */
    @Column( name = "mobile_number" )
    @Expose
    private String mobileNumber;
    /**
     * The Date of birth.
     */
    @Column( name = "date_of_birth" )
    @Expose
    private String dateOfBirth;

    /**
     * Instantiates a new Lecturer.
     */
    public Lecturer( ) {

    }

    /**
     * Gets lecturer id.
     *
     * @return the lecturer id
     */
    public long getLecturerId( ) {
        return lecturerId;
    }

    /**
     * Sets lecturer id.
     *
     * @param lecturerId the lecturer id
     */
    public void setLecturerId( final long lecturerId ) {
        this.lecturerId = lecturerId;
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
     * Gets category.
     *
     * @return the category
     */
    public String getCategory( ) {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory( final String category ) {
        this.category = category;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail( ) {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail( final String email ) {
        this.email = email;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber( ) {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber( final String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets mobile number.
     *
     * @return the mobile number
     */
    public String getMobileNumber( ) {
        return mobileNumber;
    }

    /**
     * Sets mobile number.
     *
     * @param mobileNumber the mobile number
     */
    public void setMobileNumber( final String mobileNumber ) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth( ) {
        return dateOfBirth;
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth( final String dateOfBirth ) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets lecturer credential.
     *
     * @return the lecturer credential
     */
    public UserCredential getUserCredential( ) {
        return userCredential;
    }

    /**
     * Sets lecturer credential.
     *
     * @param userCredential the lecturer credential
     */
    public void setUserCredential( final UserCredential userCredential ) {
        this.userCredential = userCredential;
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
        if ( !( o instanceof Lecturer ) ) return false;
        Lecturer lecturer = ( Lecturer ) o;
        return Objects.equals( getLecturerId( ), lecturer.getLecturerId( ) ) &&
                Objects.equals( getUserCredential( ), lecturer.getUserCredential( ) ) &&
                Objects.equals( getFirstName( ), lecturer.getFirstName( ) ) &&
                Objects.equals( getLastName( ), lecturer.getLastName( ) ) &&
                Objects.equals( getGender( ), lecturer.getGender( ) ) &&
                Objects.equals( getCategory( ), lecturer.getCategory( ) ) &&
                Objects.equals( getEmail( ), lecturer.getEmail( ) ) &&
                Objects.equals( getPhoneNumber( ), lecturer.getPhoneNumber( ) ) &&
                Objects.equals( getMobileNumber( ), lecturer.getMobileNumber( ) ) &&
                Objects.equals( getDateOfBirth( ), lecturer.getDateOfBirth( ) );
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode( ) {
        return Objects.hash( getUserCredential( ), getLecturerId( ), getFirstName( ), getLastName( ), getGender( ), getCategory( ), getEmail( ), getPhoneNumber( ), getMobileNumber( ), getDateOfBirth( ) );
    }


    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString( ) {
        return "Lecturer{" +
                "userCredential=" + userCredential +
                ", lecturerId=" + lecturerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", category='" + category + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
