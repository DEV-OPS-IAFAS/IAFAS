package ep.mil.pe.iafas.administrativo.maestros.combos.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

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

    @SuppressWarnings("unchecked")
 	public List<IafasCombos> getTipoGrado(String filtro) {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.tipoGrado",filtro);
         }
         finally{
              session.close();
         }
         return lista;
     }
     @SuppressWarnings("unchecked")
    	public List<IafasCombos> getTipoArea() {
            List<IafasCombos> lista = null;
            SqlSession session = sqlSessionFactory.openSession();
            try{
                  lista = session.selectList("Combos.tipoArea");
            }
            finally{
                 session.close();
            }
            return lista;
        }
     
     @SuppressWarnings("unchecked")
 	public List<IafasCombos> getTipoFamilia() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.tipoFamilia");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     @SuppressWarnings("unchecked")
 	public List<IafasCombos> getMonedas() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.monedas");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     @SuppressWarnings("unchecked")
 	public List<IafasCombos> getProveedores() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.proveedores");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     @SuppressWarnings("unchecked")
 	public List<IafasCombos> getBancos() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.bancos");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     @SuppressWarnings("unchecked")
   	public List<IafasCombos> getTipoDocumento() {
           List<IafasCombos> lista = null;
           SqlSession session = sqlSessionFactory.openSession();
           try{
                 lista = session.selectList("Combos.tipoDocumento");
           }
           finally{
                session.close();
           }
           return lista;
       }
     
     @SuppressWarnings("unchecked")
    	public List<IafasCombos> getTipoImpuesto() {
            List<IafasCombos> lista = null;
            SqlSession session = sqlSessionFactory.openSession();
            try{
                  lista = session.selectList("Combos.tipoImpuesto");
            }
            finally{
                 session.close();
            }
            return lista;
        }
     
     /*Cambios agregados por Elvis Severino*/
     @SuppressWarnings("unchecked")
   	public List<IafasCombos> getPeriodos() {
           List<IafasCombos> lista = null;
           SqlSession session = sqlSessionFactory.openSession();
           try{
                 lista = session.selectList("Combos.periodos");
           }
           finally{
                session.close();
           }
           return lista;
       }
     
     public List<IafasCombos> getEntidades() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.entidades");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     public List<IafasCombos> getSistemaContratacion() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.sistemaContratacion");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     @SuppressWarnings("unchecked")
  	public List<IafasCombos> getModalidadContratacion() {
          List<IafasCombos> lista = null;
          SqlSession session = sqlSessionFactory.openSession();
          try{
                lista = session.selectList("Combos.modalidadContratacion");
          }
          finally{
               session.close();
          }
          return lista;
      }
     
     @SuppressWarnings("unchecked")
   	public List<IafasCombos> getItemTipo() {
           List<IafasCombos> lista = null;
           SqlSession session = sqlSessionFactory.openSession();
           try{
                 lista = session.selectList("Combos.itemTipo");
           }
           finally{
                session.close();
           }
           return lista;
       }
     
     @SuppressWarnings("unchecked")
   	public List<IafasCombos> getItem() {
           List<IafasCombos> lista = null;
           SqlSession session = sqlSessionFactory.openSession();
           try{
                 lista = session.selectList("Combos.item");
           }
           finally{
                session.close();
           }
           return lista;
       }
     
     @SuppressWarnings("unchecked")
   	public List<IafasCombos> getUnidadMedida() {
           List<IafasCombos> lista = null;
           SqlSession session = sqlSessionFactory.openSession();
           try{
                 lista = session.selectList("Combos.unidadMedida");
           }
           finally{
                session.close();
           }
           return lista;
       }
     
     @SuppressWarnings("unchecked")
  	public List<IafasCombos> getTipoProcedimiento(String filtro) {
          List<IafasCombos> lista = null;
          SqlSession session = sqlSessionFactory.openSession();
          try{
                lista = session.selectList("Combos.tipoProcedimiento",filtro);
          }
          finally{
               session.close();
          }
          return lista;
      }
     
     public List<IafasCombos> getUbigeo() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.ubigeo");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     @SuppressWarnings("unchecked")
  	public List<IafasCombos> getProvincia(String filtro) {
          List<IafasCombos> lista = null;
          SqlSession session = sqlSessionFactory.openSession();
          try{
                lista = session.selectList("Combos.tipoprovincia",filtro);
          }
          finally{
               session.close();
          }
          return lista;
      }
     
     
     @SuppressWarnings("unchecked")
 	public List<IafasCombos> gettipoDepartamento() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.departamento");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     
     @SuppressWarnings("unchecked")
   	public List<IafasCombos> getUbigeoDet(String filtro) {
           List<IafasCombos> lista = null;
           SqlSession session = sqlSessionFactory.openSession();
           try{
                 lista = session.selectList("Combos.ubigeoDet",filtro);
           }
           finally{
                session.close();
           }
           return lista;
       }
      
     @SuppressWarnings("unchecked")
 	public List<IafasCombos> gettipoPlanilla() {
         List<IafasCombos> lista = null;
         SqlSession session = sqlSessionFactory.openSession();
         try{
               lista = session.selectList("Combos.tipoPlanilla");
         }
         finally{
              session.close();
         }
         return lista;
     }
     
     @SuppressWarnings("unchecked")
  	public List<IafasCombos> getTipoEtapa() {
          List<IafasCombos> lista = null;
          SqlSession session = sqlSessionFactory.openSession();
          try{
                lista = session.selectList("Combos.tipoetapa");
          }
          finally{
               session.close();
          }
          return lista;
      }
}
