package br.com.ada.locadora.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.ada.locadora.entity.ClienteFisico;

public class ClienteFisicoRepository {

	private List<ClienteFisico> clientesFisicos;

	public ClienteFisicoRepository() {
		this.clientesFisicos = new ArrayList<>();
	}

	public void cadastrar(ClienteFisico clienteFisico) {
		clientesFisicos.add(clienteFisico);
	}

	public void alterar(ClienteFisico clienteFisico) {
		for (int i = 0; i < clientesFisicos.size(); i++) {
			if (clientesFisicos.get(i).getIdentificador().equals(clienteFisico.getIdentificador())) {
				clientesFisicos.set(i, clienteFisico);
				break;
			}
		}
	}

	public ClienteFisico buscarPorIdentificador(Integer id) {
		for (ClienteFisico clienteFisico : clientesFisicos) {
			if (clienteFisico.getIdentificador().equals(id)) {
				return clienteFisico;
			}
		}
		return null;
	}

	public List<ClienteFisico> buscarPorCPF(String cpf) {
		List<ClienteFisico> result = new ArrayList<>();
		for (ClienteFisico clienteFisico : clientesFisicos) {
			if (clienteFisico.getCpf().contains(cpf)) {
				result.add(clienteFisico);
			}
		}
		return result;
	}
	
	public void deletar(Integer id) {
	    for (int i = 0; i < clientesFisicos.size(); i++) {
	        if (clientesFisicos.get(i).getIdentificador().equals(id)) {
	            clientesFisicos.remove(i);
	            break;
	        }
	    }
	}
	
	public List<ClienteFisico> buscarTodos() {
	    return new ArrayList<>(clientesFisicos);
	}
}
