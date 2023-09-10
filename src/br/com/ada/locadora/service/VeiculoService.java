package br.com.ada.locadora.service;

import java.util.List;

import br.com.ada.locadora.entity.Veiculo;
import br.com.ada.locadora.repository.VeiculoRepository;

public class VeiculoService {

	private VeiculoRepository veiculoRepository;

	public VeiculoService() {
		this.veiculoRepository = new VeiculoRepository();
	}

	public void cadastrar(Veiculo veiculo) {
		veiculoRepository.cadastrar(veiculo);
	}

	public void alterar(Veiculo veiculo) {
		veiculoRepository.alterar(veiculo);
	}

	public Veiculo buscarPorIdentificador(Integer id) {
		return veiculoRepository.buscarPorIdentificador(id);
	}

	public List<Veiculo> buscarPorNome(String nome) {
		return veiculoRepository.buscarPorNome(nome);
	}

	public void deletar(Integer id) {
		veiculoRepository.deletar(id);
	}
	
	public List<Veiculo> buscarTodos() {
	    return veiculoRepository.buscarTodos();
	}
}
