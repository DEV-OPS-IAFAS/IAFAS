package ep.mil.pe.iafas.logistica.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.logistica.dao.IafasProveedoresDao;
import ep.mil.pe.iafas.logistica.model.IafasProveedores;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

@Data
@ManagedBean(name="mbProveedores")
@SessionScoped
public class IafasProveedoresController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<IafasProveedores> lista;
	private String cproveedorRuc;
	private String vproveedorRazonSocial;
	private String vproveedorRepresentante;
	private String vproveedorDireccion;
	private String cproveedorTipo;
	private String vproveedorTelefono;
	private String cestadoCodigo;
	private String vusuarioCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private Date dusuarioFecha;
	private String usuario;
	
	private String pdetalleCod;
	private String pdetalleCodRnp;
	
	//BANCO 
	private String vproveedorBancoCci;
	private String vproveedorBancoCuenta;
	private String cbancoCodigo; 
	//RNP
	private String vproveedorRnpCodigo;
	private Date dproveedorRnpInicio;
	private Date dproveedorRnpVigencia;
	
		 
	 private static final Logger logger = Logger.getLogger(IafasProveedoresController.class);
	 IafasProveedoresDao proveedoresDao = new IafasProveedoresDao(MySQLSessionFactory.getSqlSessionFactory());
	
	public IafasProveedoresController() {
		
		    HttpSession session=null; 
	 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	 		IafasUsuariosController usuarioSession = new IafasUsuariosController();
	 		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
	 		usuario = usuarioSession.getIdUsuario();
	 		verProveedor();
	}
	//BUSCAR PROVEEDORES
	
		public String verProveedor() {
			lista = new ArrayList<IafasProveedores>();
			logger.info("[INICIO] Metodo listarProveedores {}");
			try {
				IafasProveedores prov = new IafasProveedores();
				List<IafasProveedores> proveedores =proveedoresDao.listaProveedor(prov);
				for (IafasProveedores l :proveedores) {
					setCproveedorRuc(proveedores.get(0).getCproveedorRuc());
					setVproveedorRazonSocial(proveedores.get(0).getVproveedorRazonSocial());
					setVproveedorRepresentante(proveedores.get(0).getVproveedorRepresentante());
					setVproveedorDireccion(proveedores.get(0).getVproveedorDireccion());
					setCproveedorTipo(proveedores.get(0).getCproveedorTipo());
					setVproveedorTelefono(proveedores.get(0).getVproveedorTelefono());
					logger.info("Datos a Registrar: " +proveedores.toString());
					lista.add(l);
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Ver Error :"+e.getMessage());
			}
			
			 return "";
		}
    //NUEVO PROVEEDORES
	    public String nuevoRegistro() {
	    	  String page = "insRegProveedores.xhtml";	
	    	  LimpiarCampos();
	    	  return page;
	    	  
	      }
	    
	    // GRABAR PROVEEDORE
	    
	    public String grabarProveedores() {
	    	
	    	try {
				IafasProveedores pro = new IafasProveedores();
				pro.setCproveedorRuc(cproveedorRuc);
				pro.setVproveedorRazonSocial(vproveedorRazonSocial);
				pro.setVproveedorRepresentante(vproveedorRepresentante);
				pro.setVproveedorDireccion(vproveedorDireccion);
				pro.setCproveedorTipo(cproveedorTipo);
				pro.setVproveedorTelefono(vproveedorTelefono);
				pro.setVusuarioCreador(usuario);
				pro.setVusuarioCodigo(usuario);
				logger.info("Datos a Registrar: " +pro.toString());
				proveedoresDao.grabarProveedor(pro);
			} catch (Exception e) {
				// TODO: handle exception 
				System.out.println("Ver Error :"+e.getMessage());
			} 
	    	verProveedor();
	    	LimpiarCampos();
	    	 return "mainProveedores.xhtml";
	    	
	    }
	    
	    // LIMPIAR CAMPOS
	    public void LimpiarCampos() {
	    	
	    	cproveedorRuc=null;
	    	vproveedorRazonSocial=null;
	    	vproveedorRepresentante=null;
	    	vproveedorDireccion=null;
	    	vproveedorTelefono=null;
	    	cproveedorTipo= Constantes.VACIO;
	    	vproveedorBancoCci= null;
	    	vproveedorBancoCuenta= null;
	    	cbancoCodigo= Constantes.VACIO;
	    	dproveedorRnpInicio= null;
	    	dproveedorRnpVigencia= null;
	    	vproveedorRnpCodigo= null;
	    	
	    }
	    
	    // VER PROVEERDOR
	    
	    public void VerProveedor() {
	    	
	    	 pdetalleCod =(String) extContext().getRequestParameterMap().get("pdetalleCod");
	    	  logger.info("ver proveedor " +pdetalleCod);
	    	  IafasProveedores pe= new IafasProveedores();
	    	  pe.setCproveedorRuc(pdetalleCod);
	    	  List<IafasProveedores> verprovee =proveedoresDao.verProveedor(pe);
	    	  for (IafasProveedores l : verprovee) {
	    		  setCproveedorRuc(l.getCproveedorRuc());
	    		  setVproveedorRazonSocial(l.getVproveedorRazonSocial());
	    		  
	    	  }
	    }
	    
	    public void VerProveedorRnp() {
	    	
	    	pdetalleCodRnp =(String) extContext().getRequestParameterMap().get("pdetalleCodRnp");
	    	  logger.info("ver proveedor " +pdetalleCodRnp);
	    	  IafasProveedores pe= new IafasProveedores();
	    	  pe.setCproveedorRuc(pdetalleCodRnp);
	    	  List<IafasProveedores> verprovee =proveedoresDao.verProveedor(pe);
	    	  for (IafasProveedores l : verprovee) {
	    		  setCproveedorRuc(l.getCproveedorRuc());
	    		  setVproveedorRazonSocial(l.getVproveedorRazonSocial());
	    		  
	    	  }
	    }
	    
 // GRABAR BANCO
	    
	    public String grabarBanco() {
	    	
	    	try {
	    		IafasProveedores pro = new IafasProveedores();
				pro.setCproveedorRuc(pdetalleCod);
				pro.setVproveedorBancoCci(vproveedorBancoCci);
				pro.setVproveedorBancoCuenta(vproveedorBancoCuenta);
				pro.setCbancoCodigo(cbancoCodigo);
				pro.setVusuarioCreador(usuario);
				pro.setVusuarioCodigo(usuario);
				logger.info("Datos a Registrar: " +pro.toString());
				proveedoresDao.grabarBanco(pro);
			} catch (Exception e) {
				// TODO: handle exception 
				System.out.println("Ver Error :"+e.getMessage());
			} 
	    	verProveedor();
	    	LimpiarCampos();
	    	 return "mainProveedores.xhtml";
	    	
	    }
	    
	    
	    
	    public String grabarRnp() {
	    	
	    	try {
	    		IafasProveedores pro = new IafasProveedores();
				pro.setCproveedorRuc(pdetalleCodRnp);
				pro.setVproveedorRnpCodigo(vproveedorRnpCodigo);
				pro.setDproveedorRnpInicio(dproveedorRnpInicio);
				pro.setDproveedorRnpVigencia(dproveedorRnpVigencia);
				pro.setVusuarioCreador(usuario);
				pro.setVusuarioCodigo(usuario);
				logger.info("Datos a Registrar: " +pro.toString());
				proveedoresDao.grabarRnp(pro);
			} catch (Exception e) {
				// TODO: handle exception 
				System.out.println("Ver Error :"+e.getMessage());
			} 
	    	verProveedor();
	    	LimpiarCampos();
	    	 return "mainProveedores.xhtml";
	    	
	    }
	    
	    public String retornar() {
			LimpiarCampos();
			VerProveedor();
			return "mainProveedores.xhtml";
		}
	      private ExternalContext extContext() {
	          FacesContext c = FacesContext.getCurrentInstance();
	          ExternalContext ec = c.getExternalContext();
	          return ec;
	      }
}
