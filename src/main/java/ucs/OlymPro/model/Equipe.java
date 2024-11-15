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
    @Column(name="TEAM_COUNTRY")
    private String pais;
    @Column(name="TEAM_GOLD")
    private int ouros;
    @Column(name="TEAM_SILVER")
    private int pratas;
    @Column(name="TEAM_BRONZE")
    private int bronzes;
    
    @Embedded
	@OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    private Set<Atleta> atletas;
    
    @Embedded
	@OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    Set<Resultado> tempos = new HashSet<Resultado>();
    

    public Equipe(String nome, String pais) {
        this.nome = nome;
        this.pais = pais;
        this.atletas = new HashSet<Atleta>();
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

    public void adicionarAtleta(Atleta atleta) {
        atletas.add(atleta);
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    public Set<Resultado> getTempos() {
		return tempos;
	}

	public void addTempo(Resultado res) {
		this.tempos.add(res);
	}

    @Override
    public String toString() {
        return "Equipe: " + nome + ", País: " + pais + ", Atletas: " + atletas +
               ", Ouro: " + ouros + ", Prata: " + pratas + ", Bronze: " + bronzes;
    }
}
