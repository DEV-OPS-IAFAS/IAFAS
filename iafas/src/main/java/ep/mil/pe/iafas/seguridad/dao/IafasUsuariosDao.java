package ep.mil.pe.iafas.seguridad.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.seguridad.model.IafasUsuarios;

public class IafasUsuariosDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasUsuariosDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<IafasUsuarios> SelectAllUsuario() {
	    List<IafasUsuarios> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUsuarios.selectAllUsuarios");
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	  
	  public List<IafasUsuarios> SelectListFiltro1(String objBean) {
	    List<IafasUsuarios> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUsuarios.selectFiltro1", objBean);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	  
	  public List<IafasUsuarios> SelectListFiltro2(IafasUsuarios objBean) {
	    List<IafasUsuarios> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUsuarios.selectFiltro2", objBean);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	  
	  public int mantenimientoUsuarios(IafasUsuarios usuario) throws SQLException {
	    int i = 0;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      i = session.insert("IafasUsuarios.SP_MTO_IAFAS_USUARIOS", usuario);
	      //session.selectMap("", usuario);
	    } finally {
	      session.close();
	    } 
	    return i;
	  }
}
