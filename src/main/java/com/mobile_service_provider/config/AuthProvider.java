package com.mobile_service_provider.config;


import com.mobile_service_provider.model.Authority;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {



    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email=authentication.getName();
        String pwd=authentication.getCredentials().toString();

        Optional<Users> target=usersRepository.findByEmail(email);

        if(target.isPresent()){
            String s = target.get().getPassword();
            if(passwordEncoder.matches(pwd,target.get().getPassword())){
                return new UsernamePasswordAuthenticationToken(email,pwd,getGrantedAuthorities((Set<Authority>) target.get().getAuthorities()));
            }
            throw new BadCredentialsException("Invalid password!");
        }
        throw new BadCredentialsException("No user registered with this details!");
    }


    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}