package com.abner.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abner.helpdesk.domain.Chamado;
import com.abner.helpdesk.domain.Cliente;
import com.abner.helpdesk.domain.Tecnico;
import com.abner.helpdesk.domain.enums.Perfil;
import com.abner.helpdesk.domain.enums.Prioridade;
import com.abner.helpdesk.domain.enums.Status;
import com.abner.helpdesk.repositories.ChamadoRepository;
import com.abner.helpdesk.repositories.PessoaRepository;

@Service
public class DBService {

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {

		Tecnico tec1 = new Tecnico(null, "Vmt", "765.159.630-76", "vmt@mail.com", encoder.encode("1234"));
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Luan", "903.347.070-56", "luan@mail.com", encoder.encode("1234"));
		tec2.addPerfil(Perfil.ADMIN);
		Tecnico tec3 = new Tecnico(null, "Andrey", "271.068.470-54", "andrey@mail.com", encoder.encode("1234"));
		tec3.addPerfil(Perfil.ADMIN);
		Tecnico tec4 = new Tecnico(null, "Rogerinho Almeida", "162.720.120-39", "rogerinho@mail.com",
				encoder.encode("1234"));
		Tecnico tec5 = new Tecnico(null, "Jose", "778.556.170-27", "jose@mail.com", encoder.encode("1234"));

		Cliente cli1 = new Cliente(null, "Roberto", "111.661.890-74", "roberto@mail.com", encoder.encode("1234"));
		Cliente cli2 = new Cliente(null, "Maria", "322.429.140-06", "maria@mail.com", encoder.encode("1234"));
		Cliente cli3 = new Cliente(null, "Charles", "792.043.830-62", "charles@mail.com", encoder.encode("1234"));
		Cliente cli4 = new Cliente(null, "Paulo", "177.409.680-30", "paulo@mail.com", encoder.encode("1234"));
		Cliente cli5 = new Cliente(null, "Rui", "081.399.300-83", "Rui@mail.com", encoder.encode("1234"));

		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "C 1", "Teste os 1", tec2, cli2);
		Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "C 2", "Teste os 2", tec2, cli2);
		Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "C 3", "Teste os 3", tec3, cli3);
		Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "C 4", "Teste os 4", tec4, cli4);
		Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "C 5", "Teste os 5", tec5, cli5);
		Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "C 6", "Teste os 6", tec1, cli5);

		pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
	}

}
