package ep.mil.pe.iafas.administrativo.girado.controller;

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

import ep.mil.pe.iafas.administrativo.devengado.dao.iafasDevengadoDao;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasComprobanteRetencion;
import ep.mil.pe.iafas.administrativo.girado.dao.IafasEntidadesCuentasDao;
import ep.mil.pe.iafas.administrativo.girado.dao.IafasGiradoDao;
import ep.mil.pe.iafas.administrativo.girado.dao.IafasMovimientoCadenasDao;
import ep.mil.pe.iafas.administrativo.girado.model.IafasEntidadesCuentas;
import ep.mil.pe.iafas.administrativo.girado.model.IafasGirado;
import ep.mil.pe.iafas.administrativo.girado.model.IafasMovimientoCadenas;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@ManagedBean(name = "iafasGiradoController")
@SessionScoped
public class IafasGiradoController implements Serializable{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(IafasGiradoController.class.getName());
	
	private String vano;
	private String vregSiaf;
	private String vglosa;
	private BigDecimal impMonSol;
	private String vcci;
	private String cproveedorCuentaBanco;
	private String nombreBanco;
	private String vruc;
	private List<IafasGirado> listaPorGirar = new ArrayList<IafasGirado>();
	private List<IafasGirado> listaGirados = new ArrayList<IafasGirado>();
	private List<IafasMovimientoCadenas> listaPorGirarCadenas = new ArrayList<IafasMovimientoCadenas>();
	private List<IafasMovimientoCadenas> listaGiradasCadenas = new ArrayList<IafasMovimientoCadenas>();
	private List<IafasComprobanteRetencion> listaRetencionesPorGirar = new ArrayList<IafasComprobanteRetencion>();
	private BigDecimal ntipCam;
	private String entidadGiro;
	private String tipoGiro;
	private String tipMon;
	private String msgSP;
	private String msgValidacion;
	public boolean pasoValidacion = true;
	public boolean muestraBotonAnular = false;
	private String descProveedor;
	private String simboloMondea;
	private String mensajeModal;
	private String tipoInsercion =Constantes.GIRO_TOTAL ;
	
	private int typeMessages = Constantes.UNO_NEGATIVO;
	private String messages = Constantes.MESSAGE_VALIDACION_PARAMETROS;
	private String cuentaGiro;
	private ArrayList<SelectItem> cuentaEntidadGiro;
	private BigDecimal montoTecho;
	private BigDecimal montoSaldo;
	/*booleanos para mostrar objetos*/
	private boolean muestraCheckGiro = false;
	private boolean muestraBotonGiroTodo = false;
	private boolean muestraGiroCadenas = false;
	private boolean muestraGiroRetenciones = false;
	private boolean valueCheckGiro = false;
	
	public IafasGiradoController() {
		//buscarGirados();
	}
	
	public ArrayList<SelectItem>  cargarCuentasEntidades(){
		logger.info("[INICIO:] Metodo : cargarCuentasEntidades");
		
		this.cuentaEntidadGiro = new ArrayList<>();
		
		IafasEntidadesCuentas objBn =  new IafasEntidadesCuentas();
		objBn.setCodigoEntidad(entidadGiro);
		IafasEntidadesCuentasDao objDao =  new IafasEntidadesCuentasDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasEntidadesCuentas> lsts = objDao.cargarCuentasPorEntidades(objBn);
		
		for(IafasEntidadesCuentas obj : lsts) {
			this.cuentaEntidadGiro.add(
					new SelectItem(obj.getCentidadCuenta(), obj.getDescripcionTipoCuenta()+":"+ obj.getCentidadCuenta()));
		}
		
		logger.info("[FIN:] Metodo : cargarCuentasEntidades");
		return this.cuentaEntidadGiro;
	}
	
