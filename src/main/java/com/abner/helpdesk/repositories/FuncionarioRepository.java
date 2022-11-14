package com.abner.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abner.helpdesk.domain.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{

}
