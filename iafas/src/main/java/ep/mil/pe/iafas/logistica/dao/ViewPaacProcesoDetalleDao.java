package ep.mil.pe.iafas.logistica.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.logistica.model.ViewPaacProcesoDetalle;

public class ViewPaacProcesoDetalleDao {

	private SqlSessionFactory sqlSessionFactory = null;
	
	public ViewPaacProcesoDetalleDao(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewPaacProcesoDetalle> showDetailsPaacProcess(ViewPaacProcesoDetalle viewDetails){
		List<ViewPaacProcesoDetalle> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("ViewPaacProcesoDetalle.findProcessPaacDetail",viewDetails);
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
	public List<ViewPaacProcesoDetalle> showRNP(String  viewDetails){
		List<ViewPaacProcesoDetalle> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("ViewPaacProcesoDetalle.findProvRNP",viewDetails);
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
