package ep.mil.pe.iafas.logistica.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import ep.mil.pe.iafas.logistica.controller.IafasPacContratosController;
import ep.mil.pe.iafas.logistica.model.IafasPaacProcesoDetalle;
import ep.mil.pe.iafas.logistica.model.IafasPaacProcesos;
import ep.mil.pe.iafas.logistica.model.IafasPacContratos;

public class IafasPaacProcesoDao {
	
	private SqlSessionFactory sqlSessionFactory = null;
	private String mensajeBD;
	private static final Logger logger = Logger.getLogger(IafasPaacProcesoDao.class);


	public IafasPaacProcesoDao(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<IafasPaacProcesos> showDetailContract(String periodo){
		List<IafasPaacProcesos> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPaacProceso.showPaacProceso",periodo);
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
      finally{
           session.close();
      }
		return lista;
	}
	
	public int saveProcessPAAC(IafasPaacProcesos paac) {
		int i = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			i = session.insert("IafasPaacProceso.saveProcess", paac);
			i=1;
		} catch (Exception e) {
			setMensajeBD(e.getMessage());
			logger.error(e.getMessage());
		}
		finally {
			session.close();
		}
		return i;
	}
	
	public int saveProcessEtapaPAAC(IafasPaacProcesos paac) {
		int i = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			i = session.insert("IafasPaacProceso.saveProcessEtapa", paac);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			session.close();
		}
		return i;
	}
	
	@SuppressWarnings("unchecked")
	public List<IafasPaacProcesos> showProcessHead(IafasPaacProcesos proceso){
		List<IafasPaacProcesos> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPaacProceso.showProcessHead",proceso);
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
	public List<IafasPaacProcesos> showProcessEtapa(IafasPaacProcesos proceso){
		List<IafasPaacProcesos> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasPaacProceso.showProcessEtapa",proceso);
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
      finally{
           session.close();
      }
		return lista;
	}
	
	public int saveProcessEtapaDocPAAC(IafasPaacProcesos paac) {
		int i = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			i = session.insert("IafasPaacProceso.saveProcessEtapaDoc", paac);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			session.close();
		}
		return i;
	}

	public String getMensajeBD() {
		return mensajeBD;
	}

	public void setMensajeBD(String mensajeBD) {
		this.mensajeBD = mensajeBD;
	}
	
	
}
