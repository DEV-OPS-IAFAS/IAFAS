package ep.mil.pe.iafas.administrativo.girado.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.girado.model.IafasGirado;
import ep.mil.pe.iafas.seguridad.model.IafasUsuarios;

public class IafasGiradoDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasGiradoDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasGirado> TraerDatosFaseDevengado(IafasGirado objBn) {
	    List<IafasGirado> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasGirado.TraerDatosFaseDevengado",objBn);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
	public int mantenimientoGiradoCab(IafasGirado giro) throws SQLException {
		int i = 0;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			i = session.insert("IafasGirado.SP_MTO_GIRADO", giro);
		} finally {
			session.close();
		}
		return i;
	}
}
