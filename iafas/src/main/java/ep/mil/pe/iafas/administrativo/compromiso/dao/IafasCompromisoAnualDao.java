package ep.mil.pe.iafas.administrativo.compromiso.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnual;

public class IafasCompromisoAnualDao {
	
	// definicion de la session
	private SqlSessionFactory sqlSessionFactory = null;

	public IafasCompromisoAnualDao(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<IafasCompromisoAnual> listaCompromisoAnual(IafasCompromisoAnual ca) {
		
        List<IafasCompromisoAnual> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("CompromisoAnual.listadoCab",ca);
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
	public List<IafasCompromisoAnual> listaDetalleCompromisoAnual(IafasCompromisoAnual ca) {
		
        List<IafasCompromisoAnual> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("CompromisoAnual.detalleCompAnual",ca);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	
	public int grabarCompAnual(IafasCompromisoAnual anual) {
		int reg =0;
	       SqlSession session = sqlSessionFactory.openSession();
	       try{
	        reg = session.insert("CompromisoAnual.grabarCompAnual",anual);
	       }
	       finally{
	        session.close();
	       }
		return reg;
	}
	
	public int enviarCompAnual(IafasCompromisoAnual anual) {
		int reg =0;
	       SqlSession session = sqlSessionFactory.openSession();
	       try{
	        reg = session.update("CompromisoAnual.enviarCompAnual",anual);
	       }
	       finally{
	        session.close();
	       }
		return reg;
	}
	
	public int deleteCompAnual(IafasCompromisoAnual anual) {
		int reg =0;
	       SqlSession session = sqlSessionFactory.openSession();
	       try{
	        reg = session.update("CompromisoAnual.dropCompAnual",anual);
	       }
	       finally{
	        session.close();
	       }
		return reg;
	}
	
	@SuppressWarnings("unchecked")
	public List<IafasCompromisoAnual> verMovimientoCA(IafasCompromisoAnual ca) {
		
        List<IafasCompromisoAnual> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("CompromisoAnual.listaMovCA",ca);
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
	public List<IafasCompromisoAnual> verOC(IafasCompromisoAnual ca) {
		
        List<IafasCompromisoAnual> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("CompromisoAnual.testOC",ca);
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
	public List<IafasCompromisoAnual> validaDuplicados(IafasCompromisoAnual ca) {
		
        List<IafasCompromisoAnual> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("CompromisoAnual.validarDuplicados",ca);
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
