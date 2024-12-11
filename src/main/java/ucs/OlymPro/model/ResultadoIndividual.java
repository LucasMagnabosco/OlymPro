package ucs.OlymPro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resultados_individuais")
public class ResultadoIndividual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atleta_id", nullable = false)
    private Athlete atleta;

    @Column(nullable = false)
    private String prova;
    @Column(name = "TEMPOMS", nullable = false)
    private long tempoMS;
    @Column(nullable = false)
    private String fase; // "FINAL", "SEMIFINAL", etc.

    @ManyToOne
    @JoinColumn(name = "modalidade_id")
    private Modalidade modalidade;

    // Construtor padrão
    public ResultadoIndividual() {
    }

    // Construtor com parâmetros
    public ResultadoIndividual(Athlete atleta, String prova, String fase, Modalidade modalidade) {
        this.atleta = atleta;
        this.prova = prova;
        this.fase = fase;
        this.modalidade = modalidade;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public long getTempoMS() {
		return tempoMS;
	}

	public void setTempoMS(long tempoMS) {
		this.tempoMS = tempoMS;
	}

	public Athlete getAtleta() {
        return atleta;
    }

    public void setAtleta(Athlete atleta) {
        this.atleta = atleta;
    }

    public String getProva() {
        return prova;
    }

    public void setProva(String prova) {
        this.prova = prova;
    }


    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    // Métodos auxiliares para conversão
    public void setTempoFormatado(String tempo) {
        // Converte string "MM:SS.dd" para milissegundos
        String[] partes = tempo.split("[:.]");
        long minutos = Long.parseLong(partes[0]);
        long segundos = Long.parseLong(partes[1]);
        long decimos = Long.parseLong(partes[2]);
        
        this.tempoMS = (minutos * 60 * 1000) + 
                                      (segundos * 1000) + 
                                      (decimos * 10);
    }

    public String getTempoFormatado() {
        // Converte milissegundos para string "MM:SS.dd"
        long totalSegundos = tempoMS / 1000;
        long minutos = totalSegundos / 60;
        long segundos = totalSegundos % 60;
        long decimos = (tempoMS % 1000) / 10;

        return String.format("%02d:%02d.%02d", minutos, segundos, decimos);
    }

    // Método para comparação
    public int compareTempo(ResultadoIndividual outro) {
        return Long.compare(this.tempoMS, outro.tempoMS);
    }
}