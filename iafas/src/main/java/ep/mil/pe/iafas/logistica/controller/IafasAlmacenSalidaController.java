package ep.mil.pe.iafas.logistica.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Mensajeria;
import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.logistica.dao.IafasAlmacenSalidaDao;
import ep.mil.pe.iafas.logistica.dao.IafasAlmacenSalidaDetalleDao;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenSalida;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenSalidaDetalle;
import ep.mil.pe.iafas.programacion.dao.IafasItemDao;
import ep.mil.pe.iafas.programacion.model.IafasItem;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ManagedBean(name = "iafasAlmacenSalidaController")
@SessionScoped
public class IafasAlmacenSalidaController implements Serializable {

	private static final long serialVersionUID = -7231521627213709048L;
	private static final Logger logger = Logger.getLogger(IafasAlmacenSalidaController.class.getName());
	
	private String cPeriodo;
	private String codigoMes;
	private List<IafasAlmacenSalida> listaConsultaPrincipal =  new ArrayList<IafasAlmacenSalida>();
	private List<IafasAlmacenSalida> listaConsultaPrincipalSalida =  new ArrayList<IafasAlmacenSalida>();
	private int typeMessages = Constantes.UNO_NEGATIVO;
	private String messages = Constantes.MESSAGE_VALIDACION_PARAMETROS;
	private String descripcionItem;
	private int codigoItem;	
	private String lblDescripcionUnidadMedida;
	private String codigoUnidadMedida;	
	private String codigoAlmacen;
	private String motivo;
	private List<IafasAlmacenSalidaDetalle> listaDetalle = new ArrayList<IafasAlmacenSalidaDetalle>();
	private IafasAlmacenSalidaDetalle detallesGrillaSession;
	private Date hoy = new Date();
	private String modo = Constantes.MODE_REGISTRO;
	private int cantidadSolicitada = Constantes.CERO_INT;
	private IafasAlmacenSalida bean;
	private int cantidadStock = Constantes.CERO_INT;
	private int cantidadAtendida = Constantes.CERO_INT;
	
	/*==========Consultas==========*/
	public List<IafasAlmacenSalida> mostrarConsultaPrincipal() {

		logger.info("[INICIO:] Metodo : mostrarConsultaPrincipal");

		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String cAreaLaboral = usuarioSession.getIdAreaLaboral();

		this.listaConsultaPrincipal = new ArrayList<>();
		if (this.listaConsultaPrincipal != null)
			this.listaConsultaPrincipal.clear();

		IafasAlmacenSalidaDao objDao = new IafasAlmacenSalidaDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasAlmacenSalida objBn = new IafasAlmacenSalida();
		objBn.setCPeriodoCodigo(cPeriodo);
		objBn.setCMesCodigo(codigoMes);
		objBn.setCAreaLaboralCodigo(cAreaLaboral);
		
		List<IafasAlmacenSalida> listaCab = objDao.mostrarConsultaPrincipal(objBn);
		if (listaCab.size() > 0) {
			for (IafasAlmacenSalida objBeanLista : listaCab) {
				this.listaConsultaPrincipal.add(objBeanLista);
			}
		}

		logger.info("[FIN:] Metodo : mostrarConsultaPrincipal");
		return listaConsultaPrincipal;
	}
	
	public List<IafasAlmacenSalida> mostrarConsultaPrincipalSalida() {

		logger.info("[INICIO:] Metodo : mostrarConsultaPrincipalSalida");

		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String cAreaLaboral = usuarioSession.getIdAreaLaboral();

		this.listaConsultaPrincipalSalida = new ArrayList<>();
		if (this.listaConsultaPrincipalSalida != null)
			this.listaConsultaPrincipalSalida.clear();

		IafasAlmacenSalidaDao objDao = new IafasAlmacenSalidaDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasAlmacenSalida objBn = new IafasAlmacenSalida();
		objBn.setCPeriodoCodigo(cPeriodo);
		objBn.setCMesCodigo(codigoMes);
		objBn.setCAreaLaboralCodigo(cAreaLaboral);
		
		List<IafasAlmacenSalida> listaCab = objDao.mostrarConsultaPrincipalSalida(objBn);
		if (listaCab.size() > 0) {
			for (IafasAlmacenSalida objBeanLista : listaCab) {
				this.listaConsultaPrincipalSalida.add(objBeanLista);
			}
		}

		logger.info("[FIN:] Metodo : mostrarConsultaPrincipalSalida");
		return listaConsultaPrincipalSalida;
	}
	/*==============================*/
	
	
	
