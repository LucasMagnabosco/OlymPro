import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ATLETA")
public class Atleta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String nacionalidade;
    private int idade;
    private int id;
    private int ouros;
    private int pratas;
    private int bronzes;

    public Atleta(String nome, String nacionalidade, int idade, int id) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.idade = idade;
        this.id = id;
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Atleta: " + nome + ", Nacionalidade: " + nacionalidade +
               ", Idade: " + idade + ", Ouro: " + ouros +
               ", Prata: " + pratas + ", Bronze: " + bronzes;
    }
}
