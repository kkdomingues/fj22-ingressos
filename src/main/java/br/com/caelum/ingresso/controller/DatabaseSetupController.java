package br.com.caelum.ingresso.controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Controller

@RequestMapping("/setup")
public class DatabaseSetupController {

	@Autowired
	private FilmeDao filmeDao;

	@Autowired
	private SalaDao salaDao;

	@Autowired
	private SessaoDao sessaoDao;

	@GetMapping("/filmes")

	@Transactional
	public String setupFilmes() {
		if (filmeDao.findAll().size() == 0) {
			List.of(new Filme("Blade Runner", Duration.ofMinutes(150), "Sci-fi", BigDecimal.valueOf(15.0)),
					new Filme("Mulan", Duration.ofMinutes(90), "Animação", BigDecimal.valueOf(14.0)),
					new Filme("Kill Bill", Duration.ofMinutes(120), "Ação", BigDecimal.valueOf(13.0)),
					new Filme("Inception", Duration.ofMinutes(130), "Sci-fi", BigDecimal.valueOf(11.0)),
					new Filme("Toy Story 2", Duration.ofMinutes(130), "Animação", BigDecimal.valueOf(10.0)),
					new Filme("Titanic", Duration.ofMinutes(180), "Drama", BigDecimal.valueOf(16.0)),
					new Filme("It", Duration.ofMinutes(130), "Terror", BigDecimal.valueOf(15.0)),
					new Filme("Aliens", Duration.ofMinutes(130), "Sci-fi", BigDecimal.valueOf(13.0)),
					new Filme("Contact", Duration.ofMinutes(120), "Sci-fi", BigDecimal.valueOf(14.0))).stream()
					.forEach(filmeDao::save);
		}

		return "redirect:/";
	}

	@GetMapping("/salas")
	@Transactional
	public String setupSalas() {
		if (salaDao.findAll().size() == 0) {
			List.of(new Sala("Sala 1", BigDecimal.valueOf(10.0)), new Sala("Sala 2", BigDecimal.valueOf(9.0)),
					new Sala("Sala 3", BigDecimal.valueOf(8.0)), new Sala("Sala 3D", BigDecimal.valueOf(15.0)),
					new Sala("Sala 5D", BigDecimal.valueOf(20.0))).stream().forEach(salaDao::save);
		}
		return "redirect:/";
	}

	@GetMapping("/sessoes")
	@Transactional
	public String setupSessoes() {
		List<Sala> salas = salaDao.findAll();
		List<Filme> filmes = filmeDao.findAll();
		List<Sessao> sessoes = sessaoDao.findAll();
		if (sessoes.size() == 0 && filmes.size() > 0 && salas.size() > 0) {
			int numDeSessoes = 6;
			int numFilmes = filmes.size();
			for (Sala sala : salas) {
				LocalTime horarioDoFilme = LocalTime.parse("06:00:00");
				int[] indiceDeFilmes = new Random().ints(numDeSessoes, 0, numFilmes - 1).toArray();
				
				for (int i : indiceDeFilmes) {
					Filme filme = filmes.get(i);
					sessoes.add(new Sessao(filme, sala, horarioDoFilme));
					horarioDoFilme = horarioDoFilme.plus(filme.getDuracao());
				}
			}
			sessoes.stream().forEach(sessaoDao::save);
		}

		return "redirect:/";
	}

}