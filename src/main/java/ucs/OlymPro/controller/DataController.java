package ucs.OlymPro.controller;

import ucs.OlymPro.exceptions.ExcecaoEspacoVazio;
import ucs.OlymPro.exceptions.ExcecaoNotNumber;
import ucs.OlymPro.exceptions.ExcecaoObjetoJaCadastrado;
import ucs.OlymPro.model.Atleta;

public class DataController {

	Utility util = new Utility();
	
	public void registerAthlete( String nome, String nacionalidade, String idadeS) 
								throws ExcecaoEspacoVazio, ExcecaoNotNumber, ExcecaoObjetoJaCadastrado{
		util.check(nome, nacionalidade);
		util.checkNum(idadeS);
		int idade = Integer.parseInt(idadeS);
		Atleta at = new Atleta(nome, nacionalidade, idade);
		
		
	}
	
}
