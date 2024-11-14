package ucs.OlymPro.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESULT")
public class Resultado implements Serializable {

    private static final long serialVersionUID = 112L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="RESULT_TIME")
    private double tempo;
    @Column(name="RESULT_STAGE")
    private String etapa;
    @Column(name="RESULT_DATE")
    private LocalDate data;

    public Resultado(double tempo, String etapa, LocalDate data) {
        this.tempo = tempo;
        this.etapa = etapa;
        this.data = data;
    }

    public double getTempo() {
        return tempo;
    }

    public String getEtapa() {
        return etapa;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Resultado: " + tempo + " segundos, Etapa: " + etapa + ", Data: " + data;
    }

	
}
