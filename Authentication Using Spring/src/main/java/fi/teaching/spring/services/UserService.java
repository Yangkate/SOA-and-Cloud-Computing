package fi.teaching.spring.services;

import fi.teaching.spring.db.User;
import fi.teaching.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 12/10/2015.
 */
@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public void loginUser(String email, String password) throws UserRegistrationFailureException {
        User u = userRepository.findByEmail(email);
        if (u == null) {
            throw new UserRegistrationFailureException("Register first");
        }
        if(!password.equals(u.getPassword())){
           throw new UserRegistrationFailureException("wrong password");
        }
    }

    public void registerUser(String email, String password1, String password2) throws UserRegistrationFailureException {
        User u = userRepository.findByEmail(email);
        if (u != null) {
            throw new UserRegistrationFailureException("User already exists");
        }
        if(password1.length() < 6){
            throw new UserRegistrationFailureException("Password should be atleast 6 characters");
        }
        if(!password1.equals(password2)){
            throw new UserRegistrationFailureException("Passwords don't match");
        }
        u = new User();
        u.setEmail(email);
        u.setPassword(new BCryptPasswordEncoder().encode(password1));
        userRepository.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        List<SimpleGrantedAuthority> auths = new LinkedList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), auths);
    }
}
