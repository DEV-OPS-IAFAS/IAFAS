package ep.mil.pe.iafas.programacion.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.programacion.dao.IafasFuentesFinanciamientoDao;
import ep.mil.pe.iafas.programacion.dao.IafasPeriodosDao;
import ep.mil.pe.iafas.programacion.dao.IafasTareasPptalDao;
import ep.mil.pe.iafas.programacion.dao.IafasUbigeoDao;
import ep.mil.pe.iafas.programacion.model.IafasFuentesFinanciamiento;
import ep.mil.pe.iafas.programacion.model.IafasPeriodos;
import ep.mil.pe.iafas.programacion.model.IafasTareasPptal;
import ep.mil.pe.iafas.programacion.model.IafasUbigeo;
import lombok.Data;


@Data
@ManagedBean(name = "programacionMultiAnualController")
@SessionScoped
public class ProgramacionMultiAnualController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ProgramacionMultiAnualController.class.getName());

	private String cperiodo;
	private String fteFinanc;
	private int codTarea;
	private int cperiodo1;
	private int cperiodo2;
	private int cperiodo3;
	private String codDep;
	private String codProv;
	private String codUbigeo;
	private BigDecimal monto1;
	private BigDecimal monto2;
	private BigDecimal monto3;
	
	private List<SelectItem> periodos;
	private List<SelectItem> tareas;
	private List<SelectItem> fuentes;
	private ArrayList<SelectItem> departamentos;
	private ArrayList<SelectItem> provincias;
	private ArrayList<SelectItem> ubigeos;
	
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
		setCperiodo1(Constantes.CERO_INT);
		setCperiodo2(Constantes.CERO_INT);
		setCperiodo3(Constantes.CERO_INT);
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
//		departamentos.add(new SelectItem("00", "Seleccione.."));
		IafasUbigeoDao DptoDao = new IafasUbigeoDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasUbigeo> lstDpts = DptoDao.obtenerDepartamentos();
		for (IafasUbigeo p : lstDpts) {
			this.departamentos.add(new SelectItem(p.getCodigoDepartamento(),
					p.getCodigoDepartamento() +" - " +p.getNombreDepartamento()));
		}
		logger.info("[FIN:] Metodo : getDepartamentos");
		return this.departamentos;

	}
	
	public void cargarProvincias(ValueChangeEvent event) throws IOException {

		logger.info("[INICIO:] Metodo : cargarProvincias");

		this.provincias = new ArrayList<SelectItem>();
		provincias.add(new SelectItem("00", "Seleccione.."));
		IafasUbigeoDao provDao = new IafasUbigeoDao(MySQLSessionFactory.getSqlSessionFactory());
		List<IafasUbigeo> lstProv = provDao.obtenerProvincias( event.getNewValue().toString());

		for (IafasUbigeo p : lstProv) {
			this.provincias.add(
					new SelectItem(p.getCodigoProvincia(), p.getCodigoProvincia() + " - " + p.getNombreProvincia()));
		}
		logger.info("[FIN:] Metodo : cargarProvincias");
	}
	
	
	/*public List<SelectItem> getProvincias() {
		logger.info("[INICIO:] Metodo : getProvincias");
		
		this.provincias = new ArrayList<>();
		IafasUbigeoDao provDao = new IafasUbigeoDao(MySQLSessionFactory.getSqlSessionFactory());
		
		List<IafasUbigeo> lstProv = provDao.obtenerProvincias(codDep);
		
		for (IafasUbigeo p : lstProv) {
			this.fuentes.add(new SelectItem(p.getCodigoProvincia(),
					p.getCodigoProvincia() +" - " +p.getNombreProvincia()));
		}
		logger.info("[FIN:] Metodo : getProvincias");
		return this.provincias;

	}
	
	public List<SelectItem> getUbigeos() {
		logger.info("[INICIO:] Metodo : getUbigeos");
		
		this.ubigeos = new ArrayList<>();
		IafasUbigeoDao ubigeoDao = new IafasUbigeoDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasUbigeo objBn = new IafasUbigeo();
		objBn.setCodigoDepartamento(codDep);
		objBn.setCodigoProvincia(codProv);
		
		List<IafasUbigeo> lstUbigeo = ubigeoDao.obtenerUbigeos(objBn);
		
		for (IafasUbigeo p : lstUbigeo) {
			this.fuentes.add(new SelectItem(p.getCodigoUbigeo(),
					p.getCodigoUbigeo() +" - " +p.getNombreUbigeo()));
		}
		logger.info("[FIN:] Metodo : getUbigeos");
		return this.ubigeos;

	}*/
}
