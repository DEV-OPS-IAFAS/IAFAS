package ep.mil.pe.iafas.seguridad.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.seguridad.model.IafasUsuarios;
import ep.mil.pe.iafas.seguridad.model.IafasUsuariosMenu;

public class IafasUsuariosMenuDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasUsuariosMenuDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<IafasUsuariosMenu> SelectListFiltro1(String objBean) {
		List<IafasUsuariosMenu> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasUsuariosMenu.selectFiltro1", objBean);
		} finally {
			session.close();
		}
		return list;
	}
	
	public List<IafasUsuariosMenu> SelectListFiltro2(IafasUsuariosMenu objBean) {
	    List<IafasUsuariosMenu> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUsuariosMenu.selectFiltro2", objBean);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
}
