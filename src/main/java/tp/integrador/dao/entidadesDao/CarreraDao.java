package tp.integrador.dao.entidadesDao;

import tp.integrador.dao.Dao;
import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraDao implements Dao<CarreraDao> {

    public CarreraDao() {
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


}

