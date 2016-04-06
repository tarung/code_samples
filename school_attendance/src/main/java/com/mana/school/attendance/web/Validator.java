package com.mana.school.attendance.web;

import org.apache.commons.codec.binary.Base64;
import com.mana.school.attendance.domain.Lecturer;
import com.mana.school.attendance.domain.Program;
import com.mana.school.attendance.domain.Session;
import com.mana.school.attendance.domain.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tarung on 8/6/2015 4:38 AM. This class is Validator
 *
 * @author tarung
 * @Copyright
 */
public class Validator {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger(Validator.class.getCanonicalName());

    /**
     * Validate lecturer.
     *
     * @param lecturer the lecturer
     * @return the string
     */
    String validateLecturer(Lecturer lecturer) {
        String validateString = validateFirstName(lecturer.getFirstName()) + nl()
                + validateLastName(lecturer.getLastName()) + nl()
                + validateUserName(lecturer.getUserCredential().getUserName()) + nl()
                + validateCategory(lecturer.getCategory()) + nl()
                + validateDateOfBirth(lecturer.getDateOfBirth()) + nl()
                + validateEmail(lecturer.getEmail()) + nl()
                + validatePhoneNumber(lecturer.getPhoneNumber()) + nl()
                + validateMobileNumber(lecturer.getMobileNumber()) + nl()
                + validateGender(lecturer.getGender());
        return validateString.trim().equals("") ? "true" : validateString;
    }

    /**
     * Validate student.
     *
     * @param student the student
     * @return the string
     */
    String validateStudent(Student student) {
        String validateString = validateFirstName(student.getFirstName()) + nl()
                + validateLastName(student.getLastName()) + nl()
//                + validateDateOfBirth( student.getDateOfBirth( ) ) + nl( )
//                + validateEmail( student.getEmail( ) ) + nl( )
                + validateProgram(student.getProgram()) + nl()
//                + validatePhoneNumber( student.getPhoneNumber( ) ) + nl( )
//                + validateMobileNumber( student.getMobileNumber( ) ) + nl( )
                + validateGender(student.getGender());
        return validateString.trim().equals("") ? "true" : validateString;
    }

    /**
     * Validate session.
     *
     * @param session the session
     * @return the string
     */
    String validateSession(Session session) {
        String validateString = validateSessionOptions(session.getSession()) + nl()
                + validateStatus(session.getStatus()) + nl();
//                + validateDateTaken(session.getDateTaken().toString()) + nl();
        return validateString.trim().equals("") ? "true" : validateString;
    }

    /**
     * Validate program.
     *
     * @param program the program
     * @return the string
     */
    private String validateProgram(final Program program) {

        if (program == null || program.getProgramName() == null || program.getProgramId() < 1) {
            return "Program cannot be null or program name empty or id of program empty";
        } else {
            return "";
        }
    }

    /**
     * Validate first name.
     *
     * @param firstName the first name
     * @return the string
     */
    String validateFirstName(String firstName) {

        if (firstName == null || firstName.trim().isEmpty()) {

            return "FirstName cannot be null or empty";
        } else {
            Pattern pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(firstName);
            boolean isInvalid = matcher.find();
            if (isInvalid) {
                return "FirstName can only contain alphabets";
            } else return "";
        }
    }

    /**
     * Validate last name.
     *
     * @param lastName the last name
     * @return the string
     */
    String validateLastName(String lastName) {

        if (lastName == null || lastName.trim().isEmpty()) {

            return "LastName cannot be null or empty";
        } else {
            Pattern pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(lastName);
            boolean isInvalid = matcher.find();
            if (isInvalid) {
                return "LastName can only contain alphabets";
            } else return "";
        }
    }

