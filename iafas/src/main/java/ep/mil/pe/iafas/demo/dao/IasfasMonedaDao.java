package ep.mil.pe.iafas.demo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.demo.model.IafasMonedas;


public class IasfasMonedaDao {

	// definicion de la session
	private SqlSessionFactory sqlSessionFactory = null;
	
	

	public IasfasMonedaDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}



	public List<IafasMonedas> listaMonedas() {
		
        List<IafasMonedas> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("Monedas.listado");
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
