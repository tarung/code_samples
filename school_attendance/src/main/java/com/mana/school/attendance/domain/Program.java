package com.mana.school.attendance.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/7/2015 4:20 PM. This class is Program
 *
 * @author tarung
 * @Copyright
 */
@Entity
@Table( name = "programs" )
public class Program {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( Program.class.getCanonicalName( ) );

    /**
     * The Program id.
     */
    @Id
    @Column( name = "program_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Expose
    private long programId;

    /**
     * The Program name.
     */
    @Column( name = "program_name" )
    @Expose
    private String programName;

    /**
     * The Modules.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "program")
    @Expose
    private List<Module> modules;

    /**
     * The Students.
     */
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "program" )
    private List< Student > students;

    /**
     * Gets program id.
     *
     * @return the program id
     */
    public long getProgramId() {
        return programId;
    }

    /**
     * Sets program id.
     *
     * @param program_id the program _ id
     */
    public void setProgramId(final long program_id) {
        this.programId = program_id;
    }

    /**
     * Gets program name.
     *
     * @return the program name
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets program name.
     *
     * @param program_name the program _ name
     */
    public void setProgramName(final String program_name) {
        this.programName = program_name;
    }

    /**
     * Gets modules.
     *
     * @return the modules
     */
    public List< Module > getModules( ) {
        return modules;
    }

    /**
     * Sets modules.
     *
     * @param modules the modules
     */
    public void setModules( final List< Module > modules ) {
        this.modules = modules;
    }

    /**
     * Gets students.
     *
     * @return the students
     */
    public List< Student > getStudents( ) {
        return students;
    }

    /**
     * Sets students.
     *
     * @param students the students
     */
    public void setStudents( final List< Student > students ) {
        this.students = students;
    }
}
