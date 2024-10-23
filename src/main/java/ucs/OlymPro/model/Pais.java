package ucs.OlymPro.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PAIS")
public class Pais implements Serializable {

    private static final long serialVersionUID = 100L;

    private int id;
    private String nome;
    private int ouros;
    private int pratas;
    private int bronzes;

    public Pais(int id, String nome, int ouros, int pratas, int bronzes) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "País: " + nome + ", Ouro: " + ouros + ", Prata: " + pratas + ", Bronze: " + bronzes;
    }
}
