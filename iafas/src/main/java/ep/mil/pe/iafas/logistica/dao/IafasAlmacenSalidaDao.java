package ep.mil.pe.iafas.logistica.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenSalida;

public class IafasAlmacenSalidaDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasAlmacenSalidaDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<IafasAlmacenSalida> mostrarConsultaPrincipal(IafasAlmacenSalida objBn) {
		List<IafasAlmacenSalida> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasAlmacenSalida.mostrarConsultaPrincipal",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	
public Response mantenimientoCabecera(IafasAlmacenSalida objBn) throws SQLException {
		
		Response response = new Response();
		SqlSession session = this.sqlSessionFactory.openSession();
		Map<String, String> param = new HashMap<String, String>();
		try {
			param.put("cPeriodoCodigo", objBn.getCPeriodoCodigo());
            param.put("nAlmacenCodigo", String.valueOf(objBn.getNAlmacenCodigo()));
            param.put("cMesCodigo", objBn.getCMesCodigo());
            param.put("vAlmacenSalidaMotivo", objBn.getVAlmacenSalidaMotivo());
            param.put("cAreaLaboralCodigo", objBn.getCAreaLaboralCodigo());
            param.put("vUsuarioCodigo", objBn.getVUsuarioCreador());           
            param.put("mode", objBn.getMode());	
            
			String respuesta  =(String) session.selectOne("IafasAlmacenSalida.SP_IDU_PEDIDO_ALMACEN", param);
			 
			response.setCodigoRespuesta(param.get("codigoRespuesta"));
	        response.setMensajeRespuesta(param.get("mensajeRespuesta"));
	        response.setIdTransaccion(param.get("numeroAlmacen"));
	            
		} finally {
			session.close();
		}
		return response;
	}
}
