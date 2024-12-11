package ucs.OlymPro.controller;

import java.util.ArrayList;
import java.util.Collections;
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

public class DataController {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("dados");
	Utility util = new Utility();

	public void registerAthlete(String nome, String nacionalidade, String idadeS, boolean check)
			throws ExcecaoEspacoVazio, ExcecaoNotNumber, ExcecaoObjetoJaCadastrado {
		util.check(nome, nacionalidade);
		util.checkNum(idadeS);
		int idade = Integer.parseInt(idadeS);

		EntityManager manager = factory.createEntityManager();
		try {
			TypedQuery<Pais> queryPais = manager.createQuery("FROM Pais WHERE nome = :value", Pais.class);
			queryPais.setParameter("value", nacionalidade);
			Pais pais = queryPais.getSingleResult();

			// Verifica duplicatas
			String frase = "select count(*) from Athlete where name = :value";
			util.duplicates(manager, frase, nome);

			Athlete at = new Athlete(nome, nacionalidade, idade, check);
			at.setPais(pais);
			pais.addAtletas(at);

			// Inicia transação e salva
			manager.getTransaction().begin();
			manager.persist(at);
			manager.merge(pais);
			manager.getTransaction().commit();
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}

	public void registerTeam(String nome, String paisNome, boolean check) throws ExcecaoEspacoVazio, ExcecaoObjetoJaCadastrado {
		util.check(nome, paisNome);

		EntityManager manager = factory.createEntityManager();
		try {

			TypedQuery<Pais> queryPais = manager.createQuery("FROM Pais WHERE nome = :value", Pais.class);
			queryPais.setParameter("value", paisNome);
			Pais pais = queryPais.getSingleResult();
			
			Equipe tm = new Equipe(nome, pais, check);
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

	public void registerCountry(String nome) throws ExcecaoEspacoVazio, ExcecaoObjetoJaCadastrado {
		util.check(nome);
		String frase = "select count(*) from Pais where nome = :value";
		EntityManager manager = factory.createEntityManager();
		util.duplicates(manager, frase, nome);
		Pais pais = new Pais(nome);
		util.commit(manager, pais);
	}

	public Object[][] athleteToArray() {
		EntityManager manager = factory.createEntityManager();
		TypedQuery<Athlete> query = manager.createQuery("FROM Athlete", Athlete.class);
		List<Athlete> atletas = query.getResultList();
		Object[][] array = new Object[atletas.size()][2];
		for (int i = 0; i < atletas.size(); i++) {
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
		Object[][] teamsArray = new Object[teams.size()][2];
		for (int i = 0; i < teams.size(); i++) {
			if(teams.get(i).isVolei()) {
				teamsArray[i][0] = teams.get(i).getNome();
				teamsArray[i][1] = "Vôlei";	
			}else {				
				teamsArray[i][0] = teams.get(i).getNome();
				teamsArray[i][1] = "Futebol";	
			}
		}
		manager.close();
		return teamsArray;
	}

	public void deleteRelation(String teamName, String athleteName) {
		EntityManager manager = factory.createEntityManager();

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

	public void addAthleteToTeam(String teamName, String athleteName) throws IllegalStateException{
		EntityManager manager = factory.createEntityManager();

		TypedQuery<Athlete> qAthlete = manager.createQuery("FROM Athlete WHERE name = :value", Athlete.class);
		qAthlete.setParameter("value", athleteName);
		Athlete athlete = qAthlete.getSingleResult();

		// Verifica se o atleta é individual
		if (athlete.isIndividual()) {
			manager.close();
			throw new IllegalStateException("Este atleta compete individualmente e não pode ser cadastrado em uma equipe.");
		}

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

	public List<Equipe> getEquipesPorModalidade(String modalidade) {
		List<Equipe> equipes = null;
		EntityManager manager = factory.createEntityManager();
		try {
			boolean isVolei;
			if(modalidade.equals("Futebol")) {
				isVolei = false;
			}else {
				isVolei = true;
			}
			TypedQuery<Equipe> query = manager.createQuery(
				"FROM Equipe e WHERE e.isVolei = :isVolei", 
				Equipe.class
			);
			query.setParameter("isVolei", isVolei);
			equipes = query.getResultList();
			return equipes;
		} catch (Exception e) {
			e.printStackTrace();
			return equipes;
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
	
	public List<Athlete> getAtletasInd() {
		EntityManager manager = factory.createEntityManager();
		try {
			TypedQuery<Athlete> query = manager.createQuery(
				"FROM Athlete a WHERE a.isIndividual = true", 
				Athlete.class
			);
			return query.getResultList();
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
	
	public String[] getModalidadeArray() {
		EntityManager manager = factory.createEntityManager();

		try {
			TypedQuery<Modalidade> query = manager.createQuery("FROM Modalidade", Modalidade.class);
			List<Modalidade> modalidades = query.getResultList();
			
			String[] modalidadesArray = new String[modalidades.size()];
			
			for (int i = 0; i < modalidades.size(); i++) {
				modalidadesArray[i] = modalidades.get(i).getNome();
			}
			return modalidadesArray;
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
	
	public String[] getCountriesArray() {
		EntityManager manager = factory.createEntityManager();

		try {
			TypedQuery<Pais> query = manager.createQuery("FROM Pais", Pais.class);
			List<Pais> paises = query.getResultList();
			
			String[] paisesArray = new String[paises.size()];
			
			for (int i = 0; i < paises.size(); i++) {
				paisesArray[i] = paises.get(i).getNome();
			}
			return paisesArray;
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}

	public Modalidade getModalidade(String modalidade) {
		EntityManager manager = factory.createEntityManager();
		TypedQuery<Modalidade> query = manager.createQuery("FROM Modalidade WHERE nome = :value", Modalidade.class);
		query.setParameter("value", modalidade);
		return query.getSingleResult();
	}

	public Object[][] getCountriesData() {
		EntityManager manager = factory.createEntityManager();

		try {
			TypedQuery<Pais> query = manager.createQuery("FROM Pais", Pais.class);
			List<Pais> paises = query.getResultList();

			Object[][] dadosPaises = new Object[paises.size()][5];

			for (int i = 0; i < paises.size(); i++) {
				Pais pais = paises.get(i);
				dadosPaises[i][0] = pais.getNome();
				dadosPaises[i][1] = pais.getOuros();
				dadosPaises[i][2] = pais.getPratas();
				dadosPaises[i][3] = pais.getBronzes();
				dadosPaises[i][4] = pais.somaTotal();
			}

			return dadosPaises;
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}

	public Object[][] getTop5Paises() {
		EntityManager manager = factory.createEntityManager();
		try {
			// Buscar todos os países do banco
			TypedQuery<Pais> query = manager.createQuery("FROM Pais", Pais.class);
			List<Pais> paisesList = query.getResultList();

			// Ordenar países por medalhas
			Collections.sort(paisesList, (p1, p2) -> {
				int compareOuro = Integer.compare(p2.getOuros(), p1.getOuros());
				if (compareOuro != 0)
					return compareOuro;

				int comparePrata = Integer.compare(p2.getPratas(), p1.getPratas());
				if (comparePrata != 0)
					return comparePrata;

				return Integer.compare(p2.getBronzes(), p1.getBronzes());
			});

			// Criar array com top 5
			Object[][] dados = new Object[5][6];
			for (int i = 0; i < Math.min(5, paisesList.size()); i++) {
				Pais pais = paisesList.get(i);
				dados[i][0] = i + 1;
				dados[i][1] = pais.getNome();
				dados[i][2] = pais.getOuros();
				dados[i][3] = pais.getPratas();
				dados[i][4] = pais.getBronzes();
				dados[i][5] = pais.somaTotal();
			}

			// Preencher restante com zeros se necessário
			for (int i = paisesList.size(); i < 5; i++) {
				dados[i][0] = i + 1;
				dados[i][1] = "-";
				dados[i][2] = 0;
				dados[i][3] = 0;
				dados[i][4] = 0;
				dados[i][5] = 0;
			}

			return dados;
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}

	public void adicionarMedalha(String paisNome, String tipoMedalha) throws Exception {
		EntityManager manager = factory.createEntityManager();
		try {
			// Buscar o país
			TypedQuery<Pais> query = manager.createQuery("FROM Pais WHERE nome = :nome", Pais.class);
			query.setParameter("nome", paisNome);
			Pais pais = query.getSingleResult();

			// Atualizar medalhas
			manager.getTransaction().begin();
			switch (tipoMedalha.toLowerCase()) {
				case "ouro":
					pais.setOuros(pais.getOuros() + 1);
					break;
				case "prata":
					pais.setPratas(pais.getPratas() + 1);
					break;
				case "bronze":
					pais.setBronzes(pais.getBronzes() + 1);
					break;
				default:
					throw new Exception("Tipo de medalha inválido");
			}
			
			manager.merge(pais);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			throw new Exception("Erro ao adicionar medalha: " + e.getMessage());
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}

	public void removerMedalha(String paisNome, String tipoMedalha) throws Exception {
		EntityManager manager = factory.createEntityManager();
		try {
			// Buscar o país
			TypedQuery<Pais> query = manager.createQuery("FROM Pais WHERE nome = :nome", Pais.class);
			query.setParameter("nome", paisNome);
			Pais pais = query.getSingleResult();

			// Verificar se há medalhas para remover
			int medalhasAtuais = 0;
			switch (tipoMedalha.toLowerCase()) {
				case "ouro":
					medalhasAtuais = pais.getOuros();
					break;
				case "prata":
					medalhasAtuais = pais.getPratas();
					break;
				case "bronze":
					medalhasAtuais = pais.getBronzes();
					break;
			}

			if (medalhasAtuais <= 0) {
				throw new Exception("Não há medalhas deste tipo para remover");
			}

			// Atualizar medalhas
			manager.getTransaction().begin();
			switch (tipoMedalha.toLowerCase()) {
				case "ouro":
					pais.setOuros(pais.getOuros() - 1);
					break;
				case "prata":
					pais.setPratas(pais.getPratas() - 1);
					break;
				case "bronze":
					pais.setBronzes(pais.getBronzes() - 1);
					break;
				default:
					throw new Exception("Tipo de medalha inválido");
			}
			
			manager.merge(pais);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			throw new Exception("Erro ao remover medalha: " + e.getMessage());
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}

}
