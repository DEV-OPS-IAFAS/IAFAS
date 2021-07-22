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

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

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
	/*==============================*/
	
	
	
	/**********Nuevo***********/
	public void nuevo() {
		logger.info("[INICIO:] Metodo : nuevo");
		
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
		
		logger.info("ITEM BUSCADO:::>"+queryUpperCase);
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
		
		logger.info("[FIN:] Metodo : cleanCamposDetalle");
	}
	
	public void cleanCamposCabecera() {
		logger.info("[INICIO:] Metodo : cleanCamposCabecera");
		setMotivo(Constantes.VACIO);
		setCodigoAlmacen(Constantes.CERO_STRING);
		listaDetalle = new ArrayList<>();
		
		logger.info("[FIN:] Metodo : cleanCamposCabecera");
	}
	/*===================*/
	
	
	/********AGREGAR ITEMS***********/
	public void cargarListaDetalleSession() {
		
		logger.info("[INICIO:] Metodo : cargarListaDetalleSession");
		
		detallesGrillaSession = new IafasAlmacenSalidaDetalle(cPeriodo, Constantes.CERO_INT, codigoItem,
				Constantes.CERO_INT, Constantes.CERO_INT, Constantes.VACIO, hoy, Constantes.VACIO, hoy,
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
		objBn.setNAlmacenCodigo(Integer.parseInt(codigoAlmacen));
		objBn.setCMesCodigo(codigoMes);
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
		}
		catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : insPedidoAlmacen");
		}
		
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
