package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.IafasFuentesFinanciamiento;

public class IafasFuentesFinanciamientoDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasFuentesFinanciamientoDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<IafasFuentesFinanciamiento> obtenerFuentesFinanciamiento() {
		List<IafasFuentesFinanciamiento> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasFuentesFinanciamiento.obtenerFuentesFinanciamiento");
		} finally {
			session.close();
		}
		return list;
	}
}
