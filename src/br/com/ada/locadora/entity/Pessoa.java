package br.com.ada.locadora.entity;

import br.com.ada.locadora.enuns.TipoPessoa;

public abstract class Pessoa {

	private TipoPessoa tipoPessoa;

	public Pessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}
}
