package br.com.caelum.ingresso.descontos.impl;

import java.math.BigDecimal;

public interface Desconto {
	
	public BigDecimal aplicarDescontosSobre(BigDecimal precoOriginal);

	public String getDescricao();

}
