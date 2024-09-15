package tp.integrador.entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EstudianteCarrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @Column
    private Integer antiguedad;

    @Column
    private Date fechaInscripcion;

    public EstudianteCarrera() {
        super();
    }

    public EstudianteCarrera(long id, Estudiante estudiante, Carrera carrera, Integer antiguedad, Date fechaInscripcion) {
        super();
        this.id = id;
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.antiguedad = antiguedad;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    @Override
    public String toString() {
        return "EstudianteCarrera{" +
                "id=" + id +
                ", estudiante=" + estudiante.getId() +
                ", carrera=" + carrera.getNombre() +
                ", antiguedad=" + antiguedad + " años" +
                ", fecha de inscripción=" + fechaInscripcion +
                '}' + "\n";
    }
}
