package com.abner.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abner.helpdesk.domain.Cliente;
import com.abner.helpdesk.domain.Pessoa;
import com.abner.helpdesk.domain.dtos.ClienteDTO;
import com.abner.helpdesk.repositories.ClienteRepository;
import com.abner.helpdesk.repositories.PessoaRepository;
import com.abner.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.abner.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder; 

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objecto não encontrado! ID: " + id));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return repository.save(newObj);
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);

	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		if (obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		} else {
			repository.deleteById(id);
		}

	}

	private void validaPorCpfEEmail(ClienteDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF ja cadastrado no sistema");
		}

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail ja cadastrado no sistema");
		}

	}

}
