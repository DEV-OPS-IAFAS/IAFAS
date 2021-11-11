package ep.mil.pe.iafas.patrimonio.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.patrimonio.model.IafasPatrimonioActa;
import ep.mil.pe.iafas.patrimonio.model.IafasPatrimonioActaDetalle;

public class IafasPatrimonioDetalleActaDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasPatrimonioDetalleActaDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	
	public List<IafasPatrimonioActaDetalle> obtenerPatrimonioDetalleActa(IafasPatrimonioActaDetalle objBn) {
		List<IafasPatrimonioActaDetalle> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasPatrimonioActaDetalle.obtenerPatrimonioDetalleActa", objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public Response SP_IDU_IAFAS_PATRIMONIO_ACTA_DETALE(IafasPatrimonioActaDetalle bean) throws SQLException {
		 Response  response = new Response();
		 SqlSession session = this.sqlSessionFactory.openSession();
		 Map<String, String> param = new HashMap<String, String>();
		 try {
		   param.put("cPeriodoCodigo", bean.getCPeriodoCodigo());
		   param.put("nPersonaCodigo", String.valueOf(bean.getNPersonaCodigo()));
		   param.put("nPersonalContratoCodigo", String.valueOf(bean.getNPersonalContratoCodigo()));
		   param.put("nPatrimonioTipoActaCodigo", String.valueOf(bean.getNPatrimonioTipoActaCodigo()));
		   param.put("nPatrimonioActaCodigo", String.valueOf(bean.getNPatrimonioActaCodigo()));
		   param.put("nPatrimonioCodigo", String.valueOf(bean.getNPatrimonioCodigo()));
		   param.put("vinternamientoDetalleUbicacion", bean.getVinternamientoDetalleUbicacion());
		   param.put("vinternamientoDetalleObservacion", bean.getVinternamientoDetalleObservacion());
		   param.put("vusuarioCodigo", bean.getVUsuarioCodigo());
		   param.put("mode", bean.getMode());
		  param.put("codigoRespuesta", bean.getCodigoRespuesta());
         param.put("mensajeRespuesta", bean.getMensajeRespuesta());
         
         String respuesta= (String) session.selectOne("IafasPatrimonioActaDetalle.SP_IDU_IAFAS_PATRIMONIO_ACTA_DETALE", param);
         
         response.setCodigoRespuesta(param.get("codigoRespuesta"));
         response.setMensajeRespuesta(param.get("mensajeRespuesta"));
        
		} finally {
			session.close();
		}
		return response;
	}
}
