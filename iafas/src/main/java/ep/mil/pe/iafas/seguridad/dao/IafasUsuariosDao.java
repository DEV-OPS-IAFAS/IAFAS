package ep.mil.pe.iafas.seguridad.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.seguridad.model.IafasUsuarios;

public class IafasUsuariosDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasUsuariosDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<IafasUsuarios> SelectAllUsuario() {
	    List<IafasUsuarios> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUsuarios.selectAllUsuarios");
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	  
	  public List<IafasUsuarios> SelectListFiltro1(String objBean) {
	    List<IafasUsuarios> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUsuarios.selectFiltro1", objBean);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	  
	  public List<IafasUsuarios> SelectListFiltro2(IafasUsuarios objBean) {
	    List<IafasUsuarios> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUsuarios.selectFiltro2", objBean);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	  
	  public Response mantenimientoUsuarios(IafasUsuarios usuario) throws SQLException {
		    Response  response = new Response();
		    SqlSession session = this.sqlSessionFactory.openSession();
		    try {
		    	Map<String, String> param = new HashMap<String, String>();
                param.put("vusuarioCodigo", usuario.getVusuarioCodigo());
                param.put("vusuarioNombres", usuario.getVusuarioNombres());
                param.put("vusuarioPaterno", usuario.getVusuarioPaterno());
                param.put("vusuarioPassword", usuario.getVusuarioPassword());
                param.put("vusuarioCorreo", usuario.getVusuarioCorreo());
                param.put("vusuarioTelefono", usuario.getVusuarioTelefono());
                param.put("cestadoCodigo", usuario.getCestadoCodigo());
                param.put("vusuarioModifica", usuario.getVusuarioModifica());
                param.put("vusuarioMaterno", usuario.getVusuarioMaterno());
                param.put("vusuarioCargo", usuario.getVusuarioCargo());
                param.put("areaLaboralCodigo", usuario.getAreaLaboralCodigo());
                param.put("vusuarioCreador", usuario.getVusuarioCreador());
                param.put("modo", usuario.getModo());
                param.put("codigoRespuesta", usuario.getCodigoRespuesta());
                param.put("mensajeRespuesta", usuario.getMensajeRespuesta());
                String respuesta =(String) session.selectOne("IafasUsuarios.SP_MTO_IAFAS_USUARIOS", param);
                

                response.setCodigoRespuesta(param.get("codigoRespuesta"));
                response.setMensajeRespuesta(param.get("mensajeRespuesta"));
                
		    } finally {
		      session.close();
		    } 
		    return response;
		  }
}
