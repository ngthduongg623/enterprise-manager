package io.github.ngthduongg623.enterprise_manager.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.github.ngthduongg623.enterprise_manager.dao.RoleDao;
import io.github.ngthduongg623.enterprise_manager.dao.UserDao;
import io.github.ngthduongg623.enterprise_manager.entity.Role;
import io.github.ngthduongg623.enterprise_manager.entity.User;
import jakarta.persistence.EntityManager;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleDao roleDao;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public DataLoader(RoleDao roleDao, UserDao userDao, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // ensure roles exist
        if (roleDao.findRoleByName("ROLE_ADMIN") == null) {
            entityManager.persist(new Role("ROLE_ADMIN"));
        }
        if (roleDao.findRoleByName("ROLE_EMPLOYEE") == null) {
            entityManager.persist(new Role("ROLE_EMPLOYEE"));
        }

        // create default admin
        if (userDao.findByUserName("admin@example.com") == null) {
            Role adminRole = roleDao.findRoleByName("ROLE_ADMIN");
            User admin = new User();
            admin.setUserName("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            admin.setRoles(Arrays.asList(adminRole));
            userDao.save(admin);
        }

        // create some default employees for testing
        if (userDao.findByUserName("employee1@example.com") == null) {
            Role empRole = roleDao.findRoleByName("ROLE_EMPLOYEE");
            User e1 = new User();
            e1.setUserName("employee1@example.com");
            e1.setPassword(passwordEncoder.encode("password1"));
            e1.setEnabled(true);
            e1.setRoles(Arrays.asList(empRole));
            userDao.save(e1);
        }
        if (userDao.findByUserName("employee2@example.com") == null) {
            Role empRole = roleDao.findRoleByName("ROLE_EMPLOYEE");
            User e2 = new User();
            e2.setUserName("employee2@example.com");
            e2.setPassword(passwordEncoder.encode("password2"));
            e2.setEnabled(true);
            e2.setRoles(Arrays.asList(empRole));
            userDao.save(e2);
        }
    }
}
