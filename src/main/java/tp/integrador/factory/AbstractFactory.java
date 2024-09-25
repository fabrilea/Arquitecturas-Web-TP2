package tp.integrador.factory;


import tp.integrador.repositories.entitiesRepositories.CarreraRepositoryImpl;
import tp.integrador.repositories.entitiesRepositories.EstudianteCarreraRepositoryImpl;
import tp.integrador.repositories.entitiesRepositories.EstudianteRepositoryImpl;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public abstract EstudianteRepositoryImpl getEstudianteRepository();
    public abstract CarreraRepositoryImpl getCarreraRepository();
    public abstract EstudianteCarreraRepositoryImpl getEstudianteCarreraRepository();
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
