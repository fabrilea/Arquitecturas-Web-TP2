package tp.integrador.entidades;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;
    @Column
    private Integer duracion;

    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    private List<EstudianteCarrera> estudiantes;

    public Carrera() {
        super();
        this.estudiantes = new ArrayList<EstudianteCarrera>();
    }

    public Carrera(String nombre, Integer duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public List<EstudianteCarrera> getEstudiantes() {
        if (this.estudiantes == null) {
            this.estudiantes = new ArrayList<>();
        }
        return this.estudiantes;
    }


    @Override
    public String toString() {
        return "Carrera{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", duracion=" + duracion + " años" +
                '}' + "\n";
    }
}
