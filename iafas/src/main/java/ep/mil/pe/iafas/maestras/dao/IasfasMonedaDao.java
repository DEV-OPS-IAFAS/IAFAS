package ep.mil.pe.iafas.maestras.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.maestras.model.IafasMonedas;

public class IasfasMonedaDao {
	 private SqlSessionFactory sqlSessionFactory = null;
	  
	  public IasfasMonedaDao(SqlSessionFactory sqlSessionFactory) {
	    this.sqlSessionFactory = sqlSessionFactory;
	  }
	  
	  public List<IafasMonedas> listaMonedas() {
	    List<IafasMonedas> lista = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      lista = session.selectList("Monedas.listado");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      session.close();
	    } 
	    return lista;
	  }
}
