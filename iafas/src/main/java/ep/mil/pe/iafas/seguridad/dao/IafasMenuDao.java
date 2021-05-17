package ep.mil.pe.iafas.seguridad.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.seguridad.model.IafasMenu;

public class IafasMenuDao {
	
	private SqlSessionFactory sqlSessionFactory = null;

	public IafasMenuDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasMenu> selectMenus(String objBean) {
	    List<IafasMenu> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasMenu.selectMenus",objBean);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
}
