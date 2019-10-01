package br.com.caelum.ingresso.descontos.impl;

import java.math.BigDecimal;

import br.com.caelum.ingresso.descontos.inter.Desconto;

public class DescontosParaBancos implements Desconto {

	@Override
	public BigDecimal aplicarDescontosSobre(BigDecimal precoOriginal) {
		return precoOriginal.multiply(new BigDecimal(".3"));
	}

}
