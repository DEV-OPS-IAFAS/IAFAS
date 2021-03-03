package ep.mil.pe.iafas.administrativo.compromiso.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnualDet;

public class IafasCompromisoAnualDetDao {
	private SqlSessionFactory sqlSessionFactory = null;
	
	public IafasCompromisoAnualDetDao(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<IafasCompromisoAnualDet> listaCompromisoAnualDet(IafasCompromisoAnualDet det) {
		
        List<IafasCompromisoAnualDet> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("CompromisoAnual.listadoDet",det);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	
	public int grabarCompAnualDet(IafasCompromisoAnualDet detalle) {
		int reg =0;
	       SqlSession session = sqlSessionFactory.openSession();
	       try{
	        reg = session.insert("CompromisoAnual.grabarCompAnualDet",detalle);
	       }
	       finally{
	        session.close();
	       }
		return reg;
	}
}
