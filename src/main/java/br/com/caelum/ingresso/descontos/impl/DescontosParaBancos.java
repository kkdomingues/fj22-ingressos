package br.com.caelum.ingresso.descontos.impl;

import java.math.BigDecimal;

public class DescontosParaBancos implements Desconto {

	@Override
	public BigDecimal aplicarDescontosSobre(BigDecimal precoOriginal) {
		return precoOriginal.multiply(new BigDecimal(".3"));
	}
	
	@Override
	public String getDescricao() {
		return "Desconto Banco";
	
	}
}
