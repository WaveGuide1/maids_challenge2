package io.barth.sms.authentication;

import io.barth.sms.user.Role;
import io.barth.sms.validation.PasswordCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String username;

    @PasswordCheck
    private String password;

    private Role role;
}
