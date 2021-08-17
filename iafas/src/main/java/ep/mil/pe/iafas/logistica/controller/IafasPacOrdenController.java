package ep.mil.pe.iafas.logistica.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import ep.mil.pe.iafas.administrativo.maestros.combos.dao.IafasCombosDao;
import ep.mil.pe.iafas.administrativo.maestros.combos.model.IafasCombos;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.logistica.dao.IafasPacOrdenDao;
import ep.mil.pe.iafas.logistica.model.IafasPacOrden;
import ep.mil.pe.iafas.programacion.dao.IafasItemDao;
import ep.mil.pe.iafas.programacion.model.IafasItem;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

@Data
@ManagedBean(name="mbOrden")
@SessionScoped
public class IafasPacOrdenController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IafasPacOrden> listaOrden;
	private List<IafasPacOrden> listaOrdenDetalle;
	private List<IafasPacOrden> listaOrdenCertificado;
	private List<IafasPacOrden> listaOrdenConsulta;
	
	private String cperiodoCodigo  ;
	private Integer nfuenteFinanciamiento;
    private String citemTipoCodigo;
    private Integer nordenCodigo;
    private String cperiodoContrato;
    private Integer ncontratoCodigo;
    private Integer ntareaPresupuestalCodigo;
    private Integer nclasificadorPresupuestalCodigo;
    private Date dordenFecha;
    private Integer nplazoEntregaCodigo;
    private String vordenDescripcion;
    private String cproveedorRuc;
    private Integer nmonedaCodigo;
    private BigDecimal nordenTipoCambio;
    private Integer ncondicionEntregaCodigo;
    private String cestadoCodigo;
    private String vusuarioCreador;
	private Date dusuarioCreador;
	private String vusuarioCodigo;
	private Date Dusuariofecha;
    private Integer ncondicionPagoCodigo;
    private Integer npacOrdenPlazo;
    private Integer ncertificadoPresupuestalCodigo;
	
	private String usuario;
	private List<SelectItem> secuencia;
	
	
	private String descripcionFF;
	private String razonSocial;
	private String moneda;
	private String descripcionItem;
	private String descripcionContrato;
	private String vcodtipoFinancieamiento;
	private BigDecimal nordenDetalleMonto;
	
	// detalle de la orden
	 private Integer nitemCodigo;
	 private BigDecimal nordenDetalleCantidad;
	 private BigDecimal nordenDetalleImporte;
	 private BigDecimal nordenDetalleExtranjera;
     private String vordenDetalleObservacion;
     private String descripcionTarea;
     private String clasificador;
     private String descripcionClasificador;

	// unidad medida
     private String cunidadMedidaCodigo;
     private String descripcionUnidadMedida;
     private int codigoItem;	
 	private String lblDescripcionUnidadMedida;
 	
 	// cantidades de contrato con orden
 	private Integer lblcantContrato;
 	private Integer lblcandOrden;
 	private Integer cantidadContrato;
    private Integer cantidadOrden;
    
    private String busqueda;
    private String descripcionBien;
    private Integer ctipoOrden;
     
 	private List<IafasPacOrden> listaDetalleSession =new ArrayList<IafasPacOrden>();
 	private List<IafasPacOrden> listaDetalleItemSession =new ArrayList<IafasPacOrden>();
	private IafasPacOrden detalleItemSession;
	private IafasPacOrden detalleItemSessionItem;
	
	private static final Logger logger = Logger.getLogger(IafasPacOrdenController.class);
	IafasPacOrdenDao ordenDao = new IafasPacOrdenDao(MySQLSessionFactory.getSqlSessionFactory());
	
	
    public IafasPacOrdenController () {
    	
    	 HttpSession session=null; 
  		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
  		IafasUsuariosController usuarioSession = new IafasUsuariosController();
  		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
  		usuario = usuarioSession.getIdUsuario();
  		verOrden();
    }
    
    public String NuevoRegistro() {   	
    	String page ="mainOrdenCS.xhtml";
    	if (ctipoOrden == 1) 
    	{page ="insRegOrden.xhtml"; LimpiarCampos();}
    	if (ctipoOrden == 2) 
    	{page ="insRegOrdenSin.xhtml"; LimpiarCampos(); }
    	if (ctipoOrden == 0) 
    	{ page ="mainOrdenCS.xhtml";  }		
    	return page;
    }
    
	////////////LIMPIAR CAMPOS DETALLE/////////////////////
	public void cleanCamposDetalle() {
		logger.info("[INICIO:] Metodo : cleanCamposDetalle");
		setDescripcionItem(Constantes.VACIO);
		setLblDescripcionUnidadMedida(Constantes.VACIO);
		setLblcandOrden(null);
		setLblcantContrato(null);
		setNordenDetalleCantidad(null);
		setVordenDetalleObservacion(Constantes.VACIO);
		setNordenDetalleImporte(null);

		logger.info("[FIN:] Metodo : cleanCamposDetalle");
	}

	////////////////////////////////////////////////////////
	
	////////////LIMPIAR CAMPOS DETALLE REGISTROS/////////////////////
	public void LimpiarCampos() {
		logger.info("[INICIO:] Metodo : cleanCampos");
		setNcertificadoPresupuestalCodigo(null);
		setNcontratoCodigo(null);
		setNcondicionPagoCodigo(00);
		setCitemTipoCodigo(Constantes.VACIO);
		setDescripcionItem(Constantes.VACIO);
		setNplazoEntregaCodigo(00);
		setNmonedaCodigo(00);
		setNordenTipoCambio(null);
		setNpacOrdenPlazo(null);
		setLblDescripcionUnidadMedida(Constantes.VACIO);
		setLblcandOrden(null);
		setLblcantContrato(null);
		setNordenDetalleCantidad(null);
		setVordenDetalleObservacion(Constantes.VACIO);
		setNordenDetalleImporte(null);
		setCperiodoContrato(null);
		setCproveedorRuc(null);
		setVordenDescripcion(Constantes.VACIO);
		setDordenFecha(null);
		setRazonSocial(null);
		setDescripcionFF(null);
		setNtareaPresupuestalCodigo(null);
		setDescripcionTarea(null);
		setClasificador(null);
        setDescripcionClasificador(null);
        
		logger.info("[FIN:] Metodo : cleanCampos");
	}

	////////////////////////////////////////////////////////
	
	// VER CONTRATOS 
	
    public String verContrato() {
    	String page="insRegOrden";
    	
    	try {
    		
    		listaOrden = new ArrayList<IafasPacOrden>();
    		IafasPacOrden ca = new IafasPacOrden();
    		ca.setCperiodoContrato(cperiodoContrato);
    		System.out.println(" periodo" +cperiodoContrato);
    		ca.setNcontratoCodigo(ncontratoCodigo);
    		System.out.println("contrato "+ncontratoCodigo);
    		List<IafasPacOrden> ordenes = ordenDao.listaContrato(ca);
    		System.out.println("lista "+ordenDao.listaContrato(ca));
    		for (IafasPacOrden l : ordenes) {
    			setCproveedorRuc(ordenes.get(0).getCproveedorRuc());
    			setRazonSocial(ordenes.get(0).getRazonSocial());    			
    			setNmonedaCodigo(ordenes.get(0).getNmonedaCodigo());
    			setMoneda(ordenes.get(0).getMoneda());
    			setNordenTipoCambio(ordenes.get(0).getNordenTipoCambio());
    			setDescripcionContrato(ordenes.get(0).getDescripcionContrato());
    			setDescripcionTarea(ordenes.get(0).getDescripcionTarea());
    			setClasificador(ordenes.get(0).getClasificador());
    			setDescripcionClasificador(ordenes.get(0).getDescripcionClasificador());
    			setDescripcionFF(ordenes.get(0).getDescripcionFF());
    			setNtareaPresupuestalCodigo(ordenes.get(0).getNtareaPresupuestalCodigo());
    			setNclasificadorPresupuestalCodigo(ordenes.get(0).getNclasificadorPresupuestalCodigo());
    			setNordenDetalleImporte(ordenes.get(0).getNordenDetalleMonto());
    			setNordenDetalleExtranjera(ordenes.get(0).getNordenDetalleExtranjera());
    			listaOrden.add(l);
    		}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ver Error :"+e.getMessage());
		}
    	//listarContratoDetalle();
    	return page;
    }
    
    
    
    public List<IafasPacOrden> listarContratoDetalle(){
    	
    	
    	listaOrdenDetalle= new ArrayList<IafasPacOrden>();
    	IafasPacOrden ord = new IafasPacOrden();
    	ord.setCperiodoContrato(cperiodoContrato);
        ord.setNcontratoCodigo(ncontratoCodigo);
        
        List<IafasPacOrden> registros = ordenDao.listaContrato(ord);
        for (IafasPacOrden l : registros)
        {
        	setNordenDetalleCantidad(l.getNordenDetalleCantidad());
        	setNordenDetalleMonto(l.getNordenDetalleMonto());
        	setNordenDetalleExtranjera(l.getNordenDetalleExtranjera());
        	setDescripcionItem(l.getDescripcionItem());
        	// setCitemTipoCodigo(l.getCitemTipoCodigo());
        	listaOrdenDetalle.add(l);
        	
        }
        
    	return listaOrdenDetalle;
    	
    }
    
    /**********BUSCADOR DE ITEM CON CONTRATOS REGISTRADOS***********/
	public List<String> completeText(String query) {

		logger.info("[INICIO:] Metodo : completeText");
		String queryLowerCase = query.toLowerCase();
		String queryUpperCase = query.toUpperCase();
		List<String> itemList = new ArrayList<>();
		IafasPacOrdenDao itemDao = new IafasPacOrdenDao(MySQLSessionFactory.getSqlSessionFactory());
		
		/*IafasPacOrden orden = new IafasPacOrden();
		orden.setCperiodoContrato(cperiodoContrato);
		orden.setNcontratoCodigo(ncontratoCodigo);
		orden.setDescripcionItem(queryUpperCase);*/
		
		List<IafasPacOrden> lsts = itemDao.obtenerItemSeleccionado(queryUpperCase); 
		for (IafasPacOrden obj : lsts) {
			descripcionItem = obj.getDescripcionItem();
			nitemCodigo = obj.getNitemCodigo();

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
		IafasPacOrdenDao itemDao = new IafasPacOrdenDao(MySQLSessionFactory.getSqlSessionFactory());

		logger.info("evento selecccionado:"+event.getObject());
		
		List<IafasPacOrden> lsts = itemDao.obtenerItemSeleccionado(event.getObject());
		for (IafasPacOrden obj : lsts) {
			lblDescripcionUnidadMedida = obj.getDescripcionUnidadMedida();
			cunidadMedidaCodigo = obj.getCunidadMedidaCodigo();
			nitemCodigo = obj.getNitemCodigo();
			lblcantContrato = obj.getCantidadContrato();
			lblcandOrden = obj.getCantidadOrden();
			
		}
		logger.info("Campos Selecciones :"+nitemCodigo+" "+cunidadMedidaCodigo);
		logger.info("[FIN:] Metodo : onItemSelect");
	}
	////////////////////////////////////////////////////////////////////////////////////////////

    
    // GRABAR ORDENES CON CONTRATOS
    
    public String GrabarOrden() {
    	int reg =0;
    	try {
    		
    		IafasPacOrden ord = new IafasPacOrden();
    		ord.setCperiodoCodigo(cperiodoCodigo);
    		ord.setNfuenteFinanciamiento(nfuenteFinanciamiento);
    		ord.setCitemTipoCodigo(citemTipoCodigo);
    		ord.setCperiodoContrato(cperiodoContrato);
    		ord.setNcontratoCodigo(ncontratoCodigo);
            ord.setNtareaPresupuestalCodigo(ntareaPresupuestalCodigo);
            ord.setNclasificadorPresupuestalCodigo(nclasificadorPresupuestalCodigo);
            ord.setDordenFecha(dordenFecha);
            ord.setNplazoEntregaCodigo(nplazoEntregaCodigo);
            ord.setVordenDescripcion(vordenDescripcion);
            ord.setCproveedorRuc(cproveedorRuc);
            ord.setNmonedaCodigo(nmonedaCodigo);
            ord.setNordenTipoCambio(nordenTipoCambio);
    	    ord.setVusuarioCreador(usuario);
    	    ord.setVusuarioCodigo(usuario);
    	    ord.setNcondicionPagoCodigo(ncondicionPagoCodigo);
    	    ord.setNpacOrdenPlazo(npacOrdenPlazo);
    	    ord.setNcertificadoPresupuestalCodigo(null);
    	    logger.info("Datos a Registrar: " +ord.toString());
    	    reg=ordenDao.registroOrden(ord);
    	    for (int j =0;j <listaDetalleSession.size(); j++) {
    	    	ord.setCperiodoCodigo(cperiodoCodigo);
    	    	ord.setCitemTipoCodigo(citemTipoCodigo);
    	    	ord.setNitemCodigo(listaDetalleSession.get(j).getNitemCodigo());
    	    	ord.setNcontratoCodigo(ncontratoCodigo);
    	        ord.setCperiodoContrato(cperiodoContrato);
    	        ord.setNordenDetalleCantidad(listaDetalleSession.get(j).getNordenDetalleCantidad());
    	        ord.setNordenDetalleImporte(nordenDetalleImporte);
    	        ord.setNordenDetalleExtranjera(nordenDetalleExtranjera);
    	        ord.setVordenDetalleObservacion(listaDetalleSession.get(j).getVordenDetalleObservacion());
    	        ord.setVusuarioCreador(usuario);
    	        ord.setVusuarioCodigo(usuario);
    	        reg = ordenDao.registroOrdenDetalle(ord);
    	        verOrden();
    	        LimpiarCampos();
    	    }
    	    
    	    
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+e.getMessage());
		}
    	
    	return "mainOrdenCS.xhtml";
    }
    
    public void cargarDetalleItem() {
    	logger.info("[INICIO:] Metodo : cargarDetalleItem");
    	
    	detalleItemSession =new IafasPacOrden(cperiodoCodigo, citemTipoCodigo, nordenCodigo, cperiodoContrato,
    			ncontratoCodigo, vusuarioCreador, vusuarioCodigo, descripcionItem, 
    			nitemCodigo, nordenDetalleCantidad, nordenDetalleImporte, 
    			nordenDetalleExtranjera, vordenDetalleObservacion);
    	
    	this.listaDetalleSession.add(detalleItemSession);
    	logger.info("[FIN:] Metodo : cargarDetalleItem");
    }
    
    // VER CERTIFICADOS PARA COMPRAS DIRECTAS
    public String verCertificado() {
    	String page="insRegOrdenSin";
    	
    	try {
    		
    		listaOrdenCertificado = new ArrayList<IafasPacOrden>();
    		IafasPacOrden ca = new IafasPacOrden();
    		ca.setCperiodoCodigo(cperiodoCodigo);
    		System.out.println(" periodo" +cperiodoCodigo);
    		ca.setNcertificadoPresupuestalCodigo(ncertificadoPresupuestalCodigo);
    		System.out.println("contrato "+ncertificadoPresupuestalCodigo);
    		List<IafasPacOrden> certificado = ordenDao.listaCertificado(ca);
    		System.out.println("lista "+ordenDao.listaCertificado(ca));
    		for (IafasPacOrden l : certificado) {
    			setNfuenteFinanciamiento(certificado.get(0).getNfuenteFinanciamiento());
    			setDescripcionFF(certificado.get(0).getDescripcionFF());
    			setNtareaPresupuestalCodigo(certificado.get(0).getNtareaPresupuestalCodigo());
    			setDescripcionTarea(certificado.get(0).getDescripcionTarea());
    			setNclasificadorPresupuestalCodigo(certificado.get(0).getNclasificadorPresupuestalCodigo());
    			setClasificador(certificado.get(0).getClasificador());
    			setDescripcionClasificador(certificado.get(0).getDescripcionClasificador());
    			listaOrdenCertificado.add(l);
    		}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ver Error :"+e.getMessage());
		}    	
    	return page;
    }
    
    
    /**********BUSCAR PROVEEDORES***********/
   	public List<String> completeTextProv(String query) {

   		logger.info("[INICIO:] Metodo : completeText");
   		String queryLowerCase = query.toLowerCase();
   		String queryUpperCase = query.toUpperCase();
   		List<String> itemList = new ArrayList<>();
   		IafasPacOrdenDao itemDao = new IafasPacOrdenDao(MySQLSessionFactory.getSqlSessionFactory());

   		
   		List<IafasPacOrden> lsts = itemDao.obtenerVerProveedor(queryUpperCase); 
   		for (IafasPacOrden obj : lsts) {
   			razonSocial = obj.getRazonSocial();
   			cproveedorRuc = obj.getCproveedorRuc();

   			itemList.add(razonSocial);
   		}
   		logger.info("[FIN:] Metodo : completeText");
   		return itemList
   				.stream()
   				.filter(t -> t.toLowerCase().startsWith(queryLowerCase))
   				.collect(Collectors.toList());
   	}
   	
   	public void onItemSelectProv(SelectEvent<String> event) {
   		logger.info("[INICIO:] Metodo : onItemSelect");
   		IafasPacOrdenDao itemDao = new IafasPacOrdenDao(MySQLSessionFactory.getSqlSessionFactory());

   		logger.info("evento selecccionado:"+event.getObject());
   		
   		List<IafasPacOrden> lsts = itemDao.obtenerVerProveedor(event.getObject());
   		for (IafasPacOrden obj : lsts) {
   			razonSocial = obj.getRazonSocial();
   			cproveedorRuc = obj.getCproveedorRuc();
   			
   		}
   		logger.info("Campos Selecciones :"+razonSocial+" "+cproveedorRuc);
   		logger.info("[FIN:] Metodo : onItemSelect");
   	}
   	/*===================*/
   	
   	/*********BUSCADORES ITEM***********/
	public List<String> completeTextItem(String query) {

		logger.info("[INICIO:] Metodo : completeText");
		String queryLowerCase = query.toLowerCase();
		String queryUpperCase = query.toUpperCase();
		List<String> itemList = new ArrayList<>();
		IafasItemDao itemDao = new IafasItemDao(MySQLSessionFactory.getSqlSessionFactory());
		
		List<IafasItem> lsts = itemDao.obtenerItemSeleccionado(queryUpperCase);
		for (IafasItem obj : lsts) {
			descripcionItem = obj.getVItemDescripcion();
			codigoItem = obj.getNItemCodigo();
			itemList.add(descripcionItem);
		}
		logger.info("[FIN:] Metodo : completeText");
		return itemList
				.stream()
				.filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}
	
	public void onItemSelectItem(SelectEvent<String> event) {
		logger.info("[INICIO:] Metodo : onItemSelect");
		IafasItemDao itemDao = new IafasItemDao(MySQLSessionFactory.getSqlSessionFactory());

		logger.info("evento selecccionado:"+event.getObject());
		
		List<IafasItem> lsts = itemDao.obtenerItemSeleccionado(event.getObject());
		for (IafasItem obj : lsts) {
			lblDescripcionUnidadMedida = obj.getDescripcionUnidadMedida();
			cunidadMedidaCodigo = obj.getCUnidadMedidaCodigo();			
			codigoItem = obj.getNItemCodigo();
			
		}
		
		logger.info("[FIN:] Metodo : onItemSelect");
	}
	/*===================*/
	
	  // CARGAR DE DETALLE ITEM
    public void cargarDetalleItemSC() {
    	logger.info("[INICIO:] Metodo : cargarDetalleItem");
    	
    	detalleItemSessionItem =new IafasPacOrden(cperiodoCodigo, citemTipoCodigo, nordenCodigo, null,
    			null, vusuarioCreador, vusuarioCodigo, descripcionItem, 
    			codigoItem, nordenDetalleCantidad, nordenDetalleImporte, 
    			nordenDetalleExtranjera, vordenDetalleObservacion);
    	
    	this.listaDetalleItemSession.add(detalleItemSessionItem);
    	logger.info("[FIN:] Metodo : cargarDetalleItemSC");
    }
    
    
    // GRABAR ORDENES  SIN CONTRATO
    

    
    public String GrabarOrdenSC() {
    	int reg =0;
    	try {
    		
    		IafasPacOrden ord = new IafasPacOrden();
    		ord.setCperiodoCodigo(cperiodoCodigo);
    		ord.setNfuenteFinanciamiento(nfuenteFinanciamiento);
    		ord.setCitemTipoCodigo(citemTipoCodigo);
    		ord.setCperiodoContrato(cperiodoContrato);
    		ord.setNcontratoCodigo(ncontratoCodigo);
            ord.setNtareaPresupuestalCodigo(ntareaPresupuestalCodigo);
            ord.setNclasificadorPresupuestalCodigo(nclasificadorPresupuestalCodigo);
            ord.setDordenFecha(dordenFecha);
            ord.setNplazoEntregaCodigo(nplazoEntregaCodigo);
            ord.setVordenDescripcion(vordenDescripcion);
            ord.setCproveedorRuc(cproveedorRuc);
            ord.setNmonedaCodigo(nmonedaCodigo);
            ord.setNordenTipoCambio(nordenTipoCambio);
    	    ord.setVusuarioCreador(usuario);
    	    ord.setVusuarioCodigo(usuario);
    	    ord.setNcondicionPagoCodigo(ncondicionPagoCodigo);
    	    ord.setNpacOrdenPlazo(npacOrdenPlazo);
    	    ord.setNcertificadoPresupuestalCodigo(ncertificadoPresupuestalCodigo);
    	    logger.info("Datos a Registrar: " +ord.toString());
    	    reg=ordenDao.registroOrden(ord);
    	    for (int j =0;j <listaDetalleItemSession.size(); j++) {
    	    	ord.setCperiodoCodigo(cperiodoCodigo);
    	    	ord.setCitemTipoCodigo(citemTipoCodigo);
    	    	ord.setNitemCodigo(listaDetalleItemSession.get(j).getNitemCodigo());
    	    	ord.setNcontratoCodigo(null);
    	        ord.setCperiodoContrato(null);
    	        ord.setNordenDetalleCantidad(listaDetalleItemSession.get(j).getNordenDetalleCantidad());
    	        ord.setNordenDetalleImporte(listaDetalleItemSession.get(j).getNordenDetalleImporte());
    	        ord.setNordenDetalleExtranjera(nordenDetalleExtranjera);
    	        ord.setVordenDetalleObservacion(listaDetalleItemSession.get(j).getVordenDetalleObservacion());
    	        ord.setVusuarioCreador(usuario);
    	        ord.setVusuarioCodigo(usuario);
    	        reg = ordenDao.registroOrdenDetalle(ord);
    	        verOrdenSC();
    	        LimpiarCampos();
    	    }
    	    
    	    
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+e.getMessage());
		}
    	
    	return "mainOrdenCS.xhtml";
    }
    
    //LISTAR LA ORDENES CON CONTRATOS O SIN CONTRATOS
    
  
    public void verOrden() {

    	try {
    		
    		listaOrdenConsulta = new ArrayList<IafasPacOrden>();
    		IafasPacOrden or = new IafasPacOrden();
    		or.setCperiodoCodigo(cperiodoCodigo);
    		System.out.println(" periodo orden" +cperiodoCodigo);
    		List<IafasPacOrden> orden = ordenDao.listaOrdenConsulta(or);
    		System.out.println("lista "+ordenDao.listaOrdenConsulta(or));
    		for (IafasPacOrden l : orden) { 
    			listaOrdenConsulta.add(l);
    		}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ver Error :"+e.getMessage());
		}    	
    	
    }
    
    

    public void verOrdenSC() {

    	try {
    		
    		listaOrdenConsulta = new ArrayList<IafasPacOrden>();
    		IafasPacOrden or = new IafasPacOrden();
    		or.setCperiodoCodigo(cperiodoCodigo);
    		System.out.println(" periodo orden" +cperiodoCodigo);
    		List<IafasPacOrden> orden = ordenDao.listaOrdenConsultaSC(or);
    		System.out.println("lista "+ordenDao.listaOrdenConsultaSC(or));
    		for (IafasPacOrden l : orden) { 
    			listaOrdenConsulta.add(l);
    		}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ver Error :"+e.getMessage());
		}    	
    	
    }
    
    public String VerConsultaOrden() {   	
    	String page ="mainOrdenCS.xhtml";
    	if (ctipoOrden == 1) 
    	{verOrden();}
    	if (ctipoOrden == 2) 
    	{verOrdenSC();  }
    	if (ctipoOrden == 0) 
    	{ page ="mainOrdenCS.xhtml";  }		
    	return page;
    }
    
}
