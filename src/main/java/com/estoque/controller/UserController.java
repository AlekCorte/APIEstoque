package com.estoque.controller;

import com.estoque.model.Role;
import com.estoque.model.User;
import com.estoque.repository.RoleRepository;
import com.estoque.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User buscarPorId(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public User atualizarUsuario(@PathVariable Long id, @RequestBody User atualizado) {
        User existente = userRepository.findById(id).orElseThrow();
        existente.setName(atualizado.getUsername()); // Corrigido
        existente.setEmail(atualizado.getEmail());
        return userRepository.save(existente);
    }


    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}/roles")
    public User atualizarRoles(@PathVariable Long id, @RequestBody Set<String> roles) {
        User usuario = userRepository.findById(id).orElseThrow();

        Set<Role> novasRoles = new HashSet<>();
        for (String nomeRole : roles) {
            Role role = roleRepository.findByName(nomeRole)
                    .orElseThrow(() -> new RuntimeException("Role não encontrada: " + nomeRole));
            novasRoles.add(role);
        }

        usuario.setRoles(novasRoles);
        return userRepository.save(usuario);
    }
}
