package ep.mil.pe.iafas.administrativo.maestros.combos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import ep.mil.pe.iafas.administrativo.maestros.combos.dao.IafasCombosDao;
import ep.mil.pe.iafas.administrativo.maestros.combos.model.IafasCombos;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;

@ManagedBean(name = "mbCombos")
@SessionScoped
public class IafasCombosController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public IafasCombosController() {
		// TODO Auto-generated constructor stub
	}
	
	public List<SelectItem> procesoSel ;
	public List<SelectItem> fuenteFinanciamiento;
	
    public List<SelectItem> getProcesoSel() {
        procesoSel = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getProcesoSel();
             for(IafasCombos d : lista){
               procesoSel.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
             }
       
        return procesoSel;
    }
    
    public List<SelectItem> getFuenteFinanciamiento() {
    	fuenteFinanciamiento = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getFuenteFin();
             for(IafasCombos d : lista){
            	 fuenteFinanciamiento.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
             }
       
        return fuenteFinanciamiento;
    }
}
