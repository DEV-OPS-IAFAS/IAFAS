package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.programacion.model.IafasEventoPrincipal;

public class IafasEventoPrincipalDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasEventoPrincipalDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasEventoPrincipal> mostrarCabecera(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.mostrarCabecera",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasEventoPrincipal> generarCorrelativo(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.generarCorrelativo",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	
}