	public List<String> completeText(String query) {

		logger.info("[INICIO:] Metodo : completeText");
		String queryLowerCase = query.toLowerCase();
		List<String> cuentasList = new ArrayList<>();
		IafasEntidadesCuentas objBn = new IafasEntidadesCuentas();
		objBn.setCodigoEntidad(entidadGiro);
		IafasEntidadesCuentasDao objDao = new IafasEntidadesCuentasDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasEntidadesCuentas> lsts = objDao.cargarCuentasPorEntidades(objBn);
		for (IafasEntidadesCuentas obj : lsts) {
			cuentaGiro = obj.getCentidadCuenta();
			cuentasList.add(cuentaGiro);
		}
		logger.info("[FIN:] Metodo : completeText");
		return cuentasList
				.stream()
				.filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}
	
	public void cargarMontosCuenta() {
		logger.info("[INICIO:] Metodo : cargarMontosCuenta");
		IafasEntidadesCuentas objBn = new IafasEntidadesCuentas();
		objBn.setCodigoEntidad(entidadGiro);
		objBn.setCentidadCuenta(cuentaGiro);
		IafasEntidadesCuentasDao objDao = new IafasEntidadesCuentasDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasEntidadesCuentas> lst = objDao.cargarMontosCuenta(objBn);
		
		for(IafasEntidadesCuentas obj : lst) {
			setMontoTecho(obj.getNmontoCuentaTecho());
			setMontoSaldo(obj.getNmontoCuentaSaldo());
		}
		
		logger.info("[FIN:] Metodo : cargarMontosCuenta");
	}
	
	public List<IafasGirado> buscarGirados() {

		logger.info("[INICIO:] Metodo : buscarGirados");

		this.listaGirados = new ArrayList<>();
		if (this.listaGirados != null)
			this.listaGirados.clear();
		
		HttpSession session=null; 
 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		IafasGiradoDao giradoDao = new IafasGiradoDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasGirado> lstGirados = giradoDao.obtenerExpedientesGirados(codUsu);
		
		if (lstGirados.size() > 0) {
			for (IafasGirado objBeanLista : lstGirados) {
				this.listaGirados.add(objBeanLista);
			}
		}

		logger.info("[FIN:] Metodo : buscarGirados");
		return listaGirados;
	}
	
	
	public List<IafasGirado> buscarPorSiafAno() {

		logger.info("[INICIO:] Metodo : buscarPorSiafAno");

		this.listaPorGirar = new ArrayList<>();
		if (this.listaPorGirar != null)
			this.listaPorGirar.clear();

		IafasGiradoDao giradoDao = new IafasGiradoDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasGirado objBn = new IafasGirado();
		objBn.setVano(vano);
		objBn.setVregSiaf(vregSiaf);
		List<IafasGirado> lstporGirar = giradoDao.TraerDatosFaseDevengado(objBn);
		if (lstporGirar.size() > 0) {
			for (IafasGirado objBeanLista : lstporGirar) {
				setVglosa(objBeanLista.getVglosa().toUpperCase());
				setImpMonSol(objBeanLista.getImpMonSol());
				setVruc(objBeanLista.getVruc());
				setVcci(objBeanLista.getVcci());
				setCproveedorCuentaBanco(objBeanLista.getCproveedorCuentaBanco());
				setNtipCam(objBeanLista.getNtipCam());
				setDescProveedor(Constantes.DOS_PUNTOS.concat(objBeanLista.getDescProveedor()));
				setSimboloMondea(objBeanLista.getSimboloMondea());
				setNombreBanco(objBeanLista.getDescBanco());
				setMuestraCheckGiro(true);
				setValueCheckGiro(false);
				this.listaPorGirar.add(objBeanLista);
			}
			obtenerCadenasPorGirar();
			obtenerRetencionesparaGiro();
		} else {
			logger.info("en el else de bsuquedas");
			obtenerCadenasPorGirar();
			obtenerRetencionesparaGiro();
			setMuestraCheckGiro(false);
			setValueCheckGiro(false);
			setMuestraGiroCadenas(false);
			setMuestraGiroRetenciones(false);
			setVglosa(Constantes.VACIO);
			setVruc(Constantes.VACIO);
			setVcci(Constantes.VACIO);
			setCproveedorCuentaBanco(Constantes.VACIO);
			setImpMonSol(null);
			setNombreBanco(Constantes.VACIO);
			setSimboloMondea(Constantes.VACIO);
			setDescProveedor(Constantes.VACIO);
			this.typeMessages = Constantes.UNO_NEGATIVO;
			this.messages = Constantes.MESSAGE_VALIDACION_BUSQUEDA_GIRO;
			PrimeFaces.current().ajax().update("frm_Girado:pnl_messages");
			refreshMessage();
			showMessages(typeMessages,messages );
		}

		logger.info("[FIN:] Metodo : buscarPorSiafAno");
		return listaPorGirar;
	}
	
