package tp.integrador.entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EstudianteCarrera {

    @EmbeddedId
    private EstudianteCarreraPK id;

    private Date fechaInscripcion;

    private Date fechaGraduacion;

    private boolean estaGraduado;

    @ManyToOne
    @MapsId("id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("id_carrera")
    private Carrera carrera;


    public EstudianteCarrera() {
        super();
    }

    public EstudianteCarrera(EstudianteCarreraPK id, Date fechaInscripcion, Date fechaGraduacion, boolean estaGraduado, Estudiante estudiante, Carrera carrera) {
        this.id = id;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaGraduacion = fechaGraduacion;
        this.estaGraduado = estaGraduado;
        this.estudiante = estudiante;
        this.carrera = carrera;
    }

    // Getters y Setters

    public EstudianteCarreraPK getId() {
        return id;
    }

    public void setId(EstudianteCarreraPK id) {
        this.id = id;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Date getFechaGraduacion() {
        return fechaGraduacion;
    }

    public void setFechaGraduacion(Date graduacion) {
        this.fechaGraduacion = graduacion;
    }

    public boolean isEstaGraduado() {
        return estaGraduado;
    }

    public void setEstaGraduado(boolean estaGraduado) {
        this.estaGraduado = estaGraduado;
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

    @Override
    public String toString() {
        return "EstudianteCarrera{" +
                "id=" + id +
                ", fecha de inscripción=" + fechaInscripcion +
                ", fecha de graduación=" + fechaGraduacion +
                ", estaGraduado=" + estaGraduado +
                ", estudiante=" + estudiante +
                ", carrera=" + carrera +
                '}' + "\n";
    }
}
