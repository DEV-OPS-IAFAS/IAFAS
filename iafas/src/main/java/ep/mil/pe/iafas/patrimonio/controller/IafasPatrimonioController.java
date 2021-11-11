package ep.mil.pe.iafas.patrimonio.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Mensajeria;
import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.patrimonio.dao.IafasPatrimonioActaDao;
import ep.mil.pe.iafas.patrimonio.dao.IafasPatrimonioDao;
import ep.mil.pe.iafas.patrimonio.dao.IafasPatrimonioDetalleActaDao;
import ep.mil.pe.iafas.patrimonio.dao.IafasPersonalContratoDao;
import ep.mil.pe.iafas.patrimonio.model.IafasPatrimonio;
import ep.mil.pe.iafas.patrimonio.model.IafasPatrimonioActa;
import ep.mil.pe.iafas.patrimonio.model.IafasPatrimonioActaDetalle;
import ep.mil.pe.iafas.patrimonio.model.IafasPersonalContrato;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;

@Setter
@Getter
@ManagedBean(name = "iafasPatrimonioController")
@SessionScoped
public class IafasPatrimonioController {
	private static final Logger logger = Logger.getLogger(IafasPatrimonioController.class.getName());
	private String ano;
	private List<SelectItem> tipoMateriales;
	private List<SelectItem> marcas;
	private List<SelectItem> colores;
	private List<SelectItem> categorias;
	private List<IafasPatrimonio> listaCabecera = new ArrayList<IafasPatrimonio>();
	private int typeMessages = Constantes.UNO_NEGATIVO;
	private String messages = Constantes.MESSAGE_VALIDACION_PARAMETROS;
	private IafasPatrimonio bean;
	private int nCodePatrimonio;
	private int tipoMaterial;
	private int categoria;
	private int marca;
	private String color;
	private String descripcionPatrimonio;
	private String seriePatrimonio;
	private String dimensionPatrimonio;
	private String observacion;
	private String codigoPatrimonio;
	private String modeloPatrimonio;
	private String mode = Constantes.MODE_REGISTRO;
	private List<IafasPatrimonioActa> listaCabeceraActa = new ArrayList<IafasPatrimonioActa>();
	private int tipoActa;
	private IafasPatrimonioActa beanActa;
	private String observacionActa;
	private int codigoPresidente;
	private int codigoVocal;
	private int codigoSecretario;
	private String descripcionPresidente;
	private String descripcionVocal;
	private String descripcionSecretario;
	private Date fechaActa;
	private int nCodigoPatrimonioActa = Constantes.CERO_INT;
	private String observacionActaDetalle;
	private String ubicacionDetalle;
	private int codigoPatrimonioDetalle;
	private List<IafasPatrimonioActaDetalle> listaDetalleActa = new ArrayList<IafasPatrimonioActaDetalle>();

	public IafasPatrimonioController() {
		buscarCabecera();
	}
	
	public List<IafasPatrimonio> buscarCabecera() {

		this.listaCabecera = new ArrayList<>();
		if (this.listaCabecera != null)
			this.listaCabecera.clear();

		IafasPatrimonioDao objDao = new IafasPatrimonioDao(MySQLSessionFactory.getSqlSessionFactory());

		List<IafasPatrimonio> lstCab = objDao.obtenerPatrimonioCabecera();

		if (lstCab.size() > 0) {
			for (IafasPatrimonio objBn : lstCab) {
				this.listaCabecera.add(objBn);
			}
		}

		return listaCabecera;
	}

	public List<IafasPatrimonioActa> buscarCabeceraActa() {

		this.listaCabeceraActa = new ArrayList<>();
		if (this.listaCabeceraActa != null)
			this.listaCabeceraActa.clear();
		IafasPatrimonioActa obj = new IafasPatrimonioActa();
		obj.setCPeriodoCodigo(ano);
		obj.setNPatrimonioTipoActaCodigo(tipoActa);
		IafasPatrimonioActaDao objDao = new IafasPatrimonioActaDao(MySQLSessionFactory.getSqlSessionFactory());

		List<IafasPatrimonioActa> lstCab = objDao.obtenerPatrimonioCabeceraActa(obj);

		if (lstCab.size() > 0) {
			for (IafasPatrimonioActa objBn : lstCab) {
				this.listaCabeceraActa.add(objBn);
			}
		}

		return listaCabeceraActa;
	}
	
