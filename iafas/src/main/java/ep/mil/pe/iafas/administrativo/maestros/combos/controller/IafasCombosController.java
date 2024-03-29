package ep.mil.pe.iafas.administrativo.maestros.combos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

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
	
	public List<SelectItem> grado;
	public List<SelectItem> area;
	public List<SelectItem> familia;
	public List<SelectItem> monedas;
	public List<SelectItem> proveedores;
	public List<SelectItem> bancos;
	public List<SelectItem> documento;
	public List<SelectItem> impuesto;
	public List<SelectItem> sistemaContratacion;
	public List<SelectItem> modalidadContratacion;
	public List<SelectItem> itemTipo;
	public List<SelectItem> item;
	public List<SelectItem> unidadMedida;
	
	public List<SelectItem> destino;
	public List<SelectItem> provincia;
	public List<SelectItem> departamento;
	public List<SelectItem> planilla;	
	
	public List<SelectItem> etapas;

	

	private static final Logger logger = Logger.getLogger(IafasCombosController.class.getName());
	private List<SelectItem> periodos;
	private List<SelectItem> entidades;
	
	private List<SelectItem> almacenes;
	
	public List<SelectItem> plazo;
	public List<SelectItem> condicion;
	
	/**************************************/
	/*[INICIO] Elvis Severino*/
	public List<SelectItem> tipoMaterial;
	public List<SelectItem> marcaPatrimonio;
	public List<SelectItem> colorPatrimonio;
	public List<SelectItem> categoriaPatrimonio;
	public List<SelectItem> tipoActa;
	
	
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
   /* public List<SelectItem> getgrado() {
    	grado = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getTipoGrado();
             for(IafasCombos d : lista){
            	 grado.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
             }
       
        return grado;
    }*/
    
    public List<SelectItem> getarea() {
    	area = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getTipoArea();
             for(IafasCombos d : lista){
            	 area.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
             }
       
        return area;
    }
    
    public List<SelectItem> getfamilia() {
    	familia = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getTipoFamilia();
             for(IafasCombos d : lista){
            	 familia.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
             }
       
        return familia;
    }
    
    public List<SelectItem> getMonedas() {
    	monedas = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getMonedas();
             for(IafasCombos d : lista){
            	 monedas.add(new SelectItem(d.getCodigo(), d.getDescripcion()));
             }
       
        return monedas;
    }
    
    public List<SelectItem> getProveedores() {
    	proveedores = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getProveedores();
             for(IafasCombos d : lista){
            	 proveedores.add(new SelectItem(d.getCodigo(), d.getCodigo()+":"+d.getDescripcion()));
             }
        
        return proveedores;
    } 
    
    public List<SelectItem> getBancos() {
    	bancos = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getBancos();
             for(IafasCombos d : lista){
            	 bancos.add(new SelectItem(d.getCodigo(), d.getCodigo()+":"+d.getDescripcion()));
             }
       
        return bancos;
    } 
    
    public List<SelectItem> getdocumento() {
    	documento = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getTipoDocumento();
             for(IafasCombos d : lista){
            	 documento.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
             }
       
        return documento;
    }
    
    public List<SelectItem> getimpuesto() {
    	impuesto = new ArrayList<>();
        IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
             List<IafasCombos> lista = cb.getTipoImpuesto();
             for(IafasCombos d : lista){
            	 impuesto.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
             }
       
        return impuesto;
    }
    
    /*Cambios agregados por Elvis Severino*/
	public List<SelectItem> getPeriodos() {
		logger.info("[INICIO:] Metodo : getPeriodos");

		this.periodos = new ArrayList<>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> lstPeriodos = cb.getPeriodos();
		for (IafasCombos p : lstPeriodos) {
			this.periodos.add(new SelectItem(p.getCodigo(), p.getCodigo()));
		}
		logger.info("[FIN:] Metodo : getPeriodos");
		return this.periodos;
	}
	
	public List<SelectItem> getEntidades() {
		logger.info("[INICIO:] Metodo : getEntidades");

		this.entidades = new ArrayList<>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> lstEntidades = cb.getEntidades();
		for (IafasCombos p : lstEntidades) {
			this.entidades.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}
		logger.info("[FIN:] Metodo : getEntidades");
		return this.entidades;
	}

	public List<SelectItem> getSistemaContratacion() {
		sistemaContratacion = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> lstContratacion = cb.getSistemaContratacion();
		for(IafasCombos tc : lstContratacion) {
			sistemaContratacion.add(new SelectItem(tc.getCodigo(), tc.getDescripcion()));
		}
		
		return sistemaContratacion;
	}	
	
	public List<SelectItem> getModalidadContratacion() {
		modalidadContratacion = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> lstProcesoDocumento = cb.getModalidadContratacion();
		for(IafasCombos tc : lstProcesoDocumento) {
			modalidadContratacion.add(new SelectItem(tc.getCodigo(), tc.getDescripcion()));
		}
		
		return modalidadContratacion;
	}
	
	
	
	public List<SelectItem> getItemTipo() {
		itemTipo = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> lstProcesoDocumento = cb.getItemTipo();
		for(IafasCombos tc : lstProcesoDocumento) {
			itemTipo.add(new SelectItem(tc.getCodigo(), tc.getDescripcion()));
		}
		return itemTipo;
	}
	
	public List<SelectItem> getItem(){
		item = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> lstItem = cb.getItem();
		for(IafasCombos tc : lstItem) {
			item.add(new SelectItem(tc.getCodigo(), tc.getDescripcion()));
		}
		
		return item;
	}
	
	public List<SelectItem> getUnidadMedida(){
		unidadMedida = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> lstItem = cb.getUnidadMedida();
		for(IafasCombos tc : lstItem) {
			unidadMedida.add(new SelectItem(tc.getCodigo(), tc.getDescripcion()));
		}
		
		return unidadMedida;
	}
	
	public List<SelectItem> getDestino() {

		destino = new ArrayList<>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getUbigeo();
		for (IafasCombos p : ls) {
			destino.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return destino;
	}
	
	public List<SelectItem> getDepartamento() {

		departamento = new ArrayList<>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.gettipoDepartamento();
		for (IafasCombos p : ls) {
			departamento.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return departamento;
	}
	
	public List<SelectItem> getPlanilla() {

		planilla = new ArrayList<>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.gettipoPlanilla();
		for (IafasCombos p : ls) {
			planilla.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return planilla;
	}
	
	public List<SelectItem> getEtapas() {
		etapas = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getTipoEtapa();
		for (IafasCombos p : ls) {
			etapas.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return etapas;
	}
	

	public List<SelectItem> getAlmacenes() {
		almacenes = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getAlmacenes();
		for (IafasCombos p : ls) {
			almacenes.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return almacenes;
	}
	
	

	public List<SelectItem> getPlazo() {
		plazo = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getPlazoEntrega();
		for (IafasCombos p : ls) {
			plazo.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return plazo;
	}
	
	public List<SelectItem> getCondicion() {
		condicion = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getCondicionEntrega();
		for (IafasCombos p : ls) {
			condicion.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return condicion;
	}

	/*[INICIO] Elvis Severino*/
	public List<SelectItem> getTipoMaterial() {
		tipoMaterial = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getTipoMaterialPatrimonio();
		for (IafasCombos p : ls) {
			tipoMaterial.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return tipoMaterial;
	}
	
	public List<SelectItem> getMarcaPatrimonio() {
		marcaPatrimonio = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getMarcaPatrimonio();
		for (IafasCombos p : ls) {
			marcaPatrimonio.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return marcaPatrimonio;
	}
	
	public List<SelectItem> getColorPatrimonio() {
		colorPatrimonio = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getColorPatrimonio();
		for (IafasCombos p : ls) {
			colorPatrimonio.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return colorPatrimonio;
	}
	
	public List<SelectItem> getCategoriaPatrimonio() {
		categoriaPatrimonio = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getCategoriaPatrimonio();
		for (IafasCombos p : ls) {
			categoriaPatrimonio.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return categoriaPatrimonio;
	}
	
	public List<SelectItem> getTipoActa() {
		tipoActa = new ArrayList<SelectItem>();
		IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasCombos> ls = cb.getTipoActa();
		for (IafasCombos p : ls) {
			tipoActa.add(new SelectItem(p.getCodigo(), p.getDescripcion()));
		}		
		return tipoActa;
	}
	/*[FIN] Elvis Severino*/
}
