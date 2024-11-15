package ucs.OlymPro.controller;


import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ucs.OlymPro.exceptions.ExcecaoEspacoVazio;
import ucs.OlymPro.exceptions.ExcecaoNotNumber;
import ucs.OlymPro.exceptions.ExcecaoObjetoJaCadastrado;
import ucs.OlymPro.model.Atleta;
import ucs.OlymPro.model.Equipe;
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
	
	public void registerModality(String nome, String tipo) throws ExcecaoEspacoVazio, ExcecaoObjetoJaCadastrado {
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
	
	public void registerResult(Atleta at, Equipe tm, String tempoS, String etapa, LocalDate data) throws ExcecaoEspacoVazio, ExcecaoNotNumber {
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
	
}
