package com.ms.et.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ms.et.domain.User;
import com.ms.et.domain.Role;
import com.ms.et.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        try {
            User user = userRepository.findByName(name);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + name);
            }
            org.springframework.security.core.userdetails.User userDetails =
                    new org.springframework.security.core.userdetails.User(
                            user.getName(),
                            user.getPassword(),
                            user.isEnabled(),
                            true,
                            true,
                            true,
                            getAuthorities(user.getRoles()));
            return userDetails;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final List<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.addAll(role.getPrivileges()
                    .stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
                    .collect(Collectors.toList()));
        }
        return authorities;
    }
}
