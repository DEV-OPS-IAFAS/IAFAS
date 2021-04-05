package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.IafasPeriodos;

public class IafasPeriodosDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasPeriodosDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasPeriodos> obtenerPeriodosActivos() {
	    List<IafasPeriodos> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasPeriodos.obtenerPeriodosActivos");
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
}
