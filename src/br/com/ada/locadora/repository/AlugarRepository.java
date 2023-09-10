package br.com.ada.locadora.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ada.locadora.entity.Alugar;
import br.com.ada.locadora.entity.Veiculo;

public class AlugarRepository {

	private List<Alugar> alugueis;
	private VeiculoRepository veiculoRepository;

	public AlugarRepository(VeiculoRepository veiculoRepository) {
		this.alugueis = new ArrayList<>();
		this.veiculoRepository = veiculoRepository;
	}

	public void cadastrar(Alugar alugar) {
		alugueis.add(alugar);
		Veiculo veiculo = alugar.getVeiculo();
		veiculo.setEstaAlugado(true);
		veiculoRepository.alterar(veiculo);
	}

	public void alterar(Alugar alugar) {
		for (int i = 0; i < alugueis.size(); i++) {
			if (alugueis.get(i).getIdentificador().equals(alugar.getIdentificador())) {
				alugueis.set(i, alugar);
				break;
			}
		}
	}

	public Alugar buscarPorIdentificador(Integer id) {
		for (Alugar alugar : alugueis) {
			if (alugar.getIdentificador().equals(id)) {
				return alugar;
			}
		}
		return null;
	}

	public List<Alugar> buscarPorDataAluguel(String dataAluguel) {
		List<Alugar> result = new ArrayList<>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = dateFormat.parse(dataAluguel);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (Alugar alugar : alugueis) {
			if (alugar.getDataAluguel().equals(date)) {
				result.add(alugar);
			}
		}
		return result;
	}

	public List<Alugar> buscarTodos() {
		return new ArrayList<>(alugueis);
	}

	public void deletar(Integer id) {
		Alugar alugar = buscarPorIdentificador(id);
		if (alugar != null) {
			Veiculo veiculo = alugar.getVeiculo();
			veiculo.setEstaAlugado(false);
			veiculoRepository.alterar(veiculo);
			alugueis.remove(alugar);
		}
	}
}
