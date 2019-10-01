package br.com.caelum.ingresso.descontos.impl;

import java.math.BigDecimal;

import br.com.caelum.ingresso.descontos.inter.Desconto;

public class DescontosParaEstudantes implements Desconto {

	@Override
	public BigDecimal aplicarDescontosSobre(BigDecimal precoOriginal) {
		return precoOriginal.divide(new BigDecimal("2.0"));
	}

}
