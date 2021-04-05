package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.IafasTareasPptal;

public class IafasTareasPptalDao {
	private SqlSessionFactory sqlSessionFactory = null;

	public IafasTareasPptalDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasTareasPptal> obtenerTareasPresupuestales() {
	    List<IafasTareasPptal> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasTareasPptal.obtenerTareasPresupuestales");
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
}
