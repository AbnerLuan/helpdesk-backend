package com.abner.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abner.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{
	


}
