package br.com.caelum.ingresso.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.descontos.impl.TipoDeIngresso;
import br.com.caelum.ingresso.model.ImagemCapa;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.model.sessao.GerenciadorDeSessao;
import br.com.caelum.ingresso.rest.OmdbClient;

@Controller
public class SessaoController {

	@Autowired
	private FilmeDao filmeDao;

	@Autowired
	private SalaDao salaDao;

	@Autowired
	private SessaoDao sessaoDao;

	@Autowired
	private OmdbClient client;

	@GetMapping({ "/admin/sessao", "/admin/sessao/{id}" })
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {

		form.setSalaId(salaId);

		ModelAndView ModelAndView = new ModelAndView("sessao/sessao");

		ModelAndView.addObject("filmes", filmeDao.findAll());
		ModelAndView.addObject("sala", salaDao.findOne(salaId));
		ModelAndView.addObject("form", form);

		return ModelAndView;

	}

	@PostMapping("/admin/sessao")
	@Transactional
	public ModelAndView salva(@Valid SessaoForm form, BindingResult result) { // voce tem que ter o mesmo numero de
																				// validacao e resultado

		if (result.hasErrors())
			return form(form.getSalaId(), form);

		form.getSalaId();

		Sessao sessao = form.toSessao(filmeDao, salaDao);

		List<Sessao> sessaoDaSala = sessaoDao.buscaSessaoDa(sessao.getSala());

		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessaoDaSala);

		if (gerenciador.cabe(sessao)) {
			sessaoDao.save(sessao);
			return new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");

		}
		return form(form.getSalaId(), form);

	}

	@GetMapping("/sessao/{id}/lugares")
	public ModelAndView lugaresNaSessao(@PathVariable("id") Integer sessaoId) {
		ModelAndView modelAndView = new ModelAndView("sessao/lugares");

		Sessao sessao = sessaoDao.findOne(sessaoId);
		Optional<ImagemCapa> imagemCapa = client.request(sessao.getFilme(), ImagemCapa.class);

		modelAndView.addObject("sessao", sessao);
		modelAndView.addObject("imagemCapa", imagemCapa.orElse(new ImagemCapa()));
		modelAndView.addObject("tiposDeIngresso", TipoDeIngresso.values());

		return modelAndView;
	}

}