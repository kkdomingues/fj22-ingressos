package br.com.caelum.ingresso.descontos.impl;

import java.math.BigDecimal;

public enum TipoDeIngresso {

	INTEIRO(new SemDesconto()), 
	ESTUDANTE(new DescontosParaEstudantes()),
	BANCO(new DescontosParaBancos());

	private final Desconto desconto;

	TipoDeIngresso(Desconto desconto) {
		this.desconto = desconto;
	}

	public BigDecimal aplicarDescontosSobre(BigDecimal valor) {
		return desconto.aplicarDescontosSobre(valor);

	}

	public String getDescricao() {
		return desconto.getDescricao();
	}

}
