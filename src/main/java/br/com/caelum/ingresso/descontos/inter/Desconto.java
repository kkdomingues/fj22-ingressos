package br.com.caelum.ingresso.descontos.inter;

import java.math.BigDecimal;

public interface Desconto {
	
	public BigDecimal aplicarDescontosSobre(BigDecimal precoOriginal);

}
