package ucs.OlymPro.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "modalidades")
public class Modalidade implements Serializable {

    private static final long serialVersionUID = 111L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    

	@Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String tipo; // "COLETIVO" ou "INDIVIDUAL"
    

    @OneToOne(fetch = FetchType.EAGER)
    private Partida partidaOuro;
    
    @OneToOne(fetch = FetchType.EAGER)
    private Partida partidaPrata;
    
    @OneToOne(fetch = FetchType.EAGER)
    private Partida partidaBronze;
    
    @OneToMany(fetch = FetchType.EAGER)
    private Set<ResultadoIndividual> resultados = new HashSet<>();

    public Modalidade() {}
    
    public Modalidade(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
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
    public void defineFinaisColetivas(Partida ouro, Partida prata, Partida bronze) {
        if (!tipo.equals("COLETIVO")) {
            throw new IllegalStateException("Esta modalidade não é coletiva");
        }
        this.partidaOuro = ouro;
        this.partidaPrata = prata;
        this.partidaBronze = bronze;
    }

    public void defineFinaisIndividuais(Set<ResultadoIndividual> resultados) {
        if (!tipo.equals("INDIVIDUAL")) {
            throw new IllegalStateException("Esta modalidade não é individual");
        }
        this.resultados = resultados;
    }


    public Partida getPartidaOuro() {
        return partidaOuro;
    }

    public Partida getPartidaPrata() {
        return partidaPrata;
    }

    public Partida getPartidaBronze() {
        return partidaBronze;
    }

    public Set<ResultadoIndividual> getResultados() {
        return resultados;
    }
    public void addResultado(ResultadoIndividual res) {
        resultados.add(res);
        res.setModalidade(this);
    }

    public void removeResultado(ResultadoIndividual res) {
        resultados.remove(res);
        res.setModalidade(null);
    }
}
