import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EQUIPE")
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String pais;
    private List<String> atletas;
    private int id;
    private int ouros;
    private int pratas;
    private int bronzes;

    public Equipe(String nome, String pais, int id) {
        this.nome = nome;
        this.pais = pais;
        this.atletas = new ArrayList<>();
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

    public void adicionarAtleta(String atleta) {
        atletas.add(atleta);
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Equipe: " + nome + ", País: " + pais + ", Atletas: " + atletas +
               ", Ouro: " + ouros + ", Prata: " + pratas + ", Bronze: " + bronzes;
    }
}
