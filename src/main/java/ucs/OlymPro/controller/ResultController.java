package ucs.OlymPro.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ucs.OlymPro.model.Athlete;
import ucs.OlymPro.model.Equipe;
import ucs.OlymPro.model.Modalidade;
import ucs.OlymPro.model.Pais;
import ucs.OlymPro.model.Partida;
import ucs.OlymPro.model.ResultadoIndividual;

public class ResultController {

	private EntityManagerFactory emf;
	private EntityManager em;
	DataController data = new DataController();
	public ResultController() {
			emf = Persistence.createEntityManagerFactory("dados");
			em = emf.createEntityManager();
	}

	public void cadastrarPartida(String modalidade, String etapa, Equipe time1, Equipe time2, int placar1, int placar2, String data) {

		if (!modalidade.equalsIgnoreCase("FUTEBOL") && !modalidade.equalsIgnoreCase("VOLEI")) {
			throw new IllegalArgumentException("Modalidade deve ser FUTEBOL ou VOLEI");
		}

		// Validações específicas para cada modalidade 
		if (placar1 < 0 || placar2 < 0) {
			throw new IllegalArgumentException("Número de gols ou pontos não pode ser negativo");
		}
		if (modalidade.equalsIgnoreCase("VOLEI")) {	
			if (Math.max(placar1, placar2) < 3) {
				throw new IllegalArgumentException("No vôlei, o vencedor precisa ter no minimo 3 sets");
			}
		}

		try {
			em.getTransaction().begin();

			Partida partida = new Partida(modalidade, etapa, time1, time2, placar1, placar2, data);

			// Define o vencedor
			if (placar1 > placar2) {
				partida.setVencedor(time1);
			} else if (placar2 > placar1) {
				partida.setVencedor(time2);
			} else {
				throw new IllegalArgumentException("Não pode haver empate nas modalidades olímpicas");
			}

			em.persist(partida);
			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao cadastrar partida: " + e.getMessage());
		}
	}

	public void cadastrarResultadoIndividual(Athlete atleta, String prova, String tempoFormatado, String fase) {

		try {
			em.getTransaction().begin();
			
			// Busca a modalidade Natação no banco
			String jpql = "SELECT m FROM Modalidade m WHERE m.nome = 'Natação'";
			Modalidade natacao = em.createQuery(jpql, Modalidade.class)
								 .getSingleResult();
			
			ResultadoIndividual resultado = new ResultadoIndividual(atleta, prova, fase, natacao);
			resultado.setTempoFormatado(tempoFormatado);
			
			// Adiciona o resultado ao conjunto de resultados da modalidade
			natacao.addResultado(resultado);
			
			//em.merge(natacao);
			em.persist(resultado);
			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException("Erro ao cadastrar resultado: " + e.getMessage());
		}
	}

	public Object[][] getPartidasFutebol() {
		String jpql = "SELECT p FROM Partida p WHERE UPPER(p.esporte) = 'FUTEBOL' " +
					  "ORDER BY CASE p.fase " +
					  "WHEN 'FINAL' THEN 1 " +
					  "WHEN 'SEMIFINAL' THEN 2 " +
					  "WHEN 'QUARTAS' THEN 3 " +
					  "ELSE 4 END";
		List<Partida> partidas = em.createQuery(jpql, Partida.class).getResultList();
		
		if (partidas.isEmpty()) {
			return new Object[0][7];
		}
		
		Object[][] data = new Object[partidas.size()][7];
		
		for (int i = 0; i < partidas.size(); i++) {
			Partida p = partidas.get(i);
			data[i][0] = p.getTime1().getNome();
			data[i][1] = p.getPlacarTime1();
			data[i][2] = "X";
			data[i][3] = p.getPlacarTime2();
			data[i][4] = p.getTime2().getNome();
			data[i][5] = p.getDataPartida();
			data[i][6] = p.getFase();
		}
		
		return data;
	}
	
	public Object[][] getPartidasVolei() {
		String jpql = "SELECT p FROM Partida p WHERE p.esporte = 'Vôlei' " +
					  "ORDER BY CASE p.fase " +
					  "WHEN 'FINAL' THEN 1 " +
					  "WHEN 'SEMIFINAL' THEN 2 " +
					  "WHEN 'QUARTAS' THEN 3 " +
					  "ELSE 4 END";
		List<Partida> partidas = em.createQuery(jpql, Partida.class).getResultList();
		
		if (partidas.isEmpty()) {
			return new Object[0][7];
		}
		
		Object[][] data = new Object[partidas.size()][7];
		
		for (int i = 0; i < partidas.size(); i++) {
			Partida p = partidas.get(i);
			data[i][0] = p.getTime1().getNome();
			data[i][1] = p.getPlacarTime1();
			data[i][2] = "X";
			data[i][3] = p.getPlacarTime2();
			data[i][4] = p.getTime2().getNome();
			data[i][5] = p.getDataPartida();
			data[i][6] = p.getFase();
		}

		return data;
	}
	
