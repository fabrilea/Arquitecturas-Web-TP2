package tp.integrador;

import tp.integrador.entidades.EstudianteCarreraPK;
import tp.integrador.repositories.entitiesRepositories.CarreraRepositoryImpl;
import tp.integrador.repositories.entitiesRepositories.EstudianteCarreraRepositoryImpl;
import tp.integrador.repositories.entitiesRepositories.EstudianteRepositoryImpl;
import tp.integrador.entidades.Carrera;
import tp.integrador.entidades.Estudiante;
import tp.integrador.entidades.EstudianteCarrera;
import tp.integrador.factory.AbstractFactory;
import tp.integrador.utils.HelperMySQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        dbMySQL.populateDB();

        AbstractFactory chosenFactory = AbstractFactory.getREPOSITORYFactory(1);

        EstudianteRepositoryImpl estudiante = chosenFactory.getEstudianteRepository();
        CarreraRepositoryImpl carrera = chosenFactory.getCarreraRepository();
        EstudianteCarreraRepositoryImpl estudianteCarrera = chosenFactory.getEstudianteCarreraRepository();

        /* Punto A */
        Estudiante e = new Estudiante(99, "Juan", "Gomez", 22, "Masculino", 12352378, "Córdoba", 1009
        );
        chosenFactory.getEstudianteRepository().insert(em, e);

        /* Punto B */
        Carrera c = chosenFactory.getCarreraRepository().findById(em, (long) 1);

        EstudianteCarreraPK ecpk = new EstudianteCarreraPK(99, 2);

        EstudianteCarrera ec = new EstudianteCarrera(ecpk, Date.valueOf("2020-01-01"), null, false, e, c);
        chosenFactory.getEstudianteCarreraRepository().insert(em, ec);

        /* Punto C */
        System.out.println("Estudiante por LU: ");
        System.out.println(estudiante.obtenerEstudiantePorLU(em, 12345));

        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Punto D */

        System.out.println("Estudiantes listados por Apellido: ");
        System.out.println(estudiante.obtenerEstudiantesPorApellidoAsc(em));

        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Punto E */
        System.out.println("Estudiantes por género: ");
        System.out.println(estudiante.obtenerEstudiantesPorGenero(em, "Masculino"));


        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();


        /* Punto F */
        System.out.println("Carreras por Inscriptos: ");
        System.out.println(carrera.obtenerCarrerasPorEstudiantesInscriptos(em));
        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Punto G */
        System.out.println("Estudiantes Ordenados por Carrera y Ciudad: ");
        System.out.println(estudiante.obtenerEstudiantesPorCarreraYCiudad(em, "Ingeniería en Sistemas", "Buenos Aires"));

        System.out.println("/////////////////////////////////////");
        System.out.println("/////////////////////////////////////");

        System.out.println();

        /* Ejercicio 3 */

        System.out.println("Generar Reporte: ");
        System.out.println(estudianteCarrera.generarReporteCarreras(em));


        em.close();
        emf.close();

    }
}
