package ep.mil.pe.iafas.personal.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnual;
import ep.mil.pe.iafas.personal.model.IafasPersona;

public class IafasPersonalDao {
	
	private SqlSessionFactory sqlSessionFactory = null;
	
	public IafasPersonalDao(SqlSessionFactory sqlSessionFactory)
	{
		
		this.sqlSessionFactory =sqlSessionFactory;
	}
	
	 	@SuppressWarnings("unchecked")
	public List<IafasPersona> listaPersonal(IafasPersona pe) {
		
        List<IafasPersona> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("IafasPersona.listadoPersonal",pe);
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
	public List<IafasPersona> listaFamilia(int pe) {
		
        List<IafasPersona> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("IafasPersona.listadoFamilia",pe);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	 	

	 	
	 	public int grabarPersonalAdm(IafasPersona ca) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("IafasPersona.grabarPersonal",ca);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
	 	
		public int grabarPersonalFamilia(IafasPersona ca) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("IafasPersona.grabarFamilia",ca);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
		
	 	@SuppressWarnings("unchecked")
		public List<IafasPersona> listaEditarFamilia(IafasPersona pe) {
			
	        List<IafasPersona> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("IafasPersona.listaEditPersona",pe);
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
	        finally{
	             session.close();
	        }
	        return lista;
		}
	 	
		public int grabarEdit(IafasPersona ca) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("IafasPersona.grabarEditarPersonal",ca);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
		 	
		@SuppressWarnings("unchecked")
		public List<IafasPersona> listaPersonaFam(IafasPersona pe) {
			
	        List<IafasPersona> lista = null;
	        SqlSession session = sqlSessionFactory.openSession();
	        try{
	              lista = session.selectList("IafasPersona.listaVerPersona",pe);
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
