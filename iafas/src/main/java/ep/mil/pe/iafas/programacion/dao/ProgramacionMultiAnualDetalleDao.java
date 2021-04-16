package ep.mil.pe.iafas.programacion.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.ProgramacionMultiAnualDetalle;

public class ProgramacionMultiAnualDetalleDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public ProgramacionMultiAnualDetalleDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<ProgramacionMultiAnualDetalle> mostrarDetalle(ProgramacionMultiAnualDetalle objBnDet) {
		List<ProgramacionMultiAnualDetalle> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnualDetalle.mostrarDetalle",objBnDet);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<ProgramacionMultiAnualDetalle> mostrarDetalleCadenas(ProgramacionMultiAnualDetalle objBnDet) {
		List<ProgramacionMultiAnualDetalle> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnualDetalle.mostrarDetalleCadenas",objBnDet);
		} finally {
			session.close();
		}
		return list;
	}
	
	
	public List<ProgramacionMultiAnualDetalle> obtenerMontoDetalle1(ProgramacionMultiAnualDetalle objBn) {
		List<ProgramacionMultiAnualDetalle> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnualDetalle.obtenerMontoDetalle1",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<ProgramacionMultiAnualDetalle> obtenerMontoDetalle2(ProgramacionMultiAnualDetalle objBn) {
		List<ProgramacionMultiAnualDetalle> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnualDetalle.obtenerMontoDetalle2",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<ProgramacionMultiAnualDetalle> obtenerMontoDetalle3(ProgramacionMultiAnualDetalle objBn) {
		List<ProgramacionMultiAnualDetalle> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnualDetalle.obtenerMontoDetalle3",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public int SP_IDU_PROGRAMACION_MULTIANUALDETALLE(ProgramacionMultiAnualDetalle beanDetalle) throws SQLException {
		int i = 0;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			i = session.insert("ProgramacionMultiAnualDetalle.SP_IDU_PROGRAMACION_MULTIANUAL_DETALLE", beanDetalle);
		} finally {
			session.close();
		}
		return i;
	}
}
