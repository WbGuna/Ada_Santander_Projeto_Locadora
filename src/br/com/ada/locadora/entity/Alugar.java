package br.com.ada.locadora.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Alugar {

	private static Integer ultimoIdentificador = 0;
	private Integer identificador;
	private Pessoa pessoa;
	private Veiculo veiculo;
	private Date dataAluguel;
	private Date entregaPrevista;
	private Date dataEntrega;
	private BigDecimal valorTotal;
	private Integer qntDiasAluguel;
	private BigDecimal multaPorAtraso;
	private BigDecimal desconto;

	public Alugar(Pessoa pessoa, Veiculo veiculo, String dataAluguel, String entregaPrevista) {
		this.identificador = ++ultimoIdentificador;
		this.pessoa = pessoa;
		this.veiculo = veiculo;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.dataAluguel = dateFormat.parse(dataAluguel);
			this.entregaPrevista = dateFormat.parse(entregaPrevista);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Date getDataAluguel() {
		return dataAluguel;
	}

	public void setDataAluguel(Date dataAluguel) {
		this.dataAluguel = dataAluguel;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getQntDiasAluguel() {
		return qntDiasAluguel;
	}

	public void setQntDiasAluguel(Integer qntDiasAluguel) {
		this.qntDiasAluguel = qntDiasAluguel;
	}

	public BigDecimal getMultaPorAtraso() {
		return multaPorAtraso;
	}

	public void setMultaPorAtraso(BigDecimal multaPorAtraso) {
		this.multaPorAtraso = multaPorAtraso;
	}
	
	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Date getEntregaPrevista() {
		return entregaPrevista;
	}

	public void setEntregaPrevista(Date entregaPrevista) {
		this.entregaPrevista = entregaPrevista;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataAluguel, dataEntrega, desconto, entregaPrevista, identificador, multaPorAtraso, pessoa,
				qntDiasAluguel, valorTotal, veiculo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alugar other = (Alugar) obj;
		return Objects.equals(dataAluguel, other.dataAluguel) && Objects.equals(dataEntrega, other.dataEntrega)
				&& Objects.equals(desconto, other.desconto) && Objects.equals(entregaPrevista, other.entregaPrevista)
				&& Objects.equals(identificador, other.identificador)
				&& Objects.equals(multaPorAtraso, other.multaPorAtraso) && Objects.equals(pessoa, other.pessoa)
				&& Objects.equals(qntDiasAluguel, other.qntDiasAluguel) && Objects.equals(valorTotal, other.valorTotal)
				&& Objects.equals(veiculo, other.veiculo);
	}

	@Override
	public String toString() {
		return "Alugar [identificador=" + identificador + ", pessoa=" + pessoa + ", veiculo=" + veiculo
				+ ", dataAluguel=" + dataAluguel + ", entregaPrevista=" + entregaPrevista + ", dataEntrega="
				+ dataEntrega + ", valorTotal=" + valorTotal + ", qntDiasAluguel=" + qntDiasAluguel
				+ ", multaPorAtraso=" + multaPorAtraso + ", desconto=" + desconto + "]";
	}

}
