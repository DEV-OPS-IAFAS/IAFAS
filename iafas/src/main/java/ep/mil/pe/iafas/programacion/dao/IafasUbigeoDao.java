package ep.mil.pe.iafas.programacion.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.programacion.model.IafasTareasPptal;
import ep.mil.pe.iafas.programacion.model.IafasUbigeo;

public class IafasUbigeoDao {
	private SqlSessionFactory sqlSessionFactory = null;

	public IafasUbigeoDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<IafasUbigeo> obtenerDepartamentos() {
	    List<IafasUbigeo> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUbigeo.obtenerDepartamentos");
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
	public List<IafasUbigeo> obtenerProvincias(String codigoDepartamento) {
	    List<IafasUbigeo> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUbigeo.obtenerProvincias",codigoDepartamento);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
	
	public List<IafasUbigeo> obtenerUbigeos(IafasUbigeo objBn) {
	    List<IafasUbigeo> list = null;
	    SqlSession session = this.sqlSessionFactory.openSession();
	    try {
	      list = session.selectList("IafasUbigeo.obtenerUbigeos",objBn);
	    } finally {
	      session.close();
	    } 
	    return list;
	  }
}
