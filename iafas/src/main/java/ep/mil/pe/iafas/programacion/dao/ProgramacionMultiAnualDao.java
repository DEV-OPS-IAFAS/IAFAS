package ep.mil.pe.iafas.programacion.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
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
	
	public Response SP_IDU_PROGRAMACION_MULTIANUAL(ProgramacionMultiAnual bean) throws SQLException {
		 Response  response = new Response();
		 SqlSession session = this.sqlSessionFactory.openSession();
		 Map<String, String> param = new HashMap<String, String>();
		 try {
			param.put("periodo", bean.getPeriodo());
            param.put("fuenteFinac", String.valueOf(bean.getFuenteFinac()));
            param.put("tareaPtalCodigo", String.valueOf(bean.getTareaPtalCodigo()));
            param.put("ubigeoCodigo", bean.getUbigeoCodigo());
            param.put("importeA", String.valueOf(bean.getImporteA()));
            param.put("importeB", String.valueOf(bean.getImporteB()));
            param.put("importeC", String.valueOf(bean.getImporteB()));
            param.put("metaFisicaA", String.valueOf(bean.getMetaFisicaA()));
            param.put("metaFisicaB", String.valueOf(bean.getMetaFisicaB()));
            param.put("metaFisicaC", String.valueOf(bean.getMetaFisicaC()));
            param.put("usuarioCodigo", bean.getUsuarioCodigo());
            param.put("tipo", bean.getTipo());
            param.put("codigoRespuesta", bean.getCodigoRespuesta());
            param.put("mensajeRespuesta", bean.getMensajeRespuesta());
            
            String respuesta= (String) session.selectOne("ProgramacionMultiAnual.SP_IDU_PROGRAMACION_MULTIANUAL", param);
            
            response.setCodigoRespuesta(param.get("codigoRespuesta"));
            response.setMensajeRespuesta(param.get("mensajeRespuesta"));
           
		} finally {
			session.close();
		}
		return response;
	}
	
}
