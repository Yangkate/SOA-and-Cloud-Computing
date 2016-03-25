package fi.teaching.spring.repositories;

import fi.teaching.spring.db.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.jws.soap.SOAPBinding;

/**
 * Created by Administrator on 12/10/2015.
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
