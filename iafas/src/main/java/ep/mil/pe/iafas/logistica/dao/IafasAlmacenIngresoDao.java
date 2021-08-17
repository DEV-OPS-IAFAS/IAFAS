package ep.mil.pe.iafas.logistica.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.logistica.model.IafasAlmacenIngreso;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenIngresoDetalle;




public class IafasAlmacenIngresoDao {
	
	private SqlSessionFactory sqlSessionFactory = null;

	public IafasAlmacenIngresoDao(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<IafasAlmacenIngreso> showAlmacenIngreso(IafasAlmacenIngreso alm){
		List<IafasAlmacenIngreso> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasAlmacenIngreso.listarNeas",alm);
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
	public List<IafasAlmacenIngresoDetalle> showItemsOrden(IafasAlmacenIngresoDetalle alm){
		List<IafasAlmacenIngresoDetalle> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasAlmacenIngresoDetalle.verItemsOrden",alm);
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
      finally{
           session.close();
      }
		return lista;
	}
	
	//Store Procedure
	public int saveAlmacenIngreso(IafasAlmacenIngreso alm) {
		int i = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			i = session.insert("IafasAlmacenIngreso.insertAlmacenIngreso", alm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return i;
	}
	
	public int saveAlmacenIngresoDetails(IafasAlmacenIngresoDetalle alm) {
		int i = 0;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			i = session.insert("IafasAlmacenIngresoDetalle.insertAlmacenIngresoDet", alm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return i;
	}
	
	/*[INICIO] METODO AGREGADO POR ELVIS SEVERINO*/
	@SuppressWarnings("unchecked")
	public List<IafasAlmacenIngresoDetalle> obtenerCantidadPorItem(IafasAlmacenIngresoDetalle alm){
		List<IafasAlmacenIngresoDetalle> lista = null;
		SqlSession session = sqlSessionFactory.openSession();
		try{
            lista = session.selectList("IafasAlmacenIngresoDetalle.obtenerCantidadPorItem",alm);
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
      finally{
           session.close();
      }
		return lista;
	}
	/*[FIN] METODO AGREGADO POR ELVIS SEVERINO*/
}
