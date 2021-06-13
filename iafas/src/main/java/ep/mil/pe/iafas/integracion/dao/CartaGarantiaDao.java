package ep.mil.pe.iafas.integracion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.integracion.model.OrdenesCS;

public class CartaGarantiaDao {
	
	private SqlSessionFactory sqlServer2SessionFactory = null;

	public CartaGarantiaDao(SqlSessionFactory sqlServer2SessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlServer2SessionFactory = sqlServer2SessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrdenesCS> findCartaGarantia(String numero) {
		
        List<OrdenesCS> lista = null;
        SqlSession session = sqlServer2SessionFactory.openSession();
        try{
              lista = session.selectList("IntegracionSQLServer.listCartaGarantia",numero);
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
