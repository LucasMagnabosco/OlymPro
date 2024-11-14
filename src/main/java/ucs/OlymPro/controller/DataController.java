package ucs.OlymPro.controller;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ucs.OlymPro.exceptions.ExcecaoEspacoVazio;
import ucs.OlymPro.exceptions.ExcecaoNotNumber;
import ucs.OlymPro.exceptions.ExcecaoObjetoJaCadastrado;
import ucs.OlymPro.model.Atleta;
import ucs.OlymPro.model.Equipe;
import ucs.OlymPro.model.Pais;

public class DataController {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("dados");
	Utility util = new Utility();
	
	public void registerAthlete( String nome, String nacionalidade, String idadeS) 
								throws ExcecaoEspacoVazio, ExcecaoNotNumber, ExcecaoObjetoJaCadastrado{
		util.check(nome, nacionalidade);
		util.checkNum(idadeS);
		int idade = Integer.parseInt(idadeS);
		Atleta at = new Atleta(nome, nacionalidade, idade);
		String frase = "select count(*) from Athlete where athlete_name = :value";
		EntityManager manager = factory.createEntityManager();
		util.duplicates(manager, frase, nome);
		util.commit(manager, at);
	}
	
	public void registerTeam(String nome, String pais) throws ExcecaoEspacoVazio, ExcecaoObjetoJaCadastrado {
		util.check(nome, pais);
		Equipe tm = new Equipe(nome, pais);
		String frase = "select count(*) from Team where team_name = :value";
		EntityManager manager = factory.createEntityManager();
		util.duplicates(manager, frase, nome);
		util.commit(manager, tm);
	}
	
	public void registerModal(String nome, String tipo) throws ExcecaoEspacoVazio, ExcecaoObjetoJaCadastrado {
		util.check(nome, tipo);
		Equipe md = new Equipe(nome, tipo);
		String frase = "select count(*) from Modality where modality_name = :value";
		EntityManager manager = factory.createEntityManager();
		util.duplicates(manager, frase, nome);
		util.commit(manager, md);
	}
	
	public void registerCountry(String nome, String ouros, String pratas, String bronzes) throws ExcecaoEspacoVazio, ExcecaoNotNumber, ExcecaoObjetoJaCadastrado {
		util.check(nome);
		util.checkNum(ouros, pratas, bronzes);
		int o = Integer.parseInt(ouros);
		int p = Integer.parseInt(pratas);
		int b = Integer.parseInt(bronzes);
		Pais pais = new Pais(nome, o, p, b);
		String frase = "select count(*) from Country where contry_name = :value";
		EntityManager manager = factory.createEntityManager();
		util.duplicates(manager, frase, nome);
		util.commit(manager, pais);
		
	}
	
}
