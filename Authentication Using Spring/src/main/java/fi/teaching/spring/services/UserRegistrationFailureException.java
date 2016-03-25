package fi.teaching.spring.services;

/**
 * Created by Administrator on 13/10/2015.
 */
public class UserRegistrationFailureException extends Exception {

    public UserRegistrationFailureException(String message) {
        super(message);
    }
}