	public void refreshMessage() {
		setTypeMessages(typeMessages);
		setMessages(messages);
	}

	public void limpiarCamposMto() {
		setCodigoPatrimonio(Constantes.VACIO);
		setTipoMaterial(Constantes.CERO_INT);
		setCategoria(Constantes.CERO_INT);
		setMarca(Constantes.CERO_INT);
		setColor(Constantes.CERO_STRING);
		setDescripcionPatrimonio(Constantes.VACIO);
		setObservacion(Constantes.VACIO);
		setModeloPatrimonio(Constantes.VACIO);
		setSeriePatrimonio(Constantes.VACIO);
		setDimensionPatrimonio(Constantes.VACIO);
	}
	
	public void limpiarCamposMtoActas() {
		setDescripcionPresidente(Constantes.VACIO);
		setDescripcionSecretario(Constantes.VACIO);
		setDescripcionVocal(Constantes.VACIO);
		setObservacionActa(Constantes.VACIO);
		setCodigoPresidente(Constantes.CERO_INT);
		setCodigoVocal(Constantes.CERO_INT);
		setCodigoSecretario(Constantes.CERO_INT);
	}
	
	public void nuevo() {
		logger.info("[INICIO:] Metodo : nuevo:::");
		limpiarCamposMto();
		logger.info("[FIN:] Metodo : nuevo:::");
	}
	
	public void nuevoActas() {
		logger.info("[INICIO:] Metodo : nuevoActas:::");
		limpiarCamposMtoActas();
		logger.info("[FIN:] Metodo : nuevoActas:::");
	}
	public void editarPatrimonio() {
		logger.info("[INICIO:] Metodo : editarPatrimonio");
		IafasPatrimonioDao objDao = new IafasPatrimonioDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasPatrimonio objBn  = new IafasPatrimonio();
		int codigoPatrimonio = bean.getNPatrimonioCodigo();
		objBn.setNPatrimonioCodigo(codigoPatrimonio);
		setNCodePatrimonio(codigoPatrimonio);
		logger.info("codigoPatrimonio : "+codigoPatrimonio);
		List<IafasPatrimonio> lst = objDao.editarPatrimonio(objBn);
		
		for(IafasPatrimonio obj : lst) {
			setCodigoPatrimonio(obj.getVPatrimonioCodigo());
			setTipoMaterial(obj.getNTipoMaterialCodigo());
			setCategoria(obj.getCPatrimonioCategoriaCodigo());
			setMarca(obj.getNMarcaCodigo());
			setColor(obj.getVPtarimonioColorCodigo());
			setDescripcionPatrimonio(obj.getVPatrimonioDescripcion());
			setObservacion(obj.getVPatrimonioObservacion());
			setModeloPatrimonio(obj.getVPatrimonioModelo());
			setSeriePatrimonio(obj.getVpatrimonioSerie());
			setDimensionPatrimonio(obj.getVPatriminioDimensiones());
		}
		setMode(Constantes.MODE_ACTUALIZACION);
		logger.info("[FIN:] Metodo : editarPatrimonio");
	}
	
