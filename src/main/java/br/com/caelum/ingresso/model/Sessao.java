package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Sessao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne // COMPORTAMENTO EAGER
	private Filme filme;

	@ManyToOne
	private Sala sala;

	@Override
	public String toString() {
		return "Sessao [id=" + id + ", filme=" + filme.getNome() + ", sala=" + sala.getNome() + ", horario=" + horario
				+ "]";
	}

	private LocalTime horario;

	private BigDecimal preco;

	/**
	 * @deprecated hibernate only
	 */

	public Sessao() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public BigDecimal getPreco() {
		return preco.setScale(2, RoundingMode.HALF_UP);
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Sessao(Filme filme, Sala sala, LocalTime horario) {
		this.filme = filme;
		this.sala = sala;
		this.horario = horario;
		this.preco = sala.getPreco().add(filme.getPreco());
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

}
