package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.IafasCuadroNecesidadesValorizadas;

public class IafasCuadroNecesidadesValorizadasDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasCuadroNecesidadesValorizadasDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasCuadroNecesidadesValorizadas> mostrarCabecera(IafasCuadroNecesidadesValorizadas objBn) {
		List<IafasCuadroNecesidadesValorizadas> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasCuadroNecesidadesValorizadas.mostrarCabecera",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasCuadroNecesidadesValorizadas> mostrarCNV(IafasCuadroNecesidadesValorizadas objBn) {
		List<IafasCuadroNecesidadesValorizadas> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasCuadroNecesidadesValorizadas.mostrarCNV",objBn);
		} finally {
			session.close();
		}
		return list;
	}
}