	/**********Nuevo***********/
	public void nuevo() {
		logger.info("[INICIO:] Metodo : nuevo");
		validarFormulario();
		cleanCamposCabecera();
		logger.info("[FIN:] Metodo : nuevo");
	}
	/**===================*/
	
	
	/**********Buscadores de Items***********/
	public List<String> completeText(String query) {

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
	
	public void onItemSelect(SelectEvent<String> event) {
		logger.info("[INICIO:] Metodo : onItemSelect");
		IafasItemDao itemDao = new IafasItemDao(MySQLSessionFactory.getSqlSessionFactory());

		logger.info("evento selecccionado:"+event.getObject());
		
		List<IafasItem> lsts = itemDao.obtenerItemSeleccionado(event.getObject());
		for (IafasItem obj : lsts) {
			lblDescripcionUnidadMedida = obj.getDescripcionUnidadMedida();
			codigoUnidadMedida = obj.getCUnidadMedidaCodigo();
			codigoItem = obj.getNItemCodigo();
		}
		
		logger.info("[FIN:] Metodo : onItemSelect");
	}
	/*===================*/
	
	/********LIMPIAR CAMPOS***********/
	public void cleanCamposDetalle() {
		logger.info("[INICIO:] Metodo : cleanCamposDetalle");
		setLblDescripcionUnidadMedida(Constantes.VACIO);
		setDescripcionItem(Constantes.VACIO);
		setCantidadSolicitada(Constantes.CERO_INT);
		logger.info("[FIN:] Metodo : cleanCamposDetalle");
	}
	
	public void cleanCamposCabecera() {
		logger.info("[INICIO:] Metodo : cleanCamposCabecera");
		setMotivo(Constantes.VACIO);
		//setCodigoAlmacen(Constantes.CERO_STRING);
		listaDetalle = new ArrayList<>();
		
		logger.info("[FIN:] Metodo : cleanCamposCabecera");
	}
	/*===================*/
	
	
	/********AGREGAR ITEMS***********/
	public void cargarListaDetalleSession() {
		
		logger.info("[INICIO:] Metodo : cargarListaDetalleSession");
		
		detallesGrillaSession = new IafasAlmacenSalidaDetalle(cPeriodo, Constantes.CERO_INT, codigoItem,
				cantidadSolicitada , Constantes.VACIO, hoy, Constantes.VACIO, hoy,
				Constantes.VACIO, hoy, descripcionItem, lblDescripcionUnidadMedida, String.valueOf(codigoItem),
				Constantes.VACIO,Constantes.VACIO,Constantes.VACIO);

		this.listaDetalle.add(detallesGrillaSession);
		
		logger.info("[FIN:] Metodo : cargarListaDetalleSession");

	}
	/*===================*/
	
	/********REGISTRAR***********/
	public void insPedidoAlmacen() {
		
		logger.info("[INICIO:] Metodo : insPedidoAlmacen");
		
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		String cAreaLaboral = usuarioSession.getIdAreaLaboral();
		IafasAlmacenSalida objBn = new IafasAlmacenSalida();
		IafasAlmacenSalidaDao objDao = new IafasAlmacenSalidaDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasAlmacenSalidaDetalle objBnDet = new IafasAlmacenSalidaDetalle();
		IafasAlmacenSalidaDetalleDao objDaoDet = new IafasAlmacenSalidaDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
		Response response = new Response();
		Response responseDet = new Response();
		
		objBn.setCPeriodoCodigo(cPeriodo);
		objBn.setCMesCodigo(codigoMes);
		objBn.setNAlmacenSalidaCodigo(bean.getNAlmacenSalidaCodigo());
		objBn.setVAlmacenSalidaMotivo(motivo);
		objBn.setCAreaLaboralCodigo(cAreaLaboral);
		objBn.setVUsuarioCreador(codUsu);
		objBn.setMode(modo);
		
		
		try {
			response = objDao.mantenimientoCabecera(objBn);
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
				
				for(int i = 0 ; i<listaDetalle.size(); i++) {
					objBnDet.setCPeriodoCodigo(cPeriodo);
					objBnDet.setNAlmacenSalidaCodigo(Integer.parseInt(response.getIdTransaccion()));
					objBnDet.setCodigoItem(listaDetalle.get(i).getCodigoItem());
					objBnDet.setNAlmacenSalidaSolicitado(listaDetalle.get(i).getNAlmacenSalidaSolicitado());
					objBnDet.setVUsuarioCreador(codUsu);
					objBnDet.setMode(modo);
					responseDet = objDaoDet.mantenimientoDetalle(objBnDet);
					if (Constantes.CERO_STRING.equals(responseDet.getCodigoRespuesta())) {
						
						this.typeMessages = Constantes.CERO_INT;
						this.messages = response.getMensajeRespuesta();
						PrimeFaces.current().ajax().update("frm_PedidoAlmacen:pnl_messages");
						refreshMessage();
						Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()),
								responseDet.getMensajeRespuesta(), typeMessages, messages, Constantes.METODO_JS_CNV);
					}
				}
			}
			mostrarConsultaPrincipal();
		}
		catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : insPedidoAlmacen");
		}
	}
	
	/*===================*/
	
	
	/***********OBTENER REGISTROS PARA EDICION***************/
	public void obtenerRegistro() {
		logger.info("[INICIO:] Metodo : obtenerRegistro");
		
		IafasAlmacenSalida objBn = new IafasAlmacenSalida();
		IafasAlmacenSalidaDao objDao = new IafasAlmacenSalidaDao(MySQLSessionFactory.getSqlSessionFactory());
		
		objBn.setCPeriodoCodigo(cPeriodo);
		objBn.setNAlmacenSalidaCodigo(bean.getNAlmacenSalidaCodigo());
		if (!validarEstados(bean.getCEstadoCodigo())) {
			List<IafasAlmacenSalida> listaCab = objDao.obtenerRegistro(objBn);
			if (listaCab.size() > 0) {
				for (IafasAlmacenSalida objBeanLista : listaCab) {
					setMotivo(objBeanLista.getVAlmacenSalidaMotivo());
					obtenerRegistroDetalle();
				}
			}
		}
		setModo(Constantes.MODE_ACTUALIZACION);
		logger.info("[FIN:] Metodo : obtenerRegistro");
	}
	
	
	public void obtenerRegistroDetalle() {
		logger.info("[INICIO:] Metodo : obtenerRegistroDetalle");
		IafasAlmacenSalidaDetalle objBnDet = new IafasAlmacenSalidaDetalle();
		IafasAlmacenSalidaDetalleDao objDetDao = new IafasAlmacenSalidaDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
		objBnDet.setCPeriodoCodigo(cPeriodo);
		objBnDet.setNAlmacenSalidaCodigo(bean.getNAlmacenSalidaCodigo());
		if (!validarEstados(bean.getCEstadoCodigo())) {
			List<IafasAlmacenSalidaDetalle> lstdet = objDetDao.obtenerRegistroDetalle(objBnDet);
			for (IafasAlmacenSalidaDetalle objDt : lstdet) {

				detallesGrillaSession = new IafasAlmacenSalidaDetalle(objDt.getCPeriodoCodigo(), Constantes.CERO_INT,
						objDt.getNItemCodigo(), objDt.getNAlmacenSalidaSolicitado(), Constantes.VACIO, hoy,
						Constantes.VACIO, hoy, Constantes.VACIO, hoy, objDt.getDescripcionItem(),
						objDt.getDescripcionUnidadMedida(), String.valueOf(objDt.getNItemCodigo()), Constantes.VACIO,
						Constantes.VACIO, Constantes.VACIO);

				this.listaDetalle.add(detallesGrillaSession);
			}
		}
		logger.info("[FIN:] Metodo : obtenerRegistroDetalle");
	}
	/*===================*/
	
	
	/*********ANULAR REGISTRIO DE DETALLE********/
	public void anularRegistroDetalle() {
		logger.info("[INICIO:] Metodo : anularRegistroDetalle");
		
		Response response = new Response();
		String codigoItemDEL = (String) extContext().getRequestParameterMap().get("codigoItemDEL");
		String codigoAlmacenDEL = (String) extContext().getRequestParameterMap().get("codigoAlmacenDEL");
		String cperiodoDEL = (String) extContext().getRequestParameterMap().get("cperiodoDEL");
		
		logger.info("parametros DEL: "+codigoItemDEL +" - " +codigoAlmacenDEL +" - "+cperiodoDEL);
		//this.listaDetalle.remove(0);
	}
	
	/*===================*/
	
	
	/*********CAMBIAR ESTADOS********/
	public void cambiarEstados(String opcion) {
		logger.info("[INICIO:] Metodo : cambiarEstados");
		Response response = new Response();

		IafasAlmacenSalida objBn = new IafasAlmacenSalida();
		IafasAlmacenSalidaDao objDao = new IafasAlmacenSalidaDao(MySQLSessionFactory.getSqlSessionFactory());

		objBn.setCPeriodoCodigo(cPeriodo);
		objBn.setCMesCodigo(codigoMes);
		objBn.setNAlmacenSalidaCodigo(bean.getNAlmacenSalidaCodigo());

		if (Constantes.MODE_CERRAR_TECHO.equals(opcion)) {
			objBn.setMode(Constantes.MODE_CERRAR_TECHO);
		} else {
			objBn.setMode(Constantes.MODE_ABRIR_TECHO);
		}

		try {
			response = objDao.mantenimientoCabecera(objBn);
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {

				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				PrimeFaces.current().ajax().update("frm_PedidoAlmacen:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
						typeMessages, messages, Constantes.METODO_JS_CNV);
			}
			mostrarConsultaPrincipal();
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : cambiarEstados");
		}
	}
	
	/*===================*/
	
	/*********VALIDAR FORMULARIO********/
	public boolean validarFormulario() {
		boolean valiadacionFormulario = false;
		
		if (cPeriodo.equals(Constantes.CERO_STRING)) {
			this.typeMessages = Constantes.UNO_INT;
			this.messages = Constantes.VALIDACION_AF;
			PrimeFaces.current().ajax().update("frm_PedidoAlmacen:pnl_messages");
			refreshMessage();
			Mensajeria.showMessages(typeMessages, messages, typeMessages, messages, Constantes.METODO_JS_CNV);
			valiadacionFormulario = true;
		}

		if (codigoMes.equals(Constantes.CERO_CERO_STRING)) {
			this.typeMessages = Constantes.UNO_INT;
			this.messages = Constantes.VALIDACION_MES;
			PrimeFaces.current().ajax().update("frm_PedidoAlmacen:pnl_messages");
			refreshMessage();
			Mensajeria.showMessages(typeMessages, messages, typeMessages, messages, Constantes.METODO_JS_CNV);
			valiadacionFormulario = true;
		}
		return valiadacionFormulario;
	}
	
	public boolean validarEstados(String estado) {
		boolean valiadacionEstado = false;
		if (estado.equals(Constantes.ESTADO_TECHO_CERRADO)) {
			this.typeMessages = Constantes.UNO_INT;
			this.messages = Constantes.MESSAGE_VALIDACION_TECHO_CERRADO;
			PrimeFaces.current().ajax().update("frm_PedidoAlmacen:pnl_messages");
			refreshMessage();
			Mensajeria.showMessages(typeMessages, messages,typeMessages, messages, Constantes.METODO_JS_CNV);
			valiadacionEstado = true;

		}
		return valiadacionEstado;
	}
	/*===================*/
	
	public void refreshMessage() {
		setTypeMessages(typeMessages);
		setMessages(messages);
	}
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
}
