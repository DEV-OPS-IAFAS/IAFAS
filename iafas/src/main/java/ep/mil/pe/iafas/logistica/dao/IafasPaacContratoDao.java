package ep.mil.pe.iafas.logistica.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import ep.mil.pe.iafas.logistica.model.IafasPacContratos;

import java.util.*;


public class IafasPaacContratoDao {
	
	private SqlSessionFactory sqlSessionFactory = null;
	private static final Logger logger = Logger.getLogger(IafasPaacContratoDao.class);

	
	public IafasPaacContratoDao(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
	} 
	
	@SuppressWarnings("unchecked")
	public List<IafasPacContratos> showDetailContract(IafasPacContratos pac){
		List<IafasPacContratos> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPacContratos.showDetailContract",pac);
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
	public List<IafasPacContratos> showPaacProcess(String periodo){
		List<IafasPacContratos> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPacContratos.findProcessPaac",periodo);
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
	public List<IafasPacContratos> showPaacProcessFilter(IafasPacContratos entity){
		List<IafasPacContratos> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPacContratos.findProcessPaacFilter",entity);
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
	public List<IafasPacContratos> showContractsByYear(String periodo){
		List<IafasPacContratos> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPacContratos.showContracts",periodo);
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
      finally{
           session.close();
      }
		return lista;
	}
	
	public int saveContractPAAC(IafasPacContratos paac) {
		int i = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			i = session.insert("IafasPacContratos.saveContracts", paac);
		} catch (Exception e) {
			logger.error("ERROR : "+e.getMessage());
		}
		finally {
			session.close();
		}
		return i;
	}
	public int saveContractDetailsPAAC(IafasPacContratos paac) {
		int i = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			i = session.insert("IafasPacContratos.saveContractsDetails", paac);
		} catch (Exception e) {
			logger.error("ERROR : "+e.getMessage());
		}
		finally {
			session.close();
		}
		return i;
	}
}