	private void obtenerCadenasPorGirar() {
		logger.info("[INICIO:] Metodo : obtenerCadenasPorGirar");

		this.listaPorGirarCadenas = new ArrayList<>();
		if (this.listaPorGirarCadenas != null)
			this.listaPorGirarCadenas.clear();

		IafasMovimientoCadenasDao objDao = new IafasMovimientoCadenasDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasMovimientoCadenas objBn = new IafasMovimientoCadenas();
		objBn.setVano(vano);
		objBn.setVregSiaf(vregSiaf);

		List<IafasMovimientoCadenas> lsts = objDao.obtenerCadenasPorGirar(objBn);

		if (lsts.size() > 0) {
			setMuestraGiroCadenas(true);
			for (IafasMovimientoCadenas obj : lsts) {
				listaPorGirarCadenas.add(obj);
			}
		}
		logger.info("[FIN:] Metodo : obtenerCadenasPorGirar");
	}
	
	public void obtenerCadenasGiradas() {
		logger.info("[INICIO:] Metodo : obtenerCadenasPorGirar");

		String vanoDet = (String) extContext().getRequestParameterMap().get("vano");
		String vregSiafDet = (String) extContext().getRequestParameterMap().get("vregSiaf");
		String vsecuenciaDet = (String) extContext().getRequestParameterMap().get("vsecuencia");
		String vsecuenciaIntDet = (String) extContext().getRequestParameterMap().get("vsecuenciaInt");
		
		this.listaGiradasCadenas = new ArrayList<>();
		if (this.listaGiradasCadenas != null)
			this.listaGiradasCadenas.clear();

		IafasMovimientoCadenasDao objDao = new IafasMovimientoCadenasDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasMovimientoCadenas objBn = new IafasMovimientoCadenas();
		objBn.setVano(vanoDet);
		objBn.setVregSiaf(vregSiafDet);
		objBn.setVsecuencia(vsecuenciaDet);
		objBn.setVsecuenciaInt(vsecuenciaIntDet);
		
		List<IafasMovimientoCadenas> lsts = objDao.obtenerCadenasGiradas(objBn);

		if (lsts.size() > 0 || lsts != null) {
			for (IafasMovimientoCadenas obj : lsts) {
				listaGiradasCadenas.add(obj);
			}
		}
		logger.info("[FIN:] Metodo : obtenerCadenasPorGirar");
	}
	
	public void obtenerRetencionesparaGiro() {
		logger.info("[INICIO:] Metodo : obtenerRetencionesparaGiro");

		this.listaRetencionesPorGirar = new ArrayList<>();
		if (this.listaRetencionesPorGirar != null)
			this.listaRetencionesPorGirar.clear();

		iafasDevengadoDao objDao = new iafasDevengadoDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasComprobanteRetencion objBn = new IafasComprobanteRetencion();
		objBn.setVano(vano);
		objBn.setVexpediente(vregSiaf);
				
		List<IafasComprobanteRetencion> lsts = objDao.obtenerRetencionesparaGiro(objBn);

		if ( lsts != null ) {
			setMuestraGiroRetenciones(true);
			for (IafasComprobanteRetencion obj : lsts) {
				listaRetencionesPorGirar.add(obj);
			}
		}
		logger.info("[FIN:] Metodo : obtenerRetencionesparaGiro");
		
	}
	
