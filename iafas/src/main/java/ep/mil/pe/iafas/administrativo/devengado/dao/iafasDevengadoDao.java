package ep.mil.pe.iafas.administrativo.devengado.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensual;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasComprobanteRetencion;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasCompromiso;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasCompromisoDet;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasDevengado;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasDevengadoDet;

public class iafasDevengadoDao {
	
	// definicion de la session cambio de kate
		private SqlSessionFactory sqlSessionFactory = null;
		
		public iafasDevengadoDao(SqlSessionFactory sqlSessionFactory) {
			this.sqlSessionFactory = sqlSessionFactory;
		}


		@SuppressWarnings("unchecked")
		public List<IafasCompromiso> listaMensual(IafasCompromiso ca){
	        List<IafasCompromiso> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("VCompromiso.listado",ca);
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
		public List<IafasCompromisoDet> buscaCompromisoMensualDet(IafasCompromisoDet ca) {
			
	        List<IafasCompromisoDet> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("VCompromiso.listadoDet",ca);
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
		public List<IafasCompromiso> buscarDevengado(String vano) {
			
	        List<IafasCompromiso> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("VCompromiso.listadoConsultaDet",vano);
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
		public List<IafasCompromiso> obtenerDevengado(IafasCompromiso comp) {
			
	        List<IafasCompromiso> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("VCompromiso.verDevengado",comp);
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
		public List<IafasComprobanteRetencion> buscaReten(IafasComprobanteRetencion ret) {
			
	        List<IafasComprobanteRetencion> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("DevengadoRet.verRetencion",ret);
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	        finally{
	             session.close();
	        }
	        return lista;
		}
		
		public int registroDevengado(IafasDevengado deven) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("DevengadoRet.grabarDevengado",deven);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
		
		public int registroDevengadoDet(IafasDevengadoDet detdev) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("DevengadoRet.grabarDevengadoDet",detdev);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
		
		public int registroRetencionComprobante(IafasComprobanteRetencion ret) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("DevengadoRet.grabarDevengadoRetencion",ret);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
		
		
		public int deleteDevengado(IafasDevengado ret) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("DevengadoRet.dropDevengado",ret);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
		
		/*Metodo agregado por Elvis Severino*/
		@SuppressWarnings("unchecked")
		public List<IafasComprobanteRetencion> obtenerRetencionesparaGiro(IafasComprobanteRetencion ret) {
			
	        List<IafasComprobanteRetencion> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("DevengadoRet.obtenerRetencionesparaGiro",ret);
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	        finally{
	             session.close();
	        }
	        return lista;
		}
		
	
}
