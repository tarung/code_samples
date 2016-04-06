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

/**
 * Created by tarung on 8/4/2015 7:50 PM. This class is Module
 *
 * @author tarung
 * @Copyright
 */
@Entity
@Table( name = "modules" )
public class Module {

    /**
     * The Module id.
     */
    @Id
    @Column( name = "module_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Expose
    private long moduleId;

    /**
     * The Module name.
     */
    @Column( name = "module_title" )
    @Expose
    private String moduleTitle;

    /**
     * The Semester.
     */
    @Column( name = "semester" )
    @Expose
    private String semester;

    /**
     * The Students.
     */
    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn( name = "lecture_id", nullable = false )
    private Lecturer lecturer;

    /**
     * The Program.
     */
    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "program_id", nullable = false )
    private Program program;

    /**
     * Gets module id.
     *
     * @return the module id
     */
    public long getModuleId( ) {
        return moduleId;
    }

    /**
     * Sets module id.
     *
     * @param moduleId the module id
     */
    public void setModuleId( final long moduleId ) {
        this.moduleId = moduleId;
    }

    /**
     * Gets module name.
     *
     * @return the module name
     */
    public String getModuleName( ) {
        return moduleTitle;
    }

    /**
     * Sets module name.
     *
     * @param moduleTitle the module name
     */
    public void setModuleName( final String moduleTitle ) {
        this.moduleTitle = moduleTitle;
    }

    /**
     * Gets module title.
     *
     * @return the module title
     */
    public String getModuleTitle( ) {
        return moduleTitle;
    }

    /**
     * Sets module title.
     *
     * @param moduleTitle the module title
     */
    public void setModuleTitle( final String moduleTitle ) {
        this.moduleTitle = moduleTitle;
    }

    /**
     * Gets semester.
     *
     * @return the semester
     */
    public String getSemester( ) {
        return semester;
    }

    /**
     * Sets semester.
     *
     * @param semester the semester
     */
    public void setSemester( final String semester ) {
        this.semester = semester;
    }

    /**
     * Gets students.
     *
     * @return the lecturer
     */
    public Lecturer getLecturer( ) {
        return lecturer;
    }

    /**
     * Sets students.
     *
     * @param lecturer the lecturer
     */
    public void setLecturer( final Lecturer lecturer ) {
        this.lecturer = lecturer;
    }

}
