package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.IafasItem;

public class IafasItemDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasItemDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<IafasItem> obtenerItems() {
		List<IafasItem> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasItem.obtenerItems");
		} finally {
			session.close();
		}
		return list;
	}

	public List<IafasItem> obtenerItemSeleccionado(String  nombreItem) {
		List<IafasItem> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasItem.obtenerItemSeleccionado", nombreItem);
		} finally {
			session.close();
		}
		return list;
	}
}
