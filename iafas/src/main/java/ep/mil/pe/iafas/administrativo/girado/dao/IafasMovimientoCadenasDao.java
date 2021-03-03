package ep.mil.pe.iafas.administrativo.girado.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.girado.model.IafasMovimientoCadenas;

public class IafasMovimientoCadenasDao {
	private SqlSessionFactory sqlSessionFactory = null;
	
	public IafasMovimientoCadenasDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasMovimientoCadenas> obtenerCadenasPorGirar(IafasMovimientoCadenas objBn) {
	    List<IafasMovimientoCadenas> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasMovimientoCadenas.obtenerCadenasPorGirar",objBn);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
}
