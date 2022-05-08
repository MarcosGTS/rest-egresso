package com.egresso.ufma.controller;

import java.util.List;

import com.egresso.ufma.model.Usuario;
import com.egresso.ufma.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/client")
public class UsuarioController {
    @Autowired
    UserRepository uRepository;
    
    @GetMapping
    public List<Usuario> getMethodName() {
        return uRepository.findAll();
    }

    @PostMapping
    public Usuario postMethodName(@RequestBody Usuario entity) {
        return uRepository.save(entity);
    }
    
}
