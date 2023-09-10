package br.com.ada.locadora.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.ada.locadora.entity.Veiculo;

public class VeiculoRepository {

	private static VeiculoRepository instance;
	private List<Veiculo> veiculos;

	public VeiculoRepository() {
		this.veiculos = new ArrayList<>();
	}
	
	 public static VeiculoRepository getInstance() {
	        if (instance == null) {
	            instance = new VeiculoRepository();
	        }
	        return instance;
	    }

	public void cadastrar(Veiculo veiculo) {
		veiculo.setEstaAlugado(false);
		veiculos.add(veiculo);
	}

	public void alterar(Veiculo veiculo) {
		for (int i = 0; i < veiculos.size(); i++) {
			if (veiculos.get(i).getIdentificador().equals(veiculo.getIdentificador())) {
				veiculos.set(i, veiculo);
				break;
			}
		}
	}

	public Veiculo buscarPorIdentificador(Integer id) {
		for (Veiculo veiculo : veiculos) {
			if (veiculo.getIdentificador().equals(id)) {
				return veiculo;
			}
		}
		return null;
	}

	public List<Veiculo> buscarTodos() {
		return new ArrayList<>(veiculos);
	}

	public List<Veiculo> buscarPorNome(String nome) {
		List<Veiculo> result = new ArrayList<>();
		for (Veiculo veiculo : veiculos) {
			if (veiculo.getModelo().contains(nome)) {
				result.add(veiculo);
			}
		}
		return result;
	}
	
	public void deletar(Integer id) {
	    for (int i = 0; i < veiculos.size(); i++) {
	        if (veiculos.get(i).getIdentificador().equals(id)) {
	            veiculos.remove(i);
	            break;
	        }
	    }
	}
}
