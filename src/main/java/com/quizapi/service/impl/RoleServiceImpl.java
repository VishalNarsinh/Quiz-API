package com.quizapi.service.impl;

import com.quizapi.entity.Role;
import com.quizapi.repository.RoleRepository;
import com.quizapi.service.RoleService;
import com.quizapi.utils.AppConstants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole() {
        Role adminRole = new Role(AppConstants.ADMIN_USER,"ROLE_ADMIN");
        Role userRole= new Role(AppConstants.NORMAL_USER,"ROLE_NORMAL");
        Role quizManagerRole = new Role(AppConstants.QUIZ_MANAGER_USER, "ROLE_QUIZ_MANAGER");
        List<Role> roles = List.of(adminRole,userRole,quizManagerRole);
        List<Role> result = roleRepository.saveAll(roles);
    }
}
