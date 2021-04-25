package ep.mil.pe.iafas.programacion.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
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
	
	public List<ProgramacionMultiAnualDetalle> mostrarDatosDetalle(ProgramacionMultiAnualDetalle objBnDet) {
		List<ProgramacionMultiAnualDetalle> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("ProgramacionMultiAnualDetalle.mostrarDatosDetalle",objBnDet);
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
	
	public Response SP_IDU_PROGRAMACION_MULTIANUALDETALLE(ProgramacionMultiAnualDetalle beanDetalle)
			throws SQLException {
		Response response = new Response();
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("periodo", beanDetalle.getPeriodo());
			param.put("fuenteFinac", String.valueOf(beanDetalle.getFuenteFinac()));
			param.put("tareaPtalCodigo", String.valueOf(beanDetalle.getTareaPtalCodigo()));
			param.put("codCla", String.valueOf(beanDetalle.getCodCla()));
			param.put("importeA", String.valueOf(beanDetalle.getImporteA()));
			param.put("importeB", String.valueOf(beanDetalle.getImporteB()));
			param.put("importeC", String.valueOf(beanDetalle.getImporteB()));
			param.put("usuarioCodigo", beanDetalle.getUsuarioCodigo());
			param.put("tipo", beanDetalle.getTipo());
			param.put("codigoRespuesta", beanDetalle.getCodigoRespuesta());
			param.put("mensajeRespuesta", beanDetalle.getMensajeRespuesta());

			String respuesta = (String) session.selectOne("ProgramacionMultiAnualDetalle.SP_IDU_PROGRAMACION_MULTIANUAL_DETALLE", param);

			response.setCodigoRespuesta(param.get("codigoRespuesta"));
			response.setMensajeRespuesta(param.get("mensajeRespuesta"));
		} finally {
			session.close();
		}
		return response;
	}
	
	public List<ProgramacionMultiAnualDetalle> obtenerMontoDetalleAnio1(ProgramacionMultiAnualDetalle objBn) {

		SqlSession session = this.sqlSessionFactory.openSession();
		List<ProgramacionMultiAnualDetalle> lst= null;
		try {
			
			lst = session.selectList("ProgramacionMultiAnualDetalle.obtenerMontoDetalleAnio1", objBn);

			if (lst != null) {
				return lst ;
			}

		} finally {
			session.close();
		}
		return lst;
	}
	
	public List<ProgramacionMultiAnualDetalle> obtenerMontoDetalleAnio2(ProgramacionMultiAnualDetalle objBn) {
		List<ProgramacionMultiAnualDetalle> lst= null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {

			lst = session.selectList("ProgramacionMultiAnualDetalle.obtenerMontoDetalleAnio2", objBn);

			if (lst != null) {
				return lst ;
			}

		} finally {
			session.close();
		}
		return lst;
	}
	
	public List<ProgramacionMultiAnualDetalle> obtenerMontoDetalleAnio3(ProgramacionMultiAnualDetalle objBn) {
		List<ProgramacionMultiAnualDetalle> lst= null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {

			lst = session.selectList("ProgramacionMultiAnualDetalle.obtenerMontoDetalleAnio3", objBn);

			if (lst != null) {
				return lst ;
			}

		} finally {
			session.close();
		}
		return lst;
	}
}
