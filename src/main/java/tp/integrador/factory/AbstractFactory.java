package tp.integrador.factory;


import tp.integrador.dao.entidadesDao.CarreraDao;
import tp.integrador.dao.entidadesDao.EstudianteCarreraDao;
import tp.integrador.dao.entidadesDao.EstudianteDao;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public abstract EstudianteDao getEstudianteDao();
    public abstract CarreraDao getCarreraDao();
    public abstract EstudianteCarreraDao getEstudianteCarreraDao();
    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC : {
                return MySQLDAOFactory.getInstance();
            }
            default: {
                return null;
            }
        }
    }

}
