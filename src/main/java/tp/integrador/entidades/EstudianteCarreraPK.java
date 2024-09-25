package tp.integrador.entidades;

import javax.persistence.Embeddable;

@Embeddable
public class EstudianteCarreraPK implements java.io.Serializable {

    private static final long serialVersionUID = -2173029278696333769L;
    private long id_estudiante;

    private long id_carrera;

    public EstudianteCarreraPK() {
        super();
    }

    public EstudianteCarreraPK(long id_estudiante, long id_carrera) {
        this.id_estudiante = id_estudiante;
        this.id_carrera = id_carrera;
    }


    public long getId_estudiante() {
        return id_estudiante;
    }

    public long getId_carrera() {
        return id_carrera;
    }
}
