package ep.mil.pe.iafas.administrativo.maestros.combos.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ep.mil.pe.iafas.administrativo.maestros.combos.controller.IafasCombosController;
import ep.mil.pe.iafas.administrativo.maestros.combos.model.IafasCombos;

public class IafasCombosDao {

	private SqlSessionFactory sqlSessionFactory = null;
	
	public IafasCombosDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
    @SuppressWarnings("unchecked")
	public List<IafasCombos> getProcesoSel() {
        List<IafasCombos> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("Combos.procesoSel");
        }
        finally{
             session.close();
        }
        return lista;
    }
    
    @SuppressWarnings("unchecked")
	public List<IafasCombos> getFuenteFin() {
        List<IafasCombos> lista = null;
        SqlSession session = sqlSessionFactory.openSession();
        try{
              lista = session.selectList("Combos.tipoFinanciamiento");
        }
        finally{
             session.close();
        }
        return lista;
    }

}
