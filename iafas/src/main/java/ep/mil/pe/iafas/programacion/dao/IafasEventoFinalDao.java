package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.IafasEventoFinal;

public class IafasEventoFinalDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasEventoFinalDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<IafasEventoFinal> mostrarCabeceraEvtFinal(IafasEventoFinal objBn) {
		List<IafasEventoFinal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoFinal.mostrarCabeceraEvtFinal", objBn);
		} finally {
			session.close();
		}
		return list;
	}
}
