package ep.mil.pe.iafas.programacion.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Mensajeria;
import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.programacion.dao.IafasCuadroNecesidadesValorizadasDao;
import ep.mil.pe.iafas.programacion.dao.IafasEventoFinalDao;
import ep.mil.pe.iafas.programacion.dao.IafasEventoPrincipalDao;
import ep.mil.pe.iafas.programacion.dao.IafasItemDao;
import ep.mil.pe.iafas.programacion.model.IafasCuadroNecesidadesValorizadas;
import ep.mil.pe.iafas.programacion.model.IafasEventoFinal;
import ep.mil.pe.iafas.programacion.model.IafasEventoPrincipal;
import ep.mil.pe.iafas.programacion.model.IafasItem;
import ep.mil.pe.iafas.programacion.model.ProgramacionMultiAnual;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Getter;
import lombok.Setter;

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
	private String niveles;
	private String valueCheckEventoAnexo =  Constantes.CERO_STRING;
	private boolean boolCheckEventoAnexo =  false;
	private boolean desactivarObjetos = false;
	private String nombreTarea;
	private String varEventoPrincipalFinal = Constantes.CERO_STRING;
	private IafasEventoPrincipal beanEvp;
	private List<IafasEventoFinal> listaCabeceraEvtFinal = new ArrayList<IafasEventoFinal>();
	private String varEventoFinal=Constantes.VACIO;
	private String varEventoAnteriorSecundario=Constantes.VACIO;
	private List<IafasEventoPrincipal> listaCabeceraEvtSecundario = new ArrayList<IafasEventoPrincipal>();
	private int varInicioNivelSecundario = Constantes.CERO_INT;
	private int varFinNivelSecundario = Constantes.CERO_INT;
	private String codigoEventoSecundario;
	private String nombreEventoSecundario;
	private IafasEventoPrincipal beanEveSec;
	private int varNivelSecundarioAnterior = Constantes.CERO_INT;
	private String codigoEventoFinal;
	private String nombreEventoFinal;
	private int numeroOrden;
	private int cantidadeventoFinal;
	private IafasEventoFinal beanEveFinal;
	private int neventoFinalCodigo = Constantes.CERO_INT;
	private List<IafasCuadroNecesidadesValorizadas> listaMantCNV = new ArrayList<IafasCuadroNecesidadesValorizadas>();
	private int codigoTarea=Constantes.CERO_INT;
	private List<SelectItem> cadenas;
	private List<SelectItem> items;
	private int codigoItem;
	private String lblDescripcionUnidadMedida;
	private String codigoUnidadMedida;	
	private String codCla;
	private String descripcionItem;
	private BigDecimal cantidad = Constantes.ZERO_BIG_DECIMAL;
	private BigDecimal valorReferencial= Constantes.ZERO_BIG_DECIMAL;
	private BigDecimal total = Constantes.ZERO_BIG_DECIMAL;
	private String modo = Constantes.MODE_REGISTRO;
	private BigDecimal montoCadenaGasto = Constantes.ZERO_BIG_DECIMAL;
	
	private int typeMessages = Constantes.UNO_NEGATIVO;
	private String messages = Constantes.MESSAGE_VALIDACION_PARAMETROS;
	
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
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		
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
		setNombreTarea(bean.getNtareaPresupuestalCodigo()+":"+bean.getDescripcionTarea());
		logger.info("[FIN:] Metodo : crearEventoPrincipal");

		return pagina;
	}
	
	public void nuevo() {
		logger.info("[INICIO:] Metodo : nuevo");
		int codTarea= bean.getNtareaPresupuestalCodigo();
		String numeroCorrelativo= generarCorrelativo(codTarea);
		setModo(Constantes.MODE_REGISTRO);
		codigoEventoPrincipal = Constantes.TEXTO_CNV
								.concat(String.valueOf(codTarea))
								.concat(Constantes.GUION)
								.concat(numeroCorrelativo);
		cleanCampos();
		logger.info("[FIN:] Metodo : nuevo");
	}
	
	public void nuevoEventoFinal() {
		logger.info("[INICIO:] Metodo : nuevoEventoFinal");
		
		setModo(Constantes.MODE_REGISTRO);
		cleanCamposEventoFinal();
		logger.info("[FIN:] Metodo : nuevoEventoFinal");
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
	
	public void mantRegEventoPrincipal() {
		logger.info("[INICIO:] Metodo : mantRegEventoPrincipal:::");
		
		IafasEventoPrincipalDao objDao =  new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn = new IafasEventoPrincipal();
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		Response response = new Response();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalCodigo(codigoEventoPrincipal);
		objBn.setVeventoPrincipalAnexo(null);
		objBn.setVeventoPrincipalNombre(nombreEvento);
		objBn.setVeventoPrincipalComentario(comentario);
		
		if(niveles.length()>0) {
			objBn.setNeventoPrincipalNivel(Constantes.CERO_INT);
			objBn.setNeventoPrincipalNiveles(Integer.parseInt(niveles));
		}
		objBn.setCeventoPrincipalFinal(varEventoPrincipalFinal);
		objBn.setVusuarioCodigo(codUsu);
		objBn.setMode(modo);
		try {
			if (!validacionesEventoPrincipal()) {
				response = objDao.mantenimientoEventoPrincipal(objBn);
				if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
					this.typeMessages = Constantes.CERO_INT;
					this.messages = response.getMensajeRespuesta();
					buscarCabeceraEP();
					PrimeFaces.current().ajax().update("frm_EventoPrincipal:pnl_messages");
					refreshMessage();
					Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()),
							response.getMensajeRespuesta(), typeMessages, messages, Constantes.METODO_JS_CNV);
				}
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : mantRegEventoPrincipal:::");
		}		
	}
	
	public void actualizarBoolean() {
		logger.info("[INICIO:] Metodo : actualizarBoolean");
		boolean value = boolCheckEventoAnexo ? true : false;
		if (value) {
			setValueCheckEventoAnexo(Constantes.UNO_STRING);
			setDesactivarObjetos(Constantes.TRUE);
			setNiveles(Constantes.VACIO);
			setVarEventoPrincipalFinal(Constantes.UNO_STRING);
		}
		else {
			setValueCheckEventoAnexo(Constantes.VACIO);
			setDesactivarObjetos(Constantes.FALSE);
			setVarEventoPrincipalFinal(Constantes.CERO_STRING);
		}
		
		logger.info("[FIN:] Metodo : actualizarBoolean");
	}
	
	public String verEvento() {
		logger.info("[INICIO:] Metodo : verEvento");
		String page = Constantes.VACIO;
		int varNumeroNivel = beanEvp.getNeventoPrincipalNivel();
		int varNumeroNiveles = beanEvp.getNeventoPrincipalNiveles();
		varEventoFinal = beanEvp.getVeventoPrincipalCodigo();
		varEventoAnteriorSecundario = beanEvp.getVeventoPrincipalCodigo();
		varFinNivelSecundario = beanEvp.getNeventoPrincipalNiveles();
		int codigoTarea = beanEvp.getNtareaPresupuestalCodigo();
		
		if(Constantes.CERO_INT == varNumeroNivel &&  Constantes.CERO_INT==varNumeroNiveles) {
			page = "mainEventosFinales.xhtml";
			buscarCabeceraEvtFinal();
		}
		else {
			varInicioNivelSecundario  =  Integer.parseInt(obtenerNivelSecundarioActual(codigoTarea, varEventoAnteriorSecundario));
			buscarCabeceraEvtSecundario();
			page = "mainEventosSecundarios.xhtml";
		}
		logger.info("[FIN:] Metodo : verEvento");
		return page;
	}
	
	public List<IafasEventoFinal> buscarCabeceraEvtFinal() {
		logger.info("[INICIO:] Metodo : buscarCabeceraEvtFinal");

		this.listaCabeceraEvtFinal = new ArrayList<>();
		if (this.listaCabeceraEvtFinal != null)
			this.listaCabeceraEvtFinal.clear();
		
		IafasEventoFinalDao objDao =  new IafasEventoFinalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoFinal objBn = new IafasEventoFinal();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalCodigo(varEventoFinal);
		
		List<IafasEventoFinal> lstCab = objDao.mostrarCabeceraEvtFinal(objBn);
		if (lstCab.size() > 0) {
			for (IafasEventoFinal objBeanLista : lstCab) {
				this.listaCabeceraEvtFinal.add(objBeanLista);
			}
		}

		logger.info("[FIN:] Metodo : buscarCabeceraEvtFinal");
		return listaCabeceraEvtFinal;
	}
	
	public List<IafasEventoPrincipal> buscarCabeceraEvtSecundario() {
		logger.info("[INICIO:] Metodo : buscarCabeceraEvtSecundario");

		this.listaCabeceraEvtSecundario = new ArrayList<>();
		if (this.listaCabeceraEvtSecundario != null)
			this.listaCabeceraEvtSecundario.clear();
		
		IafasEventoPrincipalDao objDao =  new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn = new IafasEventoPrincipal();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		objBn.setNeventoPrincipalNivel(varInicioNivelSecundario);
		objBn.setVeventoPrincipalAnexo(varEventoAnteriorSecundario);
		List<IafasEventoPrincipal> lstCab = objDao.verEventoSecundario(objBn);
		if (lstCab.size() > 0) {
			for (IafasEventoPrincipal objBeanLista : lstCab) {
				this.listaCabeceraEvtSecundario.add(objBeanLista);
			}
		}
		else {
			setVarEventoAnteriorSecundario(varEventoAnteriorSecundario);
			setVarInicioNivelSecundario(varInicioNivelSecundario);
			this.listaCabeceraEvtSecundario = new ArrayList<>();
			this.listaCabeceraEvtSecundario.clear();
		}

		logger.info("[FIN:] Metodo : buscarCabeceraEvtSecundario");
		return listaCabeceraEvtSecundario;
	}

	private String generarCorrelativoSecundario(int codigoTarea, String codigoEventoAnterior) {
		logger.info("[INICIO:] Metodo : generarCorrelativo");
		String numero = Constantes.VACIO;
		IafasEventoPrincipalDao objDao =  new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn = new IafasEventoPrincipal();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(codigoTarea);
		objBn.setCanioRegistro(String.valueOf(cperiodo));
		objBn.setVeventoPrincipalAnexo(codigoEventoAnterior);
		numero = objDao.generarCorrelativoSecundario(objBn)
				.get(0)
				.getVeventoPrincipalCodigo()
				.toString();
		logger.info("[FIN:] Metodo : generarCorrelativo");

		return numero;
	}
	
	private String obtenerNivelSecundarioActual(int codigoTarea, String codigoEventoAnterior) {
		logger.info("[INICIO:] Metodo : obtenerNivelSecundarioActual");
		String numeroNivelSecundarioActual = Constantes.VACIO;
		IafasEventoPrincipalDao objDao = new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn = new IafasEventoPrincipal();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(codigoTarea);
		objBn.setCanioRegistro(String.valueOf(cperiodo));
		objBn.setVeventoPrincipalAnexo(codigoEventoAnterior);
		numeroNivelSecundarioActual = String
				.valueOf(objDao.obtenerNivelActual(objBn).get(0).getNeventoPrincipalNivel());
		logger.info("[FIN:] Metodo : obtenerNivelSecundarioActual");

		return numeroNivelSecundarioActual;
	}
	
	public void nuevoEventoSecundario() {
		logger.info("[INICIO:] Metodo : nuevoEventoSecundario");
		int codTarea= bean.getNtareaPresupuestalCodigo();
		String numeroCorrelativoSecundario= generarCorrelativoSecundario(codTarea,varEventoAnteriorSecundario);
		String numeroAnterior = varEventoAnteriorSecundario.substring(4, varEventoAnteriorSecundario.length());
		codigoEventoPrincipal = Constantes.TEXTO_HTS
								.concat(numeroAnterior)
								.concat(Constantes.GUION)
								.concat(numeroCorrelativoSecundario);
		setCodigoEventoSecundario(codigoEventoPrincipal);
		setModo(Constantes.MODE_REGISTRO);
		cleanCamposEvtSecundario();
		logger.info("[FIN:] Metodo : nuevoEventoSecundario");
	}

	public void insRegEventoSecundario() {
		logger.info("[INICIO:] Metodo : insRegEventoSecundario:::");
		
		IafasEventoPrincipalDao objDao =  new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn = new IafasEventoPrincipal();
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		logger.info("datos de isercion: "+codigoEventoSecundario +" - "+varEventoAnteriorSecundario);
		Response response = new Response();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalCodigo(codigoEventoSecundario);
		objBn.setVeventoPrincipalAnexo(varEventoAnteriorSecundario);
		objBn.setVeventoPrincipalNombre(nombreEventoSecundario);
		objBn.setVeventoPrincipalComentario(comentario);
		objBn.setNeventoPrincipalNivel(varInicioNivelSecundario);
		objBn.setNeventoPrincipalNiveles(varFinNivelSecundario);
		objBn.setCeventoPrincipalFinal(varEventoPrincipalFinal);
		objBn.setVusuarioCodigo(codUsu);
		objBn.setMode(modo);
		try {

			if (!validacionesEventoSecundario()) {

				response = objDao.mantenimientoEventoPrincipal(objBn);
				if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
					this.typeMessages = Constantes.CERO_INT;
					this.messages = response.getMensajeRespuesta();
					buscarCabeceraEvtSecundario();
					PrimeFaces.current().ajax().update("frm_EventoSecundarios:pnl_messages");
					refreshMessage();
					Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()),
							response.getMensajeRespuesta(), typeMessages, messages, Constantes.METODO_JS_CNV);
				}
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : insRegEventoSecundario:::");
		}
		
	}
	
	public String verEventoSiguiente() {
		logger.info("[INICIO:] Metodo : verEventoSiguiente");
		String page = Constantes.VACIO;
		varEventoAnteriorSecundario = beanEveSec.getVeventoPrincipalCodigo();
		varNivelSecundarioAnterior = beanEveSec.getNeventoPrincipalNivel();

		varInicioNivelSecundario = varNivelSecundarioAnterior + 1;
		page = Constantes.VACIO;
		if(varFinNivelSecundario == varNivelSecundarioAnterior) {
			varEventoFinal = varEventoAnteriorSecundario;
			buscarCabeceraEvtFinal();
			page = "mainEventosFinales.xhtml";
		}
		else {
			buscarCabeceraEvtSecundario();
			page = "mainEventosSecundarios.xhtml";
		}
		
		logger.info("[FIN:] Metodo : verEventoSiguiente");
		return page;
	}
	
	public String verEventoAnterior() {
		logger.info("[INICIO:] Metodo : verEventoAnterior");
		String page = Constantes.VACIO;
		
		if (beanEveSec != null) {
			varNivelSecundarioAnterior = beanEveSec.getNeventoPrincipalNivel();
			varInicioNivelSecundario = varNivelSecundarioAnterior - 1;

			if (Constantes.UNO_INT == beanEveSec.getNeventoPrincipalNivel()) {
				buscarCabeceraEP();
				page = "mainEventosPrincipales.xhtml";
			} else {
				buscarCabeceraEvtSecundarioAnterior();
				page = "mainEventosSecundarios.xhtml";
			}
		}	
		else {
			buscarCabeceraEP();
			page = "mainEventosPrincipales.xhtml";
		}
		
		logger.info("[FIN:] Metodo : verEventoAnterior");
		return page;
	}
	
	/*LIMPIANDO CAMPOS*/
	public void cleanCampos() {
		logger.info("[INICIO:] Metodo : cleanCampos");
		setNombreEvento(Constantes.VACIO);
		setComentario(Constantes.VACIO);
		setBoolCheckEventoAnexo(Constantes.FALSE);
		setNiveles(Constantes.VACIO);
		setDesactivarObjetos(Constantes.FALSE);
		logger.info("[FIN:] Metodo : cleanCampos");
	}
	
	public void cleanCamposRetornar() {
		logger.info("[INICIO:] Metodo : cleanCamposRetornar");
		setNombreEvento(Constantes.VACIO);
		setComentario(Constantes.VACIO);
		setBoolCheckEventoAnexo(Constantes.FALSE);
		setNiveles(Constantes.VACIO);
		setDesactivarObjetos(Constantes.FALSE);
		setVarEventoPrincipalFinal(Constantes.VACIO);
		setVarEventoFinal(Constantes.VACIO);
		setVarInicioNivelSecundario(Constantes.CERO_INT);
		setVarNivelSecundarioAnterior(Constantes.CERO_INT);
		logger.info("[FIN:] Metodo : cleanCamposRetornar");
	}
	
	public void cleanCamposEvtSecundario() {
		logger.info("[INICIO:] Metodo : cleanCamposEvtSecundario");
		setNombreEventoSecundario(Constantes.VACIO);
		logger.info("[FIN:] Metodo : cleanCamposEvtSecundario");
	}
	
	public void cleanCamposEventoFinal() {
		logger.info("[INICIO:] Metodo : cleanCamposEventoFinal");
		setNombreEventoFinal(Constantes.VACIO);
		setNumeroOrden(Constantes.CERO_INT);
		setCantidadeventoFinal(Constantes.CERO_INT);
		logger.info("[FIN:] Metodo : cleanCamposEventoFinal");
	}
	
	public String retornarEventoPrincipal() {
		logger.info("[INICIO:] Metodo : retornarEventoPrincipal");
		String page = Constantes.VACIO;
		
		cleanCamposRetornar();
		page="mainEventosPrincipales.xhtml";
		logger.info("[FIN:] Metodo : retornarEventoPrincipal");
		return page;

	}
	
	public List<IafasEventoPrincipal> buscarCabeceraEvtSecundarioAnterior() {
		logger.info("[INICIO:] Metodo : buscarCabeceraEvtSecundarioAnterior");

		this.listaCabeceraEvtSecundario = new ArrayList<>();
		if (this.listaCabeceraEvtSecundario != null)
			this.listaCabeceraEvtSecundario.clear();

		IafasEventoPrincipalDao objDao = new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn = new IafasEventoPrincipal();
		List<IafasEventoPrincipal> lt = objDao.obtenerEventoPrincipalDelAnexo(varEventoAnteriorSecundario);
		String eventoPrincipal = lt.get(0).getVeventoPrincipalAnexo();

		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalAnexo(eventoPrincipal);// varEventoAnteriorSecundario);
		objBn.setNeventoPrincipalNivel(varInicioNivelSecundario);

		List<IafasEventoPrincipal> lstCab = objDao.verEventoSecundarioAnterior(objBn);
		if (lstCab.size() > 0) {
			for (IafasEventoPrincipal objBeanLista : lstCab) {
				setVarEventoAnteriorSecundario(objBeanLista.getVeventoPrincipalAnexo());
				this.listaCabeceraEvtSecundario.add(objBeanLista);
			}
		} else {
			setVarEventoAnteriorSecundario(varEventoAnteriorSecundario);
			setVarInicioNivelSecundario(varInicioNivelSecundario);
			this.listaCabeceraEvtSecundario = new ArrayList<>();
			this.listaCabeceraEvtSecundario.clear();
		}

		logger.info("[FIN:] Metodo : buscarCabeceraEvtSecundarioAnterior");
		return listaCabeceraEvtSecundario;
	}
	
	public void mantRegEventoFinal() {
		logger.info("[INICIO:] Metodo : mantRegEventoFinal:::");

		IafasEventoFinalDao objDao = new IafasEventoFinalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoFinal objBn = new IafasEventoFinal();
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		Response response = new Response();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalCodigo(varEventoAnteriorSecundario);
		objBn.setNeventoFinalCodigo(neventoFinalCodigo);
		objBn.setVeventoFinalNombre(nombreEventoFinal);
		objBn.setNeventoFinalPrioridad(numeroOrden);
		objBn.setNeventoFinalMetaFisica(cantidadeventoFinal);
		objBn.setVusuarioCodigo(codUsu);
		objBn.setMode(modo);
		
		try {
			if (!validacionesEventoFinal()) {
			response = objDao.mantenimientoEventoFinal(objBn);
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				buscarCabeceraEvtFinal();
				PrimeFaces.current().ajax().update("frm_EventoFinal:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
				typeMessages, messages, Constantes.METODO_JS_CNV);
		 	}
		 }
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : mantRegEventoFinal:::");
		}
	}
	
	public String registrarInsumos() {
		logger.info("[INICIO:] Metodo : registrarInsumos");
		String page = Constantes.VACIO;
		neventoFinalCodigo = beanEveFinal.getNeventoFinalCodigo();
		buscarCNVMantenimiento();
		page = "mantCuadroNecesidadesValorizadas.xhtml";

		logger.info("[FIN:] Metodo : registrarInsumos");
		return page;
	}
	
	public List<IafasCuadroNecesidadesValorizadas> buscarCNVMantenimiento() {
		logger.info("[INICIO:] Metodo : buscarCNVMantenimiento");

		this.listaMantCNV = new ArrayList<>();
		if (this.listaMantCNV != null)
			this.listaMantCNV.clear();
		IafasCuadroNecesidadesValorizadasDao objDao = new IafasCuadroNecesidadesValorizadasDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasCuadroNecesidadesValorizadas objBn  = new IafasCuadroNecesidadesValorizadas();
		codigoTarea = beanEveFinal.getNtareaPresupuestalCodigo();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(codigoTarea);
		objBn.setCanioRegistro(cperiodo);
		objBn.setVeventoPrincipalCodigo(varEventoFinal);
		objBn.setNeventoFinalCodigo(neventoFinalCodigo);
		
		List<IafasCuadroNecesidadesValorizadas> lstCab = objDao.mostrarCNV(objBn);
		if (lstCab.size() > 0) {
			for (IafasCuadroNecesidadesValorizadas objBeanLista : lstCab) {
				this.listaMantCNV.add(objBeanLista);
			}
		}
		
		logger.info("[FIN:] Metodo : buscarCNVMantenimiento");
		return listaMantCNV;
	}
	
	public List<SelectItem> getCadenas() {
		logger.info("[INICIO:] Metodo : getCadenas");
		
		this.cadenas = new ArrayList<>();
		IafasCuadroNecesidadesValorizadasDao cadenasDao = new IafasCuadroNecesidadesValorizadasDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasCuadroNecesidadesValorizadas objBn= new IafasCuadroNecesidadesValorizadas();
		
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		
		List<IafasCuadroNecesidadesValorizadas> lstCadenas = cadenasDao.obtenerCadenasGastoCNV(objBn);
		for (IafasCuadroNecesidadesValorizadas p : lstCadenas) {
			this.cadenas.add(new SelectItem(p.getNclasificadorPresupuestalCodigo(),
					p.getCadenaGasto() +" : " +p.getDescripcionCadenaGasto()));
		}
		logger.info("[FIN:] Metodo : getCadenas");
		return this.cadenas;
	}
	
	public void obtenerMontoCadenaGasto() {
		logger.info("[INICIO:] Metodo : obtenerMontoCadenaGasto");
		IafasCuadroNecesidadesValorizadasDao cadenasDao = new IafasCuadroNecesidadesValorizadasDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasCuadroNecesidadesValorizadas objBn= new IafasCuadroNecesidadesValorizadas();
		
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		objBn.setNclasificadorPresupuestalCodigo(Integer.parseInt(codCla));
		List<IafasCuadroNecesidadesValorizadas> lstCadenas = cadenasDao.obtenerMontoCadenasGasto(objBn);
		
		for (IafasCuadroNecesidadesValorizadas p : lstCadenas) {
			setMontoCadenaGasto(p.getTotalCadenaGasto());
		}
		logger.info("[FIN:] Metodo : obtenerMontoCadenaGasto");
	}
	
	public void nuevoCNV() {
		logger.info("[INICIO:] Metodo : nuevoCNV");
		logger.info("[FIN:] Metodo : nuevoCNV");
	}
	
	public List<String> completeText(String query) {

		logger.info("[INICIO:] Metodo : completeText");
		String queryLowerCase = query.toLowerCase();
		List<String> itemList = new ArrayList<>();
		IafasItemDao itemDao = new IafasItemDao(MySQLSessionFactory.getSqlSessionFactory());
		
		List<IafasItem> lsts = itemDao.obtenerItems();
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

	public void mantRegistroCNV() {
		logger.info("[INICIO:] Metodo : mantRegistroCNV:::");
	
		IafasCuadroNecesidadesValorizadasDao objDao =  new IafasCuadroNecesidadesValorizadasDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasCuadroNecesidadesValorizadas objBn = new IafasCuadroNecesidadesValorizadas();
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		Response response = new Response();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(bean.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalCodigo(varEventoFinal);
		objBn.setNeventoFinalCodigo(neventoFinalCodigo);
		objBn.setNclasificadorPresupuestalCodigo(Integer.parseInt(codCla));
		objBn.setNitemCodigo(codigoItem);
		objBn.setCunidadMedidaCodigo(codigoUnidadMedida);
		objBn.setNcnvCantidad(cantidad);
		objBn.setNcnvPrecio(valorReferencial);
		objBn.setVusuarioCodigo(codUsu);
		objBn.setMode(modo);
		
		try {
			if (!validacionesCNV()) {
			response = objDao.mantenimientoCNV(objBn);
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				buscarCNVMantenimiento();
				PrimeFaces.current().ajax().update("frm_MantCNV:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()),
						response.getMensajeRespuesta(), typeMessages, messages, Constantes.METODO_JS_CNV);
			}
		}	
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : mantRegistroCNV:::");
		}		
	}
	
	/*MODIFICACION DE LOS REGISTROS*/
	public void editEventoPrincipal() {
		logger.info("[INICIO:] Metodo : editEventoPrincipal");
		IafasEventoPrincipalDao objDao = new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn  = new IafasEventoPrincipal();
		String evtPrincipalCodigoEdit = beanEvp.getVeventoPrincipalCodigo();
		int ntareaPtalEdit = beanEvp.getNtareaPresupuestalCodigo();
		
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(ntareaPtalEdit);
		objBn.setVeventoPrincipalCodigo(evtPrincipalCodigoEdit);
		
		List<IafasEventoPrincipal> lst = objDao.editEventoPrincipal(objBn);
		
		for(IafasEventoPrincipal obj : lst) {
			setCodigoEventoPrincipal(obj.getVeventoPrincipalCodigo());
			setNombreEvento(obj.getVeventoPrincipalNombre());
			setComentario(obj.getVeventoPrincipalComentario());
			setNiveles(String.valueOf(obj.getNeventoPrincipalNiveles()));
			setModo(Constantes.MODE_ACTUALIZACION);
			String checkeo  = obj.getCeventoPrincipalFinal();
			if(Constantes.UNO_STRING.equals(checkeo)) {
				setBoolCheckEventoAnexo(Constantes.TRUE);
				actualizarBoolean();
			}else {
				setBoolCheckEventoAnexo(Constantes.FALSE);
				actualizarBoolean();
			}
		}
		
		logger.info("[FIN:] Metodo : editEventoPrincipal");
	}
	
	public void editEventoSecundario() {
		logger.info("[INICIO:] Metodo : editEventoSecundario");
		IafasEventoPrincipalDao objDao = new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn  = new IafasEventoPrincipal();
		String evtSecundarioCodigoEdit = beanEveSec.getVeventoPrincipalCodigo();
		int ntareaPtalEdit = beanEvp.getNtareaPresupuestalCodigo();
		
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(ntareaPtalEdit);
		objBn.setVeventoPrincipalCodigo(evtSecundarioCodigoEdit);
		
		List<IafasEventoPrincipal> lst = objDao.editEventoSecundario(objBn);
		
		for(IafasEventoPrincipal obj : lst) {
			setCodigoEventoSecundario(obj.getVeventoPrincipalCodigo());
			setNombreEventoSecundario(obj.getVeventoPrincipalNombre());
			setModo(Constantes.MODE_ACTUALIZACION);
		}
		
		logger.info("[FIN:] Metodo : editEventoSecundario");
	}
	
	public void editEventoFinal() {
		logger.info("[INICIO:] Metodo : editEventoFinal");
		IafasEventoFinalDao objDao = new IafasEventoFinalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoFinal objBn  = new IafasEventoFinal();
		String evtPrincipalCodigoEdit = beanEveFinal.getVeventoPrincipalCodigo();
		int ntareaPtalEdit = beanEveFinal.getNtareaPresupuestalCodigo();
		int evtFinalCodigoEdit = beanEveFinal.getNeventoFinalCodigo();
		
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(ntareaPtalEdit);
		objBn.setVeventoPrincipalCodigo(evtPrincipalCodigoEdit);
		objBn.setNeventoFinalCodigo(evtFinalCodigoEdit);
		
		List<IafasEventoFinal> lst = objDao.editEventoFinal(objBn);
		
		for(IafasEventoFinal obj : lst) {
			setNombreEventoFinal(obj.getVeventoFinalNombre());
			setNumeroOrden(obj.getNeventoFinalPrioridad());
			setCantidadeventoFinal(obj.getNeventoFinalMetaFisica());
			setModo(Constantes.MODE_ACTUALIZACION);
			setNeventoFinalCodigo(obj.getNeventoFinalCodigo());
		}
		
		logger.info("[FIN:] Metodo : editEventoFinal");
	}
	
	/*ANULAR REGISTROS*/
	public void anularEventoPrincipal() {
		logger.info("[INICIO:] Metodo : anularEventoPrincipal");
		IafasEventoPrincipalDao objDao = new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn  = new IafasEventoPrincipal();
		Response response = new Response();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(beanEvp.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalCodigo(beanEvp.getVeventoPrincipalCodigo());
		objBn.setMode(Constantes.MODE_ELIMINACION_LOGICA);
		try {
			response = objDao.mantenimientoEventoPrincipal(objBn);
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				buscarCabeceraEP();
				PrimeFaces.current().ajax().update("frm_EventoPrincipal:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
				typeMessages, messages, Constantes.METODO_JS_CNV);
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {
			logger.info("[FIN:] Metodo : anularEventoPrincipal");
		}	
	}
	
	public void anularEventoSecundario() {
		logger.info("[INICIO:] Metodo : anularEventoSecundario");
		IafasEventoPrincipalDao objDao = new IafasEventoPrincipalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoPrincipal objBn  = new IafasEventoPrincipal();
		Response response = new Response();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(beanEveSec.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalCodigo(beanEveSec.getVeventoPrincipalCodigo());
		objBn.setMode(Constantes.MODE_ELIMINACION_LOGICA);
		try {
			response = objDao.mantenimientoEventoPrincipal(objBn);
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				buscarCabeceraEvtSecundario();
				PrimeFaces.current().ajax().update("frm_EventoSecundarios:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
				typeMessages, messages, Constantes.METODO_JS_CNV);
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {
			logger.info("[FIN:] Metodo : anularEventoSecundario");
		}	
	}
	
	public void anularEventoFinal() {
		logger.info("[INICIO:] Metodo : anularEventoFinal");
		IafasEventoFinalDao objDao = new IafasEventoFinalDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasEventoFinal objBn  = new IafasEventoFinal();
		Response response = new Response();
		objBn.setCperiodoCodigo(cperiodo);
		objBn.setNfuenteFinanciamientoCodigo(Integer.parseInt(fteFinanc));
		objBn.setNtareaPresupuestalCodigo(beanEveFinal.getNtareaPresupuestalCodigo());
		objBn.setVeventoPrincipalCodigo(beanEveFinal.getVeventoPrincipalCodigo());
		objBn.setNeventoFinalCodigo(beanEveFinal.getNeventoFinalCodigo());
		objBn.setMode(Constantes.MODE_ELIMINACION_LOGICA);
		try {
			response = objDao.mantenimientoEventoFinal(objBn);
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				buscarCabeceraEvtFinal();
				PrimeFaces.current().ajax().update("frm_EventoFinal:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
						typeMessages, messages, Constantes.METODO_JS_CNV);
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {
			logger.info("[FIN:] Metodo : anularEventoFinal");
		}	
	}
	
	/*MENSAJES HACIA EL FRONT-END*/
	
	public void refreshMessage() {
		setTypeMessages(typeMessages);
		setMessages(messages);
	}

	public boolean validacionesEventoPrincipal() {
		boolean pasoValidacion = false;
		if (nombreEvento.equals(Constantes.VACIO)) {
			typeMessages = Constantes.DOS_INT;
			messages = Constantes.MESSAGE_VALIDACION_CAMPOS_NULOS;
			PrimeFaces.current().ajax().update("frm_EventoPrincipal:pnl_messages");
			refreshMessage();
			Mensajeria.showMessages(typeMessages,
					messages, typeMessages, messages, Constantes.METODO_JS_CNV);
			pasoValidacion = true;
		} 
		
		return pasoValidacion;
	}
	
	public boolean validacionesEventoSecundario() {
		boolean pasoValidacion = false;
		if (nombreEventoSecundario.equals(Constantes.VACIO)) {
			typeMessages = Constantes.DOS_INT;
			messages = Constantes.MESSAGE_VALIDACION_CAMPOS_NULOS;
			PrimeFaces.current().ajax().update("frm_EventoSecundarios:pnl_messages");
			refreshMessage();
			Mensajeria.showMessages(typeMessages,
					messages, typeMessages, messages, Constantes.METODO_JS_CNV);
			pasoValidacion = true;
		} 
		
		return pasoValidacion;
	}
	
	public boolean validacionesEventoFinal() {
		boolean pasoValidacion = false;
		if (Constantes.VACIO.equals(nombreEventoFinal)) {
			typeMessages = Constantes.DOS_INT;
			messages = Constantes.MESSAGE_VALIDACION_CAMPOS_NULOS;
			PrimeFaces.current().ajax().update("frm_EventoFinal:pnl_messages");
			refreshMessage();
			Mensajeria.showMessages(typeMessages, messages, typeMessages, messages, Constantes.METODO_JS_CNV);
			pasoValidacion = true;
		}

		return pasoValidacion;
	}
	
	
	public boolean validacionesCNV() {
		boolean pasoValidacion = false;
		if (descripcionItem.equals(Constantes.VACIO) || Constantes.CERO_STRING.equals(codCla)
				|| lblDescripcionUnidadMedida == null || lblDescripcionUnidadMedida.length() == 0) {
			typeMessages = Constantes.DOS_INT;
			messages = Constantes.MESSAGE_VALIDACION_CAMPOS_NULOS;
			PrimeFaces.current().ajax().update("frm_MantCNV:pnl_messages");
			refreshMessage();
			Mensajeria.showMessages(typeMessages, messages, typeMessages, messages, Constantes.METODO_JS_CNV);
			pasoValidacion = true;
		}
		
		if (total.compareTo(montoCadenaGasto)==Constantes.UNO_INT) {
			typeMessages = Constantes.DOS_INT;
			messages = Constantes.MESSAGE_VALIDACION_TOTAL_EXCEDE_AL_SALDO;
			PrimeFaces.current().ajax().update("frm_MantCNV:pnl_messages");
			refreshMessage();
			Mensajeria.showMessages(typeMessages, messages, typeMessages, messages, Constantes.METODO_JS_CNV);
			pasoValidacion = true;
		}
		
		return pasoValidacion;
	}
	
	public void calcularTotal() {
		setTotal((this.cantidad ).multiply(this.valorReferencial));
	}
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
}