    /**
     * Validate lecturer name.
     *
     * @param userName the user name
     * @return the string
     */
    String validateUserName(String userName) {

        if (userName == null || userName.trim().isEmpty()) {

            return "LastName cannot be null or empty";
        } else {
            Pattern pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userName);
            boolean isInvalid = matcher.find();
            if (isInvalid) {
                return "LastName can only contain alphabets and numbers";
            } else return "";
        }
    }

    /**
     * Validate gender.
     *
     * @param gender the gender
     * @return the string
     */
    String validateGender(String gender) {

        if (gender == null || gender.trim().isEmpty()) {

            return "";
        } else {
            gender = gender.toLowerCase();
            if (gender.equals("male") || gender.equals("female")) {
                return "";
//                return "Gender can only Male, Female";
            } else return "";
        }
    }

    /**
     * Validate category.
     *
     * @param category the category
     * @return the string
     */
    String validateCategory(String category) {

        if (category == null || category.trim().isEmpty()) {

            return "Category cannot be null or empty";
        } else {
            category = category.toLowerCase();
            if (category.equals("admin") || category.equals("lecturer")) {
                return "";
            } else return "Category can only admin or lecturer";
        }
    }

    /**
     * Validate date of birth.
     *
     * @param dateOfBirth the date of birth
     * @return the string
     */
    String validateDateOfBirth(String dateOfBirth) {

        if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {

            return "DateOfBirth cannot be null or empty";
        } else {
            Date dob;
            try {
                dob = new SimpleDateFormat("MM-dd-yyyy").parse(dateOfBirth);
            } catch (ParseException exception) {
                return "Invalid Date format";
            }
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(dob);
            Calendar today = Calendar.getInstance();
            if ((today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)) > 21 ||
                    (today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)) < 2) {
                return "Not possible please check Date of Birth and try again";
            } else return "";
        }
    }

    /**
     * Validate phone number.
     *
     * @param phoneNumber the phone number
     * @return the string
     */
    String validatePhoneNumber(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "PhoneNumber cannot be null or empty";
        } else if (phoneNumber.length() < 10 || phoneNumber.length() > 15) {
            return "PhoneNumber cannot be less than 10 or greater than 15";
        } else {
//            try {
//                if (phoneNumber.startsWith("+")) {
//                    phoneNumber = phoneNumber.replace("+", "00");
//                }
////                int number = Integer.parseInt(phoneNumber);
//
//            } catch (NumberFormatException exception) {
//                return "PhoneNumber has to be numeric";
//            }
            return "";
        }
    }

    /**
     * Validate mobile number.
     *
     * @param mobileNumber the mobile number
     * @return the string
     */
    String validateMobileNumber(String mobileNumber) {

        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            return "MobileNumber cannot be null or empty";
        } else if (mobileNumber.length() < 10 || mobileNumber.length() > 15) {
            return "MobileNumber cannot be less than 10 or greater than 15";
        } else {
//            try {
//                if (mobileNumber.startsWith("+")) {
//                    mobileNumber = mobileNumber.replace("+", "00");
//                }
////                int number = Integer.parseInt(mobileNumber);
//
//            } catch (NumberFormatException exception) {
//                return "MobileNumber has to be numeric";
//            }
            return "";
        }
    }

    /**
     * Validate email.
     *
     * @param email the email
     * @return the string
     */
    String validateEmail(String email) {

        if (email == null || email.trim().isEmpty()) {

            return "Email cannot me null or empty";
        }
        if (email.length() > 50) {
            return "Email length exceeded maximum limit of 50.";
        }
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        boolean isMatched = email.matches(EMAIL_REGEX);
        if (isMatched) {
            return "";
        } else {
            return "Invalid email format";
        }
    }

    /**
     * Validate password.
     *
     * @param password the password
     * @return the string
     */
    String validatePassword(String password) {

        if (password == null || password.trim().isEmpty()) {

            return "Password cannot me null or empty";
        }
        if (password.length()< 8) {
            return "Password length must be at least characters 8.";
        }
        return "";
    }

    /**
     * Validate date taken.
     *
     * @param dateTaken the date taken
     * @return the string
     */
    String validateDateTaken(String dateTaken) {
        if (dateTaken == null || dateTaken.trim().isEmpty()) {

            return "DateOfBirth cannot be null or empty";
        } else {
            Date dt;
            try {
                dt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(dateTaken);
            } catch (ParseException exception) {
                return "Invalid Date format";
            }
            Calendar dateTakenCalendar = Calendar.getInstance();
            dateTakenCalendar.setTime(dt);
            Calendar today = Calendar.getInstance();
            if ((today.get(Calendar.YEAR) - dateTakenCalendar.get(Calendar.YEAR)) > 21 ||
                    (today.get(Calendar.YEAR) - dateTakenCalendar.get(Calendar.YEAR)) < 0) {
                return "Not possible please check Date Taken and try again";
            } else return "";
        }
    }

    /**
     * Validate session.
     *
     * @param session the session
     * @return the string
     */
    String validateSessionOptions(String session) {
        if (session == null || session.trim().isEmpty()) {

            return "Session cannot be null or empty";
        } else {
            session = session.toLowerCase();
            if (session.equals("lecture") || session.equals("lab") || session.equals("both")) {
                return "";
            } else return "Session can only lecture, lab or both";
        }
    }

    /**
     * Validate status.
     *
     * @param status the status
     * @return the string
     */
    String validateStatus(String status) {
        if (status == null || status.trim().isEmpty()) {

            return "Status cannot be null or empty";
        } else {
            status = status.toLowerCase();
            if (status.equals("absent") || status.equals("present")) {
                return "";
            } else return "Status can only be Absent or Present";
        }
    }

    /**
     * Nl string.
     *
     * @return the string
     */
    String nl() {
        return "\n";
    }

    /**
     * Sp string.
     *
     * @return the string
     */
    String sp() {
        return " ";
    }
}