	public void insRegistroCab() {
		logger.info("[INICIO:] Metodo : insRegistroCab");
		IafasPatrimonioDao objDao = new IafasPatrimonioDao(MySQLSessionFactory.getSqlSessionFactory());
		Response response = new Response();
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		IafasPatrimonio objBn = new IafasPatrimonio();
		logger.info("codigoPatrimonio en insert: "+nCodePatrimonio);
		objBn.setNPatrimonioCodigo(nCodePatrimonio);
		objBn.setVPatrimonioCodigo(codigoPatrimonio);
		objBn.setVPatrimonioDescripcion(descripcionPatrimonio);
		objBn.setNMarcaCodigo(marca);
		objBn.setVPatrimonioModelo(modeloPatrimonio);
		objBn.setVPtarimonioColorCodigo(color);
		objBn.setNTipoMaterialCodigo(tipoMaterial);
		objBn.setCPatrimonioCategoriaCodigo(categoria);
		objBn.setVpatrimonioSerie(seriePatrimonio);
		objBn.setVPatriminioDimensiones(dimensionPatrimonio);
		objBn.setVPatrimonioObservacion(observacion);
		objBn.setVpatrimonioTipo("1");
		objBn.setVUsuarioCodigo(codUsu);
		objBn.setTipo(mode);
		
		try {
			response = objDao.SP_IDU_PATRIMONIO(objBn);

			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {

				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				buscarCabecera();
				PrimeFaces.current().ajax().update("frm_Patrimonio:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
				typeMessages, messages, Constantes.METODO_JS_CNV);
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {
			setMode(Constantes.MODE_REGISTRO);
			logger.info("[FIN:] Metodo : insRegistroCab");
			limpiarCamposMto();
		}
	}
	
		
	public List<String> completeTextPresidente(String query) {

		logger.info("[INICIO:] Metodo : completeTextPresidente");
		String queryLowerCase = query.toLowerCase();
		List<String> personaList = new ArrayList<>();
		IafasPersonalContratoDao objDao = new IafasPersonalContratoDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasPersonalContrato objBn = new IafasPersonalContrato();
		objBn.setCPeriodoCodigo(ano);
		objBn.setNombrePersona(queryLowerCase);
		logger.info("PRESIDENTE BUSCADO:::>"+queryLowerCase);
		List<IafasPersonalContrato> lsts = objDao.obtenerPersonaContratoCodigo(objBn);
		for (IafasPersonalContrato obj : lsts) {
			descripcionPresidente= obj.getNombrePersona();
			codigoPresidente = obj.getNPersonaCodigo();
			personaList.add(descripcionPresidente);
		}
		logger.info("[FIN:] Metodo : completeTextPresidente");
		return personaList
				.stream()
				.filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}

	public List<String> completeTextVocal(String query) {

		logger.info("[INICIO:] Metodo : completeTextVocal");
		String queryLowerCase = query.toLowerCase();
		List<String> personaList = new ArrayList<>();
		IafasPersonalContratoDao objDao = new IafasPersonalContratoDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasPersonalContrato objBn = new IafasPersonalContrato();
		objBn.setCPeriodoCodigo(ano);
		objBn.setNombrePersona(queryLowerCase);
		logger.info("VOCAL BUSCADO:::>"+queryLowerCase);
		List<IafasPersonalContrato> lsts = objDao.obtenerPersonaContratoCodigo(objBn);
		for (IafasPersonalContrato obj : lsts) {
			descripcionVocal= obj.getNombrePersona();
			codigoVocal= obj.getNPersonaCodigo();
			personaList.add(descripcionVocal);
		}
		logger.info("[FIN:] Metodo : completeTextVocal");
		return personaList
				.stream()
				.filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}
	
	public List<String> completeTextSecretario(String query) {

		logger.info("[INICIO:] Metodo : completeTextSecretario");
		String queryLowerCase = query.toLowerCase();
		List<String> personaList = new ArrayList<>();
		IafasPersonalContratoDao objDao = new IafasPersonalContratoDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasPersonalContrato objBn = new IafasPersonalContrato();
		objBn.setCPeriodoCodigo(ano);
		objBn.setNombrePersona(queryLowerCase);
		logger.info("SECRETARIO BUSCADO:::>"+queryLowerCase);
		List<IafasPersonalContrato> lsts = objDao.obtenerPersonaContratoCodigo(objBn);
		for (IafasPersonalContrato obj : lsts) {
			descripcionSecretario= obj.getNombrePersona();
			codigoSecretario= obj.getNPersonaCodigo();
			personaList.add(descripcionSecretario);
		}
		logger.info("[FIN:] Metodo : completeTextSecretario");
		return personaList
				.stream()
				.filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}
	
	public void editarPatrimonioActa() {
		logger.info("[INICIO:] Metodo : editarPatrimonioActa");
		IafasPatrimonioActaDao objDao = new IafasPatrimonioActaDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasPatrimonioActa objBn  = new IafasPatrimonioActa();
		int codigoPatrimonioActa = beanActa.getNPatrimonioActaCodigo();
		objBn.setNPatrimonioActaCodigo(codigoPatrimonioActa);
		objBn.setCPeriodoCodigo(ano);
		List<IafasPatrimonioActa> lst = objDao.obtenerPatrimonioActaEdicion(objBn);
		
		for(IafasPatrimonioActa obj : lst) {
			setNCodigoPatrimonioActa(codigoPatrimonioActa);
			setObservacionActa(obj.getVPatrimonioActaObservacion());
			setCodigoPresidente(obj.getNPersonaPresidente());
			setCodigoSecretario(obj.getNPersonaSecretario());
			setCodigoVocal(obj.getNPersonaVocal());
			setDescripcionPresidente(obj.getNombrePresidente());
			setDescripcionSecretario(obj.getNombreSecretario());
			setDescripcionVocal(obj.getNombreVocal());
			
		}
		setMode(Constantes.MODE_ACTUALIZACION);
		logger.info("[FIN:] Metodo : editarPatrimonioActa");
	}
	
	public void insRegistroActaCab() {
		logger.info("[INICIO:] Metodo : insRegistroActaCab");
		IafasPatrimonioActaDao objDao = new IafasPatrimonioActaDao(MySQLSessionFactory.getSqlSessionFactory());
		Response response = new Response();
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		IafasPatrimonioActa objBn = new IafasPatrimonioActa();

		System.out.println("datos de insercion: "+ano+" - "+tipoActa+" - "+codigoPresidente+" - "+codigoVocal+" - "+codigoSecretario+" - "+mode);
		objBn.setCPeriodoCodigo(ano);
		objBn.setNPatrimonioTipoActaCodigo(tipoActa);
		objBn.setNPatrimonioActaCodigo(nCodigoPatrimonioActa);
		objBn.setNPersonaCodigo(Constantes.UNO_INT);//coordinar con helmer
		objBn.setNPersonalContratoCodigo(Constantes.UNO_INT);//coordinar con helmer
		//objBn.setDPatrimonioActaFecha(fechaActa);
		objBn.setNPersonaPresidente(codigoPresidente);
		objBn.setNPersonaVocal(codigoVocal);
		objBn.setNPersonaSecretario(codigoSecretario);
		objBn.setVPatrimonioActaObservacion(observacionActa);
		objBn.setVUsuarioCodigo(codUsu);
		objBn.setMode(mode);
		
		try {
			response = objDao.SP_IDU_IAFAS_PATRIMONIO_ACTA(objBn);

			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {

				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				buscarCabeceraActa();
				PrimeFaces.current().ajax().update("frm_PatrimonioActas:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
				typeMessages, messages, Constantes.METODO_JS_CNV);
			}
			else {
				this.typeMessages = Constantes.UNO_INT;
				this.messages = response.getMensajeRespuesta();
				PrimeFaces.current().ajax().update("frm_PatrimonioActas:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
				typeMessages, messages, Constantes.METODO_JS_CNV);
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {
			setMode(Constantes.MODE_REGISTRO);
			logger.info("[FIN:] Metodo : insRegistroActaCab");
			limpiarCamposMto();
		}
	}
	
	public List<String> completeTextPatrimonio(String query) {

		logger.info("[INICIO:] Metodo : completeTextPatrimonio");
		String queryLowerCase = query.toLowerCase();
		List<String> patrimonioList = new ArrayList<>();
		IafasPatrimonioDao objDao = new IafasPatrimonioDao(MySQLSessionFactory.getSqlSessionFactory());
		logger.info("PATRIMONIO BUSCADO:::>"+queryLowerCase);
		List<IafasPatrimonio> lsts = objDao.obtenerPatrimonioCompletext(queryLowerCase);
		for (IafasPatrimonio obj : lsts) {
			descripcionPatrimonio= obj.getVPatrimonioDescripcion();
			codigoPatrimonioDetalle= obj.getNPatrimonioCodigo();
			patrimonioList.add(descripcionPatrimonio); 
		}
		logger.info("[FIN:] Metodo : completeTextPatrimonio"); 
		return patrimonioList
				.stream()
				.filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}
	
	public void mostrarPatrimonioActa() {
		logger.info("[INICIO:] Metodo : mostrarPatrimonioActa");
		
		String anoPatrimonioActa = beanActa.getCPeriodoCodigo();
		int personaCodigoActa = beanActa.getNPersonaCodigo();
		int personalContratoCodigo = beanActa.getNPersonalContratoCodigo();
		int tipoActaCodigo = beanActa.getNPatrimonioTipoActaCodigo();
		int codigoActa = beanActa.getNPatrimonioActaCodigo();
		
		buscarDetalleActa(anoPatrimonioActa, personaCodigoActa, personalContratoCodigo, tipoActaCodigo, codigoActa);

		setMode(Constantes.MODE_REGISTRO);
		logger.info("[FIN:] Metodo : mostrarPatrimonioActa");
	}
	
	public List<IafasPatrimonioActaDetalle> buscarDetalleActa(String anoPatrimonioActa, int personaCodigoActa, int personalContratoCodigo,  
			int tipoActaCodigo , int codigoActa) {
		
		logger.info("[INICIO:] Metodo : buscarDetalleActa");
		
		this.listaDetalleActa = new ArrayList<>();
		if (this.listaDetalleActa != null)
			this.listaDetalleActa.clear();

		IafasPatrimonioDetalleActaDao objDao = new IafasPatrimonioDetalleActaDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasPatrimonioActaDetalle objBn  = new IafasPatrimonioActaDetalle();
		
		objBn.setCPeriodoCodigo(anoPatrimonioActa);
		objBn.setNPersonaCodigo(personaCodigoActa);
		objBn.setNPersonalContratoCodigo(personalContratoCodigo);
		objBn.setNPatrimonioTipoActaCodigo(tipoActaCodigo);
		objBn.setNPatrimonioActaCodigo(codigoActa);
		
		System.out.println("DATOS EN BUSCAR DETALLE:::"+anoPatrimonioActa +" - "+personaCodigoActa+" - "+personalContratoCodigo +" - "+tipoActaCodigo+" - "+codigoActa);
		
		List<IafasPatrimonioActaDetalle> lstDet = objDao.obtenerPatrimonioDetalleActa(objBn);

		if (lstDet.size() > 0) {
			for (IafasPatrimonioActaDetalle objBean : lstDet) {
				this.listaDetalleActa.add(objBean);
			}
		}
		logger.info("[FIN:] Metodo : buscarDetalleActa");
		return listaDetalleActa;
	}
	
	
	public void limpiarCamposMtoDetalleActas() {
		setObservacionActaDetalle(Constantes.VACIO);
		setDescripcionPatrimonio(Constantes.VACIO);
		setUbicacionDetalle(Constantes.VACIO);
		//listaDetalleActa.clear();
	}
	
	public void insRegistroActaDet() {
		logger.info("[INICIO:] Metodo : insRegistroActaDet");
		IafasPatrimonioDetalleActaDao objDao = new IafasPatrimonioDetalleActaDao(MySQLSessionFactory.getSqlSessionFactory());
		Response response = new Response();
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		IafasPatrimonioActaDetalle objBn = new IafasPatrimonioActaDetalle();


		String anoPatrimonioActa = beanActa.getCPeriodoCodigo();
		int personaCodigoActa = beanActa.getNPersonaCodigo();
		int personalContratoCodigo = beanActa.getNPersonalContratoCodigo();
		int tipoActaCodigo = beanActa.getNPatrimonioTipoActaCodigo();
		int codigoActa = beanActa.getNPatrimonioActaCodigo();
		System.out.println("DATOS EN INSERCION DETALLE:::"+anoPatrimonioActa +" - "+personaCodigoActa+" - "+personalContratoCodigo +" - "+tipoActaCodigo+" - "+codigoActa);

		objBn.setCPeriodoCodigo(anoPatrimonioActa);
		objBn.setNPersonaCodigo(personaCodigoActa);
		objBn.setNPersonalContratoCodigo(personalContratoCodigo);
		objBn.setNPatrimonioTipoActaCodigo(tipoActaCodigo);
		objBn.setNPatrimonioActaCodigo(codigoActa);
		objBn.setNPatrimonioCodigo(codigoPatrimonioDetalle);
		objBn.setVinternamientoDetalleUbicacion(ubicacionDetalle);
		objBn.setVinternamientoDetalleObservacion(observacionActaDetalle);
		objBn.setVUsuarioCodigo(codUsu);
		objBn.setMode(mode);
		
		try {
			response = objDao.SP_IDU_IAFAS_PATRIMONIO_ACTA_DETALE(objBn);

			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {

				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				buscarDetalleActa(anoPatrimonioActa, personaCodigoActa, personalContratoCodigo,  
						tipoActaCodigo , codigoActa);
				PrimeFaces.current().ajax().update("frm_PatrimonioActas:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
				typeMessages, messages, Constantes.METODO_JS_CNV);
			}
			else {
				this.typeMessages = Constantes.UNO_INT;
				this.messages = response.getMensajeRespuesta();
				PrimeFaces.current().ajax().update("frm_PatrimonioActas:pnl_messages");
				refreshMessage();
				Mensajeria.showMessages(Integer.parseInt(response.getCodigoRespuesta()), response.getMensajeRespuesta(),
				typeMessages, messages, Constantes.METODO_JS_CNV);
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {
			setMode(Constantes.MODE_REGISTRO);
			logger.info("[FIN:] Metodo : insRegistroActaDet");
			limpiarCamposMto();
		}
	}

	public void anularRegistroActaDetalle() {
		logger.info("[INICIO:] Metodo : anularRegistroActaDetalle");
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		Response response = new Response();
		
		String codPeriodo = (String) extContext().getRequestParameterMap().get("codPeriodo");
		String codPersona = (String) extContext().getRequestParameterMap().get("codPersona");
		String codPersonaContrato = (String) extContext().getRequestParameterMap().get("codPersonaContrato");
		String codPatrimonioActa = (String) extContext().getRequestParameterMap().get("codPatrimonioActa");
		String codPatrimonio = (String) extContext().getRequestParameterMap().get("codPatrimonio");
		String codTipoActa = (String) extContext().getRequestParameterMap().get("codTipoActa");
		
		IafasPatrimonioActaDetalle objBn = new IafasPatrimonioActaDetalle();
		IafasPatrimonioDetalleActaDao objDao = new IafasPatrimonioDetalleActaDao(MySQLSessionFactory.getSqlSessionFactory());

		objBn.setCPeriodoCodigo(codPeriodo);
		objBn.setNPersonaCodigo(Integer.parseInt(codPersona));
		objBn.setNPersonalContratoCodigo(Integer.parseInt(codPersonaContrato));
		objBn.setNPatrimonioTipoActaCodigo(Integer.parseInt(codTipoActa));
		objBn.setNPatrimonioActaCodigo(Integer.parseInt(codPatrimonioActa));
		objBn.setNPatrimonioCodigo(Integer.parseInt(codPatrimonio));
		objBn.setVUsuarioCodigo(codUsu);
		objBn.setMode(Constantes.MODE_ELIMINACION_LOGICA);
		
		try {
			response = objDao.SP_IDU_IAFAS_PATRIMONIO_ACTA_DETALE(objBn);

			if (response.getCodigoRespuesta() == Constantes.CERO_STRING) {
				logger.info("La anulación se realizo con exito");
			}

		} catch (Exception e) {
			logger.error("error : " + response.getMensajeRespuesta());
		} finally {
			mostrarPatrimonioActa();
			logger.info("[FIN:] Metodo : anularRegistroActaDetalle");
		}
	}
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
	
}
