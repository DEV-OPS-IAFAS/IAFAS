package ep.mil.pe.iafas.programacion.controller;

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
import ep.mil.pe.iafas.programacion.dao.IafasClasificadoresDao;
import ep.mil.pe.iafas.programacion.dao.IafasFuentesFinanciamientoDao;
import ep.mil.pe.iafas.programacion.dao.IafasPeriodosDao;
import ep.mil.pe.iafas.programacion.dao.IafasTareasPptalDao;
import ep.mil.pe.iafas.programacion.dao.IafasUbigeoDao;
import ep.mil.pe.iafas.programacion.dao.ProgramacionMultiAnualDao;
import ep.mil.pe.iafas.programacion.dao.ProgramacionMultiAnualDetalleDao;
import ep.mil.pe.iafas.programacion.model.IafasClasificadores;
import ep.mil.pe.iafas.programacion.model.IafasFuentesFinanciamiento;
import ep.mil.pe.iafas.programacion.model.IafasPeriodos;
import ep.mil.pe.iafas.programacion.model.IafasTareasPptal;
import ep.mil.pe.iafas.programacion.model.IafasUbigeo;
import ep.mil.pe.iafas.programacion.model.ProgramacionMultiAnual;
import ep.mil.pe.iafas.programacion.model.ProgramacionMultiAnualDetalle;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;


