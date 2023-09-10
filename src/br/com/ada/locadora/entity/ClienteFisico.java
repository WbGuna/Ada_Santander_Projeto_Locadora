package br.com.ada.locadora.entity;

import java.util.Objects;

import br.com.ada.locadora.enuns.TipoPessoa;

public class ClienteFisico extends Pessoa{
	
	private static Integer ultimoIdentificador = 0;
	private Integer identificador;
	private String nome;
    private Integer idade;
    private String numeroCarteira;
    private String cpf;
    
    public ClienteFisico(String nome, Integer idade, String numeroCarteira, String cpf) {
        super(TipoPessoa.FISICA);
        this.identificador = ++ultimoIdentificador;
        this.nome = nome;
        this.idade = idade;
        this.numeroCarteira = numeroCarteira;
        this.cpf = cpf;
    }

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getNumeroCarteira() {
		return numeroCarteira;
	}

	public void setNumeroCarteira(String numeroCarteira) {
		this.numeroCarteira = numeroCarteira;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, idade, identificador, nome, numeroCarteira);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteFisico other = (ClienteFisico) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(idade, other.idade)
				&& Objects.equals(identificador, other.identificador) && Objects.equals(nome, other.nome)
				&& Objects.equals(numeroCarteira, other.numeroCarteira);
	}

	@Override
	public String toString() {
		return nome + " - " + super.getTipoPessoa();
	}  
}
