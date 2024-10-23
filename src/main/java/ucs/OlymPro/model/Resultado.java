import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RESULTADO")
public class Resultado implements Serializable {

    private static final long serialVersionUID = 1L;

    private double tempo;
    private String etapa;
    private LocalDate data;

    public Resultado(double tempo, String etapa, LocalDate data) {
        this.tempo = tempo;
        this.etapa = etapa;
        this.data = data;
    }

    public double getTempo() {
        return tempo;
    }

    public String getEtapa() {
        return etapa;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Resultado: " + tempo + " segundos, Etapa: " + etapa + ", Data: " + data;
    }
}
