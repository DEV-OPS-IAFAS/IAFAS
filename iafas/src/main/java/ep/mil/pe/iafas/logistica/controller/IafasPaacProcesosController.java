package ep.mil.pe.iafas.logistica.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import ep.mil.pe.iafas.administrativo.maestros.combos.dao.IafasCombosDao;
import ep.mil.pe.iafas.administrativo.maestros.combos.model.IafasCombos;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.logistica.dao.IafasPaacProcesoDao;
import ep.mil.pe.iafas.logistica.dao.ViewPaacProcesoDetalleDao;
import ep.mil.pe.iafas.logistica.model.IafasPaacProcesoDetalle;
import ep.mil.pe.iafas.logistica.model.IafasPaacProcesos;
import ep.mil.pe.iafas.logistica.model.ViewPaacProcesoDetalle;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

@Data
@ManagedBean(name="mbProcesos")
@SessionScoped
public class IafasPaacProcesosController implements Serializable{
	
	private String periodo;
	private List<IafasPaacProcesos> listaCab;
	private List<IafasPaacProcesoDetalle> listaDet;
	private int fuenteFinan;
	private String numeroPaac;
	private int nprocesoEtapa;
	private int nprocesoDocumento;
	private String ctipoProcedimiento;
	private int tipoProcesoContratacion;
	private String numProceso;
	private String descrProceso;
	private boolean procesosCompras;
	private Date fechaConvocatoria;
	private Date fechaParticipantes;
	private Date fechaObservaciones;
	private Date fechaAbsolucion;
	private Date fechaIntegracion;
	private Date fechOfertas;
	private Date fechaEvaluacion;
	private Date fechaBuenaPro;
	private Date fechaConsentimiento;
	private Date fechaContrato;
	private BigDecimal montoProceso;
	private int nroCertificado;
	
	private int itemCodigo;
	private double cantidadItem;
	private double precioItem;
	private String unidadMedida;
	private String observacionItem;
	private int itemCodDet;
	
	//Parametros
	private int pFinan;
	private int pCodigo;
	
	//labels
	private String labelDescProceso;
	private double labelMontoProceso;
	private String labelNumeroPaac;
	private String labelNumProceso;
	
	
	private int typeMessages;
	private int tam =0;
	private String usuario;
	private String messagesBD;
	
	private List<SelectItem> tipoEtapa;
	private List<SelectItem> procedimientoSel;
	
	List<ViewPaacProcesoDetalle> listaDetalle;

