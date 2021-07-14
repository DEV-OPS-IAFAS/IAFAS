package ep.mil.pe.iafas.logistica.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import ep.mil.pe.iafas.logistica.model.IafasProcedimientoItemGrupo;


public class IafasProcedimientoItemGrupoDao {
	private SqlSessionFactory sqlSessionFactory = null;
	private static final Logger logger = Logger.getLogger(IafasProcedimientoItemGrupo.class); 
	
	public IafasProcedimientoItemGrupoDao(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<IafasProcedimientoItemGrupo> listaGrupoItem(IafasProcedimientoItemGrupo pe) {
		
        List<IafasProcedimientoItemGrupo> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("IafasProcedimientoItemGrupo.verGrupoItem",pe);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally{
             session.close();
        }
        return lista;
	}
	 	
	 	
	 	  	public int grabarGrupoItem(IafasProcedimientoItemGrupo ca) {
			int reg =0;
		       SqlSession session = sqlSessionFactory.openSession();
		       try{
		        reg = session.insert("IafasProcedimientoItemGrupo.grabarGrupoItem",ca);
		       }
		       finally{
		        session.close();
		       }
			return reg;
		}
	 	 

}
