package br.com.ada.locadora.service;

import java.util.List;

import br.com.ada.locadora.entity.ClienteFisico;
import br.com.ada.locadora.repository.ClienteFisicoRepository;

public class ClienteFisicoService {

	private ClienteFisicoRepository clienteFisicoRepository;

	public ClienteFisicoService() {
		this.clienteFisicoRepository = new ClienteFisicoRepository();
	}

	public void cadastrar(ClienteFisico clienteFisico) {
		clienteFisicoRepository.cadastrar(clienteFisico);
	}

	public void alterar(ClienteFisico clienteFisico) {
		clienteFisicoRepository.alterar(clienteFisico);
	}

	public ClienteFisico buscarPorIdentificador(Integer id) {
		return clienteFisicoRepository.buscarPorIdentificador(id);
	}

	public List<ClienteFisico> buscarPorCPF(String cpf) {
		return clienteFisicoRepository.buscarPorCPF(cpf);
	}

	public void deletar(Integer id) {
		clienteFisicoRepository.deletar(id);
	}
	
	public List<ClienteFisico> buscarTodos() {
	    return clienteFisicoRepository.buscarTodos();
	}
}
