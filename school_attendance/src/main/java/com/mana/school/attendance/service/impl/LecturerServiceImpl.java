package com.mana.school.attendance.service.impl;

import com.mana.school.attendance.dao.LecturerDAO;
import com.mana.school.attendance.dao.impl.LecturerDAOImpl;
import com.mana.school.attendance.domain.Lecturer;
import com.mana.school.attendance.service.LecturerService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarung on 8/5/2015 5:58 PM. This class is LecturerServiceImpl
 *
 * @author tarung
 * @Copyright
 */
public class LecturerServiceImpl implements LecturerService {

    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger( LecturerServiceImpl.class.getCanonicalName( ) );
    /**
     * The Lecturer dAO.
     */
    private LecturerDAO lecturerDAO;

    /**
     * Instantiates a new Lecturer service impl.
     */
    public LecturerServiceImpl( ) {
        lecturerDAO = new LecturerDAOImpl( );
    }

    /**
     * Add lecturer.
     *
     * @param lecturer the lecturer
     * @return the lecturer
     */
    @Override
    public Lecturer add( Lecturer lecturer ) {
        String location = this.getClass( ).getCanonicalName( ) + "#()";
        logger.log( Level.INFO, "Starting " + location );
        try {
            String password = lecturer.getUserCredential().getPassword();
            try {
//              Hash the password with md5 before saving to Database
                MessageDigest messageDigest = MessageDigest.getInstance( "MD5" );
                messageDigest.update( password.getBytes( ) );

                byte byteData[] = messageDigest.digest();

                //convert the byte to hex format method 1
                StringBuilder stringBuffer = new StringBuilder();
                for ( byte aByteData : byteData ) {
                    stringBuffer.append( Integer.toString( ( aByteData & 0xff ) + 0x100, 16 ).substring( 1 ) );
                }
                password = stringBuffer.toString();
                lecturer.getUserCredential().setPassword( password );

                lecturer = lecturerDAO.add( lecturer );
            } catch ( NoSuchAlgorithmException exception ) {
                logger.log( Level.SEVERE, "Exception occurred while hashing password" + location, exception );
            }
            if ( lecturer != null ) {
                lecturer.getUserCredential( ).setLecturer( null );
            }
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while creating lecturer " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return lecturer;
    }

    /**
     * Modify lecturer.
     *
     * @param lecturer the lecturer
     * @return the lecturer
     */
    @Override
    public Lecturer modify( Lecturer lecturer ) {

        String location = this.getClass( ).getCanonicalName( ) + "#modify()";

        logger.log( Level.INFO, "Starting " + location );
        try {
            lecturer = lecturerDAO.modify( lecturer );
            if ( lecturer != null ) {
                lecturer.getUserCredential( ).setLecturer( null );
            }
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while modifying lecturer " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return lecturer != null ? lecturer : null;
    }

    /**
     * Delete boolean.
     *
     * @param lecturerId the lecturer id
     * @return the boolean
     */
    @Override
    public Boolean delete( final long lecturerId ) {
        String location = this.getClass( ).getCanonicalName( ) + "#delete()";
        logger.log( Level.INFO, "Starting " + location );
        boolean isLecturerDeleted = false;
        try {
            isLecturerDeleted = lecturerDAO.delete( lecturerId );
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while creating lecturer " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return isLecturerDeleted;
    }

    /**
     * Gets lecturer by id.
     *
     * @param lecturerId the lecturer id
     * @return the lecturer by id
     */
    @Override
    public Lecturer getLecturerById( final long lecturerId ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getLecturerByLecturerId()";
        logger.log( Level.INFO, "Starting " + location );
        Lecturer lecturer = null;
        try {
            lecturer = lecturerDAO.getLecturerByLecturerId( lecturerId );
            if ( lecturer != null ) {
                lecturer.getUserCredential( ).setLecturer( null );
            }
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while get lecturer : "+ lecturerId, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return lecturer != null ? lecturer : null;
    }

    /**
     * Gets lecturers.
     *
     * @return the lecturers
     */
    @Override
    public List< Lecturer > getLecturers( ) {
        String location = this.getClass( ).getCanonicalName( ) + "#getLecturers()";
        logger.log( Level.INFO, "Starting " + location );
        List< Lecturer > lecturers = null;
        try {
            lecturers = lecturerDAO.getLecturers(  );
            if ( lecturers != null ) {
                for ( Lecturer lecturer : lecturers ) {
                    if ( lecturer != null && lecturer.getUserCredential( ) != null ) {
                        lecturer.getUserCredential( ).setLecturer( null );
                    }
                }
            }
        } catch ( RuntimeException exception ) {
            logger.log( Level.SEVERE, "Exception occurred while getting lecturers " + location, exception );
        }
        logger.log( Level.INFO, "Finishing " + location );
        return lecturers != null ? lecturers : null;
    }
}
