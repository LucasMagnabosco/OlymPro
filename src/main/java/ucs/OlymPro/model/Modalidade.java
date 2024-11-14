package ucs.OlymPro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MODALITY")
public class Modalidade implements Serializable {

    private static final long serialVersionUID = 111L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="MODALITY_")
    private String nome;
    @Column(name="MODALITY_TYPE")
    private String tipo; // "individual" ou "equipe"
    @Column(name="MODALITY_RANKING")
    private List<Resultado> ranking; // Supondo que a classe Resultado já exista

    public Modalidade(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.ranking = new ArrayList<>();
    }

    public int somaPontos() {
        int totalPontos = 0;
        for (Resultado resultado : ranking) {
            //totalPontos += resultado.getPontos(); // Supondo que a classe Resultado tem um método getPontos()
        }
        return totalPontos;
    }

    public void criaChaveamento() {
        System.out.println("Chaveamento criado para a modalidade: " + nome);
    }

    public void distribuiMedalhas() {
        System.out.println("Medalhas distribuídas para a modalidade: " + nome);
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Resultado> getRanking() {
        return ranking;
    }

    public void addResultado(Resultado resultado) {
        ranking.add(resultado);
    }

    @Override
    public String toString() {
        return "Modalidade: " + nome + ", Tipo: " + tipo + ", Ranking: " + ranking.size() + " resultados.";
    }
}
