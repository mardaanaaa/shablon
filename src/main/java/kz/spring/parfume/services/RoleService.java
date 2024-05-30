package kz.spring.parfume.services;

import kz.spring.parfume.entities.Role;
import kz.spring.parfume.entities.User;
import kz.spring.parfume.repositories.RoleRepository;
import kz.spring.parfume.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public boolean hasRoles(String role, String userRoles){
        String[] roles = userRoles.split(",");
        for (String p:roles){
            if (p.equals(role)){
                return true;
            }
        }
        return false;
    }

    public Role findRole(String role){
        return roleRepository.findByRole(role);
    }

    public Role userRole(){
        return roleRepository.findByRole("ROLE_USER");
    }
}
