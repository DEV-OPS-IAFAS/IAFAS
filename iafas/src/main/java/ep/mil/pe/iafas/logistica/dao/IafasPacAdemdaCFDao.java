package ep.mil.pe.iafas.logistica.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import ep.mil.pe.iafas.logistica.model.IafasPacAdemdaCF;
import ep.mil.pe.iafas.logistica.model.IafasPacContratos;

public class IafasPacAdemdaCFDao {

	private SqlSessionFactory sqlSessionFactory = null;

	private static final Logger logger = Logger.getLogger(IafasPacAdemdaCFDao.class);

	public IafasPacAdemdaCFDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public int registrarCartaFianza(IafasPacAdemdaCF entity) {
		int d = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {

			d = session.insert("IafasPacAdemdaCF.saveCF", entity);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] : " + e.getMessage());
		} finally {
			session.close();
		}
		return d;
	}

	public int registrarAdemda(IafasPacAdemdaCF entity) {
		int d = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {

			d = session.insert("IafasPacAdemdaCF.saveAdemda", entity);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] : " + e.getMessage());
		} finally {
			session.close();
		}
		return d;
	}
	
	@SuppressWarnings("unchecked")
	public List<IafasPacAdemdaCF> showPaacCartaFianza(IafasPacAdemdaCF entity){
		List<IafasPacAdemdaCF> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPacAdemdaCF.showCF",entity);
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
      finally{
           session.close();
      }
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<IafasPacAdemdaCF> showPaacAdemda(IafasPacAdemdaCF entity){
		List<IafasPacAdemdaCF> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPacAdemdaCF.showAdemda",entity);
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
