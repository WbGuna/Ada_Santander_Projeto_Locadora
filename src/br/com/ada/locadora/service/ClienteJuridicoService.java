package br.com.ada.locadora.service;

import java.util.List;

import br.com.ada.locadora.entity.ClienteJuridico;
import br.com.ada.locadora.repository.ClienteJuridicoRepository;

public class ClienteJuridicoService {

	private ClienteJuridicoRepository clienteJuridicoRepository;

	public ClienteJuridicoService() {
		this.clienteJuridicoRepository = new ClienteJuridicoRepository();
	}

	public void cadastrar(ClienteJuridico clienteJuridico) {
		clienteJuridicoRepository.cadastrar(clienteJuridico);
	}

	public void alterar(ClienteJuridico clienteJuridico) {
		clienteJuridicoRepository.alterar(clienteJuridico);
	}

	public ClienteJuridico buscarPorIdentificador(Integer id) {
		return clienteJuridicoRepository.buscarPorIdentificador(id);
	}

	public List<ClienteJuridico> buscarPorCNPJ(String cnpj) {
		return clienteJuridicoRepository.buscarPorCNPJ(cnpj);
	}

	public void deletar(Integer id) {
		clienteJuridicoRepository.deletar(id);
	}
	
	public List<ClienteJuridico> buscarTodos() {
	    return clienteJuridicoRepository.buscarTodos();
	}
}
