package ep.mil.pe.iafas.administrativo.girado.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.administrativo.devengado.dao.iafasDevengadoDao;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasComprobanteRetencion;
import ep.mil.pe.iafas.administrativo.girado.dao.IafasGiradoDao;
import ep.mil.pe.iafas.administrativo.girado.dao.IafasMovimientoCadenasDao;
import ep.mil.pe.iafas.administrativo.girado.model.IafasGirado;
import ep.mil.pe.iafas.administrativo.girado.model.IafasMovimientoCadenas;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

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
	private boolean muestraBotonGiro = false;
	private BigDecimal ntipCam;
	private String ctaCte;
	private String tipoGiro;
	private String tipMon;
	private String msgSP;
	private String msgValidacion;
	public boolean pasoValidacion = true;
	public boolean muestraBotonAnular = false;
	private String descProveedor;
	private String simboloMondea;
	
	public IafasGiradoController() {
		//buscarGirados();
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
				setMuestraBotonGiro(true);
				setNtipCam(objBeanLista.getNtipCam());
				setDescProveedor(objBeanLista.getDescProveedor());
				setSimboloMondea(objBeanLista.getSimboloMondea());
				this.listaPorGirar.add(objBeanLista);
			}
			
		} else {
			setMuestraBotonGiro(false);
			setVglosa(Constantes.VACIO);
			setVruc(Constantes.VACIO);
			setVcci(Constantes.VACIO);
			setCproveedorCuentaBanco(Constantes.VACIO);
			setImpMonSol(null);
		}
		obtenerCadenasPorGirar();
		obtenerRetencionesparaGiro();

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

		if (lsts.size() > 0 || lsts!= null) {
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

		if (lsts.size() > 0 || lsts != null) {
			for (IafasComprobanteRetencion obj : lsts) {
				listaRetencionesPorGirar.add(obj);
			}
		}
		logger.info("[FIN:] Metodo : obtenerRetencionesparaGiro");
		
	}
	
	public String insRegistroGiradoCab() {
		String retorno=Constantes.VACIO;
		logger.info("[INICIO:] Metodo : insRegistroGiradoCab:::");
		IafasGiradoDao giradoDao = new IafasGiradoDao(MySQLSessionFactory.getSqlSessionFactory());
		HttpSession session=null; 
 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		
		IafasGirado objBn = new IafasGirado();
		objBn.setVano(vano);
		objBn.setVregSiaf(vregSiaf);
		objBn.setVctaCodigo(ctaCte);
		objBn.setVcodTipGiro(tipoGiro);
		objBn.setVglosa(vglosa);
		objBn.setImpMonSol(impMonSol);
		objBn.setMode(Constantes.MODE_REGISTRO);
		objBn.setVruc(vruc);
		objBn.setNtipCam(ntipCam);
		objBn.setVtipMon(tipMon);
		objBn.setVusuarioIng(codUsu);

		try {
			//if (validarRegistroGiro()) {
				int i = giradoDao.mantenimientoGiradoCab(objBn);
				if (i == 0) {
					setMsgSP("Tu registro de giro se realizó con éxito");
					retorno = "mainIafasConsultaGirados.xhtml";
				}
			//}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : insRegistroGiradoCab");
		}

		return retorno;
	}
	
	public String anularGirado() {
		
		String retorno=Constantes.VACIO;
		logger.info("[INICIO:] Metodo : anularGirado:::");
		IafasGiradoDao giradoDao = new IafasGiradoDao(MySQLSessionFactory.getSqlSessionFactory());
		HttpSession session=null; 
 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		
		String vanoAnu = (String) extContext().getRequestParameterMap().get("vanoAnu");
		String vsecuenciaAnu = (String) extContext().getRequestParameterMap().get("vsecuenciaAnu");
		String vsecuenciaIntAnu= (String) extContext().getRequestParameterMap().get("vsecuenciaIntAnu");
		IafasGirado objBn = new IafasGirado();
		objBn.setVano(vanoAnu);
		objBn.setVsecuencia(vsecuenciaAnu);
		objBn.setVsecuenciaInt(vsecuenciaIntAnu);
		objBn.setMode(Constantes.MODE_ANULACION);
		objBn.setVusuarioIng(codUsu);

		try {
				int i = giradoDao.mantenimientoGiradoCab(objBn);
				if (i == 0) {
					setMsgSP("Tu ANULACION de giro se realizó con éxito");
					retorno = "mainIafasConsultaGirados.xhtml";
				}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : anularGirado");
		}

		return retorno;
	}

	private boolean validarRegistroGiro() {

		logger.info("[INICIO:] Metodo : validarRegistroGiro:::");

		logger.info(ntipCam);
		if (Constantes.UNO_STRING.equals(tipMon) && !Constantes.UNO_BG.equals(ntipCam)) {
			setMsgValidacion("No puede ingresar un tipo de Cambio diferente a 1, ya que el tipo de Moneda es Soles");
			pasoValidacion = false;

		} else if (!Constantes.UNO_STRING.equals(tipMon) && Constantes.UNO_BG.equals(ntipCam)) {
			setMsgValidacion("Debe registrar un tipo de cambio correspondiente a la moneda seleccionada");
			pasoValidacion = false;
		}

		logger.info("[FIN:] Metodo : validarRegistroGiro:::" + pasoValidacion);
		return pasoValidacion;
	}
	
	public String retornar() {
		limpiarcampos();
		return "mainIafasGirado.xhtml";
	}
	
	private void limpiarcampos() {
		logger.info("[INICIO:] Metodo : limpiarcampos");
		setVregSiaf(Constantes.VACIO);
		setMuestraBotonGiro(false);
		setVglosa(Constantes.VACIO);
		setVruc(Constantes.VACIO);
		setVcci(Constantes.VACIO);
		setCproveedorCuentaBanco(Constantes.VACIO);
		setImpMonSol(null);
		setMsgSP(Constantes.VACIO);
		setSimboloMondea(Constantes.VACIO);
		setDescProveedor(Constantes.VACIO);
		obtenerCadenasPorGirar();
		logger.info("[FIN:] Metodo : limpiarcampos");
	}
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
}
