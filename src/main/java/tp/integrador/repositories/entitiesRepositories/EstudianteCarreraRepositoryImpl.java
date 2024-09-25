package tp.integrador.repositories.entitiesRepositories;

import tp.integrador.entidades.EstudianteCarreraPK;
import tp.integrador.dto.EstudianteCarreraDto;
import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;
import tp.integrador.entidades.EstudianteCarrera;
import tp.integrador.repositories.EstudianteCarreraRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class EstudianteCarreraRepositoryImpl implements EstudianteCarreraRepository {

    @Override
    public void insert(EntityManager em, EstudianteCarrera estudianteCarrera) {
        em.getTransaction().begin(); // La transacción se inicia acá y una vez activada no es necesaria repetirla en los otros insert/update
        em.persist(estudianteCarrera);  // Insertar el objeto estudianteCarrera
        em.getTransaction().commit();
    }

    @Override
    public EstudianteCarrera findById(EntityManager em, EstudianteCarreraPK id) {
        return em.find(EstudianteCarrera.class, id);
    }

    @Override
    public List<EstudianteCarrera> findAll(EntityManager em) {
        Query query = em.createQuery("SELECT ec FROM EstudianteCarrera ec");
        return query.getResultList();
    }

    @Override
    public void update(EntityManager em, EstudianteCarrera estudianteCarrera) {
        em.merge(estudianteCarrera);  // Actualizar el objeto estudianteCarrera
        em.getTransaction().commit();
    }

    @Override
    public void delete(EntityManager em, EstudianteCarreraPK id) {
        EstudianteCarrera estudianteCarrera = em.find(EstudianteCarrera.class, id);
        if (estudianteCarrera != null) {
            em.remove(estudianteCarrera);  // Eliminar el objeto estudianteCarrera
        }
        em.getTransaction().commit();
    }

    public static EstudianteCarreraRepositoryImpl getInstance() {
        return new EstudianteCarreraRepositoryImpl();
    }

    public static List<EstudianteCarreraDto> generarReporteCarreras(EntityManager em) {
        // Consulta JPQL para obtener los datos agrupados por carrera y año
        String jpql = "SELECT new tp.integrador.dto.EstudianteCarreraDto(c.nombre, YEAR(ec.fechaInscripcion), " +
                "COUNT(ec.estudiante.id), " +
                "SUM(CASE WHEN ec.estaGraduado = TRUE THEN 1 ELSE 0 END)) " +
                "FROM EstudianteCarrera ec " +
                "JOIN ec.carrera c " +
                "JOIN ec.estudiante e " +
                "GROUP BY c.nombre, YEAR(ec.fechaInscripcion)";

        // Crear la consulta con el EntityManager
        TypedQuery<EstudianteCarreraDto> query = em.createQuery(jpql, EstudianteCarreraDto.class);

        // Obtener los resultados de la consulta
        List<EstudianteCarreraDto> resultados = query.getResultList();

        // Ordenar los resultados en memoria por el año (segundo campo del DTO)
        resultados.sort(Comparator.comparing(EstudianteCarreraDto::getAnio));

        // Retornar la lista de resultados ordenada
        return resultados;
    }

}
