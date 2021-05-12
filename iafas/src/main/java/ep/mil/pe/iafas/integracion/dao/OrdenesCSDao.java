package ep.mil.pe.iafas.integracion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.integracion.model.OrdenesCS;

public class OrdenesCSDao {
	
	private SqlSessionFactory sqlServerSessionFactory = null;

	
	
	public OrdenesCSDao(SqlSessionFactory sqlServerSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlServerSessionFactory = sqlServerSessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<OrdenesCS> findPurchaseOrder(OrdenesCS entity) {
		
        List<OrdenesCS> lista = null;
        SqlSession session = sqlServerSessionFactory.openSession();
        try{
              lista = session.selectList("IntegracionSQLServer.listOrdenes",entity);
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
