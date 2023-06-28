package org.exercicio.banco.template.persistencia.database.model;

import java.math.BigDecimal;
import java.util.List;

public interface IConta {
	
	public boolean isStatus();
	public Integer getNumeroConta();
	public BigDecimal getSaldo();
	public void depositar(BigDecimal quantia);
	public void transferir(IConta destino, BigDecimal quantia);
	public void sacar (BigDecimal quantia);
	public void setSaldo(BigDecimal add);
	public List <RegistroTransacao> getTransacoes();
	
	
	

}
