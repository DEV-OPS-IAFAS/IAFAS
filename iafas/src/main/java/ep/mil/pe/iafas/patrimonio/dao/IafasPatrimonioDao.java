package ep.mil.pe.iafas.patrimonio.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.patrimonio.model.IafasPatrimonio;
import ep.mil.pe.iafas.patrimonio.model.IafasPersonalContrato;

public class IafasPatrimonioDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasPatrimonioDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<IafasPatrimonio> obtenerPatrimonioCabecera() {
		List<IafasPatrimonio> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasPatrimonio.obtenerPatrimonioCabecera");
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasPatrimonio> editarPatrimonio(IafasPatrimonio objBn) {
		List<IafasPatrimonio> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasPatrimonio.editarPatrimonio",objBn);
		} finally {
			session.close();
		}
		return list;
	}
	
	
	public Response SP_IDU_PATRIMONIO(IafasPatrimonio bean) throws SQLException {
		 Response  response = new Response();
		 SqlSession session = this.sqlSessionFactory.openSession();
		 Map<String, String> param = new HashMap<String, String>();
		 try {
		   param.put("nPatrimonioCodigo", String.valueOf(bean.getNPatrimonioCodigo()));
		   param.put("vPatrimonioCodigo", bean.getVPatrimonioCodigo());
		   param.put("vPatrimonioDescripcion", bean.getVPatrimonioDescripcion());
		   param.put("nMarcaCodigo", String.valueOf(bean.getNMarcaCodigo()));
		   param.put("vPatrimonioModelo", bean.getVPatrimonioModelo());
		   param.put("vPtarimonioColorCodigo", bean.getVPtarimonioColorCodigo());
		   param.put("nTipoMaterialCodigo", String.valueOf(bean.getNTipoMaterialCodigo()));
		   param.put("cPatrimonioCategoriaCodigo", String.valueOf(bean.getCPatrimonioCategoriaCodigo()));
		   param.put("vpatrimonioSerie", bean.getVpatrimonioSerie());
		   param.put("vPatriminioDimensiones", bean.getVPatriminioDimensiones());
		   param.put("vPatrimonioObservacion", bean.getVPatrimonioObservacion());
		   param.put("vpatrimonioTipo", bean.getVpatrimonioTipo());
           param.put("vUsuarioCodigo", bean.getVUsuarioCodigo());
           param.put("mode", bean.getTipo());
           param.put("codigoRespuesta", bean.getCodigoRespuesta());
           param.put("mensajeRespuesta", bean.getMensajeRespuesta());
           
           String respuesta= (String) session.selectOne("IafasPatrimonio.SP_IDU_PATRIMONIO", param);
           
           response.setCodigoRespuesta(param.get("codigoRespuesta"));
           response.setMensajeRespuesta(param.get("mensajeRespuesta"));
          
		} finally {
			session.close();
		}
		return response;
	}
	
	public List<IafasPatrimonio> obtenerPatrimonioCompletext(String  nombrePatrimonio) {
		List<IafasPatrimonio> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasPatrimonio.obtenerPatrimonioCompletext",nombrePatrimonio);
		} finally {
			session.close();
		}
		return list;
	}
}
