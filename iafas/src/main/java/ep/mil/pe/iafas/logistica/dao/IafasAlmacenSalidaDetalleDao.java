package ep.mil.pe.iafas.logistica.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenSalidaDetalle;

public class IafasAlmacenSalidaDetalleDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasAlmacenSalidaDetalleDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public Response mantenimientoDetalle(IafasAlmacenSalidaDetalle objBn) throws SQLException {

		Response response = new Response();
		SqlSession session = this.sqlSessionFactory.openSession();
		Map<String, String> param = new HashMap<String, String>();
		try {
			param.put("cPeriodoCodigo", objBn.getCPeriodoCodigo());
			param.put("nAlmacenSalidaCodigo", String.valueOf(objBn.getNAlmacenSalidaCodigo()));
			param.put("nAlmacenSalidaSolicitado", String.valueOf(objBn.getNAlmacenSalidaSolicitado()));
			param.put("codigoItem", objBn.getCodigoItem());
			param.put("vUsuarioCodigo", objBn.getVUsuarioCreador());
			param.put("mode", objBn.getMode());

			String respuesta = (String) session.selectOne("IafasAlmacenSalidaDetalle.SP_IDU_PEDIDO_ALMACEN_DETALLE",
					param);

			response.setCodigoRespuesta(param.get("codigoRespuesta"));
			response.setMensajeRespuesta(param.get("mensajeRespuesta"));

		} finally {
			session.close();
		}
		return response;
	}

	public List<IafasAlmacenSalidaDetalle> obtenerRegistroDetalle(IafasAlmacenSalidaDetalle objBn) {
		List<IafasAlmacenSalidaDetalle> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasAlmacenSalidaDetalle.obtenerRegistroDetalle", objBn);
		} finally {
			session.close();
		}
		return list;
	}
}
