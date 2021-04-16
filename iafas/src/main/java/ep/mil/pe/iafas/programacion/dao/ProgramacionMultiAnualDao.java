package ep.mil.pe.iafas.programacion.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.ProgramacionMultiAnual;
import ep.mil.pe.iafas.programacion.model.ProgramacionMultiAnualDetalle;

public class ProgramacionMultiAnualDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public ProgramacionMultiAnualDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<ProgramacionMultiAnual> mostrarCabecera(ProgramacionMultiAnual objBn) {
		List<ProgramacionMultiAnual> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnual.mostrarCabecera",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<ProgramacionMultiAnual> obtenerRegistro(ProgramacionMultiAnual objBn) {
		List<ProgramacionMultiAnual> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnual.obtenerRegistro",objBn);
		} finally {
			session.close();
		}
		return list;
	}

	public List<ProgramacionMultiAnual> obtenerMonto1(ProgramacionMultiAnual objBn) {
		List<ProgramacionMultiAnual> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnual.obtenerMonto1",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<ProgramacionMultiAnual> obtenerMonto2(ProgramacionMultiAnual objBn) {
		List<ProgramacionMultiAnual> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnual.obtenerMonto2",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<ProgramacionMultiAnual> obtenerMonto3(ProgramacionMultiAnual objBn) {
		List<ProgramacionMultiAnual> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnual.obtenerMonto3",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public int SP_IDU_PROGRAMACION_MULTIANUAL(ProgramacionMultiAnual bean) throws SQLException {
		int i = 0;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			i = session.insert("ProgramacionMultiAnual.SP_IDU_PROGRAMACION_MULTIANUAL", bean);
		} finally {
			session.close();
		}
		return i;
	}
	
}
