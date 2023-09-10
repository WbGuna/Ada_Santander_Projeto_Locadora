package br.com.ada.locadora.entity;

import java.util.Objects;

import br.com.ada.locadora.enuns.TipoPessoa;

public class ClienteJuridico extends Pessoa {

	private static Integer ultimoIdentificador = 0;
	private Integer identificador;
	private String razaoSocial;
    private String nomeFuncionario;
    private String numeroCarteiraFuncionario;
    private String cnpj;
    
    public ClienteJuridico(String razaoSocial, String nomeFuncionario, String numeroCarteiraFuncionario, String cnpj) {
        super(TipoPessoa.JURIDICA);
        this.identificador = ++ultimoIdentificador;
        this.razaoSocial = razaoSocial;
        this.nomeFuncionario = nomeFuncionario;
        this.numeroCarteiraFuncionario = numeroCarteiraFuncionario;
        this.cnpj = cnpj;
    }

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getNumeroCarteiraFuncionario() {
		return numeroCarteiraFuncionario;
	}

	public void setNumeroCarteiraFuncionario(String numeroCarteiraFuncionario) {
		this.numeroCarteiraFuncionario = numeroCarteiraFuncionario;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, identificador, nomeFuncionario, numeroCarteiraFuncionario, razaoSocial);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteJuridico other = (ClienteJuridico) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(identificador, other.identificador)
				&& Objects.equals(nomeFuncionario, other.nomeFuncionario)
				&& Objects.equals(numeroCarteiraFuncionario, other.numeroCarteiraFuncionario)
				&& Objects.equals(razaoSocial, other.razaoSocial);
	}

	@Override
	public String toString() {
		return razaoSocial + " - " + super.getTipoPessoa();
	}
}
