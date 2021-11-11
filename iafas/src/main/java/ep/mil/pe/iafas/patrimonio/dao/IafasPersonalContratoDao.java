package ep.mil.pe.iafas.patrimonio.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.patrimonio.model.IafasPersonalContrato;

public class IafasPersonalContratoDao {

	private SqlSessionFactory sqlSessionFactory = null;

	public IafasPersonalContratoDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasPersonalContrato> obtenerPersonaContratoCodigo(IafasPersonalContrato objBn) {
		List<IafasPersonalContrato> list = null;
		SqlSession session = this.sqlSessionFactory.openSession();
		try {
			list = session.selectList("IafasPersonalContrato.obtenerPersonaContratoCodigo",objBn);
		} finally {
			session.close();
		}
		return list;
	}
}
