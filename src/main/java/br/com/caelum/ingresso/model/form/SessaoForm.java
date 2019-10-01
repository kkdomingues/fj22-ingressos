package br.com.caelum.ingresso.model.form;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class SessaoForm {
	
	@NotNull
	private Integer filmeId;
	
	@NotNull
	private Integer salaId;
	
	@NotNull
	@DateTimeFormat(pattern="HH:mm")   //essa anotação te faz escolher o tipo de formato voce quer para o seu horario///data
	private LocalTime horario;

	public Integer getFilmeId() {
		return filmeId;
	}

	public void setFilmeId(Integer filmeId) {
		this.filmeId = filmeId;
	}

	public Integer getSalaId() {
		return salaId;
	}

	public void setSalaId(Integer salaId) {
		this.salaId = salaId;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}
	
	public Sessao toSessao(FilmeDao filmeDao, SalaDao salaDao) {
		Sala sala = salaDao.findOne(salaId);
		Filme filme = filmeDao.findOne(filmeId);
		
		Sessao sessao =  new Sessao(filme, sala, horario);
		sessao.setId(salaId);
		return sessao; 
		
		
	}

}
