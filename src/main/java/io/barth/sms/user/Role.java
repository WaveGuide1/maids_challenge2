package io.barth.sms.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.barth.sms.user.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {

    USER(Collections.emptySet()),

    MANAGER(Set.of(
            MANAGER_CREATE,
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_DELETE
    )),

    ADMIN(Set.of(

            ADMIN_CREATE,
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            MANAGER_CREATE,
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_DELETE
    ));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){

        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
