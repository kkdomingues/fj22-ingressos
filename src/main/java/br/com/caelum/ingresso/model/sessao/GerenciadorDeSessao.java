package br.com.caelum.ingresso.model.sessao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessoesDaSala;

	public GerenciadorDeSessao(List<Sessao> sessoesDaSala) {
		this.sessoesDaSala = sessoesDaSala;
	}

	public boolean cabe(Sessao sessaoNova) {
		if (terminaAmanha(sessaoNova)) {

			return false;
		}
		return sessoesDaSala.stream().noneMatch(sessaoExistente -> horarioIsConflitante(sessaoExistente, sessaoNova));
	}

	private boolean terminaAmanha(Sessao sessao) {

		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(sessao);
		LocalDateTime ultimoSegundoDeHoje = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

		if (terminoSessaoNova.isAfter(ultimoSegundoDeHoje)) {
			return true;
		}

		return false;

	}

	private boolean horarioIsConflitante(Sessao sessaoExistente, Sessao sessaoNova) {

		LocalDateTime inicioSEssaoExistente = getInicioSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime terminoSEssaoExistente = getTerminoSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(sessaoNova);
		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(sessaoNova);

		boolean sessaoNovaTerminaAntesDaExistente = terminoSessaoNova.isBefore(inicioSEssaoExistente);

		boolean sessaoNOvaComecaDepoisDaExistente = terminoSEssaoExistente.isBefore(inicioSessaoNova);

		if (sessaoNovaTerminaAntesDaExistente || sessaoNOvaComecaDepoisDaExistente) {
			return false;
		}

		return true;

	}

	private LocalDateTime getInicioSessaoComDiaDeHoje(Sessao sessao) {
		LocalDate hoje = LocalDate.now();
		return sessao.getHorario().atDate(hoje);
	}

	private LocalDateTime getTerminoSessaoComDiaDeHoje(Sessao sessao) {
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(sessao);

		return inicioSessaoNova.plus(sessao.getFilme().getDuracao());
	}

}
