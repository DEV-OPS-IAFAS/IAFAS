package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.IafasClasificadores;

public class IafasClasificadoresDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasClasificadoresDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasClasificadores> obtenerClasificadores() {
	    List<IafasClasificadores> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasClasificadores.obtenerClasificadores");
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
}
