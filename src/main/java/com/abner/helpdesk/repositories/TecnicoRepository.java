package com.abner.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abner.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
