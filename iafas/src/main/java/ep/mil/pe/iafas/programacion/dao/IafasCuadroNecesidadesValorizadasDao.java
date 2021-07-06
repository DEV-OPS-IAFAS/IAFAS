package ep.mil.pe.iafas.programacion.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.programacion.model.IafasCuadroNecesidadesValorizadas;
import ep.mil.pe.iafas.programacion.model.IafasEventoPrincipal;

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
	
	public List<IafasCuadroNecesidadesValorizadas> obtenerCadenasGastoCNV(IafasCuadroNecesidadesValorizadas objBn) {
		List<IafasCuadroNecesidadesValorizadas> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasCuadroNecesidadesValorizadas.obtenerCadenasGastoCNV",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public Response mantenimientoCNV(IafasCuadroNecesidadesValorizadas objBn) throws SQLException {

		Response response = new Response();
		SqlSession session = this.sqlSessionFactory.openSession();
		Map<String, String> param = new HashMap<String, String>();
		try {
			param.put("cperiodoCodigo", objBn.getCperiodoCodigo());
			param.put("nfuenteFinanciamientoCodigo", String.valueOf(objBn.getNfuenteFinanciamientoCodigo()));
			param.put("ntareaPresupuestalCodigo", String.valueOf(objBn.getNtareaPresupuestalCodigo()));
			param.put("veventoPrincipalCodigo", objBn.getVeventoPrincipalCodigo());
			param.put("neventoFinalCodigo", String.valueOf(objBn.getNeventoFinalCodigo()));
			param.put("nclasificadorPresupuestalCodigo", String.valueOf(objBn.getNclasificadorPresupuestalCodigo()));
			param.put("nitemCodigo", String.valueOf(objBn.getNitemCodigo()));
			param.put("cunidadMedidaCodigo", objBn.getCunidadMedidaCodigo());
			param.put("ncnvCantidad", String.valueOf(objBn.getNcnvCantidad()));
			param.put("ncnvPrecio", String.valueOf(objBn.getNcnvPrecio()));
			param.put("vusuarioCodigo", objBn.getVusuarioCodigo());
			param.put("mode", objBn.getMode());

			String respuesta = (String) session
					.selectOne("IafasCuadroNecesidadesValorizadas.SP_IDU_CUADRO_NECESIDADES_VALORIZADAS", param);

			response.setCodigoRespuesta(param.get("codigoRespuesta"));
			response.setMensajeRespuesta(param.get("mensajeRespuesta"));

		} finally {
			session.close();
		}
		return response;
	}

	public List<IafasCuadroNecesidadesValorizadas> obtenerMontoCadenasGasto(IafasCuadroNecesidadesValorizadas objBn) {
		List<IafasCuadroNecesidadesValorizadas> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasCuadroNecesidadesValorizadas.obtenerMontoCadenasGasto", objBn);
		} finally {
			session.close();
		}
		return list;
	}
}
