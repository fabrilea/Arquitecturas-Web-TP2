package tp.integrador.dao.entidadesDao;

import tp.integrador.dao.Dao;
import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;
import tp.integrador.entidades.EstudianteCarrera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLException;

public class EstudianteCarreraDao implements Dao<EstudianteCarrera> {

    @Override
    public Object select(EntityManager em, long id) {
        String jpql = "SELECT ec FROM EstudianteCarrera ec WHERE ec.id = :id";
        TypedQuery<EstudianteCarrera> query = em.createQuery(jpql, EstudianteCarrera.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void insert(EntityManager em, Object estudiante_carrera) throws SQLException {
        if (estudiante_carrera instanceof EstudianteCarrera) {// Inicia la transacción
            Carrera c = ((EstudianteCarrera) estudiante_carrera).getCarrera();
            Estudiante e = ((EstudianteCarrera) estudiante_carrera).getEstudiante();
            c.getEstudiantes().add((EstudianteCarrera) estudiante_carrera);
            e.getCarreras().add((EstudianteCarrera) estudiante_carrera);
            em.merge(estudiante_carrera); // Inserta la entidad en la base de datos
            em.merge(e);
            em.merge(c);
        }
    }

    @Override
    public void update(EntityManager em, long id, Object estudiante_carrera) {
        if (estudiante_carrera instanceof EstudianteCarrera) {
            String jpql = "UPDATE EstudianteCarrera ec SET ec.estudiante = :estudiante, ec.carrera = :carrera, ec.antiguedad = :antiguedad WHERE ec.id = :id";

            EstudianteCarrera estudiante_carreraActualizada = (EstudianteCarrera) estudiante_carrera;

            Query query = em.createQuery(jpql);
            query.setParameter("estudiante", estudiante_carreraActualizada.getEstudiante());
            query.setParameter("carrera", estudiante_carreraActualizada.getCarrera());
            query.setParameter("antiguedad", estudiante_carreraActualizada.getAntiguedad());
            query.setParameter("id", id);
            query.executeUpdate();  // Ejecuta la actualización
        }
    }

    @Override
    public void delete(EntityManager em, long id) {
        String jpql = "DELETE FROM EstudianteCarrera ec WHERE ec.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();  // Ejecuta la eliminación
    }

    @Override
    public Object find(EntityManager em, long id) {
        String jpql = "SELECT ec FROM EstudianteCarrera ec WHERE ec.id = :id";
        TypedQuery<EstudianteCarrera> query = em.createQuery(jpql, EstudianteCarrera.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
