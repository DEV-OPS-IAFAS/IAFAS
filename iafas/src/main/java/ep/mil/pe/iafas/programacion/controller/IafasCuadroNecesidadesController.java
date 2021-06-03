package ep.mil.pe.iafas.programacion.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.programacion.dao.IafasCuadroNecesidadesValorizadasDao;
import ep.mil.pe.iafas.programacion.dao.IafasEventoPrincipalDao;
import ep.mil.pe.iafas.programacion.model.IafasCuadroNecesidadesValorizadas;
import ep.mil.pe.iafas.programacion.model.IafasEventoPrincipal;
import ep.mil.pe.iafas.programacion.model.ProgramacionMultiAnual;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Setter
@Getter
@ManagedBean(name = "iafasCuadroNecesidadesController")
@SessionScoped
public class IafasCuadroNecesidadesController implements Serializable {
	 
	private static final long serialVersionUID = 7144421072686401368L;
	
	private static final Logger logger = Logger.getLogger(IafasCuadroNecesidadesController.class.getName());
	
	private String cperiodo;
	private String fteFinanc =  Constantes.CERO_STRING;
	private List<ProgramacionMultiAnual> listaMultiAnual = new ArrayList<ProgramacionMultiAnual>();
	private List<IafasCuadroNecesidadesValorizadas> listaCabeceraCNV = new ArrayList<IafasCuadroNecesidadesValorizadas>();
	private List<IafasEventoPrincipal> listaCabecera = new ArrayList<IafasEventoPrincipal>();
	private IafasCuadroNecesidadesValorizadas bean;
	private String codigoEventoPrincipal;
	private String nombreEvento;
	private String comentario;
	
	public List<IafasCuadroNecesidadesValorizadas> buscarCabeceraCNV() {
		logger.info("[INICIO:] Metodo : buscarCabeceraCNV");

		this.listaCabeceraCNV = new ArrayList<>();
		if (this.listaCabeceraCNV != null)
			this.listaCabeceraCNV.clear();
		IafasCuadroNecesidadesValorizadasDao objDao = new IafasCuadroNecesidadesValorizadasDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasCuadroNecesidadesValorizadas objBn  = new IafasCuadroNecesidadesValorizadas();
		
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		
		List<IafasCuadroNecesidadesValorizadas> lstCab = objDao.mostrarCabecera(objBn);
		if (lstCab.size() > 0) {
			for (IafasCuadroNecesidadesValorizadas objBeanLista : lstCab) {
				this.listaCabeceraCNV.add(objBeanLista);
			}
		}
		
		logger.info("[FIN:] Metodo : buscarCabeceraCNV");
		return listaCabeceraCNV;
	}

	public List<IafasEventoPrincipal> buscarCabeceraEP() {
		logger.info("[INICIO:] Metodo : buscarCabeceraEP");

		this.listaCabecera = new ArrayList<>();
		if (this.listaCabecera != null)
			this.listaCabecera.clear();
		
		IafasEventoPrincipalDao objDao =  new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn = new IafasEventoPrincipal();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		
		List<IafasEventoPrincipal> lstCab = objDao.mostrarCabecera(objBn);
		if (lstCab.size() > 0) {
			for (IafasEventoPrincipal objBeanLista : lstCab) {
				this.listaCabecera.add(objBeanLista);
			}
		}

		logger.info("[FIN:] Metodo : buscarCabeceraEP");
		return listaCabecera;
	}
	
	public String crearEventoPrincipal() {

		logger.info("[INICIO:] Metodo : crearEventoPrincipal");
		String pagina = "mainEventosPrincipales.xhtml";
		setCperiodo(this.cperiodo);
		setFteFinanc(this.fteFinanc);
		buscarCabeceraEP();
		logger.info("[FIN:] Metodo : crearEventoPrincipal");

		return pagina;
	}
	
	public void nuevo() {
		logger.info("[INICIO:] Metodo : nuevo");
		int codTarea= bean.getNtareaPresupuestalCodigo();
		String numeroCorrelativo= generarCorrelativo(codTarea);
		codigoEventoPrincipal = Constantes.TEXTO_CNV
								.concat(String.valueOf(codTarea))
								.concat(Constantes.GUION)
								.concat(numeroCorrelativo);
		
		logger.info("[FIN:] Metodo : nuevo");
	}
	
	private String generarCorrelativo(int codigoTarea) {
		logger.info("[INICIO:] Metodo : generarCorrelativo");
		String numero = Constantes.VACIO;
		IafasEventoPrincipalDao objDao =  new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn = new IafasEventoPrincipal();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(codigoTarea);
		objBn.setCanioRegistro(String.valueOf(cperiodo));
		numero = objDao.generarCorrelativo(objBn)
				.get(0)
				.getVeventoPrincipalCodigo()
				.toString();
		logger.info("[FIN:] Metodo : generarCorrelativo");

		return numero;
	}
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
}