	public void insRegistroGiradoCab() {
		logger.info("[INICIO:] Metodo : insRegistroGiradoCab:::");
		
		IafasGiradoDao giradoDao = new IafasGiradoDao(MySQLSessionFactory.getSqlSessionFactory());
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		Response response = new Response();
		IafasGirado objBn = new IafasGirado();
		objBn.setVano(vano);
		objBn.setVregSiaf(vregSiaf);
		objBn.setVctaCodigo(cuentaGiro);
		objBn.setVcodTipGiro(tipoGiro);
		objBn.setVglosa(vglosa);
		objBn.setImpMonSol(impMonSol);
		objBn.setMode(Constantes.MODE_REGISTRO);
		objBn.setVruc(vruc);
		objBn.setNtipCam(ntipCam);
		objBn.setVtipMon(tipMon);
		objBn.setVusuarioIng(codUsu);
		objBn.setTipoInsercion(tipoInsercion);
		objBn.setBancodBco(entidadGiro); 
		
		try {
			if(!validacionesGiro()){
				response = giradoDao.mantenimientoGiradoCab(objBn);
				if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
					this.typeMessages = Constantes.CERO_INT;
					this.messages = response.getMensajeRespuesta();
					PrimeFaces.current().ajax().update("frm_Girado:pnl_messages");
					refreshMessage();
					showMessages(typeMessages, messages);
				}
			}

		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : insRegistroGiradoCab");
		}
	}
	
	public void refreshMessage() {
		setTypeMessages(typeMessages);
		setMessages(messages);
	}
	
	public void anularGirado() {

		logger.info("[INICIO:] Metodo : anularGirado:::");
		IafasGiradoDao giradoDao = new IafasGiradoDao(MySQLSessionFactory.getSqlSessionFactory());
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		Response response = new Response();
		String vanoAnu = (String) extContext().getRequestParameterMap().get("vanoAnu");
		String vsecuenciaAnu = (String) extContext().getRequestParameterMap().get("vsecuenciaAnu");
		String vsecuenciaIntAnu = (String) extContext().getRequestParameterMap().get("vsecuenciaIntAnu");
		IafasGirado objBn = new IafasGirado();
		objBn.setVano(vanoAnu);
		objBn.setVsecuencia(vsecuenciaAnu);
		objBn.setVsecuenciaInt(vsecuenciaIntAnu);
		objBn.setMode(Constantes.MODE_ANULACION);
		objBn.setVusuarioIng(codUsu);

		try {
			response = giradoDao.mantenimientoGiradoCab(objBn);
			logger.info("respueesta de anulacion:.::"+response.getCodigoRespuesta());
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
				this.typeMessages = Constantes.CERO_INT;
				this.messages = response.getMensajeRespuesta();
				PrimeFaces.current().ajax().update("frm_ConsultaGiros:pnl_messages");
				refreshMessage();
				showMessages(typeMessages, messages);
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : anularGirado");
		}

	}

	public String retornar() {
		limpiarcampos();
		return "mainIafasGirado.xhtml";
	}
	
	private void limpiarcampos() {
		logger.info("[INICIO:] Metodo : limpiarcampos");
		setVregSiaf(Constantes.VACIO);
		setMuestraCheckGiro(false);
		setVglosa(Constantes.VACIO);
		setVruc(Constantes.VACIO);
		setVcci(Constantes.VACIO);
		setCproveedorCuentaBanco(Constantes.VACIO);
		setImpMonSol(null);
		setMsgSP(Constantes.VACIO);
		setSimboloMondea(Constantes.VACIO);
		setDescProveedor(Constantes.VACIO);
		obtenerCadenasPorGirar();
		obtenerRetencionesparaGiro();
		setMuestraCheckGiro(false);
		setValueCheckGiro(false);
		setMuestraGiroCadenas(false);
		setMuestraGiroRetenciones(false);
		setEntidadGiro(Constantes.VACIO);
		setCuentaGiro(Constantes.VACIO);
		setMuestraBotonGiroTodo(false);
		logger.info("[FIN:] Metodo : limpiarcampos");
	}
	
	public void retornoModal(String opcion) {
		logger.info("[INICIO:] Metodo : retornoModal");
		if(Constantes.VALOR_R.equals(opcion)) {
			mensajeModal = "RETENCIONES";
			tipoInsercion =  Constantes.GIRO_RETENCION;
		}
		else {
			mensajeModal = "CLASIFICADORES";
			tipoInsercion =  Constantes.GIRO_CLASIFICADOR;
		}
		logger.info("[FIN:] Metodo : retornoModal");
	}
	
	public void actualizarBoolean() {
		logger.info("[INICIO:] Metodo : actualizarBoolean");
		boolean value = valueCheckGiro ? true : false;
		if (value) {
			muestraGiroCadenas = false;
			muestraGiroRetenciones = false;
			muestraBotonGiroTodo = true;
		} else {
			obtenerCadenasPorGirar();
			obtenerRetencionesparaGiro();
			muestraBotonGiroTodo = false;
		}

		logger.info("[FIN:] Metodo : actualizarBoolean");
	}
	
	private String showMessages(int opcion, String mensaje) {

		logger.info("[INICIO:] Metodo : showMessages:::");
		
		switch (opcion) {
		
		case Constantes.UNO_NEGATIVO:
			typeMessages = Constantes.UNO_NEGATIVO;
			messages = mensaje;
			PrimeFaces.current().executeScript("verMensajes()");
			break;
		
		case Constantes.CERO_INT:
			typeMessages = Constantes.CERO_INT;
			messages = mensaje;
			PrimeFaces.current().executeScript("verMensajes()");
			break;
			
		case Constantes.UNO_INT:
			typeMessages = Constantes.UNO_INT;
			messages = mensaje;
			PrimeFaces.current().executeScript("verMensajes()");
			break;
			
		case Constantes.DOS_INT:
			typeMessages = Constantes.DOS_INT;
			messages = mensaje;
			PrimeFaces.current().executeScript("verMensajes()");
			break;
			
		case Constantes.TRES_INT:
			typeMessages = Constantes.TRES_INT;
			messages = mensaje;
			PrimeFaces.current().executeScript("verMensajes()");
			break;
			
		default:
			typeMessages = Constantes.TRES_INT;
			messages = mensaje;
			break;
		}
		
		logger.info("[FIN:] Metodo : showMessages:::");
		return messages;
	}
	
	private boolean validacionesGiro() {
		boolean pasoGrabar = false;
		logger.info(" validando monto ::::>"+impMonSol + " - "+montoTecho + " - "+montoSaldo + " - "+tipMon + " - "+entidadGiro +" - "+cuentaGiro);
		if (Constantes.CERO_STRING.equals(entidadGiro)) {
			this.typeMessages = Constantes.DOS_INT;
			this.messages = Constantes.MESSAGE_VALIDACION_ENTIDAD_GIRO;
			PrimeFaces.current().ajax().update("frm_Girado:pnl_messages");
			refreshMessage();
			showMessages(typeMessages, messages);
			return pasoGrabar = true;
		} else if (Constantes.CERO_STRING.equals(cuentaGiro)) {
			this.typeMessages = Constantes.DOS_INT;
			this.messages = Constantes.MESSAGE_VALIDACION_CUENTA_GIRO;
			PrimeFaces.current().ajax().update("frm_Girado:pnl_messages");
			refreshMessage();
			showMessages(typeMessages, messages);
			return pasoGrabar = true;
		} 
		else if (impMonSol.compareTo(montoTecho) == Constantes.UNO_INT) {
			this.typeMessages = Constantes.DOS_INT;
			this.messages = Constantes.MESSAGE_VALIDACION_MONTO_GIRO;
			PrimeFaces.current().ajax().update("frm_Girado:pnl_messages");
			refreshMessage();
			showMessages(typeMessages, messages);
			return pasoGrabar = true;
		} else if (impMonSol.compareTo(montoSaldo) == Constantes.UNO_INT) {
			this.typeMessages = Constantes.DOS_INT;
			this.messages = Constantes.MESSAGE_VALIDACION_SALDO_GIRO;
			PrimeFaces.current().ajax().update("frm_Girado:pnl_messages");
			refreshMessage();
			showMessages(typeMessages, messages);
			pasoGrabar = true;
		}
		return pasoGrabar;
	}
	
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
}
