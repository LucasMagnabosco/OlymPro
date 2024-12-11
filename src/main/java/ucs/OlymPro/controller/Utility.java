package ucs.OlymPro.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;


import ucs.OlymPro.exceptions.ExcecaoEspacoVazio;
import ucs.OlymPro.exceptions.ExcecaoNotNumber;
import ucs.OlymPro.exceptions.ExcecaoObjetoJaCadastrado;
import ucs.OlymPro.model.Modalidade;

public class Utility {
	public void commit(EntityManager manager, Object obj) {
		manager.getTransaction().begin();     
		manager.persist(obj);
		manager.getTransaction().commit();
	}
	public void duplicates(EntityManager manager, String frase, String value) throws ExcecaoObjetoJaCadastrado {
		javax.persistence.Query q = manager.createQuery(frase);
		q.setParameter("value", value);
		long count = (Long) q.getSingleResult();
		if(count > 0) {
			throw new ExcecaoObjetoJaCadastrado();
		}
	}
	public void duplicates(EntityManager manager, String frase, int value) throws ExcecaoObjetoJaCadastrado {
		javax.persistence.Query q = manager.createQuery(frase);
		q.setParameter("value", value);
		long count = (Long) q.getSingleResult();
		if(count > 0) {
			throw new ExcecaoObjetoJaCadastrado();
		}
	}
	
	public void check(String... infos) throws ExcecaoEspacoVazio {
		for(String info: infos) {
			if(info.isEmpty()) {
				throw new ExcecaoEspacoVazio();
			}
		}
	}
	public void checkNum(String... infos) throws ExcecaoEspacoVazio, ExcecaoNotNumber {
		for(String info : infos) {
			if(info == null || info.isEmpty()) {
				throw new ExcecaoEspacoVazio();
			}
		}
	}

	public void inicializarModalidades(EntityManagerFactory factory) {
		EntityManager manager = factory.createEntityManager();
        try {
            Query query = manager.createQuery("SELECT COUNT(m) FROM Modalidade m");
            Long count = (Long) query.getSingleResult();
            
            if (count == 0) {
                Modalidade natacao = new Modalidade("Natação", "Individual");
                Modalidade futebol = new Modalidade("Futebol", "Coletivo");
                Modalidade volei = new Modalidade("Vôlei", "Coletivo");
                
                manager.getTransaction().begin();
                manager.persist(natacao);
                manager.persist(futebol);
                manager.persist(volei);
                manager.getTransaction().commit();
            }
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
        }finally {
        	manager.close();
        }
    }
}