	public Object[][] getResultadosData() {
		System.out.println("Buscando resultados de natação...");
		String jpql = "SELECT r FROM ResultadoIndividual r " +
					  "ORDER BY r.prova ASC, r.tempoMS ASC";
		List<ResultadoIndividual> resultados = em.createQuery(jpql, ResultadoIndividual.class).getResultList();
		
		if (resultados.isEmpty()) {
			return new Object[0][7];
		}
		
		Object[][] data = new Object[resultados.size()][7];
		
		for (int i = 0; i < resultados.size(); i++) {
			ResultadoIndividual r = resultados.get(i);
			data[i][0] = r.getAtleta().getName();
			data[i][1] = r.getTempoFormatado();
			data[i][2] = r.getAtleta().getPais().getNome();
		}

		return data;
	}

	public void distribuirMedalhasNatacao() throws Exception {
		Modalidade natacao = data.getModalidade("Natação");
			if (natacao == null) {
				throw new Exception("Modalidade Natação não encontrada");
			}
			
			// Pega todos os resultados e ordena por tempo
			List<ResultadoIndividual> resultados = new ArrayList<>(natacao.getResultados());
			if (resultados == null || resultados.isEmpty()) {
				throw new Exception("Nenhum resultado encontrado para Natação");
			}

			Collections.sort(resultados, (r1, r2) -> Long.compare(r1.getTempoMS(), r2.getTempoMS()));

			try {
				em.getTransaction().begin();
				
				List<ResultadoIndividual> podio = resultados.subList(0, Math.min(3, resultados.size()));
				// Atualiza e persiste cada atleta
				podio.get(0).getAtleta().adicionarOuro();
				Pais pais1 = podio.get(0).getAtleta().getPais();
				pais1.somaOuros();
				em.merge(pais1);
				em.merge(podio.get(0).getAtleta());
				
				podio.get(1).getAtleta().adicionarPrata();
				Pais pais2 = podio.get(1).getAtleta().getPais();
				pais2.somaPratas();
				em.merge(pais2);
				em.merge(podio.get(1).getAtleta());
				
				podio.get(2).getAtleta().adicionarBronze();
				Pais pais3 = podio.get(2).getAtleta().getPais();
				pais3.somaBronzes();
				em.merge(pais3);
				em.merge(podio.get(2).getAtleta());
				
				em.getTransaction().commit();
			} catch (Exception e) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				throw new Exception("Erro ao distribuir medalhas: " + e.getMessage());
			}
	}

	public void distribuirMedalhasEquipes(String modalidade) throws Exception {
		if (!modalidade.equalsIgnoreCase("FUTEBOL") && !modalidade.equalsIgnoreCase("VOLEI")) {
			throw new IllegalArgumentException("Modalidade deve ser FUTEBOL ou VOLEI");
		}

		try {
			// Busca partidas finais e de terceiro lugar da modalidade
			String jpql = "SELECT p FROM Partida p WHERE UPPER(p.esporte) = :modalidade " +
						 "AND (p.fase = 'FINAL' OR p.fase = 'TERCEIRO LUGAR') " +
						 "ORDER BY CASE p.fase " +
						 "WHEN 'FINAL' THEN 1 " +
						 "WHEN 'TERCEIRO LUGAR' THEN 2 END";
			
			List<Partida> partidas = em.createQuery(jpql, Partida.class)
									  .setParameter("modalidade", modalidade.toUpperCase())
									  .getResultList();

			if (partidas.isEmpty()) {
				throw new Exception("Nenhuma partida final/terceiro lugar encontrada para " + modalidade);
			}

			em.getTransaction().begin();

			// Procura a final
			Partida partidaFinal = partidas.stream()
										  .filter(p -> p.getFase().equalsIgnoreCase("FINAL"))
										  .findFirst()
										  .orElseThrow(() -> new Exception("Partida final não encontrada"));

			// Procura a partida de terceiro lugar
			Partida partidaTerceiro = partidas.stream()
											  .filter(p -> p.getFase().equalsIgnoreCase("TERCEIRO LUGAR"))
											  .findFirst()
											  .orElseThrow(() -> new Exception("Partida de terceiro lugar não encontrada"));

			// Ouro para o vencedor da final
			partidaFinal.getVencedor().adicionarOuro();
			Pais p = partidaFinal.getVencedor().getPais();
			p.somaOuros();
			em.merge(p);
			em.merge(partidaFinal.getVencedor());

			// Prata para o perdedor da final
			Equipe perdedorFinal = partidaFinal.getTime1().equals(partidaFinal.getVencedor()) ? 
								  partidaFinal.getTime2() : partidaFinal.getTime1();
			perdedorFinal.adicionarPrata();
			Pais p1 = partidaFinal.getVencedor().getPais();
			p1.somaPratas();
			em.merge(p1);
			em.merge(perdedorFinal);

			// Bronze para o vencedor da partida de terceiro lugar
			partidaTerceiro.getVencedor().adicionarBronze();
			Pais p2 = partidaFinal.getVencedor().getPais();
			p2.somaBronzes();
			em.merge(p);
			em.merge(partidaTerceiro.getVencedor());

			em.getTransaction().commit();

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new Exception("Erro ao distribuir medalhas de " + modalidade + ": " + e.getMessage());
		}
	}

	public void close() {
		em.close();
		emf.close();
	}

}
