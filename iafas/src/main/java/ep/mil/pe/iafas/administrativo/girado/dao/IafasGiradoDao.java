package ep.mil.pe.iafas.administrativo.girado.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.girado.model.IafasGirado;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Response;

public class IafasGiradoDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasGiradoDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasGirado> TraerDatosFaseDevengado(IafasGirado objBn) {
	    List<IafasGirado> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasGirado.TraerDatosFaseDevengado",objBn);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
	public Response mantenimientoGiradoCab(IafasGirado giro) throws SQLException {

		Response response = new Response();
		SqlSession session = this.sqlSessionFactory.openSession();
		Map<String, String> param = new HashMap<String, String>();

		try {
			param.put("vano", giro.getVano());
            param.put("vregSiaf", giro.getVregSiaf());
            param.put("vctaCodigo", giro.getVctaCodigo());
            param.put("vcodTipGiro", giro.getVcodTipGiro());
            param.put("vglosa", giro.getVglosa());
            if(!giro.getMode().equals(Constantes.MODE_ANULACION)) {
            	param.put("impMonSol",  String.valueOf(giro.getImpMonSol().doubleValue()));	
            }
            param.put("vruc", giro.getVruc());
            param.put("vusuarioIng", giro.getVusuarioIng());
            param.put("mode", giro.getMode());
            if(!giro.getMode().equals(Constantes.MODE_ANULACION)) {
            	param.put("ntipCam", String.valueOf(giro.getNtipCam().doubleValue()));	
            }
            param.put("vtipMon", giro.getVtipMon() );
            param.put("vsecuencia",giro.getVsecuencia() );
            param.put("vsecuenciaInt", giro.getVsecuenciaInt());
            param.put("tipoInsercion", giro.getTipoInsercion());
            param.put("bancodBco", giro.getBancodBco());	
            
            
			String respuesta  =(String) session.selectOne("IafasGirado.SP_MTO_GIRADO", param);
			 
			response.setCodigoRespuesta(param.get("codigoRespuesta"));
	        response.setMensajeRespuesta(param.get("mensajeRespuesta"));
	            
		} finally {
			session.close();
		}
		return response;
	}
	
	public List<IafasGirado> obtenerExpedientesGirados(IafasGirado giro){
		List<IafasGirado> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasGirado.obtenerExpedientesGirados",giro);
	    } finally {
	      session.close();
	    } 
	    return list;
	}
	
	public List<IafasGirado> obtenerMontoCabeceraGiro(IafasGirado giro){
		List<IafasGirado> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasGirado.obtenerMontoCabeceraGiro",giro);
	    } finally {
	      session.close();
	    } 
	    return list;
	}
	
	public List<IafasGirado> obtenerMontoCabeceraDevengado(IafasGirado giro){
		List<IafasGirado> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasGirado.obtenerMontoCabeceraDevengado",giro);
	    } finally {
	      session.close();
	    } 
	    return list;
	}
	
	public List<IafasGirado> obtenerGiradosPorExpediente(IafasGirado giro){
		List<IafasGirado> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasGirado.obtenerGiradosPorExpediente",giro);
	    } finally {
	      session.close();
	    } 
	    return list;
	}
}
