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
@Table(name = "ATHLETE")

public class Atleta implements Serializable {

    private static final long serialVersionUID = 113L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="ATHLETE_NAME")
    private String nome;
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

    @Embedded
	@OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    Set<Resultado> tempos = new HashSet<Resultado>();
    

	public Atleta(String nome, String nacionalidade, int idade) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.idade = idade;
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

    public String getNome() {
        return nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public int getIdade() {
        return idade;
    }

    public Set<Resultado> getTempos() {
		return tempos;
	}

	public void addTempo(Resultado res) {
		this.tempos.add(res);
	}
    
    @Override
    public String toString() {
        return "Atleta: " + nome + ", Nacionalidade: " + nacionalidade +
               ", Idade: " + idade + ", Ouro: " + ouros +
               ", Prata: " + pratas + ", Bronze: " + bronzes;
    }
}
