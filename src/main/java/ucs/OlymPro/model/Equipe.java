package ucs.OlymPro.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "TEAM")
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="TEAM_NAME")
    private String nome;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Pais pais;
    @Column(name="TEAM_GOLD")
    private int ouros;
    @Column(name="TEAM_SILVER")
    private int pratas;
    @Column(name="TEAM_BRONZE")
    private int bronzes;
    
    @Column(name = "IS_VOLEI")
    private boolean isVolei;
    
    @Embedded
	@OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    private Set<Athlete> atletas = new HashSet<Athlete>();
    
    @Embedded
	@OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    Set<ResultadoIndividual> tempos = new HashSet<ResultadoIndividual>();
    
    public Equipe() {}
    
    public Equipe(String nome, Pais pais, boolean esporte) {
        this.nome = nome;
        this.pais = pais;
        this.atletas = new HashSet<Athlete>();
        this.isVolei = esporte;
        this.ouros = 0;
        this.pratas = 0;
        this.bronzes = 0;
    }

    public void adicionarOuro() {
        ouros++;
        for (Athlete atleta : atletas) {
            atleta.adicionarOuro();
        }
    }

    public void adicionarPrata() {
        pratas++;
        for (Athlete atleta : atletas) {
            atleta.adicionarPrata();
        }
    }

    public void adicionarBronze() {
        bronzes++;
        for (Athlete atleta : atletas) {
            atleta.adicionarBronze();
        }
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

    public void adicionarAtleta(Athlete atleta) {
        atletas.add(atleta);
    }
    public void removerAtleta(Athlete at) {
    	atletas.remove(at);
    }

    public String getNome() {
        return nome;
    }

    public Pais getPais() {
        return pais;
    }

    public boolean isVolei() {
		return isVolei;
	}

	public void setVolei(boolean isNatacao) {
		this.isVolei = isNatacao;
	}

	public Set<ResultadoIndividual> getTempos() {
		return tempos;
	}

	public void addTempo(ResultadoIndividual res) {
		this.tempos.add(res);
	}

    @Override
    public String toString() {
        return "Equipe: " + nome;
    }

	public Set<Athlete> getAthletes() {
		return atletas;
	}

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
