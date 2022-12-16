package com.abner.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abner.helpdesk.domain.Pessoa;
import com.abner.helpdesk.domain.Tecnico;
import com.abner.helpdesk.domain.dtos.TecnicoDTO;
import com.abner.helpdesk.repositories.PessoaRepository;
import com.abner.helpdesk.repositories.TecnicoRepository;
import com.abner.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.abner.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder; 

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objecto não encontrado! ID: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);

	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if (obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("Tecnico possui ordens de serviço e não pode ser deletado!");
		} else {
			repository.deleteById(id);
		}

	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
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
