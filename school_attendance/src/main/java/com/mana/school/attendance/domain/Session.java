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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by tarung on 8/4/2015 9:12 PM. This class is Session
 *
 * @author tarung
 * @Copyright
 */
@Entity
@Table( name = "sessions" )
public class Session {

    /**
     * The Session id.
     */
    @Id
    @Column( name = "session_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Expose
    private int sessionId;

    /**
     * The Status.
     */
    @Column( name = "status" )
    @Expose
    private String status;

    /**
     * The Date taken.
     */
    @Column( name = "date_taken", columnDefinition = "Timestamp" )
    @Temporal( value = TemporalType.TIMESTAMP )
    @Expose
    private Date dateTaken;

    /**
     * The Session.
     */
    @Column( name = "session" )
    @Expose
    private String session;

    /**
     * The Student.
     */
    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.MERGE} )
    @JoinColumn( name = "student_id", nullable = false )
    @Expose
    private Student student;

    /**
     * The Module.
     */
    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.MERGE} )
    @JoinColumn( name = "module_id", nullable = false)
    @Expose
    private Module module;

    /**
     * Gets session id.
     *
     * @return the session id
     */
    public int getSessionId( ) {
        return sessionId;
    }

    /**
     * Sets session id.
     *
     * @param sessionId the session id
     */
    public void setSessionId( final int sessionId ) {
        this.sessionId = sessionId;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus( ) {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * Gets date taken.
     *
     * @return the date taken
     */
    public Date getDateTaken( ) {
        return dateTaken;
    }

    /**
     * Sets date taken.
     *
     * @param dateTaken the date taken
     */
    public void setDateTaken( final Date dateTaken ) {
        this.dateTaken = dateTaken;
    }

    /**
     * Gets session.
     *
     * @return the session
     */
    public String getSession( ) {
        return session;
    }

    /**
     * Sets session.
     *
     * @param session the session
     */
    public void setSession( final String session ) {
        this.session = session;
    }

    /**
     * Gets student.
     *
     * @return the student
     */
    public Student getStudent( ) {
        return student;
    }

    /**
     * Sets student.
     *
     * @param student the student
     */
    public void setStudent( final Student student ) {
        this.student = student;
    }

    /**
     * Gets module.
     *
     * @return the module
     */
    public Module getModule( ) {
        return module;
    }

    /**
     * Sets module.
     *
     * @param module the module
     */
    public void setModule( final Module module ) {
        this.module = module;
    }
}
