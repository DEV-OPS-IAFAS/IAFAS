package ep.mil.pe.iafas.logistica.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenSalida;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenSalidaAtencion;

public class IafasAlmacenSalidaAtencionDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasAlmacenSalidaAtencionDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	
	public Response mantenimientoCabecera(IafasAlmacenSalidaAtencion objBn) throws SQLException {

		Response response = new Response();
		SqlSession session = this.sqlSessionFactory.openSession();
		Map<String, String> param = new HashMap<String, String>();
		try {
			param.put("cPeriodoCodigo", objBn.getCPeriodoCodigo());
			param.put("nAlmacenSalidaCodigo", String.valueOf(objBn.getNAlmacenSalidaCodigo()));
			param.put("nItemCodigo", String.valueOf(objBn.getNItemCodigo()));
			param.put("nAlmacenCodigo", String.valueOf(objBn.getNAlmacenCodigo()));
			param.put("nAlmacenSalidaAtendido", String.valueOf(objBn.getNAlmacenSalidaAtendido()));
			param.put("vUsuarioCodigo", objBn.getVUsuarioCreador());
			param.put("mode", objBn.getMode());

			String respuesta = (String) session.selectOne("IafasAlmacenSalidaAtencion.SP_IDU_PEDIDO_ALMACEN_ATENCION", param);

			response.setCodigoRespuesta(param.get("codigoRespuesta"));
			response.setMensajeRespuesta(param.get("mensajeRespuesta"));

		} finally {
			session.close();
		}
		return response;
	}
	
	public List<IafasAlmacenSalidaAtencion> mostrarAtencion(IafasAlmacenSalidaAtencion objBn) {
		List<IafasAlmacenSalidaAtencion> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasAlmacenSalidaAtencion.mostrarAtencion", objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasAlmacenSalidaAtencion> totalAtendidoItem(IafasAlmacenSalidaAtencion objBn) {
		List<IafasAlmacenSalidaAtencion> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasAlmacenSalidaAtencion.totalAtendidoItem", objBn);
		} finally {
			session.close();
		}
		return list;
	}
}
