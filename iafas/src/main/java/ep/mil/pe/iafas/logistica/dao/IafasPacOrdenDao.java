package ep.mil.pe.iafas.logistica.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import ep.mil.pe.iafas.logistica.model.IafasPacOrden;
import ep.mil.pe.iafas.programacion.model.IafasItem;


public class IafasPacOrdenDao {
	private SqlSessionFactory sqlSessionFactory = null;
	private static final Logger logger = Logger.getLogger(IafasPacOrden.class); 
	
	public IafasPacOrdenDao ( SqlSessionFactory sqlSessionFactory) {
		
		
		// TODO Auto-generated constructor stub
				this.sqlSessionFactory = sqlSessionFactory;
		
	}

	
	@SuppressWarnings("unchecked")
	public List<IafasPacOrden> listaContrato(IafasPacOrden ca){
        List<IafasPacOrden> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("IafasPacOrden.ordenContratoReg",ca);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	
	
	 public int registroOrden(IafasPacOrden orde) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("IafasPacOrden.grabarOrden",orde);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
	 
	 public int registroOrdenDetalle(IafasPacOrden orde) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("IafasPacOrden.grabarOrdenDetalle",orde);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
	 
		@SuppressWarnings("unchecked")
		public  List<IafasPacOrden> obtenerItemSeleccionado(String nombreItem) {
			List<IafasPacOrden> lista = null;
			 SqlSession session = sqlSessionFactory.openSession();
			try {
				lista = session.selectList("IafasPacOrden.obtenerItemContrato", nombreItem);
			} finally {
				session.close();
			}
			return lista;
		}
		
		@SuppressWarnings("unchecked")
		public List<IafasPacOrden> listaCertificado(IafasPacOrden ca){
	        List<IafasPacOrden> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("IafasPacOrden.obtenerCertificado",ca);
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
		public  List<IafasPacOrden> obtenerVerProveedor(String nombreItem) {
			List<IafasPacOrden> lista = null;
			 SqlSession session = sqlSessionFactory.openSession();
			try {
				lista = session.selectList("IafasPacOrden.obtenerProveedor", nombreItem);
			} finally {
				session.close();
			}
			return lista;
		}
		
		@SuppressWarnings("unchecked")
		public List<IafasPacOrden> listaOrdenConsulta(IafasPacOrden ca){
	        List<IafasPacOrden> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("IafasPacOrden.consultaOrden",ca);
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
		public List<IafasPacOrden> listaOrdenConsultaSC(IafasPacOrden ca){
	        List<IafasPacOrden> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("IafasPacOrden.consultaOrdenSC",ca);
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
