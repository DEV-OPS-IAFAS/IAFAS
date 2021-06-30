package ep.mil.pe.iafas.personal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.personal.model.IafasPersona;
import ep.mil.pe.iafas.personal.model.IafasPersonaVacaciones;

public class IafasPersonaVacacionesDao {

	
	private SqlSessionFactory sqlSessionFactory = null;
	
	 public IafasPersonaVacacionesDao (SqlSessionFactory sqlSessionFactory)
		{
			
			this.sqlSessionFactory =sqlSessionFactory;
		}
	 
	 

			 	@SuppressWarnings("unchecked")
			   public List<IafasPersonaVacaciones> listaPersonal(IafasPersonaVacaciones pe) {
				
			    List<IafasPersonaVacaciones> lista = null;
			    SqlSession session = sqlSessionFactory.openSession();
			    try{
			          lista = session.selectList("IafasPersonaVacaciones.listadoDocumento",pe);
			    }
			    catch(Exception e) {
			    	e.printStackTrace();
			    }
			    finally{
			         session.close();
			    }
			    return lista;
			}
			 	
			 
			public int grabarVacacionesInicio(IafasPersonaVacaciones ca) {
					   int reg =0;
				       SqlSession session = sqlSessionFactory.openSession();
				       try{
				        reg = session.insert("IafasPersonaVacaciones.grabarPersonalVacacion",ca);
				       }
				       finally{
				        session.close();
				       }
			return reg;
		}
			
			@SuppressWarnings("unchecked")
			   public List<IafasPersonaVacaciones> listaDetalleVaca(int pe) {
				
			    List<IafasPersonaVacaciones> lista = null;
			    SqlSession session = sqlSessionFactory.openSession();
			    try{
			          lista = session.selectList("IafasPersonaVacaciones.listadoPersonalVacaciones",pe);
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
			   public List<IafasPersonaVacaciones> listaEstado(IafasPersonaVacaciones pe) {
				
			    List<IafasPersonaVacaciones> lista = null;
			    SqlSession session = sqlSessionFactory.openSession();
			    try{
			          lista = session.selectList("IafasPersonaVacaciones.listadoEstado",pe);
			    }
			    catch(Exception e) {
			    	e.printStackTrace();
			    }
			    finally{
			         session.close();
			    }
			    return lista;
			}
			
			public int ActualizarVacacion(IafasPersonaVacaciones ca) {
				   int reg =0;
			       SqlSession session = sqlSessionFactory.openSession();
			       try{
			        reg = session.insert("IafasPersonaVacaciones.actualizarPersonalVacacion",ca);
			       }
			       finally{
			        session.close();
			       }
		return reg;
	}
			
			@SuppressWarnings("unchecked")
			   public List<IafasPersonaVacaciones> listaDetalleVacaAprobacion(IafasPersonaVacaciones pe) {
				
			    List<IafasPersonaVacaciones> lista = null;
			    SqlSession session = sqlSessionFactory.openSession();
			    try{
			          lista = session.selectList("IafasPersonaVacaciones.listadoPersonalAprobacion",pe);
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