@Data
@ManagedBean(name = "programacionMultiAnualController")
@SessionScoped
public class ProgramacionMultiAnualController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ProgramacionMultiAnualController.class.getName());

	private String cperiodo;
	private String fteFinanc =  Constantes.CERO_STRING;
	private int codTarea;
	private int cperiodo1;
	private int cperiodo2;
	private int cperiodo3;
	private String codDep;
	private String codProv;
	private String codUbigeo;
	private int monto1;
	private int monto2;
	private int monto3;
	private int meta1;
	private int meta2;
	private int meta3;
	
	private List<SelectItem> periodos;
	private List<SelectItem> tareas;
	private List<SelectItem> fuentes;
	private ArrayList<SelectItem> departamentos;
	private ArrayList<SelectItem> provincias;
	private ArrayList<SelectItem> ubigeos;
	private ProgramacionMultiAnual bean;
	private String modo = Constantes.MODE_REGISTRO;
	
	private List<ProgramacionMultiAnual> listaCabecera = new ArrayList<ProgramacionMultiAnual>();
	private List<ProgramacionMultiAnualDetalle> listaDetalle = new ArrayList<ProgramacionMultiAnualDetalle>();
	
	private int codCla;
	private List<SelectItem> cadenas;
	private int montoDet1;
	private int montoDet2;
	private int montoDet3;
	private boolean pasoActividad = false;
	private int saldoDetalle1 = Constantes.CERO_INT;
	private int saldoDetalle2= Constantes.CERO_INT;
	private int saldoDetalle3= Constantes.CERO_INT;
	
	public List<ProgramacionMultiAnual> buscarCabecera() {

		logger.info("[INICIO:] Metodo : buscarCabecera");

		int monto1 = Constantes.CERO_INT;
		int monto2 = Constantes.CERO_INT;
		int monto3 = Constantes.CERO_INT;
		int detalle1 = Constantes.CERO_INT;
		int detalle2 = Constantes.CERO_INT;
		int detalle3 = Constantes.CERO_INT;
		int saldo1 = Constantes.CERO_INT;
		int saldo2 = Constantes.CERO_INT;
		int saldo3 = Constantes.CERO_INT;
		
		
		this.listaCabecera = new ArrayList<>();
		if (this.listaCabecera != null)
			this.listaCabecera.clear();

		ProgramacionMultiAnualDao objDao = new ProgramacionMultiAnualDao(MySQLSessionFactory.getSqlSessionFactory());
		ProgramacionMultiAnual objBn  = new ProgramacionMultiAnual();
		
		objBn.setPeriodo(cperiodo);
		objBn.setFuenteFinac(Integer.parseInt(fteFinanc));
		
		List<ProgramacionMultiAnual> lstCab = objDao.mostrarCabecera(objBn);

		if (lstCab.size() > 0) {
			for (ProgramacionMultiAnual objBeanLista : lstCab) {
				objBn.setAno1(Integer.parseInt(cperiodo));
				objBn.setAno2(cperiodo1);
				objBn.setAno3(cperiodo2);
				objBn.setCodigoTareaPtal(objBeanLista.getCodigoTareaPtal());
				List<ProgramacionMultiAnual> lstMonto1 =	objDao.obtenerMonto1(objBn);
				List<ProgramacionMultiAnual> lstMonto2 =	objDao.obtenerMonto2(objBn);
				List<ProgramacionMultiAnual> lstMonto3 =	objDao.obtenerMonto3(objBn);
				
				if (lstMonto1.size() > 0 && lstMonto2.size() > 0 && lstMonto3.size() > 0) {
					monto1 = lstMonto1.get(0).getMonto1();
					monto2 = lstMonto2.get(0).getMonto2();
					monto3 = lstMonto3.get(0).getMonto3();
					objBeanLista.setMonto1(monto1);
					objBeanLista.setMonto2(monto2);
					objBeanLista.setMonto3(monto3);
					
				}
				
				ProgramacionMultiAnualDetalleDao detDao = new ProgramacionMultiAnualDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
				ProgramacionMultiAnualDetalle detBean = new ProgramacionMultiAnualDetalle();
				detBean.setPeriodo(cperiodo);
				detBean.setFuenteFinac(Integer.parseInt(fteFinanc));
				logger.info("codigo de tarea:::."+objBeanLista.getCodigoTareaPtal());
				detBean.setCodigoTareaPtal(objBeanLista.getCodigoTareaPtal());
				detBean.setAno1(Integer.parseInt(cperiodo));
				detBean.setAno2(cperiodo1);
				detBean.setAno3(cperiodo2);
				List<ProgramacionMultiAnualDetalle> lstMontoDet1 =detDao.obtenerMontoDetalleAnio1(detBean);
				List<ProgramacionMultiAnualDetalle> lstMontoDet2 =detDao.obtenerMontoDetalleAnio2(detBean);
				List<ProgramacionMultiAnualDetalle> lstMontoDet3 =detDao.obtenerMontoDetalleAnio3(detBean);
				detalle1 = lstMontoDet1.get(0).getMontoDetalle1();
				detalle2 = lstMontoDet2.get(0).getMontoDetalle2();
				detalle3 = lstMontoDet3.get(0).getMontoDetalle3();
				
				saldo1 = monto1-detalle1;
				saldo2 = monto2-detalle2;
				saldo3 = monto3-detalle3;
				objBeanLista.setDetalle1(detalle1);
				objBeanLista.setDetalle2(detalle2);
				objBeanLista.setDetalle3(detalle3);
				objBeanLista.setSaldo1(saldo1);
				objBeanLista.setSaldo2(saldo2);
				objBeanLista.setSaldo3(saldo3);
				
				this.listaCabecera.add(objBeanLista);
			}
		}
		
		logger.info("[FIN:] Metodo : buscarCabecera");
		return listaCabecera;
	}
	
	
	public List<SelectItem> getPeriodos() {
		logger.info("[INICIO:] Metodo : getPeriodos");
		
		this.periodos = new ArrayList<>();
		IafasPeriodosDao periodosDao = new IafasPeriodosDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasPeriodos> lstPeriodos = periodosDao.obtenerPeriodosActivos();
		for (IafasPeriodos p : lstPeriodos) {
			this.periodos.add(new SelectItem(p.getCperiodoCodigo(),
					p.getCperiodoCodigo() ));
		}
		logger.info("[FIN:] Metodo : getPeriodos");
		return this.periodos;

	}
	
	public List<SelectItem> getTareas() {
		logger.info("[INICIO:] Metodo : getTareas");
		
		this.tareas = new ArrayList<>();
		IafasTareasPptalDao tareasDao = new IafasTareasPptalDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasTareasPptal> lstTareas = tareasDao.obtenerTareasPresupuestales();
		for (IafasTareasPptal p : lstTareas) {
			this.tareas.add(new SelectItem(p.getNTareaPresupuestal(),
					p.getNTareaPresupuestal() +" - " +p.getVTareaPresupuestalNombre()));
		}
		logger.info("[FIN:] Metodo : getTareas");
		return this.tareas;

	}
	
	public List<SelectItem> getFuentes() {
		logger.info("[INICIO:] Metodo : getFuentes");
		
		this.fuentes = new ArrayList<>();
		IafasFuentesFinanciamientoDao fuentesDao = new IafasFuentesFinanciamientoDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasFuentesFinanciamiento> lstFuentes = fuentesDao.obtenerFuentesFinanciamiento();
		for (IafasFuentesFinanciamiento p : lstFuentes) {
			this.fuentes.add(new SelectItem(p.getFuenteFinanciamientoCodigo(),
					p.getFuenteFinanciamientoCodigo() +" - " +p.getFuenteFinanciamientoNombre()));
		}
		logger.info("[FIN:] Metodo : getFuentes");
		return this.fuentes;

	}

	public void limpiarCamposMto() {
		logger.info("[INICIO:] Metodo : limpiarCamposMto");

		setCodTarea(Constantes.CERO_INT);
		setCodDep(Constantes.CERO_CERO_STRING);
		setCodProv(Constantes.CERO_CERO_STRING);
		setCodUbigeo(Constantes.CERO_CERO_STRING);
		setMeta1(Constantes.CERO_INT);
		setMeta2(Constantes.CERO_INT);
		setMeta3(Constantes.CERO_INT);
		//buscarCabecera();
		logger.info("[FIN:] Metodo : limpiarCamposMto");
	}
	
	public void calcularPeriodos() {
		
		logger.info("[INICIO:] Metodo : calcularPeriodos");

		cperiodo1 = Integer.parseInt(cperiodo)+1;
		cperiodo2 = cperiodo1+1;
		cperiodo3 = cperiodo2+1;
		
		setCperiodo1(cperiodo1);
		setCperiodo2(cperiodo2);
		setCperiodo3(cperiodo3);

		logger.info("[FIN:] Metodo : calcularPeriodos");
	}
	
	public List<SelectItem> getDepartamentos() {
		logger.info("[INICIO:] Metodo : getDepartamentos");
		
		this.departamentos = new ArrayList<SelectItem>();
		IafasUbigeoDao DptoDao = new IafasUbigeoDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasUbigeo> lstDpts = DptoDao.obtenerDepartamentos();
		for (IafasUbigeo p : lstDpts) {
			this.departamentos.add(new SelectItem(p.getCodigoDepartamento(),p.getNombreDepartamento()));
		}
		logger.info("[FIN:] Metodo : getDepartamentos");
		return this.departamentos;

	}
	
	public ArrayList<SelectItem> cargarProvincias() {
		logger.info("[INICIO:] Metodo : cargarProvincias");

		this.provincias = new ArrayList<>();
		IafasUbigeoDao provDao = new IafasUbigeoDao(MySQLSessionFactory.getSqlSessionFactory());

		List<IafasUbigeo> lstProv = provDao.obtenerProvincias(codDep);

		for (IafasUbigeo p : lstProv) {
			this.provincias.add(
					new SelectItem(p.getCodigoProvincia(), p.getNombreProvincia()));
		}
		logger.info("[FIN:] Metodo : cargarProvincias");
		return this.provincias;
	}
	
	public ArrayList<SelectItem> cargarUbigeo() {
		logger.info("[INICIO:] Metodo : cargarUbigeo");

		this.ubigeos = new ArrayList<>();
		IafasUbigeoDao ubigeoDao = new IafasUbigeoDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasUbigeo objBn = new IafasUbigeo();
		objBn.setCodigoDepartamento(codDep);
		objBn.setCodigoProvincia(codProv);

		List<IafasUbigeo> lstUbigeo = ubigeoDao.obtenerUbigeos(objBn);

		for (IafasUbigeo p : lstUbigeo) {
			this.ubigeos.add(new SelectItem(p.getCodigoUbigeo(),p.getNombreUbigeo()));
		}
		logger.info("[FIN:] Metodo : cargarUbigeo");
		return this.ubigeos;
	}
	
	public String insRegistroCab() {
		String retorno = Constantes.VACIO;
		logger.info("[INICIO:] Metodo : insRegistroCab:::");
		ProgramacionMultiAnualDao objDao = new ProgramacionMultiAnualDao(MySQLSessionFactory.getSqlSessionFactory());
		Response response = new Response();
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession = (IafasUsuariosController) session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		ProgramacionMultiAnual objBn = new ProgramacionMultiAnual();
		objBn.setPeriodo(cperiodo);
		objBn.setFuenteFinac(Integer.parseInt(fteFinanc));
		objBn.setTareaPtalCodigo(codTarea);
		objBn.setUbigeoCodigo(codUbigeo);
		objBn.setImporteA(monto1);
		objBn.setImporteB(monto2);
		objBn.setImporteC(monto3);
		objBn.setMetaFisicaA(meta1);
		objBn.setMetaFisicaB(meta2);
		objBn.setMetaFisicaC(meta3);

		objBn.setUsuarioCodigo(codUsu);
		objBn.setTipo(modo);

		try {
			response = objDao.SP_IDU_PROGRAMACION_MULTIANUAL(objBn);
			logger.info("Respuesta en insRegistroCab ::: " + response.getCodigoRespuesta());
			if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
				retorno = "mainProgramacionMultiAnual.xhtml";
				buscarCabecera();
			}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : insRegistroCab");
		}

		return retorno;
	}
	
	public void obtenerRegistro() {
		logger.info("[INICIO:] Metodo : obtenerRegistro");
		
		ProgramacionMultiAnualDao objDao = new ProgramacionMultiAnualDao(MySQLSessionFactory.getSqlSessionFactory());
		ProgramacionMultiAnual objBn  = new ProgramacionMultiAnual();
		objBn.setPeriodo(bean.getPeriodo());
		objBn.setFuenteFinac(bean.getFuenteFinac());
		objBn.setCodigoTareaPtal(bean.getCodigoTareaPtal());
		objBn.setAno1(Integer.parseInt(getCperiodo()));
		objBn.setAno2(getCperiodo1());
		objBn.setAno3(getCperiodo2());
		
		List<ProgramacionMultiAnual> lstReg = objDao.obtenerRegistro(objBn);
		
		logger.info("Datos: "+lstReg.size());
		for (ProgramacionMultiAnual objBean : lstReg) {
			setCodTarea(objBean.getCodigoTareaPtal());
			setCodDep(objBean.getCodDepa());
			cargarProvincias();
			setCodProv(objBean.getCodProv());
			cargarUbigeo();
			setCodUbigeo(objBean.getCodDistr());
			setMonto1(objBean.getMonto1());
			setMonto2(objBean.getMonto2());
			setMonto3(objBean.getMonto3());
			setMeta1(objBean.getMeta1());
			setMeta2(objBean.getMeta2());
			setMeta3(objBean.getMeta3());
		}
		setModo(Constantes.MODE_ACTUALIZACION);
		logger.info("[FIN:] Metodo : obtenerRegistro");
	}
	
	public void nuevo() {
		logger.info("[INICIO:] Metodo : nuevo:::");
		setCodTarea(Constantes.CERO_INT);
		setModo(Constantes.MODE_REGISTRO);
		setCodDep(Constantes.VACIO);
		getDepartamentos();
		setCodProv(Constantes.VACIO);
		cargarProvincias();
		setCodUbigeo(Constantes.VACIO);
		cargarUbigeo();
		setMeta1(Constantes.CERO_INT);
		setMeta2(Constantes.CERO_INT);
		setMeta3(Constantes.CERO_INT);
		setMonto1(Constantes.CERO_INT);
		setMonto2(Constantes.CERO_INT);
		setMonto3(Constantes.CERO_INT);
		logger.info("[FIN:] Metodo : nuevo:::");
	}
	
	
	public void obtenerRegistroDetalle() {
		logger.info("[INICIO:] Metodo : obtenerRegistroDetalle");
		
		ProgramacionMultiAnualDao objDao = new ProgramacionMultiAnualDao(MySQLSessionFactory.getSqlSessionFactory());
		ProgramacionMultiAnual objBn  = new ProgramacionMultiAnual();
		objBn.setPeriodo(bean.getPeriodo());
		objBn.setFuenteFinac(bean.getFuenteFinac());
		objBn.setCodigoTareaPtal(bean.getCodigoTareaPtal());
		objBn.setAno1(Integer.parseInt(getCperiodo()));
		objBn.setAno2(getCperiodo1());
		objBn.setAno3(getCperiodo2());
		
		List<ProgramacionMultiAnual> lstReg = objDao.obtenerRegistro(objBn);
		
		logger.info("Datos: "+lstReg.size());
		for (ProgramacionMultiAnual objBean : lstReg) {
			setCodTarea(objBean.getCodigoTareaPtal());
			setMonto1(objBean.getMonto1());
			setMonto2(objBean.getMonto2());
			setMonto3(objBean.getMonto3());
			buscarDetalle();
		}
		setModo(modo);
		logger.info("[FIN:] Metodo : obtenerRegistroDetalle");
	}
	
	public List<ProgramacionMultiAnualDetalle> buscarDetalle() {

		logger.info("[INICIO:] Metodo : buscarDetalle");

		int montoDet1 = Constantes.CERO_INT;
		int montoDet2 = Constantes.CERO_INT;
		int montoDet3 = Constantes.CERO_INT;
		int importeTotal = Constantes.CERO_INT;
		
		this.listaDetalle = new ArrayList<>();
		if (this.listaDetalle != null)
			this.listaDetalle.clear();

		ProgramacionMultiAnualDetalleDao objDao = new ProgramacionMultiAnualDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
		ProgramacionMultiAnualDetalle objBnDet  = new ProgramacionMultiAnualDetalle();
		
		objBnDet.setPeriodo(cperiodo);
		objBnDet.setFuenteFinac(Integer.parseInt(fteFinanc));
		objBnDet.setTareaPtalCodigo(codTarea);
		
		List<ProgramacionMultiAnualDetalle> lstDet = objDao.mostrarDetalle(objBnDet);

		if (lstDet.size() > 0) {
			for (ProgramacionMultiAnualDetalle objBeanLista : lstDet) {
				objBnDet.setAno1(Integer.parseInt(cperiodo));
				objBnDet.setAno2(cperiodo1);
				objBnDet.setAno3(cperiodo2);
				objBnDet.setCodCla(objBeanLista.getCodCla());
				objBnDet.setCodigoTareaPtal(objBeanLista.getCodigoTareaPtal());
				
				List<ProgramacionMultiAnualDetalle> lstMontoDet1 =	objDao.obtenerMontoDetalle1(objBnDet);
				List<ProgramacionMultiAnualDetalle> lstMontoDet2 =	objDao.obtenerMontoDetalle2(objBnDet);
				List<ProgramacionMultiAnualDetalle> lstMontoDet3 =	objDao.obtenerMontoDetalle3(objBnDet);

				if (lstMontoDet1!= null   && lstMontoDet2!= null && lstMontoDet3!= null ) {
					montoDet1 = lstMontoDet1.get(0).getMontoDet1();
					montoDet2 = lstMontoDet2.get(0).getMontoDet2();
					montoDet3 = lstMontoDet3.get(0).getMontoDet3(); 
					importeTotal = montoDet1+montoDet2+montoDet3;
					objBeanLista.setMontoDet1(montoDet1);
					objBeanLista.setMontoDet2(montoDet2);
					objBeanLista.setMontoDet3(montoDet3);
					objBeanLista.setImporte(importeTotal);
				}
				this.listaDetalle.add(objBeanLista);
			}
		}

		logger.info("[FIN:] Metodo : buscarDetalle");
		return listaDetalle;
	}
	
	public List<SelectItem> getCadenas() {
		logger.info("[INICIO:] Metodo : getCadenas");
		
		this.cadenas = new ArrayList<>();
		IafasClasificadoresDao cadenasDao = new IafasClasificadoresDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasClasificadores> lstCadenas = cadenasDao.obtenerClasificadores();
		for (IafasClasificadores p : lstCadenas) {
			this.cadenas.add(new SelectItem(p.getCodCla(),
					p.getCadena() +" - " +p.getNombreCla()));
		}
		logger.info("[FIN:] Metodo : getCadenas");
		return this.cadenas;

	}
	
	public void insRegistroDet() {

		logger.info("[INICIO:] Metodo : insRegistroDet:::");
		ProgramacionMultiAnualDetalleDao objDao = new ProgramacionMultiAnualDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
		Response response = new Response();
		HttpSession session=null; 
 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		ProgramacionMultiAnualDetalle objBnDetalle = new ProgramacionMultiAnualDetalle();
		objBnDetalle.setPeriodo(cperiodo);
		objBnDetalle.setFuenteFinac(Integer.parseInt(fteFinanc));
		objBnDetalle.setTareaPtalCodigo(bean.getCodigoTareaPtal());
		objBnDetalle.setCodCla(codCla);
		objBnDetalle.setImporteA(montoDet1);
		objBnDetalle.setImporteB(montoDet2);
		objBnDetalle.setImporteC(montoDet3); 
		objBnDetalle.setUsuarioCodigo(codUsu);
		objBnDetalle.setTipo(modo);
		try {
			response= objDao.SP_IDU_PROGRAMACION_MULTIANUALDETALLE(objBnDetalle);
			logger.info("Respuesta en insRegistroDet ::: " + response.getCodigoRespuesta());
				if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
					buscarDetalle();
					pasoActividad = true;
				}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : insRegistroDet");
		}
	}
	
	public void calcularSaldos() {

		logger.info("[INICIO:] Metodo : calcularSaldos");
		
		int monto1 = Constantes.CERO_INT;
		int monto2 = Constantes.CERO_INT;
		int monto3 = Constantes.CERO_INT;
		int detalle1 = Constantes.CERO_INT;
		int detalle2 = Constantes.CERO_INT;
		int detalle3 = Constantes.CERO_INT;
		String codTareaParam = (String) extContext().getRequestParameterMap().get("codTareaParam");
		ProgramacionMultiAnualDao objDao = new ProgramacionMultiAnualDao(MySQLSessionFactory.getSqlSessionFactory());
		ProgramacionMultiAnual objBn  = new ProgramacionMultiAnual();
		
		objBn.setPeriodo(cperiodo);
		objBn.setFuenteFinac(Integer.parseInt(fteFinanc));
		
		List<ProgramacionMultiAnual> lstCab = objDao.mostrarCabecera(objBn);
		if (lstCab.size() > 0) {
			for (ProgramacionMultiAnual objBeanLista : lstCab) {
				objBn.setAno1(Integer.parseInt(cperiodo));
				objBn.setAno2(cperiodo1);
				objBn.setAno3(cperiodo2);
				objBn.setCodigoTareaPtal(Integer.parseInt(codTareaParam));
				List<ProgramacionMultiAnual> lstMonto1 =	objDao.obtenerMonto1(objBn);
				List<ProgramacionMultiAnual> lstMonto2 =	objDao.obtenerMonto2(objBn);
				List<ProgramacionMultiAnual> lstMonto3 =	objDao.obtenerMonto3(objBn);
				
				if (lstMonto1.size() > 0 && lstMonto2.size() > 0 && lstMonto3.size() > 0) {
					monto1 = lstMonto1.get(0).getMonto1();
					monto2 = lstMonto2.get(0).getMonto2();
					monto3 = lstMonto3.get(0).getMonto3();
					objBeanLista.setMonto1(monto1);
					objBeanLista.setMonto2(monto2);
					objBeanLista.setMonto3(monto3);
					
				}
				
				ProgramacionMultiAnualDetalleDao detDao = new ProgramacionMultiAnualDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
				ProgramacionMultiAnualDetalle detBean = new ProgramacionMultiAnualDetalle();
				detBean.setPeriodo(cperiodo);
				detBean.setFuenteFinac(Integer.parseInt(fteFinanc));
				detBean.setCodigoTareaPtal(Integer.parseInt(codTareaParam));
				detBean.setAno1(Integer.parseInt(cperiodo));
				detBean.setAno2(cperiodo1);
				detBean.setAno3(cperiodo2);
				List<ProgramacionMultiAnualDetalle> lstMontoDet1 =detDao.obtenerMontoDetalleAnio1(detBean);
				List<ProgramacionMultiAnualDetalle> lstMontoDet2 =detDao.obtenerMontoDetalleAnio2(detBean);
				List<ProgramacionMultiAnualDetalle> lstMontoDet3 =detDao.obtenerMontoDetalleAnio3(detBean);
				detalle1 = lstMontoDet1.get(0).getMontoDetalle1();
				detalle2 = lstMontoDet2.get(0).getMontoDetalle2();
				detalle3 = lstMontoDet3.get(0).getMontoDetalle3();
				
				saldoDetalle1 = monto1-detalle1;
				saldoDetalle2 = monto2-detalle2;
				saldoDetalle3 = monto3-detalle3;
			}
		}
		logger.info("[FIN:] Metodo : calcularSaldos");
	}
	
	public void obtenerDatos() {
		logger.info("[INICIO:] Metodo : obtenerDatos");
		String codClaMod = (String) extContext().getRequestParameterMap().get("codClaMod");
		String codTareaMod = (String) extContext().getRequestParameterMap().get("codTareaMod");

		logger.info("Parametros: " + codClaMod +" -  "+codTareaMod);
		ProgramacionMultiAnualDetalleDao detDao = new ProgramacionMultiAnualDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
		ProgramacionMultiAnualDetalle detBean = new ProgramacionMultiAnualDetalle();
		
		detBean.setPeriodo(cperiodo);
		detBean.setFuenteFinac(Integer.parseInt(fteFinanc));
		detBean.setAno1(Integer.parseInt(cperiodo));
		detBean.setAno2(cperiodo1);
		detBean.setAno3(cperiodo2);
		detBean.setCodCla(Integer.parseInt(codClaMod));
		detBean.setCodigoTareaPtal(Integer.parseInt(codTareaMod));

	
		List<ProgramacionMultiAnualDetalle> lstDet = detDao.mostrarDatosDetalle(detBean);
		for(ProgramacionMultiAnualDetalle objLista : lstDet) {
			
		}
		
		
		List<ProgramacionMultiAnualDetalle> lstMontoDet1 =	detDao.obtenerMontoDetalle1(detBean);
		List<ProgramacionMultiAnualDetalle> lstMontoDet2 =	detDao.obtenerMontoDetalle2(detBean);
		List<ProgramacionMultiAnualDetalle> lstMontoDet3 =	detDao.obtenerMontoDetalle3(detBean);

		if (lstMontoDet1!= null   && lstMontoDet2!= null && lstMontoDet3!= null ) {
			montoDet1 = lstMontoDet1.get(0).getMontoDet1();
			montoDet2 = lstMontoDet2.get(0).getMontoDet2();
			montoDet3 = lstMontoDet3.get(0).getMontoDet3(); 
			calcularSaldos();
		}
		setModo(Constantes.MODE_ACTUALIZACION);
		logger.info("[FIN:] Metodo : obtenerDatos");
	}
	
	public void anularRegistro() {
		logger.info("[INICIO:] Metodo : anularRegistro");
		Response response = new Response();
		String codClaDel = (String) extContext().getRequestParameterMap().get("codClaDel");
		String codTareaDel = (String) extContext().getRequestParameterMap().get("codTareaDel");
		ProgramacionMultiAnualDetalleDao objDao =  new ProgramacionMultiAnualDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
		ProgramacionMultiAnualDetalle objBn = new ProgramacionMultiAnualDetalle();
		objBn.setPeriodo(cperiodo);
		objBn.setFuenteFinac(Integer.parseInt(fteFinanc));
		objBn.setTareaPtalCodigo(Integer.parseInt(codTareaDel));
		objBn.setCodCla(Integer.parseInt(codClaDel));
		objBn.setTipo(Constantes.MODE_ELIMINACION_LOGICA);
		
		try {
			response= objDao.SP_IDU_PROGRAMACION_MULTIANUALDETALLE(objBn);
			logger.info("Respuesta en anularRegistro ::: " + response.getCodigoRespuesta());
				if (Constantes.CERO_STRING.equals(response.getCodigoRespuesta())) {
					logger.info("Se elimino el registro Correctamente!");
				}
		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : anularRegistro");
		}
	}
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
}
