package com.example.api_java.service.impl;

import com.example.api_java.model.entity.Role;
import com.example.api_java.repository.IRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl {
    private final IRoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).get();
    }

    public void save(Role entity) {
        roleRepository.save(entity);
    }

    public void delete(Role entity) {
        roleRepository.delete(entity);
    }
}
