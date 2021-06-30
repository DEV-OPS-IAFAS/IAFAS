package ep.mil.pe.iafas.personal.controller;

import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ep.mil.pe.iafas.administrativo.maestros.combos.dao.IafasCombosDao;
import ep.mil.pe.iafas.administrativo.maestros.combos.model.IafasCombos;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.BDCon;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.configuracion.util.Utilitarios;
import ep.mil.pe.iafas.personal.dao.IafasPersonaVacacionesDao;
import ep.mil.pe.iafas.personal.model.IafasPersona;
import ep.mil.pe.iafas.personal.model.IafasPersonaVacaciones;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;

@Data
@ManagedBean(name="mbVacaciones")
@SessionScoped
public class IafasPersonaVacacionesController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<IafasPersonaVacaciones> lista;
	private List<IafasPersonaVacaciones> listaDetalle;
	private List<IafasPersonaVacaciones> listaPendiente;
	
	private String cperiodoCodigo;
	private Integer npersonaCodigo;
	private Integer npersonalVacacionesCodigo;
	private Date dpersonalVacacionesFecha;
	private Date dpersonalVacacionesInicio;
	private Date dpersonalVacacionesDin;
	private String cubigeoOrigen;
	private String cubigeoDestino;
	private String cestadoCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private String vusuarioCodigo;
	private String vusuarioAprueba;
	private Date dusuarioAprueba;
	private Date dusuarioFecha;
	private Integer npersonalTipoPlanillaCodigo;
	private Integer npersonalCantidadDias;
	private Integer npersonalCantidadHoras;
	
	private String nombreCodigo;
	private String origenUbi;
	private String destinoUbi;
	private String usuarioApro;
	
	
	private String vpersonaNumeroDocumento;
	private String vpersonaPaterno;
	private String vpersonaMaterno;
	private String vpersonaNombre;
	private Integer ncodigo;
	private Integer pcodigoPer;
	private short reg;
	private String usuario;
	
	private Integer pdetalleCod;
	private Integer pdetalleCodVac;
	
	private Date fecinicio;
	private Date fecfin;
	private Integer codigoinicio;
	private Integer codigovacacion;
	
	 private List<SelectItem> provincia;
	 private List<SelectItem> provinciaDestino;
	 private List<SelectItem> ubigeoDeta;
	 private List<SelectItem> ubigeoDestino;
	 private String codDepartamento;
	 private String codProvincia;
	 private String codDepartamentoDest;
	 private String codProvinciaDest;
	 private int dias;
	 private String destipoPlanilla;
	 private String estadoCivil;
	 private String vpersonalCargo;
	 private String desArea;
	 private String desGrado;
	 private Integer nrodocumetoPer;
	 
	 private StreamedContent img;
	 private byte[] b1;


	 private static final Logger logger = Logger.getLogger(IafasPersonaVacacionesController.class);
	 IafasPersonaVacacionesDao vacacionDao = new IafasPersonaVacacionesDao(MySQLSessionFactory.getSqlSessionFactory());
	 
	 
	 
	 public IafasPersonaVacacionesController() {
		 
		 HttpSession session=null; 
 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
 		IafasUsuariosController usuarioSession = new IafasUsuariosController();
 		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
 		usuario = usuarioSession.getIdUsuario();
 		//verPersonal() ;
 		listaPendienteAproba();
	 }
	 
	 // COMBO DEPENDIENTE PROVINCIA

      public List<SelectItem> cargarProvincia() {
      	provincia = new ArrayList<>();
          IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
               List<IafasCombos> lista = cb.getProvincia(codDepartamento);
               for(IafasCombos d : lista){
              	 provincia.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
               }
         
          return provincia;
          }
      
      public List<SelectItem> cargarProvinciaDest() {
    	  provinciaDestino = new ArrayList<>();
            IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
                 List<IafasCombos> lista = cb.getProvincia(codDepartamentoDest);
                 for(IafasCombos d : lista){
                	 provinciaDestino.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
                 }
           
            return provinciaDestino;
            }
      
      // COMBO DEPENDIENTE UBIGEO

      public List<SelectItem> cargarUbigeo() {
    	  ubigeoDeta = new ArrayList<>();
          IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
               List<IafasCombos> lista = cb.getUbigeoDet(codDepartamento+codProvincia);
               for(IafasCombos d : lista){
            	   ubigeoDeta.add(new SelectItem(d.getCodigo(), d.getDescripcion()));
               }
         
          return ubigeoDeta;
          }
      
      
      public List<SelectItem> cargarUbigeoDest() {
    	  ubigeoDestino = new ArrayList<>();
          IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
               List<IafasCombos> lista = cb.getUbigeoDet(codDepartamentoDest+codProvinciaDest);
               for(IafasCombos d : lista){
            	   ubigeoDestino.add(new SelectItem(d.getCodigo(), d.getDescripcion()));
               }
         
          return ubigeoDestino;
          }
      
	
	 // BUSCAR PERSONAL 
	 public String verPersonal() {
		 try {
			 
			 lista = new ArrayList<IafasPersonaVacaciones>();
			 IafasPersonaVacaciones ca = new IafasPersonaVacaciones();
			 ca.setVpersonaNumeroDocumento(vpersonaNumeroDocumento);
			 List<IafasPersonaVacaciones> vacacion =vacacionDao.listaPersonal(ca);
			 for (IafasPersonaVacaciones l :vacacion) {
				 setVpersonaPaterno(vacacion.get(0).getVpersonaPaterno());
				 setVpersonaMaterno(vacacion.get(0).getVpersonaMaterno());
				 setVpersonaNombre(vacacion.get(0).getVpersonaNombre());
				 setNcodigo(vacacion.get(0).getNcodigo());
				 setEstadoCivil(vacacion.get(0).getEstadoCivil());
				 setDesArea(vacacion.get(0).getDesArea());
				 setDesGrado(vacacion.get(0).getDesGrado());
				 setVpersonalCargo(vacacion.get(0).getVpersonalCargo());
				 verImagenPersonal(l.getNcodigo());
				 logger.info("Datos a Registrar: " +vacacion.toString());
				 lista.add(l);
				 LimpiarLista();
				 
			 }
			 
			
		} catch(Exception e) {
			System.out.println("Ver Error :"+e.getMessage());
		}
		 return "";
	 }
	 
	 public void mostrarDias() {
		 Utilitarios u = new Utilitarios();
		 if(dias>0) {
	     logger.info("Parametros de calculo {} "+dpersonalVacacionesInicio +" "+dias );
	     dpersonalVacacionesDin=u.verFechaFin(dpersonalVacacionesInicio, dias);
		 }
	 }
	 
	// GRABAR VACACIONES INICIAL 
	 
	 public String grabarVacaciones() {
		 try {
			 
			 IafasPersonaVacaciones vac = new IafasPersonaVacaciones();
			 vac.setCperiodoCodigo("2021");
			 vac.setNpersonaCodigo(ncodigo);
			 vac.setNpersonalVacacionesCodigo(npersonalVacacionesCodigo);
			 vac.setDpersonalVacacionesFecha(dpersonalVacacionesFecha);
			 vac.setDpersonalVacacionesInicio(dpersonalVacacionesInicio);
			 vac.setDpersonalVacacionesDin(dpersonalVacacionesDin);
			 vac.setCubigeoOrigen(cubigeoOrigen);
			 vac.setCubigeoDestino(cubigeoDestino);			
			 vac.setCestadoCodigo("PE");
			 vac.setVusuarioCreador(usuario);
			 vac.setDusuarioCreador(dusuarioCreador);
			 vac.setVusuarioCodigo(usuario);
			 vac.setVusuarioAprueba(usuario);
			 vac.setDusuarioAprueba(dusuarioAprueba);
			 vac.setDusuarioFecha(dusuarioFecha);
			 vac.setNpersonalTipoPlanillaCodigo(npersonalTipoPlanillaCodigo);
			 vac.setNpersonalCantidadDias(dias);
			 vac.setNpersonalCantidadHoras(npersonalCantidadHoras);
			 
			 logger.info("Datos a Registrar: " +vac.toString());
			 vacacionDao.grabarVacacionesInicio(vac);
			 listarDetalleVacacion(ncodigo);
			 LimpiarCampos() ;
			
		} catch (Exception e) {
			System.out.println("Ver Error :"+e.getMessage());
			// TODO: handle exception
		}
		return "";
	}

	 
	 //BUSCAR HISTORIAL DEL PERSONAL DE VACACIONES
	 
	 public void verHistorial() {
		 
		  try {
			  String pcodigoPerona = (String) extContext().getRequestParameterMap().get("pcodigoPer");	
				 pcodigoPer = Integer.valueOf(pcodigoPerona);
				 logger.info("Obteniendo Parametros  peronsal vacaciones " +pcodigoPer);
				 IafasPersonaVacaciones vac =new IafasPersonaVacaciones();
				 vac.setNcodigo(pcodigoPer);
				 listarDetalleVacacion(vac.getNcodigo());
		} catch (Exception e) {
			logger.error("[ERROR] Metodo verHistorial {} "+e);
		}
		
	
	 }
	 
	 //LISTA HISTORIAL
	 
	 public List<IafasPersonaVacaciones> listarDetalleVacacion (int codigo)
	 {
		 listaDetalle = new ArrayList<IafasPersonaVacaciones>();
		 logger.info("[INICIO] Metodo listarDetalleVacacion {}");
		 try {
			IafasPersonaVacaciones ca = new IafasPersonaVacaciones();
			List<IafasPersonaVacaciones> vacacion =vacacionDao.listaDetalleVaca(codigo);
			reg = (short) vacacion.size();
			logger.info("Cantidad de VACACIONES Encontrados "+reg);
			for (IafasPersonaVacaciones l :vacacion) {
				listaDetalle.add(l);
			}
			logger.info("[FIN] Metodo listarDetalleVacacion {}");
		} 
		 catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] Metodo listarDetalleVacacion {} "+e);
		}
		 return listaDetalle;
	 }

	 // LIMPIAR LISTA
	  public void LimpiarLista() {
		  
		  listaDetalle.clear();
	  }
	  
	  // LIMPIAR CAMPOS
	  
	  public void LimpiarCampos() {
		  
		  cubigeoOrigen =Constantes.VACIO;
		  cubigeoDestino =Constantes.VACIO;
		  dpersonalVacacionesInicio=null;
		  dpersonalVacacionesDin= null;
		  cestadoCodigo= Constantes.VACIO;
		  npersonalCantidadDias =0;
		  npersonalTipoPlanillaCodigo= 0;
		  cargarUbigeo().clear();
		  cargarUbigeoDest().clear();
		  cargarProvincia().clear();
		  cargarProvinciaDest().clear();
		  dias = 0;
		  codDepartamento = null;
		  codDepartamentoDest = null;
		  
		  
		  
	  }
	  
     // BUSCAR VACACIONES 
	  
		 public String verPersonalVacacion() {
			 try {
					
				 String pdetalle = (String) extContext().getRequestParameterMap().get("pdetalleCod");	
				 pdetalleCod = Integer.valueOf(pdetalle);
				 String pdetalleVac = (String) extContext().getRequestParameterMap().get("pdetalleCodVac");	
				 pdetalleCodVac = Integer.valueOf(pdetalleVac);
				 IafasPersonaVacaciones ca = new IafasPersonaVacaciones();
				 ca.setCodigoinicio(pdetalleCod);
				 ca.setCodigovacacion(pdetalleCodVac);
				 List<IafasPersonaVacaciones> vacacion =vacacionDao.listaEstado(ca);
				 for (IafasPersonaVacaciones l :vacacion) {
					 setFecinicio(vacacion.get(0).getFecinicio());
					 setFecfin(vacacion.get(0).getFecfin());
					 logger.info("Datos a Registrar: " +vacacion.toString());				 
				 }
				 
				
			} catch(Exception e) {
				System.out.println("Ver Error :"+e.getMessage());
			}
			 return "";
		 }
		 
		 public void verImagenPersonal(int codigo ) throws ClassNotFoundException {
	    	 if(img!=null) {
	    		 img = null;
	    		 setB1(null);
	    	 }
	    	try {
	  			Statement stm = null;
	  				Class.forName(BDCon.DRIVERBD);
	  				Connection cn = DriverManager.getConnection(BDCon.CONEXION_PRODUCCION);
	  				//Connection cn = DriverManager.getConnection(BDCon.CONEXION_LOCAL);
	  				stm = cn.createStatement();
	  				String sql = "SELECT BPERSONA_FOTO FROM iafas_persona "
	  						+ "where NPERSONA_CODIGO ="+codigo;
	  				ResultSet rs = stm.executeQuery(sql);
	  				logger.info("Consultando Imagen del Personal : {}" +codigo);
	  				while(rs.next()) {
	  					b1 =  rs.getBytes("BPERSONA_FOTO");
	  					logger.info("blobBD : {}" +rs.getBytes("BPERSONA_FOTO"));
	  					if(rs.getBytes("BPERSONA_FOTO")==null){
	  						img = null;
	  						//messageBlob = "El personal no tiene registrado la Foto";
	  					}
	  					else {
	  						logger.info("Leyendo stream del archivo Blob");
	  						InputStream in = new ByteArrayInputStream(b1);
	  						new DefaultStreamedContent();
	  					    img = DefaultStreamedContent.builder().stream(() -> in).build();
	  					    //messageBlob = "";
	  					    logger.info("Se Cargo la Imagen del Personal {} " +codigo);
	  					}	
	  				}
	  				cn.close();
	  				
	  					
	  			
	  		} catch (SQLException e) {
	  			// TODO: handle exception
	  			logger.error("ERROR al Obtener la Imagen : "+e.getMessage());
	  		}
	    	catch (Exception e) {
	  		// TODO: handle exception
	  		logger.error("ERROR al Obtener la Imagen : "+e.getMessage());
	  	}
	    }
	   private ExternalContext extContext() {
	          FacesContext c = FacesContext.getCurrentInstance();
	          ExternalContext ec = c.getExternalContext();
	          return ec;
	      }
	   
	   // ACTUALIZAR ESTADO
	 
	     public String ActualizarEstado() {
     		int reg = 0;		
     		try {
     			logger.info("Ingreso al Metodo Actualizar estado");
     			 IafasPersonaVacaciones ca = new IafasPersonaVacaciones();
     			 ca.setNpersonaCodigo(pdetalleCod);
     			 ca.setNpersonalVacacionesCodigo(pdetalleCodVac);
     			 ca.setCestadoCodigo(cestadoCodigo);
     			 ca.setVusuarioCreador(usuario);			 		 
     			 reg = vacacionDao.ActualizarVacacion(ca) ;			
     			 logger.info("Actualizo el estado {} "+pdetalleCod);   
     			listarDetalleVacacion(pdetalleCod);
     			listaPendienteAproba();
     			 
     		} catch (Exception e) {
     			// TODO: handle exception
     			logger.error("[ERROR] No se Registro Actualizacion : "+e);
     		}
     	
          	return "";
     	}
	   
	     
	     // LISTA DE PENDIENTES 
	     
 
	       
     public List<IafasPersonaVacaciones> listaPendienteAproba(){
    	 
    	 listaPendiente = new ArrayList<IafasPersonaVacaciones>();
    	 try {
    		 logger.info("Ingreso lista de pendientes VACQCIONES");
    		 IafasPersonaVacaciones pe = new IafasPersonaVacaciones();
    		 List<IafasPersonaVacaciones> pendiente = vacacionDao.listaDetalleVacaAprobacion(pe);
    		 reg = (short) pendiente.size();
    		 for(IafasPersonaVacaciones p :pendiente) {   			 
    			 listaPendiente.add(p);
    		 }
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] lista Pendiente :"+e);
		}
    	 
    	 return listaPendiente;
     }
	     
}
