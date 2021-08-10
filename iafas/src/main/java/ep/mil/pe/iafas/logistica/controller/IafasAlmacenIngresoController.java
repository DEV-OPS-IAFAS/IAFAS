package ep.mil.pe.iafas.logistica.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.logistica.dao.IafasAlmacenIngresoDao;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenIngreso;
import ep.mil.pe.iafas.logistica.model.IafasAlmacenIngresoDetalle;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
@ManagedBean(name="mbAlmacenIng")
@SessionScoped
public class IafasAlmacenIngresoController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String periodo;
	private String mes;
	private String usuario;
	
	private String periodoOrden;
	private int numOrden;
	private Date fechaIngreso;
	private String guiaIngreso;
	private int almacen;
	private int typeMessages;
	
	// labels
	private Date labelfechaOrden;
	private String labeldescripOrden;
	private String labelRuc;
	private String labelRazonSocial;
	// prueba
	
	private List<IafasAlmacenIngreso> lista;
	private List<IafasAlmacenIngresoDetalle> listaDetalle;
	
	private static final Logger logger = Logger.getLogger(IafasAlmacenIngresoController.class);
	
   IafasAlmacenIngresoDao almacenDao = new IafasAlmacenIngresoDao(MySQLSessionFactory.getSqlSessionFactory());
	public IafasAlmacenIngresoController() {
		// TODO Auto-generated constructor stub
		HttpSession session=null; 
		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		usuario = usuarioSession.getIdUsuario();
	}

	public List<IafasAlmacenIngreso> listarAlmacenIngreso(){
		lista = new ArrayList<IafasAlmacenIngreso>();
		IafasAlmacenIngreso alm = new IafasAlmacenIngreso();
		alm.setPeriodo(periodo);
		alm.setCmesCodigo(mes);
		List<IafasAlmacenIngreso> ingreso = almacenDao.showAlmacenIngreso(alm);
		ingreso.forEach((p) -> lista.add(p));	
		return lista;	
	}
	
	public void buscarOC(){
		listaDetalle = new ArrayList<IafasAlmacenIngresoDetalle>();
		IafasAlmacenIngresoDetalle det = new IafasAlmacenIngresoDetalle();
		det.setPeriodoOrden(periodoOrden);
		det.setNroOrden(numOrden);
		
		List<IafasAlmacenIngresoDetalle> registros = almacenDao.showItemsOrden(det);
		logger.info("Busqueda de Orden :"+registros.size());
		for(IafasAlmacenIngresoDetalle r :registros) {
		    setLabelfechaOrden(r.getFechaOrden());
		    setLabelRazonSocial(r.getRazonSocial());
		    setLabelRuc(r.getRuc());
		    setLabeldescripOrden(r.getDescOrden());
		    listaDetalle.add(r);
		}
		
	}
	
	public String nuevoRegistro() {

		return "insRegAlmacenIng.xhtml";
	}
	
	public String insertaIngreso() {
		try {
			IafasAlmacenIngreso ing = new IafasAlmacenIngreso();
			ing.setPeriodo(periodo);
			ing.setNalmacenCodigo(almacen);
			ing.setCmesCodigo(mes);
			ing.setCperiodoOrden(periodoOrden);
			ing.setCordenCodigo(numOrden);
			ing.setDalmacenIngresosFecha(fechaIngreso);
			ing.setValmacenIngresosGuia(guiaIngreso);
			ing.setUsuario(usuario);
			ing.setTipo("I");
			
			int t = almacenDao.saveAlmacenIngreso(ing);
			logger.info("Registro Correctamente Almacen Ingreso "+t);
			
			System.out.println("valores : "+listaDetalle.toString()+" "+listaDetalle.get(0).getValmacenIngresoDetalleLote()+" "
					+listaDetalle.get(0).getDalmacenIngresoDetalleVencimiento());
			logger.info("Ingresando detalle de NEAS ");
			insertarDetalleIngreso();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error al Momento de Registro  Almacen Ingreso {} "+e.getMessage());
			showMessages(0);
		}
		listarAlmacenIngreso();
		return "mainAlmacenIngreso.xhtml";
	}
	
	public void insertarDetalleIngreso() {
		try {
			IafasAlmacenIngresoDetalle det = new IafasAlmacenIngresoDetalle();
			
			for(int i=0; i< listaDetalle.size();i++) {
				det.setPeriodo(periodo);
				det.setNalmacenIngresosCodigo(0);
				det.setNalmacenCodigo(almacen);
				det.setNitemCodigo(listaDetalle.get(i).getCodigoItem());
				det.setNalmacenIngresoDetalleCantidad(listaDetalle.get(i).getCantidad());
				det.setNalmacenIngresoDetallePrecio(listaDetalle.get(i).getPrecio());
				det.setDalmacenIngresoDetalleVencimiento(listaDetalle.get(i).getDalmacenIngresoDetalleVencimiento());
				det.setValmacenIngresoDetalleLote(listaDetalle.get(i).getValmacenIngresoDetalleLote());
				det.setVusuarioCodigo(usuario);
				det.setTipo("I");
				almacenDao.saveAlmacenIngresoDetails(det);
				logger.info("Se registro Correctamente item {} "+det.getNitemCodigo());
			}
			showMessages(1);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error Registro Items Almacen Ingreso {} "+e.getMessage());
			showMessages(0);
		}
	}
	
	private String showMessages(int opcion) {
		String messages = "";
		switch (opcion) {
		case 0 : typeMessages=0;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 1 : typeMessages=1;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 2 : typeMessages=2;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		default : messages="";break;
		}
		return messages;
	}
	
}
