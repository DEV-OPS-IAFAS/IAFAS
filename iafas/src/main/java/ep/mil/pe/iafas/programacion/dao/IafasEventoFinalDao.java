package ep.mil.pe.iafas.programacion.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.programacion.model.IafasEventoFinal;
import ep.mil.pe.iafas.programacion.model.IafasEventoPrincipal;

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

	public Response mantenimientoEventoFinal(IafasEventoFinal objBn) throws SQLException {

		Response response = new Response();
		SqlSession session = this.sqlSessionFactory.openSession();
		Map<String, String> param = new HashMap<String, String>();
		try {
			param.put("cperiodoCodigo", objBn.getCperiodoCodigo());
			param.put("nfuenteFinanciamientoCodigo", String.valueOf(objBn.getNfuenteFinanciamientoCodigo()));
			param.put("ntareaPresupuestalCodigo", String.valueOf(objBn.getNtareaPresupuestalCodigo()));
			param.put("veventoPrincipalCodigo", objBn.getVeventoPrincipalCodigo());
			param.put("neventoFinalCodigo", String.valueOf(objBn.getNeventoFinalCodigo()));
			param.put("veventoFinalNombre", objBn.getVeventoFinalNombre());
			param.put("neventoFinalPrioridad", String.valueOf(objBn.getNeventoFinalPrioridad()));
			param.put("neventoFinalMetaFisica", String.valueOf(objBn.getNeventoFinalMetaFisica()));
			param.put("vusuarioCodigo", objBn.getVusuarioCodigo());
			param.put("mode", objBn.getMode());

			String respuesta = (String) session.selectOne("IafasEventoFinal.SP_IDU_EVENTO_FINAL", param);

			response.setCodigoRespuesta(param.get("codigoRespuesta"));
			response.setMensajeRespuesta(param.get("mensajeRespuesta"));

		} finally {
			session.close();
		}
		return response;
	}

	public List<IafasEventoFinal> editEventoFinal(IafasEventoFinal objBn) {
		List<IafasEventoFinal> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasEventoFinal.editEventoFinal", objBn);
		} finally {
			session.close();
		}
		return list;
	}
}
