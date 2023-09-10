package br.com.ada.locadora.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.ada.locadora.entity.Alugar;
import br.com.ada.locadora.entity.ClienteFisico;
import br.com.ada.locadora.entity.ClienteJuridico;
import br.com.ada.locadora.entity.Pessoa;
import br.com.ada.locadora.entity.Veiculo;
import br.com.ada.locadora.enuns.TipoVeiculo;
import br.com.ada.locadora.repository.AlugarRepository;
import br.com.ada.locadora.repository.VeiculoRepository;

public class AlugarService {

	private AlugarRepository alugarRepository;
	@SuppressWarnings("unused")
	private VeiculoRepository veiculoRepository;

	public AlugarService(AlugarRepository alugarRepository, VeiculoRepository veiculoRepository) {
		this.alugarRepository = alugarRepository;
		this.veiculoRepository = veiculoRepository;
	}

	public void alugar(Pessoa pessoa, Veiculo veiculo, String dataAluguel, String entregaPrevista) {
		Alugar alugar = new Alugar(pessoa, veiculo, dataAluguel, entregaPrevista);
		long diffInMillies = Math.abs(alugar.getEntregaPrevista().getTime() - alugar.getDataAluguel().getTime());
		long qntDiasAluguel = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		alugar.setQntDiasAluguel((int) qntDiasAluguel);
		alugarRepository.cadastrar(alugar);
	}

	public Alugar devolver(Integer id, String dataEntrega) {
	    Alugar alugar = alugarRepository.buscarPorIdentificador(id);
	    if (alugar != null) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        try {
	            alugar.setDataEntrega(dateFormat.parse(dataEntrega));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        long diffInMillies = Math.abs(alugar.getDataEntrega().getTime() - alugar.getDataAluguel().getTime());
	        long qntDiasAluguel = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	        if (alugar.getDataEntrega().equals(alugar.getEntregaPrevista())) {
	            qntDiasAluguel = qntDiasAluguel + 1L;
	        } else {
	        	qntDiasAluguel = qntDiasAluguel + 1L;
	        }
	        TipoVeiculo tipoVeiculo = alugar.getVeiculo().getTipoVeiculo();
	        BigDecimal valorPorDia = BigDecimal.ZERO;
	        switch (tipoVeiculo) {
	        case PEQUENO:
	        	valorPorDia = new BigDecimal("100.00");
	        	break;
	        case MEDIO:
	        	valorPorDia = new BigDecimal("150.00");
	        	break;
	        case SUV:
	        	valorPorDia = new BigDecimal("200.00");
	        	break;
	        }
	        BigDecimal valorTotal = valorPorDia.multiply(new BigDecimal(qntDiasAluguel));
	        if (alugar.getPessoa() instanceof ClienteFisico && qntDiasAluguel > 5) {
	        	BigDecimal desconto = valorTotal.multiply(new BigDecimal("0.05"));
	        	alugar.setDesconto(desconto);
	        	valorTotal = valorTotal.subtract(desconto);
	        } else if (alugar.getPessoa() instanceof ClienteJuridico && qntDiasAluguel > 3) {
	        	BigDecimal desconto = valorTotal.multiply(new BigDecimal("0.10"));
	        	alugar.setDesconto(desconto);
	        	valorTotal = valorTotal.subtract(desconto);
	        }
	        alugar.setValorTotal(valorTotal);
	        alugar.setQntDiasAluguel((int)qntDiasAluguel);
	        alugarRepository.alterar(alugar);
	        alugarRepository.deletar(id);
	    }
	    return alugar;
	}

	public List<Veiculo> buscarVeiculosAlugados(List<Veiculo> list) {
		List<Veiculo> lista = new ArrayList<>();
		List<Veiculo> atual = list;
		if(!atual.isEmpty()) {
			for (int i = 0; i < atual.size(); i++) {
				Veiculo veiculo = atual.get(i);
				if(veiculo.getEstaAlugado() != null && veiculo.getEstaAlugado()) {
					lista.add(veiculo);
				} else {
					veiculo.setEstaAlugado(false);
				}
			}
		}
		return lista;
	}

	public List<Veiculo> buscarVeiculosDisponiveis(List<Veiculo> list) {
		List<Veiculo> lista = new ArrayList<>();
		List<Veiculo> atual = list;
		if(!atual.isEmpty()) {
			for (int i = 0; i < atual.size(); i++) {
				Veiculo veiculo = atual.get(i);
				if(!veiculo.getEstaAlugado()) {
					lista.add(veiculo);
				}
			}
		}
		return lista;
	}

	public Alugar buscarPorIdentificador(Integer id) {
		return alugarRepository.buscarPorIdentificador(id);
	}

	public List<Alugar> buscarPorDataAluguel(String dataAluguel) {
		return alugarRepository.buscarPorDataAluguel(dataAluguel);
	}
	
	public List<Alugar> buscarTodos() {
	    return alugarRepository.buscarTodos();
	}
}
