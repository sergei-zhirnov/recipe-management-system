package recipes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.exception.DuplicateEmailException;
import recipes.model.AppUser;
import recipes.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exists: " + user.getEmail(), e);
        }
    }

}
