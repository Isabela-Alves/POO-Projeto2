package org.exercicio.banco.template.persistencia.database.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.exercicio.banco.template.persistencia.database.util.PersistenciaEmArquivo;

public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cpf;
	private String nome;
	
	private List<IConta> contas;
	
	public Cliente() {
		
	}
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		contas = new ArrayList<>();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<IConta> getContas() {
		return contas;
	}

	public void setContas(List<IConta> contas) {
		this.contas = contas;
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", nome=" + nome + ", contas=" + contas + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
    public void adicionarConta(IConta c) {
    	
    		if (contas.contains(c)) {
    			System.out.print("A conta jah estah associada a este cliente.");
    		}else {
    			this.contas.add(c);
    			PersistenciaEmArquivo.getInstance().salvarDadosEmArquivo();
    			System.out.print("Conta adicionada com sucesso!");
    		}
    }

    
    public void removerConta(IConta c) {

    	if(contas.contains(c)) {
    		this.contas.remove(c) ;
    		PersistenciaEmArquivo.getInstance().salvarDadosEmArquivo();
    		System.out.print("Conta removida com sucesso!");
    	} else {
    		System.out.print("A conta nao esta associada a este cliente.");
    	}
    }

    public IConta localizarContaNumero(int numero) {

		for (int i = 0; i < contas.size(); i++) {
			IConta c = contas.get(i);

			if (c.getNumeroConta() == numero) {
				System.out.print("Conta encontrada!");
				return c;
			}
		}
		System.out.print("Conta nao encontrada.");
		return null;
    }

    public double balancoEntreContas() {
    	
		double ValorSaldo = 0.0;
		for (int i = 0; i < contas.size(); i++) {
			IConta c = contas.get(i);
			ValorSaldo += c.getSaldo().doubleValue();
		}

		System.out.print("Balanco entre contas: RS" + ValorSaldo );
		return ValorSaldo;
    }

}
