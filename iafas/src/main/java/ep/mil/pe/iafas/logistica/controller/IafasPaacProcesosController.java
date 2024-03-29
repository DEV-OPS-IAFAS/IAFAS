package ep.mil.pe.iafas.logistica.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;

import ep.mil.pe.iafas.administrativo.maestros.combos.dao.IafasCombosDao;
import ep.mil.pe.iafas.administrativo.maestros.combos.model.IafasCombos;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Utilitarios;
import ep.mil.pe.iafas.logistica.dao.IafasPaacProcesoDao;
import ep.mil.pe.iafas.logistica.dao.ViewPaacProcesoDetalleDao;
import ep.mil.pe.iafas.logistica.model.IafasPaacProcesoDetalle;
import ep.mil.pe.iafas.logistica.model.IafasPaacProcesos;
import ep.mil.pe.iafas.logistica.model.ViewPaacProcesoDetalle;
import ep.mil.pe.iafas.programacion.dao.IafasItemDao;
import ep.mil.pe.iafas.programacion.model.IafasItem;
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
	private int pCodEtapaDoc;
	
	//labels
	private String labelDescProceso;
	private double labelMontoProceso;
	private String labelNumeroPaac;
	private String labelNumProceso;
	
	
	private int typeMessages;
	private int tam =0;
	private String usuario;
	private String messagesBD;
	
	private String nomenclatura;
	private int numConvocatoria;
	private String tipoItem;
	private int sistemaContratacion;
	private int modalidadContratacion;
	private int moneda;
	private double tipCambio;
	private BigDecimal montoProcesoExt;
	private Date fechaProceso;
	
	// etapas
	private int codigoEtapaProcedimiento;
	private int codigoEtapaProcDocumento;
	private Date fechaInicio;
	private Date fechaFin;
	private String descEtapaDocumento;
	private String archivoEtapaDoc;
	
	private String filename;
	private String extension;
	
	private String directorioInput;
	
	List<ViewPaacProcesoDetalle> listaDetalle;
	List<IafasPaacProcesos> listadoEtapas;
	List<IafasPaacProcesos> listaEtapasDoc;
	private List<ViewPaacProcesoDetalle> listaDetProveedor;
	private List<IafasPaacProcesos> listaProveedores;
	
	private String descripcionItem;
	private String lblDescripcionUnidadMedida;
	
	private int pCodigoEtapa =0;
	
	private String rucProveedor;
	private String labelRNP;
	private String labelRazonSocial;
	
	private String labelNomenclatura;
	private String labelDescripcionProceso;
	private String labelEtapa;


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
		return "insRegProcedimientos.xhtml";
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
	/*public List<SelectItem> getTipoEtapa() {
		tipoEtapa = new ArrayList<SelectItem>();
		List<IafasCombos> cb = combosDAO.getTipoEtapa(String.valueOf(tipoProcesoContratacion));
		for(IafasCombos e : cb) {
			tipoEtapa.add(new SelectItem(e.getCodigo(), e.getDescripcion()));
		}
		return tipoEtapa;
	//}*/

	
	public void limpiarCampos() {
		fuenteFinan =0;
		numeroPaac = "";
		nprocesoEtapa=0;
		 nprocesoDocumento=0;
		ctipoProcedimiento="";
		tipoProcesoContratacion=0;
		numProceso="";
		descrProceso="";
		montoProceso = new BigDecimal(0);
		nroCertificado =0;
	}
	
	public String retornar() {
		limpiarCampos();
		return "mainPaacProcesos.xhtml";
	}
	
	public String insertaProceso() throws SQLException {
		int reg =0;
	   try {
		IafasPaacProcesos p = new IafasPaacProcesos();

		p.setPeriodo(periodo);		
		p.setNpacProcedimientoCodigo(0);
		p.setVpacProcedimientoNomenclatura(nomenclatura);
		p.setVpacProcedimientoDescripcion(descrProceso);
		p.setNprocedimientoConvocatoria(numConvocatoria);
		p.setCtipoProcedimientoCodigo(ctipoProcedimiento);
		p.setCtipoCodigoItem(tipoItem);
		p.setNisistemaContratacion(sistemaContratacion);
		p.setNmodalidadContratacion(modalidadContratacion);
		p.setNmonedaCodigo(moneda);
		p.setNtipoCambio(tipCambio);
		p.setNpacProcedimientoExtranjera(montoProcesoExt);
		p.setDfechaProcedimiento(fechaProceso);
		p.setNombreArchivo("");
		p.setVusuarioCodigo(usuario);
	    p.setNpacProcesoMonto(montoProceso);
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
	
	public String insertarEtapas() {
		IafasPaacProcesos p = new IafasPaacProcesos();
		try {
			p.setPeriodo(periodo);
			p.setNpacProcedimientoCodigo(pCodigo);
			p.setNprocedimientoEtapaCodigo(codigoEtapaProcedimiento);
			p.setDpacProcedimientoInicio(fechaInicio);
			p.setDpacProcedimientoFin(fechaFin);
			p.setCestadoCodigo("AC");
			p.setVusuarioCodigo(usuario);
			logger.info("Parametros :"+codigoEtapaProcedimiento);
	        procesoDao.saveProcessEtapaPAAC(p);
	        showEtapaDetalle();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "";
	}
	
	public void obtenerParametros() {
	     String codigoProceso = (String) extContext().getRequestParameterMap().get("pCodigo");
	     String codigoEtapa = (String) extContext().getRequestParameterMap().get("pCodigoEtapaProcedimiento");
	     logger.info("Codigo Etapa en Metodo :"+codigoEtapa);
	     pCodigo = Integer.valueOf(codigoProceso);
	     showDetailsProcess();
	     if(codigoEtapa==null) {pCodigoEtapa=0;}
	     else {	     
	    	 pCodigoEtapa = Integer.valueOf(codigoEtapa);
	     setCodigoEtapaProcDocumento(pCodigoEtapa);
	     logger.info("Obteniendo codigo de Etapa en Documentos {} "+codigoEtapaProcDocumento);
	     }

	     //verEtapas();
	     //mostrarProcesoCab();
	     //showEtapaDetalle();
	     verDocumentosEtapa();
	     
	}
	
	public void obtenerParamDocs() {

		String codigoProceso = (String) extContext().getRequestParameterMap().get("pCodigoProc");
	     String codigoEtapaDoc = (String) extContext().getRequestParameterMap().get("pCodigoDocumetoEtapa");
	     logger.info("Parametros : String :"+codigoEtapaDoc +" "+codigoProceso);
	      //pCodEtapaDoc = Integer.valueOf(codigoEtapaDoc);
	      setPCodEtapaDoc(Integer.valueOf(codigoEtapaDoc));
	  //  irProveedoresEtapa();
	}
	
	public String irProveedoresEtapa() {
		String pagina = "insProcedimientosProveedores.xhtml";
		obtenerParamDocs();
		listarProveedoresEtapas();
		return pagina;
	}
	
	
	public void mostrarProcesoCab() {
		IafasPaacProcesos procesos = new IafasPaacProcesos();
		procesos.setPeriodo(periodo);
		procesos.setNpacProcedimientoCodigo(pCodigo);
		logger.info("Parametros Busqueda:"+pCodigo+" "+periodo);
		List<IafasPaacProcesos> reg = procesoDao.showProcessHead(procesos);
		for(IafasPaacProcesos r: reg) {
			setLabelDescProceso(reg.get(0).getVpacProcedimientoDescripcion());
			setLabelMontoProceso(reg.get(0).getNpacProcesoMonto().doubleValue());
			setLabelNumeroPaac(String.valueOf(reg.get(0).getNprocedimientoConvocatoria()));
			setLabelNumProceso(reg.get(0).getVpacProcedimientoNomenclatura());
			
			logger.info("Parametros :"+reg.get(0).getVpacProcedimientoDescripcion()+" "+reg.get(0).getNpacProcesoMonto().doubleValue()+
					" "+String.valueOf(reg.get(0).getNprocedimientoConvocatoria()));
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
			/*
		     if(validaMontoItems()==true) {
		    	 logger.info("Entro a la validacion de Montos");
		    	 return "";
		     } 
		     else {
		    	 logger.info("Entror");
			     IafasPaacProcesoDetalle det = new IafasPaacProcesoDetalle();
			       det.setPeriodo(periodo);
			       det.setNpacProcedimientoCodigo(pCodigo);
			       det.setNitemCodigo(itemCodigo);
			       det.setNprocesoDetCantidad(cantidadItem);
			       det.setNprocesoDetPrecio(precioItem);
			       det.setCunidadMedidaCodigo(unidadMedida);
			       det.setVpacProcesoObs(observacionItem);
			       det.setNpacProcesoDetalleItem(itemCodDet);
			       det.setVusuarioCodigo(usuario);
			        procesoDao.saveProcessItems(det);
			        logger.info("Registro el Detalle del Proceso {} "+pCodigo);
			        showDetailsProcess(); 
			        limpiarCamposDet();
		     }*/
		     IafasPaacProcesoDetalle det = new IafasPaacProcesoDetalle();
		       det.setPeriodo(periodo);
		       det.setNpacProcedimientoCodigo(pCodigo);
		       det.setNitemCodigo(itemCodigo);
		       det.setNprocesoDetCantidad(cantidadItem);
		       det.setNprocesoDetPrecio(precioItem);
		       det.setCunidadMedidaCodigo(unidadMedida);
		       det.setVpacProcesoObs(observacionItem);
		       det.setNpacProcesoDetalleItem(itemCodDet);
		       det.setVusuarioCodigo(usuario);
		       det.setTipo("I");
		        procesoDao.saveProcessItems(det);
		        logger.info("Registro el Detalle del Proceso {} "+pCodigo);
		        showDetailsProcess(); 
		        limpiarCamposDet();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error al Registrar Item Proceso:"+e.getCause());
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
	public void verItemsProceso() {
		showDetailsProcessProveedor();
	}
	
	public List<ViewPaacProcesoDetalle> showDetailsProcessProveedor(){
		listaDetProveedor = new ArrayList<ViewPaacProcesoDetalle>();
		ViewPaacProcesoDetalle v = new ViewPaacProcesoDetalle();
		v.setPeriodo(periodo);
		v.setCodProceso(pCodigo);
		List<ViewPaacProcesoDetalle> items = viewDao.showDetailsPaacProcess(v);
		items.forEach((det) ->listaDetProveedor.add(det));
		 return listaDetProveedor;
	}
	
	public List<IafasPaacProcesos> showEtapaDetalle(){
		listadoEtapas = new ArrayList<IafasPaacProcesos>();
		IafasPaacProcesos v = new IafasPaacProcesos();
		v.setPeriodo(periodo);
		v.setNpacProcedimientoCodigo(pCodigo);
		List<IafasPaacProcesos> etapasProceso = procesoDao.showProcessEtapa(v);
		etapasProceso.forEach((e) -> listadoEtapas.add(e));
		return listadoEtapas;
	}
	
	public String verEtapas() {
		logger.info("Ingreso a Ver Etapas");
		 obtenerParametros();
	     mostrarProcesoCab();
	     showEtapaDetalle();
		return "insProcedimientosEtapas.xhtml";
	}
	
	public void limpiarCamposDet() {
		
		cantidadItem =0;
		precioItem=0;
		unidadMedida="";
		observacionItem="";
		itemCodDet=0;
		itemCodigo=0;
	}
	
	public void cargarArchivoEtapa(FileUploadEvent event) {
		 UploadedFile file = event.getFile();
	        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        filename = file.getFileName(); 
	        String filenameChange = periodo+pCodigo+"-"+labelNumProceso;
	        extension = FilenameUtils.getExtension(file.getFileName());
	        String path = Constantes.ROOT_PRODUCCION;
	        String filePath = path+filenameChange;
	        directorioInput = filePath+"."+extension;
	        logger.info("Directorio:"+directorioInput);
	        try {
	            FileOutputStream fos = new FileOutputStream(directorioInput);
	            fos.write(file.getContent());
	            fos.flush();
	            fos.close();
			} catch (FileNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public String saveFileEtapa() {
		IafasPaacProcesos p = new IafasPaacProcesos();
		try {

			String filenameChange = Constantes.ROOT_PRODUCCION+"ProcedimientosEtapas/"+periodo+pCodigo+"-"+labelNumProceso+"."+extension;
			String nomArchivo = periodo+pCodigo+"-"+labelNumProceso+"."+extension;
			p.setPeriodo(periodo);
			p.setNpacProcedimientoCodigo(pCodigo);
			p.setNprocedimientoEtapaDocCodigo(0);
			p.setNprocedimientoEtapaCodigo(codigoEtapaProcDocumento);
			p.setDescArchEtapa(descEtapaDocumento);
			p.setArchivoEtapa(nomArchivo);
			p.setEstadoDocEtapa("AC");
			p.setVusuarioCodigo(usuario);
			logger.info("Renombrando el Archivo {} "+filenameChange);
			Utilitarios.changeFileName(directorioInput, filenameChange);
			procesoDao.saveProcessEtapaDocPAAC(p);
			logger.info("Se registro el Etapa Documento {} "+p.toString());
			verDocumentosEtapa();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return "";
	}
	
	private List<IafasPaacProcesos> verDocumentosEtapa(){
		listaEtapasDoc = new ArrayList<IafasPaacProcesos>();
		IafasPaacProcesos v = new IafasPaacProcesos();
		v.setPeriodo(periodo);
		v.setNpacProcedimientoCodigo(pCodigo);
		v.setNprocedimientoEtapaCodigo(pCodigoEtapa);
		logger.info("[verDocumentosEtapa] : "+periodo + " "+pCodigo+" "+pCodigoEtapa);
		List<IafasPaacProcesos> etapasProceso = procesoDao.showProcessEtapaDoc(v);
		etapasProceso.forEach((e) -> listaEtapasDoc.add(e));
		return listaEtapasDoc;
		
	}
	
	public void buscarProvRNP() {
		List<ViewPaacProcesoDetalle> p = viewDao.showRNP(rucProveedor);
		for(ViewPaacProcesoDetalle e : p) {
			setLabelRNP(e.getRnpProveedores());
			setLabelRazonSocial(e.getRazonSocial());
		}
	}
	

	public String insertarProveedores() {
		
		IafasPaacProcesos cab = new IafasPaacProcesos();
		int c = 0;
		try {
			logger.info("[INICIO] Registro de Proveedores Procedimientos");
			cab.setPeriodo(periodo);
			cab.setNpacProcedimientoCodigo(pCodigo);
			cab.setNprocedimientoEtapaDocCodigo(pCodEtapaDoc);
			cab.setRucProveedor(rucProveedor);
			cab.setRucRNP(labelRNP);
			cab.setCestadoProv("AC");
			cab.setVusuarioCodigo(usuario);
			cab.setTipo("I");
			c=procesoDao.saveProveedoresProcedimiento(cab);
			
			if(c==1) {
				logger.info("[FIN] Registro de Proveedores Procedimientos");
				insertaItemProveedores();
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error Registro de Insertar Proveedores", e.getCause());
		}
		return "insProcedimientosProveedores.xhtml";
	}
	
	public void insertaItemProveedores() {
		IafasPaacProcesoDetalle det = new IafasPaacProcesoDetalle();
		try {
			logger.info("[INICIO] Registro de Items Provedores Procedimientos ");
			for(int i=0; i<listaDetProveedor.size();i++) {
				det.setPeriodo(periodo);
				det.setNpacProcedimientoCodigo(pCodigo);
				det.setNprocedimientoEtapaDocCodigo(pCodEtapaDoc);
				det.setRucProveedor(rucProveedor);
				det.setRucRNP(labelRNP);
				det.setNitemCodigo(listaDetProveedor.get(i).getCodItem());
				det.setNpacProcesoDetalleItem(listaDetProveedor.get(i).getCodDetalleItem());
				det.setNprocesoDetPrecio(listaDetProveedor.get(i).getPrecioOfertado());
				det.setNprocesoDetCantidad(listaDetProveedor.get(i).getCantidadOfertada());
				det.setCunidadMedidaCodigo(listaDetProveedor.get(i).getUnidadMedidaCod());
				det.setVpacProcesoObs("Sin Observacion");
				det.setVusuarioCodigo(usuario);
				det.setTipo("I");
				
				procesoDao.saveProveedoresProcedimientoDetalle(det);
			}
			logger.info("[FIN] Registro de Items Provedores Procedimientos ");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error Registro de Insertar Items Proveedores", e.getCause());
		}
	}
	
	public String listarProveedoresEtapas() {
		listaProveedores = new ArrayList<IafasPaacProcesos>();
		IafasPaacProcesos pro = new IafasPaacProcesos();
		pro.setPeriodo(periodo);
		pro.setNpacProcedimientoCodigo(pCodigo);
		pro.setNprocedimientoEtapaDocCodigo(pCodEtapaDoc);
		List<IafasPaacProcesos> proveedores = procesoDao.listaProveedoresEtapa(pro);
		for(IafasPaacProcesos p : proveedores) {
			setLabelNomenclatura(p.getVpacProcedimientoNomenclatura());
			setLabelDescripcionProceso(p.getVpacProcedimientoDescripcion());
			setLabelEtapa(p.getDescripcionEtapa());
			listaProveedores.add(p);
		}
		return "insProcedimientosProveedores.xhtml";
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
	
	public List<String> completeText(String query) {

		logger.info("[INICIO:] Metodo : completeText");
		String queryLowerCase = query.toLowerCase();
		List<String> itemList = new ArrayList<>();
		IafasItemDao itemDao = new IafasItemDao(MySQLSessionFactory.getSqlSessionFactory());
		//List<IafasItem> lsts = itemDao.obtenerItems();
		List<IafasItem> lsts = itemDao.obtenerItemSeleccionado(queryLowerCase);
		for (IafasItem obj : lsts) {
			descripcionItem = obj.getVItemDescripcion();
			itemCodigo = obj.getNItemCodigo();
			itemList.add(descripcionItem);
		}
		logger.info("[FIN:] Metodo : completeText");
		return itemList
				.stream()
				.filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}
	
	public void onItemSelect(SelectEvent<String> event) {
		logger.info("[INICIO:] Metodo : onItemSelect");
		IafasItemDao itemDao = new IafasItemDao(MySQLSessionFactory.getSqlSessionFactory());

		logger.info("evento selecccionado:"+event.getObject());
		
		List<IafasItem> lsts = itemDao.obtenerItemSeleccionado(event.getObject());
		for (IafasItem obj : lsts) {
			lblDescripcionUnidadMedida = obj.getDescripcionUnidadMedida();
			unidadMedida = obj.getCUnidadMedidaCodigo();
			itemCodigo = obj.getNItemCodigo();
		}
		
		logger.info("[FIN:] Metodo : onItemSelect {} "+itemCodigo+" UM: "+unidadMedida);
	}
	   private ExternalContext extContext() {
	        FacesContext c = FacesContext.getCurrentInstance();
	        ExternalContext ec = c.getExternalContext();
	        return ec;
	    }
}
