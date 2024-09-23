package tp.integrador.repositories.entitiesRepositories;

import tp.integrador.repositories.Repository;
import tp.integrador.dto.CarreraDto;
import tp.integrador.entidades.Carrera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;

public class CarreraRepository implements Repository<CarreraRepository> {

    public CarreraRepository() {
        ;
    }

    @Override
    public Object select(EntityManager em, long id) {
        String jpql = "SELECT c FROM Carrera c WHERE c.id = :id";
        TypedQuery<Carrera> query = em.createQuery(jpql, Carrera.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void insert(EntityManager em, Object carrera) throws SQLException {
        if (carrera instanceof Carrera) {// Inicia la transacción
            em.merge(carrera);       // Inserta la entidad en la base de datos
        }
    }

    @Override
    public void update(EntityManager em, long id, Object carrera) {
        if (carrera instanceof Carrera) {
            String jpql = "UPDATE Carrera c SET c.nombre = :nombre, c.duracion = :duracion WHERE c.id = :id";
            Carrera carreraActualizada = (Carrera) carrera;

            Query query = em.createQuery(jpql);
            query.setParameter("nombre", carreraActualizada.getNombre());
            query.setParameter("duracion", carreraActualizada.getDuracion());
            query.setParameter("id", id);
            query.executeUpdate();  // Ejecuta la actualización
        }
    }

    @Override
    public void delete(EntityManager em, long id) {
        String jpql = "DELETE FROM Carrera c WHERE c.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();  // Ejecuta la eliminación
    }

    @Override
    public Object find(EntityManager em, long id) {
        String jpql = "SELECT c FROM Carrera c WHERE c.id = :id";
        TypedQuery<Carrera> query = em.createQuery(jpql, Carrera.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public static List<CarreraDto> obtenerCarrerasPorEstudiantesInscriptos(EntityManager em) {
        String jpql = "SELECT new tp.integrador.dto.CarreraDto(c.nombre, COUNT(e.id)) " +
                "FROM Carrera c " +
                "JOIN c.estudiantes e " +
                "WHERE e.id IS NOT NULL " +
                "GROUP BY c.nombre " +
                "ORDER BY COUNT(e.id) DESC";

        TypedQuery<CarreraDto> query = em.createQuery(jpql, CarreraDto.class);
        return query.getResultList();
    }

}

