package com.example.springsecurity.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import static com.example.springsecurity.security.ApplicationUserPermission.STUDENT_READ;
import static com.example.springsecurity.security.ApplicationUserPermission.STUDENT_WRITE;
import static com.example.springsecurity.security.ApplicationUserPermission.COURSE_READ;
import static com.example.springsecurity.security.ApplicationUserPermission.COURSE_WRITE;

public enum ApplicationUserRule {
    ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_READ, COURSE_WRITE)),
    STUDENT(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_READ)),

    COURSE(Sets.newHashSet(COURSE_READ, COURSE_WRITE));
    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRule(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
