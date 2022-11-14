package com.abner.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abner.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