	private static final long serialVersionUID = 1L;
	IafasPaacProcesoDao procesoDao = new IafasPaacProcesoDao(MySQLSessionFactory.getSqlSessionFactory());
	IafasCombosDao combosDAO = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());
	ViewPaacProcesoDetalleDao viewDao = new ViewPaacProcesoDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
	private static final Logger logger = Logger.getLogger(IafasPacContratosController.class);

	public IafasPaacProcesosController() {
		// TODO Auto-generated constructor stub
		HttpSession session=null; 
		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		usuario = usuarioSession.getIdUsuario();
	}
	
	public String nuevoRegistro() {
		return "insRegProcesos.xhtml";
	}

	public List<IafasPaacProcesos> showPaacProcesos(){
		listaCab = new ArrayList<IafasPaacProcesos>();
		List<IafasPaacProcesos> procesos = procesoDao.showDetailContract(periodo);
		procesos.forEach((p) -> listaCab.add(p));
		return listaCab;
	}
	
	public void cargarDetalle() {
		listaDet = new ArrayList<IafasPaacProcesoDetalle>();
		logger.info("Tamaño :lista "+listaDet.size());
		tam = tam+listaDet.size();
		logger.info(tam);
		IafasPaacProcesoDetalle e = new IafasPaacProcesoDetalle();
		e.setNitemCodigo(itemCodigo);
		e.setNprocesoDetCantidad(cantidadItem);
		e.setNprocesoDetPrecio(precioItem);
		listaDet.add(tam, e);
		
		logger.info("Cantidad "+listaDet.toString());
		tam = listaDet.size();
	}
	// Combo dependiente
	public List<SelectItem> getTipoEtapa() {
		tipoEtapa = new ArrayList<SelectItem>();
		List<IafasCombos> cb = combosDAO.getTipoEtapa(String.valueOf(tipoProcesoContratacion));
		for(IafasCombos e : cb) {
			tipoEtapa.add(new SelectItem(e.getCodigo(), e.getDescripcion()));
		}
		return tipoEtapa;
	}
	
	public List<SelectItem> getProcedimientoSel() {
		procedimientoSel = new ArrayList<SelectItem>();
		List<IafasCombos> cb = combosDAO.getTipoProcedimiento(String.valueOf(nprocesoDocumento));
		for(IafasCombos e : cb) {
			procedimientoSel.add(new SelectItem(e.getCodigo(), e.getDescripcion()));
		}
		return procedimientoSel;
	}
	
	public void limpiarCampos() {
		fuenteFinan =0;
		numeroPaac = "";
		nprocesoEtapa=0;
		 nprocesoDocumento=0;
		ctipoProcedimiento="";
		tipoProcesoContratacion=0;
		numProceso="";
		descrProceso="";
		procesosCompras=false;
		fechaConvocatoria = null;
		 fechaParticipantes= null;
		fechaObservaciones= null;
		fechaAbsolucion = null;
		 fechaIntegracion= null;
		fechOfertas = null;
		fechaEvaluacion = null;
		fechaBuenaPro = null;
		fechaConsentimiento = null;
		fechaContrato = null;
		montoProceso = new BigDecimal(0);
		nroCertificado =0;
	}
	
	public String retornar() {
		limpiarCampos();
		return "mainPaacProcesos.xhtml";
	}
	
	public String insertaProceso() throws SQLException {
		int reg =0;
		String checkCompras = "";
	   try {
		IafasPaacProcesos p = new IafasPaacProcesos();
		p.setPeriodo(periodo);
		p.setNfuenteFinanciamiento(fuenteFinan);
		p.setNpacProcesoCodigo(0);
		p.setVpacProcesosNumeroPaac(numeroPaac);
		p.setNprocesoEtapaCodigo(nprocesoEtapa);
		p.setNprocesoDocumentoCodigo(nprocesoDocumento);
		p.setCtipoProcedimientoCodigo(ctipoProcedimiento);
		p.setNtipoProcesoContratacion(tipoProcesoContratacion);
		p.setVpacProcesoNumero(numProceso);
		p.setVpacProcesoDescripcion(descrProceso);
		if(procesosCompras == true) {
			checkCompras = "*";
			p.setCpacProcesoCompras(checkCompras);
		}else {p.setCpacProcesoCompras(checkCompras);}
		
		p.setNpacProcesoCertificado(nroCertificado);
        p.setDpacProcesoConvocatoria(fechaConvocatoria);
        p.setDpacProcesoParticipantes(fechaParticipantes);
        p.setDpacProcesoObservaciones(fechaObservaciones);
        p.setDpacProcesoAbsolucion(fechaAbsolucion);
        p.setDpacProcesoIntegracion(fechaIntegracion);
        p.setDpacProcesoOfertas(fechOfertas);
        p.setDpacProcesoEvaluacion(fechaEvaluacion);
        p.setDpacProcesoBuenaPro(fechaBuenaPro);
        p.setDpacProcesoConsentimiento(fechaConsentimiento);
	    p.setDprocesoContrato(fechaContrato);
	    p.setNpacProcesoMonto(montoProceso);
	    p.setVusuarioCodigo(usuario);
	    p.setTipo("I");
		reg = procesoDao.saveProcessPAAC(p);
		logger.info("Se Registro Procesos {} "+numProceso+" "+reg);
		if(reg ==1) {
			typeMessages = 1;
			showPaacProcesos();
		}
		else {
		  typeMessages=0;
		  if(procesoDao.getMensajeBD().contains("NRO. PROCESO YA SE ENCUENTRAN REGISTRADOS")) {
			  setMessagesBD("NRO. PROCESO "+numProceso+" YA SE ENCUENTRA REGISTRADO"); 
		  }
		  
		  logger.info("Mensaje de Error1 {} "+procesoDao.getMensajeBD()+" en Controller : "+messagesBD);
		}
	} 
	   catch (Exception e) {
		// TODO: handle exception
		typeMessages = 0;
		logger.error(e.getMessage());
		setMessagesBD(e.getMessage());
	}
	   logger.info("Mensaje de Error {} "+procesoDao.getMensajeBD()+" en Controller : "+messagesBD);
	   showMessages(typeMessages);
		return "";
	}
	
	public void obtenerParametros() {
	     String financiamiento = (String) extContext().getRequestParameterMap().get("pFinan");
	     String codigoProceso = (String) extContext().getRequestParameterMap().get("pCodigo");
	     
	     pFinan = Integer.valueOf(financiamiento);
	     pCodigo = Integer.valueOf(codigoProceso);
	     mostrarProcesoCab();
	     showDetailsProcess();
	}
	
	public void mostrarProcesoCab() {
		IafasPaacProcesos procesos = new IafasPaacProcesos();
		procesos.setPeriodo(periodo);
		procesos.setNpacProcesoCodigo(pCodigo);
		List<IafasPaacProcesos> reg = procesoDao.showProcessHead(procesos);
		for(IafasPaacProcesos r: reg) {
			setLabelDescProceso(r.getVpacProcesoDescripcion());
			setLabelMontoProceso(r.getNpacProcesoMonto().doubleValue());
			setLabelNumeroPaac(r.getVpacProcesosNumeroPaac());
			setLabelNumProceso(r.getVpacProcesoNumero());
		}
	}
	
	public boolean validaMontoItems() {
		
		double montoItem = 0;
		double montoIngresado= (cantidadItem*precioItem);
		for(int i=0; i<listaDetalle.size();i++) {
			montoItem = montoItem+(listaDetalle.get(i).getCantidadItem().doubleValue()*listaDetalle.get(i).getPrecioItem().doubleValue());
		}
	    // validando
		if((montoIngresado + montoItem) > labelMontoProceso) {
			logger.info("Entro a la validacion :"+(montoIngresado + montoItem) +" "+labelMontoProceso);
			   FacesContext.getCurrentInstance().addMessage(null, new 
						 FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR!", 
								 "EL MONTO INGRESADO SUSPERA AL MONTO TOTAL DEL PROCESO"));
			    return true;
		}
		return false;
	}
	
	public String insertaProcesoDetalle() {
		try {
			
		     if(validaMontoItems()==true) {
		    	 logger.info("Entro a la validacion de Montos");
		    	 return "";
		     } 
		     else {
		    	 logger.info("Entror");
			     IafasPaacProcesoDetalle det = new IafasPaacProcesoDetalle();
			       det.setPeriodo(periodo);
			       det.setNfuenteFinanciamiento(pFinan);
			       det.setNpacProcesoCodigo(pCodigo);
			       det.setNitemCodigo(itemCodigo);
			       det.setNprocesoDetCantidad(cantidadItem);
			       det.setNprocesoDetPrecio(precioItem);
			       det.setCunidadMedidaCodigo(unidadMedida);
			       det.setVpacProcesoObs(observacionItem);
			       det.setNpacProcesoDetalleItem(itemCodDet);
			       det.setVusuarioCodigo(usuario);
			        procesoDao.saveProcessDetailsPAAC(det);
			        logger.info("Registro el Detalle del Proceso {} "+pCodigo);
			        showDetailsProcess(); 
			        limpiarCamposDet();
		     }

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error al Registrar Item Proceso:"+e);
		}
		return "";
	}
	
	public List<ViewPaacProcesoDetalle> showDetailsProcess(){
		listaDetalle = new ArrayList<ViewPaacProcesoDetalle>();
		ViewPaacProcesoDetalle v = new ViewPaacProcesoDetalle();
		v.setPeriodo(periodo);
		v.setCodProceso(pCodigo);
		List<ViewPaacProcesoDetalle> items = viewDao.showDetailsPaacProcess(v);
		items.forEach((det) ->listaDetalle.add(det));
		 return listaDetalle;
	}
	
	public void limpiarCamposDet() {
		
		cantidadItem =0;
		precioItem=0;
		unidadMedida="";
		observacionItem="";
		itemCodDet=0;
		itemCodigo=0;
	}
	private String showMessages(int opcion) {
		String messages = "";
		switch (opcion) {
		case 0 : typeMessages=0;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 1 : typeMessages=1;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 2 : typeMessages=2;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		default : messages="";break;
		}
		return messages;
	}
	   private ExternalContext extContext() {
	        FacesContext c = FacesContext.getCurrentInstance();
	        ExternalContext ec = c.getExternalContext();
	        return ec;
	    }
}
