package ucs.OlymPro.model;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY")
public class Pais implements Serializable {

    private static final long serialVersionUID = 100L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="COUNTRY_NAME")
    private String nome;
    @Column(name="COUNTRY_GOLD")
    private int ouros;
    @Column(name="COUNTRY_SILVER")
    private int pratas;
    @Column(name="COUNTRY_BRONZE")
    private int bronzes;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pais")
    private Set<Athlete> atletas = new HashSet<Athlete>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pais")
    private Set<Equipe> equipes = new HashSet<Equipe>();


    public Pais(String nome, int ouros, int pratas, int bronzes) {
        this.nome = nome;
        this.ouros = ouros;
        this.pratas = pratas;
        this.bronzes = bronzes;
    }
    public Pais(String nome) {
        this.nome = nome;
        this.ouros = 0;
        this.pratas = 0;
        this.bronzes = 0;
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

    public String getNome() {
        return nome;
    }
    
    public Set<Athlete> getAtletas() {
		return atletas;
	}

	public void addAtletas(Athlete a) {
		this.atletas.add(a);
	}

	public void addEquipe(Equipe e) {
		this.equipes.add(e);
	}

	@Override
    public String toString() {
        return "País: " + nome + ", Ouro: " + ouros + ", Prata: " + pratas + ", Bronze: " + bronzes;
    }
}
