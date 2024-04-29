package io.barth.sms.user;

import io.barth.sms.exception.InvalidCredentialException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new InvalidCredentialException("Wrong password");
        }

        if(!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new InvalidCredentialException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
