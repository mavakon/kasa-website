package com.bdsk.kasa.domain;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    public Admin() {
        super();

        List<String> adminRoles = new ArrayList<>(getRoles());
        if (!adminRoles.contains("ROLE_ADMIN")) {
            adminRoles.add("ROLE_ADMIN");
        }
        setRoles(adminRoles);
    }
    public Admin(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEnabled(user.isEnabled());
        this.setAccountNonExpired(user.isAccountNonExpired());
        this.setCredentialsNonExpired(user.isCredentialsNonExpired());
        this.setAccountNonLocked(user.isAccountNonLocked());

        List<String> roles = new ArrayList<>(user.getRoles());
        if (!roles.contains("ROLE_ADMIN")) {
            roles.add("ROLE_ADMIN");
        }
        this.setRoles(roles);
    }
}
