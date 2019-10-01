package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Repository
public class SessaoDao {

	@PersistenceContext                /// o spring vai trabalhar em conjunto com o JPA -- Ele gerencia os dados no
	private EntityManager manager;

	public void save(Sessao sessao) {
		manager.persist(sessao);
	}

	public List<Sessao> buscaSessaoDa(Sala sala) {
		return manager.createQuery("select s from Sessao s where s.sala = :sala", Sessao.class)
				.setParameter("sala", sala).getResultList();

	}

	public List<Sessao> buscaSEssoesDoFilme(Filme filme) {
		return manager.createQuery("select s from Sessao s where s.filme = :filme", Sessao.class)
				.setParameter("filme", filme).getResultList();
	}

    public List<Sessao> findAll() {
        return manager.createQuery("select s from Sessao s", Sessao.class).getResultList();
    }

}
