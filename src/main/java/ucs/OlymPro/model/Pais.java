package ucs.OlymPro.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    

    public Pais(int id, String nome, int ouros, int pratas, int bronzes) {

        this.nome = nome;
        this.ouros = ouros;
        this.pratas = pratas;
        this.bronzes = bronzes;
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

    @Override
    public String toString() {
        return "País: " + nome + ", Ouro: " + ouros + ", Prata: " + pratas + ", Bronze: " + bronzes;
    }
}
