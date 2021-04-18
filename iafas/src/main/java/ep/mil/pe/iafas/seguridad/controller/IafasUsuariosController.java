package ep.mil.pe.iafas.seguridad.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Response;
import ep.mil.pe.iafas.seguridad.dao.IafasAreasLaboralDao;
import ep.mil.pe.iafas.seguridad.dao.IafasUsuariosDao;
import ep.mil.pe.iafas.seguridad.model.IafasAreasLaboral;
import ep.mil.pe.iafas.seguridad.model.IafasUsuarios;
import ep.mil.pe.iafas.seguridad.util.funcionesUtiles;
import lombok.Data;

@Data
@ManagedBean(name = "iafasUsuariosController")
@SessionScoped
public class IafasUsuariosController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(IafasUsuariosController.class.getName());

	private String idUsuario;
	private String passUsuario;
	private String nombreUsuario;
	private String aPaternoUsuario;
	private String aMaternoUsuario;
	private String rolUsuario;
	private List<IafasUsuarios> listaUsuarios = new ArrayList<>();
	private String msgErr;
	private String dni;
	private boolean pasoFuncionalidad = false;
	private int nroRegistros;
	private List<IafasUsuarios> listaAllUsuarios = new ArrayList<>();
	private String modo = Constantes.MODE_REGISTRO;
	private String idUsuarioMto;
	private String passUsuarioMto;
	private String nombreUsuarioMto;
	private String aPaternoUsuarioMto;
	private String aMaternoUsuarioMto;
	private boolean bllBotonAcciones = false;
	private List<IafasAreasLaboral> listaAreas = new ArrayList<>();
	private List<SelectItem> area;
	private String idAreaLaboral;  

	public List<IafasUsuarios> mostrarAllUsuarios() {

		logger.debug("[INICIO:] Metodo : mostrarAllUsuarios");

		this.listaAllUsuarios = new ArrayList<>();
		if (this.listaAllUsuarios != null)
			this.listaAllUsuarios.clear();
		IafasUsuariosDao usuarioSessionDao = new IafasUsuariosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasUsuarios> lstUsuarios = usuarioSessionDao.SelectAllUsuario();
		if (lstUsuarios.size() > 0) {
			this.nroRegistros = lstUsuarios.size();
			for (IafasUsuarios objLista : lstUsuarios) {
				this.listaAllUsuarios.add(objLista);
			}
		}

		logger.debug("[FIN:] Metodo : mostrarAllUsuarios");
		return listaAllUsuarios;
	}

	public String validaUsuario() {

		logger.info("[INICIO:] Metodo : validaUsuario");

		String valor = Constantes.VACIO;
		String Patron_encripta = "OnXEiGH9eIp8stu7UVWvlmï¿½fq0D2YZï¿½oPQR4AwyFgxhbjk65rcSTz1N3BCdJKLMa";
		funcionesUtiles encripta = new funcionesUtiles();
		boolean pasoLogin = false;
		String pasusu = encripta.EncriptCadena(Patron_encripta, this.passUsuario, true);
		logger.info("[INPUT]  :" + idUsuario);
		IafasUsuariosDao usuarioSessionDao = new IafasUsuariosDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasUsuarios c = new IafasUsuarios();
		c.setVusuarioCodigo(this.idUsuario);
		c.setVusuarioPassword(pasusu);

		List<IafasUsuarios> usuarioBuscado = usuarioSessionDao.SelectListFiltro2(c);

		if (usuarioBuscado.size() > 0) {
			for (IafasUsuarios usuario : usuarioBuscado) {

				setAPaternoUsuario(usuario.getVusuarioPaterno());
				setAMaternoUsuario(usuario.getVusuarioMaterno());
				setNombreUsuario(usuario.getVusuarioNombres());
				setIdUsuario(usuario.getVusuarioCodigo());
			}
			pasoLogin = true;
			//valor = Constantes.MAIN_PRINCIPAL;
			valor = "mainPrincipal2.xhtml";
		} else {
			setMsgErr("El Usuario no Existe o no esta Activo!!");
			valor = Constantes.LOGIN;
			limpiarCampos();
		}

		logger.info("[FIN:] Metodo : validaUsuario");
		return valor;
	}

	public void limpiarCampos() {
		logger.info("[INICIO:] Metodo : limpiarCampos");

		setIdUsuario(Constantes.VACIO);
		setPassUsuario(Constantes.VACIO);
		setAPaternoUsuario(Constantes.VACIO);
		setNombreUsuario(Constantes.VACIO);
		setDni(Constantes.VACIO);

		logger.info("[FIN:] Metodo : limpiarCampos");
	}

	public void limpiarCamposMto() {
		logger.info("[INICIO:] Metodo : limpiarCamposMto");

		setIdUsuarioMto(Constantes.VACIO);
		setNombreUsuarioMto(Constantes.VACIO);
		setAMaternoUsuarioMto(Constantes.VACIO);
		setAPaternoUsuarioMto(Constantes.VACIO);
		setPassUsuarioMto(Constantes.VACIO);
		setBllBotonAcciones(false);
		setIdAreaLaboral(Constantes.CERO_CERO);
		setModo(Constantes.MODE_REGISTRO);

		logger.info("[FIN:] Metodo : limpiarCamposMto");
	}

	public void preRegistroUsuario() {
		logger.info("[INICIO:] Metodo : preRegistroUsuario");

		Response response = new Response();
		IafasUsuariosDao usuarioSessionDao = new IafasUsuariosDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasUsuarios objUsuario = new IafasUsuarios();
		objUsuario.setVusuarioCodigo(dni);
		objUsuario.setVusuarioNombres(nombreUsuario.toUpperCase());
		objUsuario.setVusuarioPaterno(aPaternoUsuario.toUpperCase());
		objUsuario.setModo(Constantes.MODE_PRE_REGISTRO);
		pasoFuncionalidad = true;
		try {
			response = usuarioSessionDao.mantenimientoUsuarios(objUsuario);

			if (Constantes.CERO_STRING != response.getCodigoRespuesta()) {
				setMsgErr(response.getMensajeRespuesta());
			}
			limpiarCampos();
		} catch (Exception e) {
			logger.error("error : " + response.getMensajeRespuesta());
			setMsgErr(e.getMessage());

		} finally {
			logger.debug("[FIN:] Metodo : preRegistroUsuario");
		}
	}

	public void obtenerDatos() {
		logger.info("[INICIO:] Metodo : obtenerDatos");
		String codUsu = (String) extContext().getRequestParameterMap().get("codUsuario");

		logger.info("codigo de usuario enviado por parametro: " + codUsu);
		IafasUsuariosDao usuarioDao = new IafasUsuariosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasUsuarios> listUsuarios = usuarioDao.SelectListFiltro1(codUsu);

		for (IafasUsuarios objBn : listUsuarios) {
			setIdUsuarioMto(objBn.getVusuarioCodigo());
			setAPaternoUsuarioMto(objBn.getVusuarioPaterno());
			setAMaternoUsuarioMto(objBn.getVusuarioMaterno());
			setNombreUsuarioMto(objBn.getVusuarioNombres());
			setIdAreaLaboral(objBn.getAreaLaboralCodigo());
			setPassUsuarioMto(objBn.getVusuarioPassword());
		}
		setBllBotonAcciones(true);
		setModo(Constantes.MODE_ACTUALIZACION);
		logger.info("[FIN:] Metodo : obtenerDatos");
	}

	public void mantenimientoUsuarios() {
		logger.info("[INICIO:] Metodo : mantenimientoUsuarios");

		IafasUsuariosDao usuarioSessionDao = new IafasUsuariosDao(MySQLSessionFactory.getSqlSessionFactory());
		Response response = new Response();
		IafasUsuarios objUsuario = new IafasUsuarios();
		String Patron_encripta = "OnXEiGH9eIp8stu7UVWvlmï¿½fq0D2YZï¿½oPQR4AwyFgxhbjk65rcSTz1N3BCdJKLMa";
		funcionesUtiles encripta = new funcionesUtiles();
		String pasusu = encripta.EncriptCadena(Patron_encripta, this.passUsuarioMto, true);
		objUsuario.setVusuarioCodigo(idUsuarioMto);
		objUsuario.setVusuarioNombres(nombreUsuarioMto);
		objUsuario.setVusuarioPaterno(aPaternoUsuarioMto);
		objUsuario.setVusuarioMaterno(aMaternoUsuarioMto);
		objUsuario.setAreaLaboralCodigo(idAreaLaboral);
		objUsuario.setCestadoCodigo(Constantes.ESTADO_ACTIVO);
		if(!bllBotonAcciones) {
			objUsuario.setVusuarioPassword(pasusu);	
		}
		
		objUsuario.setModo(modo);
		try {
			response = usuarioSessionDao.mantenimientoUsuarios(objUsuario);
			
			if(Constantes.CERO_STRING!= response.getCodigoRespuesta()) {
				setMsgErr(response.getMensajeRespuesta());
			}
			
			
		} catch (Exception e) {
			logger.error("error : " + response.getMensajeRespuesta());
		} finally {
			
			mostrarAllUsuarios();
			logger.info("[FIN:] Metodo : mantenimientoUsuarios");
		}
	}

	public void nuevoRegistro() {
		logger.info("[INICIO:] Metodo : nuevoRegistro");
		limpiarCamposMto();
		logger.info("[FIN:] Metodo : nuevoRegistro");
	}
	
	public List<SelectItem> getArea() {
		logger.info("[INICIO:] Metodo : cargarAreasLaborales");
		this.area = new ArrayList<>();
		IafasAreasLaboralDao areaslaboralDao = new IafasAreasLaboralDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasAreasLaboral> lstAreas = areaslaboralDao.SelectAllAreas();
		for (IafasAreasLaboral p : lstAreas) {
			this.area.add(new SelectItem(p.getCareaLaboralCodigo(),
					p.getCareaLaboralCodigo() + "- " + p.getVarealaboralDescripcion()));
		}
		logger.info("[FIN:] Metodo : cargarAreasLaborales");
		return this.area;

	}
	
	public void anularRegistro() {
		logger.info("[INICIO:] Metodo : anularRegistro");

		Response response = new Response();
		String codUsuDEL = (String) extContext().getRequestParameterMap().get("codUsuarioDEL");
		IafasUsuariosDao usuarioSessionDao = new IafasUsuariosDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasUsuarios objUsuario = new IafasUsuarios();
		objUsuario.setVusuarioCodigo(codUsuDEL);
		objUsuario.setCestadoCodigo(Constantes.ESTADO_ANULADO);
		objUsuario.setModo(Constantes.MODE_ELIMINACION_LOGICA);
		try {
			//int i = usuarioSessionDao.mantenimientoUsuarios(objUsuario);
			response = usuarioSessionDao.mantenimientoUsuarios(objUsuario);

			//if (i == 0) {
			if(response.getCodigoRespuesta() == Constantes.CERO_STRING) {
				logger.info("La anulación se realizo con exito");
			}

		} catch (Exception e) {
			logger.error("error : " + response.getMensajeRespuesta());
		} finally {
			mostrarAllUsuarios();
			logger.info("[FIN:] Metodo : anularRegistro");
		}

	}
	
	public void cerraSession() {
		System.out.println("[INICIO:] Metodo : cerraSession");
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		httpSession.invalidate();
		System.out.println("[FIN:] Metodo : cerraSession");
	}
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
}
