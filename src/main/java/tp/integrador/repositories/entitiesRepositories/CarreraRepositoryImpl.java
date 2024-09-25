package tp.integrador.repositories.entitiesRepositories;

import tp.integrador.repositories.CarreraRepository;
import tp.integrador.dto.CarreraDto;
import tp.integrador.entidades.Carrera;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository {


    @Override
    public void insert(EntityManager em, Carrera carrera) {
        em.persist(carrera);  // Insertar el objeto carrera
        em.getTransaction().commit();
    }

    @Override
    public Carrera findById(EntityManager em, Long id) {
        return em.find(Carrera.class, id);
    }

    @Override
    public List<Carrera> findAll(EntityManager em) {
        Query query = em.createQuery("SELECT c FROM Carrera c");
        return query.getResultList();
    }

    @Override
    public void update(EntityManager em, Carrera carrera) {
        em.merge(carrera);  // Actualizar el objeto carrera
        em.getTransaction().commit();
    }

    @Override
    public void delete(EntityManager em, Long id) {
        Carrera carrera = em.find(Carrera.class, id);
        if (carrera != null) {
            em.remove(carrera);  // Eliminar el objeto carrera
        }
        em.getTransaction().commit();
    }

    public static CarreraRepositoryImpl getInstance() {
        return new CarreraRepositoryImpl();
    }


    public static List<CarreraDto> obtenerCarrerasPorEstudiantesInscriptos(EntityManager em) {
        // JPQL para contar el número de estudiantes inscritos en cada carrera
        String jpql = "SELECT new tp.integrador.dto.CarreraDto(c.nombre, COUNT(ec.estudiante.id)) " +
                "FROM Carrera c " +
                "JOIN c.estudiantes ec " +  // JOIN con la entidad intermedia EstudianteCarrera
                "WHERE ec.estudiante.id IS NOT NULL " +
                "GROUP BY c.nombre " +
                "ORDER BY COUNT(ec.estudiante.id) DESC";

        // Crear y ejecutar la consulta
        TypedQuery<CarreraDto> query = em.createQuery(jpql, CarreraDto.class);
        return query.getResultList();
    }


}

