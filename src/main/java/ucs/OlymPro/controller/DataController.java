package ucs.OlymPro.controller;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import ucs.OlymPro.exceptions.ExcecaoEspacoVazio;
import ucs.OlymPro.exceptions.ExcecaoNotNumber;
import ucs.OlymPro.exceptions.ExcecaoObjetoJaCadastrado;
import ucs.OlymPro.model.Athlete;
import ucs.OlymPro.model.Equipe;
import ucs.OlymPro.model.Modalidade;
import ucs.OlymPro.model.Pais;
import ucs.OlymPro.model.Resultado;

public class DataController {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("dados");
	Utility util = new Utility();
	
	public void registerAthlete( String nome, String nacionalidade, String idadeS) 
								throws ExcecaoEspacoVazio, ExcecaoNotNumber, ExcecaoObjetoJaCadastrado{
		util.check(nome, nacionalidade);
		util.checkNum(idadeS);
		int idade = Integer.parseInt(idadeS);
		
		EntityManager manager = factory.createEntityManager();
		try {
			TypedQuery<Pais> queryPais = manager.createQuery("FROM Pais WHERE nome = :value", Pais.class);
			queryPais.setParameter("value", nacionalidade);
			Pais pais = queryPais.getSingleResult();
			
			Athlete at = new Athlete(nome, nacionalidade, idade);
			at.setPais(pais);
			pais.addAtletas(at);
			
			// Verifica duplicatas
			String frase = "select count(*) from Athlete where nome = :value";
			util.duplicates(manager, frase, nome);
			
			// Inicia transação e salva
			manager.getTransaction().begin();
			manager.persist(at);
			manager.merge(pais);  // Merge do país para atualizar a relação
			manager.getTransaction().commit();
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
	
	public void registerTeam(String nome, String paisNome) throws ExcecaoEspacoVazio, ExcecaoObjetoJaCadastrado {
		util.check(nome, paisNome);
		
		EntityManager manager = factory.createEntityManager();
		try {

			TypedQuery<Pais> queryPais = manager.createQuery("FROM Pais WHERE nome = :value", Pais.class);
			queryPais.setParameter("value", paisNome);
			Pais pais = queryPais.getSingleResult();
			
			Equipe tm = new Equipe(nome, pais);
			tm.setPais(pais);
			pais.addEquipe(tm);
			
			String frase = "select count(*) from Equipe where nome = :value";
			util.duplicates(manager, frase, nome);
			
			manager.getTransaction().begin();
			manager.persist(tm);
			manager.merge(pais);
			manager.getTransaction().commit();
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
	
	 public void registerModality(String nome, String tipo) throws ExcecaoEspacoVazio, ExcecaoObjetoJaCadastrado {
	 	util.check(nome, tipo);
	 	Modalidade md = new Modalidade(nome, tipo);
	 	String frase = "select count(*) from Modality where modality_name = :value";
	 	EntityManager manager = factory.createEntityManager();
	 	util.duplicates(manager, frase, nome);
	 	util.commit(manager, md);
	 }
	
	public void registerCountry(String nome) throws ExcecaoEspacoVazio, ExcecaoNotNumber, ExcecaoObjetoJaCadastrado {
		util.check(nome);
		Pais pais = new Pais(nome);
		String frase = "select count(*) from Country where contry_name = :value";
		EntityManager manager = factory.createEntityManager();
		util.duplicates(manager, frase, nome);
		util.commit(manager, pais);
	}
	
	public void registerResult(Athlete at, Equipe tm, String tempoS, String etapa, LocalDate data) throws ExcecaoEspacoVazio, ExcecaoNotNumber {
		util.check(etapa);
		util.checkNum(tempoS);
		double tempo = Double.parseDouble(tempoS);
		Resultado res = new Resultado(tempo, etapa, data);
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(res);
		if (at !=null) {
			at.addTempo(res);
			manager.merge(at);
		}
		if(tm !=null) {
			tm.addTempo(res);
			manager.merge(tm);
		}
		manager.getTransaction().commit();
		manager.close();
	}
	
	public Object[][] athleteToArray() {
	    EntityManager manager = factory.createEntityManager();
	    TypedQuery<Athlete> query = manager.createQuery("FROM Athlete", Athlete.class);
	    List<Athlete> atletas = query.getResultList();
	    Object[][] array = new Object[atletas.size()][2];
	    for(int i=0; i<atletas.size(); i++) {
	        array[i][0] = atletas.get(i).getName();
	        Equipe equipe = atletas.get(i).getEquipe();
	        array[i][1] = equipe != null ? equipe.getNome() : "";
	    }
	    manager.close();
	    return array;
	}

	public Object[][] teamToArray() {
	    EntityManager manager = factory.createEntityManager();
	    TypedQuery<Equipe> query = manager.createQuery("FROM Equipe", Equipe.class);
	    List<Equipe> teams = query.getResultList();
	    Object[][] teamsArray = new Object[teams.size()][1];
	    for(int i=0; i<teams.size(); i++) {
	        teamsArray[i][0] = teams.get(i).getNome();
	    }
	    manager.close();
	    return teamsArray;
	}

	public void deleteRelation(String teamName, String athleteName) {
	    EntityManager manager = factory.createEntityManager();
	    Session session = manager.unwrap(Session.class);
	    
	    TypedQuery<Athlete> qAthlete = manager.createQuery("FROM Athlete WHERE name = :value", Athlete.class);
	    qAthlete.setParameter("value", athleteName);
	    Athlete athlete = qAthlete.getSingleResult();
	    
	    TypedQuery<Equipe> qTeam = manager.createQuery("FROM Equipe WHERE nome = :value", Equipe.class);
	    qTeam.setParameter("value", teamName);
	    Equipe team = qTeam.getSingleResult();
	    
	    // Remove a relação entre atleta e equipe
	    athlete.setEquipe(null);
	    team.removerAtleta(athlete);
	    
	    manager.getTransaction().begin();    
	    manager.merge(team);
	    manager.merge(athlete);
	    manager.getTransaction().commit();
	    manager.close();
	}

	public void deleteAthlete(String athleteName) {
	    EntityManager manager = factory.createEntityManager();
	    TypedQuery<Athlete> query = manager.createQuery("FROM Athlete WHERE name = :value", Athlete.class);
	    query.setParameter("value", athleteName);
	    Athlete athlete = query.getSingleResult();
	    
	    manager.getTransaction().begin();    
	    manager.remove(athlete);
	    manager.getTransaction().commit();
	    manager.close();
	}

	public void deleteTeam(String teamName) {
	    EntityManager manager = factory.createEntityManager();
	    TypedQuery<Equipe> query = manager.createQuery("FROM Equipe WHERE nome = :value", Equipe.class);
	    query.setParameter("value", teamName);
	    Equipe team = query.getSingleResult();
	    
	    manager.getTransaction().begin();    
	    manager.remove(team);
	    manager.getTransaction().commit();
	    manager.close();
	}

	public void deleteResult(Resultado resultado) {
	    EntityManager manager = factory.createEntityManager();     
	    manager.getTransaction().begin();    
	    Resultado temp = manager.find(Resultado.class, resultado.getId());
	    manager.remove(temp);
	    manager.getTransaction().commit();
	    manager.close();
	}
	
	public void addAthleteToTeam(String teamName, String athleteName) {
	    EntityManager manager = factory.createEntityManager();
	    
	    TypedQuery<Athlete> qAthlete = manager.createQuery("FROM Athlete WHERE name = :value", Athlete.class);
	    qAthlete.setParameter("value", athleteName);
	    Athlete athlete = qAthlete.getSingleResult();
	    
	    TypedQuery<Equipe> qTeam = manager.createQuery("FROM Equipe WHERE nome = :value", Equipe.class);
	    qTeam.setParameter("value", teamName);
	    Equipe team = qTeam.getSingleResult();
	    
	    manager.getTransaction().begin();
	    athlete.setEquipe(team);
	    manager.merge(athlete);
	    manager.merge(team);
	    manager.getTransaction().commit();
	    manager.close();
	}
	
	public String[] getCountriesArray() {
	    EntityManager manager = factory.createEntityManager();
	    TypedQuery<Pais> query = manager.createQuery("FROM Pais", Pais.class);
	    List<Pais> paises = query.getResultList();
	    String[] paisesArray = new String[paises.size()];
	    for(int i=0; i<paises.size(); i++) {
	        paisesArray[i] = paises.get(i).getNome();
	    }
	    manager.close();
	    return paisesArray;
	}
	
}
