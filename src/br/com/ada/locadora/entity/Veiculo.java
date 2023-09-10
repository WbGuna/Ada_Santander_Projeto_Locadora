package br.com.ada.locadora.entity;

import java.util.Objects;

import br.com.ada.locadora.enuns.TipoVeiculo;

public class Veiculo {

	private static Integer ultimoIdentificador = 0;
	private Integer identificador;
	private TipoVeiculo tipoVeiculo;
	private String marca;
	private String modelo;
	private Integer ano;
	private Double kilometragem;
	private Boolean estaAlugado;

	public Veiculo(TipoVeiculo tipoVeiculo, String marca, String modelo, Integer ano,
			Double kilometragem) {
		this.identificador = ++ultimoIdentificador;
		this.tipoVeiculo = tipoVeiculo;
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
		this.kilometragem = kilometragem;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Double getKilometragem() {
		return kilometragem;
	}

	public void setKilometragem(Double kilometragem) {
		this.kilometragem = kilometragem;
	}

	public Boolean getEstaAlugado() {
		return estaAlugado;
	}

	public void setEstaAlugado(Boolean estaAlugado) {
		this.estaAlugado = estaAlugado;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ano, estaAlugado, identificador, kilometragem, marca, modelo, tipoVeiculo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(estaAlugado, other.estaAlugado)
				&& Objects.equals(identificador, other.identificador)
				&& Objects.equals(kilometragem, other.kilometragem) && Objects.equals(marca, other.marca)
				&& Objects.equals(modelo, other.modelo) && tipoVeiculo == other.tipoVeiculo;
	}

	@Override
	public String toString() {
		return marca + "-" + modelo;
	}

	
}
