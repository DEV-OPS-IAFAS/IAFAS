package ep.mil.pe.iafas.seguridad.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.seguridad.model.IafasModulos;

public class IafasModulosDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasModulosDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasModulos> selectModulos() {
	    List<IafasModulos> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasModulos.selectModulos");
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
}
