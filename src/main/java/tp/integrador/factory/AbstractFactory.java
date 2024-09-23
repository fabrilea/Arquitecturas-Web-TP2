package tp.integrador.factory;


import tp.integrador.repositories.entitiesRepositories.CarreraRepository;
import tp.integrador.repositories.entitiesRepositories.EstudianteCarreraRepository;
import tp.integrador.repositories.entitiesRepositories.EstudianteRepository;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public abstract EstudianteRepository getEstudianteRepository();
    public abstract CarreraRepository getCarreraRepository();
    public abstract EstudianteCarreraRepository getEstudianteCarreraRepository();
    public static AbstractFactory getREPOSITORYFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC : {
                return MySQLREPOSITORYFactory.getInstance();
            }
            default: {
                return null;
            }
        }
    }

}
