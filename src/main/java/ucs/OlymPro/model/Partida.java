package ucs.OlymPro.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ucs.OlymPro.controller.DataController;

@Entity
@Table(name = "partidas")
public class Partida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String esporte;
    @Column(nullable = false)
    private String fase;
    
    @ManyToOne
    @JoinColumn(name = "time1_id", nullable = false)
    private Equipe time1;
    @ManyToOne
    @JoinColumn(name = "time2_id", nullable = false)
    private Equipe time2;
    
    @Column(name = "placar_time1")
    private int placarTime1;
    @Column(name = "placar_time2")
    private int placarTime2;
    @Column(name = "data_partida")
    private String dataPartida;
    
    @Column
    private Equipe vencedor;
    
    public Partida() {
    }
    
    public Partida(String esporte, String fase, Equipe time1, Equipe time2, int placar1, int placar2, 
    		String dataPartida) {
        this.esporte = esporte;
        this.fase = fase;
        this.time1 = time1;
        this.time2 = time2;
        this.dataPartida = dataPartida;
        this.placarTime1 = placar1;
        this.placarTime2 = placar2;
    }
    
    
    public void compareVencedor() {
    	if(placarTime1>placarTime2) {
    		this.vencedor = time1;
    	}else {
    		this.vencedor = time2;
    	}
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEsporte() {
        return esporte;
    }

    public void setEsporte(String esporte) {
        this.esporte = esporte;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Equipe getTime1() {
        return time1;
    }

    public void setTime1(Equipe time1) {
        this.time1 = time1;
    }

    public Equipe getTime2() {
        return time2;
    }

    public void setTime2(Equipe time2) {
        this.time2 = time2;
    }

    public int getPlacarTime1() {
        return placarTime1;
    }

    public void setPlacarTime1(int placarTime1) {
        this.placarTime1 = placarTime1;
    }

    public int getPlacarTime2() {
        return placarTime2;
    }

    public void setPlacarTime2(int placarTime2) {
        this.placarTime2 = placarTime2;
    }

    public String getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(String dataPartida) {
        this.dataPartida = dataPartida;
    }

	public Equipe getVencedor() {
		return vencedor;
	}

	public void setVencedor(Equipe vencedor) {
		this.vencedor = vencedor;
	}
    
    

} 