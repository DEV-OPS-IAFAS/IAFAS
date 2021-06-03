package ep.mil.pe.iafas.administrativo.girado.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.girado.model.IafasEntidadesCuentas;

public class IafasEntidadesCuentasDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasEntidadesCuentasDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasEntidadesCuentas> cargarCuentasPorEntidades(IafasEntidadesCuentas objBn) {
	    List<IafasEntidadesCuentas> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasEntidadesCuentas.cargarCuentasPorEntidades",objBn);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
	public List<IafasEntidadesCuentas> cargarMontosCuenta(IafasEntidadesCuentas objBn) {
	    List<IafasEntidadesCuentas> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasEntidadesCuentas.cargarMontosCuenta",objBn);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
}
