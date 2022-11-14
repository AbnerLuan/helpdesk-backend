package com.abner.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abner.helpdesk.domain.OrdemServico;
import com.abner.helpdesk.domain.Cliente;
import com.abner.helpdesk.domain.Funcionario;
import com.abner.helpdesk.domain.dtos.OrdemServicoDTO;
import com.abner.helpdesk.domain.enums.Prioridade;
import com.abner.helpdesk.domain.enums.Status;
import com.abner.helpdesk.repositories.OrdemServicoRepository;
import com.abner.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository repository;
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private ClienteService clienteService;

	public OrdemServico findById(Integer id) {
		Optional<OrdemServico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<OrdemServico> findAll() {
		return repository.findAll();
	}

	public OrdemServico create(OrdemServicoDTO obj) {
		return repository.save(newOrdemServico(obj));
	}

	public OrdemServico update(Integer id, @Valid OrdemServicoDTO objDTO) {
		objDTO.setId(id);
		OrdemServico oldObj = findById(id);
		oldObj = newOrdemServico(objDTO);
		return repository.save(oldObj);
	}

	private OrdemServico newOrdemServico(OrdemServicoDTO obj) {
		Funcionario funcionario = funcionarioService.findById(obj.getFuncionario());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		OrdemServico ordemServico = new OrdemServico();
		if(obj.getId() != null) {
			ordemServico.setId(obj.getId());
		}
		
		if(obj.getStatus().equals(2)) {
			ordemServico.setDataFechamento(LocalDate.now());
		}
		
		ordemServico.setFuncionario(funcionario);
		ordemServico.setCliente(cliente);
		ordemServico.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		ordemServico.setStatus(Status.toEnum(obj.getStatus()));
		ordemServico.setTitulo(obj.getTitulo());
		ordemServico.setObservacoes(obj.getObservacoes());
		return ordemServico;
	}

}
