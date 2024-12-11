package ucs.OlymPro.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ATHLETE")

public class Athlete implements Serializable {

    private static final long serialVersionUID = 1073L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="ATHLETE_NAME")
    private String name;
    @Column(name="ATHLETE_NATURE")
    private String nacionalidade;
    @Column(name="ATHLETE_AGE")
    private int idade;
    @Column(name="ATHLETE_GOLDEN")
    private int ouros;
    @Column(name="ATHLETE_SILVER")
    private int pratas;
    @Column(name="ATHLETE_BRONZE")
    private int bronzes;

    @Column(name = "IS_INDIVIDUAL")
    private boolean isIndividual;
    
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Pais pais;
    
    @OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    Set<ResultadoIndividual> tempos = new HashSet<ResultadoIndividual>();
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;
    
    public Athlete() {}
    
	public Athlete(String nome, String nacionalidade, int idade, boolean isInd) {
        this.name = nome;
        this.nacionalidade = nacionalidade;
        this.idade = idade;
        this.isIndividual = isInd;
        this.ouros = 0;
        this.pratas = 0;
        this.bronzes = 0;
    }

    public void adicionarOuro() {
        ouros++;
    }

    public void adicionarPrata() {
        pratas++;
    }

    public void adicionarBronze() {
        bronzes++;
    }

    public int somaOuros() {
        return ouros;
    }

    public int somaPratas() {
        return pratas;
    }

    public int somaBronzes() {
        return bronzes;
    }

    public int somaTotal() {
        return ouros + pratas + bronzes;
    }

    public String getName() {
        return name;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public int getIdade() {
        return idade;
    }

    public Set<ResultadoIndividual> getTempos() {
		return tempos;
	}

	public void addTempo(ResultadoIndividual res) {
		this.tempos.add(res);
	}
    
    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe novaEquipe) {
        if (this.equipe != null) {
            this.equipe.removerAtleta(this);
        }
        
        this.equipe = novaEquipe;
        
        if (novaEquipe != null && !novaEquipe.getAthletes().contains(this)) {
            novaEquipe.adicionarAtleta(this);
        }
    }
    
    public Pais getPais() {
        return pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    public boolean isIndividual() {
        return isIndividual;
    }

    public void setIndividual(boolean individual) {
        this.isIndividual = individual;
    }
    
    @Override
    public String toString() {
        return "Atleta: " + name;
    }
}
