package br.com.ada.locadora.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.ada.locadora.entity.ClienteJuridico;

public class ClienteJuridicoRepository {

	private List<ClienteJuridico> clientesJuridicos;

	public ClienteJuridicoRepository() {
		this.clientesJuridicos = new ArrayList<>();
	}

	public void cadastrar(ClienteJuridico clienteJuridico) {
		clientesJuridicos.add(clienteJuridico);
	}

	public void alterar(ClienteJuridico clienteJuridico) {
		for (int i = 0; i < clientesJuridicos.size(); i++) {
			if (clientesJuridicos.get(i).getIdentificador().equals(clienteJuridico.getIdentificador())) {
				clientesJuridicos.set(i, clienteJuridico);
				break;
			}
		}
	}

	public ClienteJuridico buscarPorIdentificador(Integer id) {
		for (ClienteJuridico clienteJuridico : clientesJuridicos) {
			if (clienteJuridico.getIdentificador().equals(id)) {
				return clienteJuridico;
			}
		}
		return null;
	}

	public List<ClienteJuridico> buscarPorCNPJ(String cnpj) {
		List<ClienteJuridico> result = new ArrayList<>();
		for (ClienteJuridico clienteJuridico : clientesJuridicos) {
			if (clienteJuridico.getCnpj().contains(cnpj)) {
				result.add(clienteJuridico);
			}
		}
		return result;
	}
	
	public void deletar(Integer id) {
	    for (int i = 0; i < clientesJuridicos.size(); i++) {
	        if (clientesJuridicos.get(i).getIdentificador().equals(id)) {
	            clientesJuridicos.remove(i);
	            break;
	        }
	    }
	}
	
	public List<ClienteJuridico> buscarTodos() {
	    return new ArrayList<>(clientesJuridicos);
	}
}
