package ep.mil.pe.iafas.patrimonio.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.patrimonio.model.IafasPatrimonioActa;

public class IafasPatrimonioActaDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasPatrimonioActaDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasPatrimonioActa> obtenerPatrimonioCabeceraActa(IafasPatrimonioActa objBn) {
		List<IafasPatrimonioActa> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasPatrimonioActa.obtenerPatrimonioCabeceraActa", objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasPatrimonioActa> obtenerPatrimonioActaEdicion(IafasPatrimonioActa objBn) {
		List<IafasPatrimonioActa> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasPatrimonioActa.obtenerPatrimonioActaEdicion", objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	public Response SP_IDU_IAFAS_PATRIMONIO_ACTA(IafasPatrimonioActa bean) throws SQLException {
		 Response  response = new Response();
		 SqlSession session = this.sqlSessionFactory.openSession();
		 Map<String, String> param = new HashMap<String, String>();
		 try {
		   param.put("cPeriodoCodigo", bean.getCPeriodoCodigo());
		   param.put("nPatrimonioTipoActaCodigo", String.valueOf(bean.getNPatrimonioTipoActaCodigo()));
		   param.put("nPatrimonioActaCodigo", String.valueOf(bean.getNPatrimonioActaCodigo()));
		   param.put("nPersonaCodigo", String.valueOf(bean.getNPersonaCodigo()));
		   param.put("nPersonalContratoCodigo", String.valueOf(bean.getNPersonalContratoCodigo()));
		   //param.put("dPatrimonioActaFecha", String.valueOf(bean.getDPatrimonioActaFecha()));
		   param.put("nPersonaPresidente", String.valueOf(bean.getNPersonaPresidente()));
		   param.put("nPersonaVocal", String.valueOf(bean.getNPersonaVocal()));
		   param.put("nPersonaSecretario", String.valueOf(bean.getNPersonaSecretario()));
		   param.put("vPatrimonioActaObservacion", bean.getVPatrimonioActaObservacion());
		   param.put("vusuarioCodigo", bean.getVUsuarioCodigo());
		   param.put("mode", bean.getMode());
		  param.put("codigoRespuesta", bean.getCodigoRespuesta());
          param.put("mensajeRespuesta", bean.getMensajeRespuesta());
          
          String respuesta= (String) session.selectOne("IafasPatrimonioActa.SP_IDU_IAFAS_PATRIMONIO_ACTA", param);
          
          response.setCodigoRespuesta(param.get("codigoRespuesta"));
          response.setMensajeRespuesta(param.get("mensajeRespuesta"));
         
		} finally {
			session.close();
		}
		return response;
	}
	
}
