package ep.mil.pe.iafas.administrativo.compromiso.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnual;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensual;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensualDet;
import ep.mil.pe.iafas.administrativo.compromiso.model.ViewIafasCompromisoMensual;


public class IafasCompromisoMensualDao {

	private SqlSessionFactory sqlSessionFactory = null;
	
	public IafasCompromisoMensualDao(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	// vistas
	@SuppressWarnings("unchecked")
	public List<ViewIafasCompromisoMensual> buscaCompromisoAnual(ViewIafasCompromisoMensual ca) {
		
        List<ViewIafasCompromisoMensual> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("VCompromisoMensual.listadoAnual",ca);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewIafasCompromisoMensual> buscaCompromisoAnualSecuencia(ViewIafasCompromisoMensual ca) {
		
        List<ViewIafasCompromisoMensual> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("VCompromisoMensual.listadoAnualDetalle",ca);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<IafasCompromisoMensual> listaMensual(String vano){
        List<IafasCompromisoMensual> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("CompromisoMensual.listaMovimiento",vano);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	
	
	public int registroCompMensual(IafasCompromisoMensual mensual) {
		int reg =0;
	       SqlSession session = sqlSessionFactory.openSession();
	       try{
	        reg = session.insert("CompromisoMensual.grabarCompMensual",mensual);
	       }
	       finally{
	        session.close();
	       }
		return reg;
	}
	
	public int registroCompMensualDet(IafasCompromisoMensualDet detMensual) {
		int reg =0;
	       SqlSession session = sqlSessionFactory.openSession();
	       try{
	        reg = session.insert("CompromisoMensual.grabarMensualDet",detMensual);
	       }
	       finally{
	        session.close();
	       }
		return reg;
	}
	
	public int enviarCompromisoMensual(IafasCompromisoMensual men) {
		int reg =0;
	       SqlSession session = sqlSessionFactory.openSession();
	       try{
	        reg = session.insert("CompromisoMensual.enviarCompromisoMensual",men);
	       }
	       finally{
	        session.close();
	       }
		return reg;
	}
}
