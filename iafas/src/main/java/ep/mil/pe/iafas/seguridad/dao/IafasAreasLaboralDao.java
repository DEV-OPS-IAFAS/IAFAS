package ep.mil.pe.iafas.seguridad.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.seguridad.model.IafasAreasLaboral;

public class IafasAreasLaboralDao {
	private SqlSessionFactory sqlSessionFactory = null;

	public IafasAreasLaboralDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	
	public List<IafasAreasLaboral> SelectAllAreas() {
	    List<IafasAreasLaboral> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasAreasLaboral.selectAll");
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
}
