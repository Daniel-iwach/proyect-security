package com.securityapp.spring_security.config;
import com.securityapp.spring_security.persistence.model.ERol;
import com.securityapp.spring_security.persistence.model.RoleEntity;
import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.service.impl.RolServiceImpl;
import com.securityapp.spring_security.service.impl.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolServiceImpl rolService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadRoles();
        loadTestUsers();
    }

    @PostConstruct
    private void loadRoles() {
        for (ERol roleName : ERol.values()) {
            if (!rolService.existsByName(roleName)) {
                rolService.save(new RoleEntity(null, roleName));
            }
        }
    }

    private void loadTestUsers() {
        UserDTO admin = new UserDTO(
                null,
                "Admin",
                passwordEncoder.encode("admin123"),
                "admin@email.com",
                Set.of("ADMIN")
        );
        UserDTO user = new UserDTO(
                null,
                "Usuario",
                passwordEncoder.encode("user123"),
                "user@email.com",
                Set.of("USER")
        );
        userService.save(admin);
        userService.save(user);
    }
}
