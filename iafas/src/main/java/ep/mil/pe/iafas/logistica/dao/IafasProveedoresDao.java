package ep.mil.pe.iafas.logistica.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import ep.mil.pe.iafas.logistica.model.IafasProveedores;


public class IafasProveedoresDao {
	private SqlSessionFactory sqlSessionFactory = null;
	private static final Logger logger = Logger.getLogger(IafasProveedores.class); 
	
	
	public IafasProveedoresDao (SqlSessionFactory sqlSessionFactory) {
		
		// TODO Auto-generated constructor stub
				this.sqlSessionFactory = sqlSessionFactory;
	}
	
	
	 	@SuppressWarnings("unchecked")
	public List<IafasProveedores> listaProveedor(IafasProveedores pe) {
		
        List<IafasProveedores> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("IafasProveedores.proveedorConsulta",pe);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	 	
	 	
	 	  	public int grabarProveedor(IafasProveedores ca) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("IafasProveedores.grabarProveedores",ca);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
	 	 

		 	@SuppressWarnings("unchecked")
		public List<IafasProveedores> verProveedor(IafasProveedores pe) {
			
	        List<IafasProveedores> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("IafasProveedores.verproveedores",pe);
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	        finally{
	             session.close();
	        }
	        return lista;
		}
		  	public int grabarBanco(IafasProveedores ca) {
				int reg =0;
			       SqlSession session = sqlSessionFactory.openSession();
			       try{
			        reg = session.insert("IafasProveedores.grabarBanco",ca);
			       }
			       finally{
			        session.close();
			       }
				return reg;
			}
		  	public int grabarRnp(IafasProveedores ca) {
				int reg =0;
			       SqlSession session = sqlSessionFactory.openSession();
			       try{
			        reg = session.insert("IafasProveedores.grabarRnp",ca);
			       }
			       finally{
			        session.close();
			       }
				return reg;
			}

}
