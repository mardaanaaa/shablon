package kz.spring.parfume.components;

import kz.spring.parfume.entities.Role;
import kz.spring.parfume.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> existingRoles = roleRepository.findAll();

        if (existingRoles.isEmpty()) {
            Role role1 = new Role();
            role1.setRole("ROLE_USER");

            Role role2 = new Role();
            role2.setRole("ROLE_ADMIN");

            roleRepository.save(role1);
            roleRepository.save(role2);

            System.out.println("Initial permissions saved successfully.");
        } else {
            System.out.println("Permissions already exist. Skipping initialization.");
        }
    }
}
