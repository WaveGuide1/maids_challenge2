package io.barth.sms.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Refresh-Token")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                            Principal connectedUser){

        userService.changePassword(changePasswordRequest, connectedUser);
        return ResponseEntity.accepted().build();
    }
}
