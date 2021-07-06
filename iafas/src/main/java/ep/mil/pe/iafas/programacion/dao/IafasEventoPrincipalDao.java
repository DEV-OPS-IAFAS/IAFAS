package ep.mil.pe.iafas.programacion.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Response;
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
	
	public List<IafasEventoPrincipal> mostrarCabeceraEvtSecundario(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.mostrarCabeceraEvtSecundario",objBn);
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
	
	public Response mantenimientoEventoPrincipal(IafasEventoPrincipal objBn) throws SQLException {
		
		Response response = new Response();
		SqlSession session = this.sqlSessionFactory.openSession();
		Map<String, String> param = new HashMap<String, String>();
		try {
			param.put("cperiodoCodigo", objBn.getCperiodoCodigo());
            param.put("nfuenteFinanciamientoCodigo", String.valueOf(objBn.getNfuenteFinanciamientoCodigo()));
            param.put("ntareaPresupuestalCodigo", String.valueOf(objBn.getNtareaPresupuestalCodigo()));
            param.put("veventoPrincipalCodigo", objBn.getVeventoPrincipalCodigo());
            param.put("veventoPrincipalAnexo", objBn.getVeventoPrincipalAnexo());
            param.put("veventoPrincipalNombre", objBn.getVeventoPrincipalNombre());
            param.put("veventoPrincipalComentario",objBn.getVeventoPrincipalComentario());
            param.put("neventoPrincipalNivel", String.valueOf(objBn.getNeventoPrincipalNivel()));
            param.put("neventoPrincipalNiveles",String.valueOf(objBn.getNeventoPrincipalNiveles()));
            param.put("ceventoPrincipalFinal", objBn.getCeventoPrincipalFinal());
            param.put("vusuarioCodigo", objBn.getVusuarioCodigo());	
            param.put("mode", objBn.getMode());	
            
            
			String respuesta  =(String) session.selectOne("IafasEventoPrincipal.SP_IDU_EVENTO_PRINCIPAL", param);
			 
			response.setCodigoRespuesta(param.get("codigoRespuesta"));
	        response.setMensajeRespuesta(param.get("mensajeRespuesta"));
	            
		} finally {
			session.close();
		}
		return response;
	}
	
	public List<IafasEventoPrincipal> generarCorrelativoSecundario(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.generarCorrelativoSecundario",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasEventoPrincipal> verEventoSecundario(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.verEventoSecundario",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasEventoPrincipal> obtenerNivelActual(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.obtenerNivelActual",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasEventoPrincipal> verEventoSecundarioAnterior(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.verEventoSecundarioAnterior",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasEventoPrincipal> obtenerEventoPrincipalDelAnexo(String evento) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.obtenerEventoPrincipalDelAnexo",evento);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasEventoPrincipal> editEventoPrincipal(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.editEventoPrincipal",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasEventoPrincipal> editEventoSecundario(IafasEventoPrincipal objBn) {
		List<IafasEventoPrincipal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoPrincipal.editEventoSecundario",objBn);
		} finally {
			session.close();
		}
		return list;
	}
}
