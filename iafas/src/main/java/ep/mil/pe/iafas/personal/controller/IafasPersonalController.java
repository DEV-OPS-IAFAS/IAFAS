package ep.mil.pe.iafas.personal.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.apache.log4j.net.SMTPAppender;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.sun.faces.taglib.html_basic.InputSecretTag;

import ep.mil.pe.iafas.administrativo.maestros.combos.controller.IafasCombosController;
import ep.mil.pe.iafas.administrativo.maestros.combos.dao.IafasCombosDao;
import ep.mil.pe.iafas.administrativo.maestros.combos.model.IafasCombos;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.BDCon;
import ep.mil.pe.iafas.personal.dao.IafasPersonalDao;
import ep.mil.pe.iafas.personal.model.IafasPersona;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

@Data
@ManagedBean(name="mbPersonal")
@SessionScoped
public class IafasPersonalController implements Serializable{
	
	private List<IafasPersona> lista;
	private List<IafasPersona> listaFamilia;
	
	 private String vpersonaNumeroDoc; 
	 private String vpersonaPaterno;
	 private String vpersonaMaterno ;
	 private String vpersonaNombres ;
	 private String cpersonaGenero ;
	 private Date  dpersonaNacimiento; 
	 private Integer npersonaCodigo ;
	 private String ctipoDocumentocodigo; 
	 private Integer nestadoCivilCodigo ;
	 private String vpersonaCelular ;
	 private String vpersonaTelefono ;
	 private String vpersonaCorreo ;
	 private String vpersonaDireccion; 
	 private String vusuarioCreador ;
	 private Date dusuarioCreador ;
	 private String vusuarioCodigo ;
	 private Date dusuarioFecha;
	 private String usuario;
	 private short reg;
	 
	 private String genero;
	 private String documento;
	 private String estadoCivil;
	 private String desArea;
	 private String desGrado;
	 private String ndetalle;
	 
	 private String pSecuencia;
	 private int pSecDetalle;
	 private String pdetalleDoc;
	 private int pSecDetallef;
	 
	 private String cperiodoCodigo;
	 private Integer ngradoCodigo;
	 private String cgradoTipo;
	 private String areaLaboral;
	 private String vpersonalCargo;
	 private String cestadoCodigo;
	 private String vcip;
	 private String messages;
	 private int typeMessages;
	 
	 private Integer npersonaFamiliaCodigo;
	 private Integer npersonaFamilaPersona;
	 private Integer ntipoFamiliaCodigo;
	 
	 private UploadedFile imagenPersonal;
	 private StreamedContent img;
	 private String messageBlob;
	 private byte[] b1;
	 
	 private List<SelectItem> grados;

	 private static final long serialVersionUID = 1L;
	 
	 private static final Logger logger = Logger.getLogger(IafasPersonalController.class);
     IafasPersonalDao personalDao = new IafasPersonalDao(MySQLSessionFactory.getSqlSessionFactory());
     
     public IafasPersonalController() {
    		HttpSession session=null; 
    		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    		IafasUsuariosController usuarioSession = new IafasUsuariosController();
    		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
    		usuario = usuarioSession.getIdUsuario();
    	  
    		
    		listaPersonalAdm();
     }
     
     public String retorno() {
    	 String retorno="mainPersonal.xhtml";
    	 listaPersonalAdm();
    	 LimpiarCampos();
    	 return retorno;
     }
     
     
     public List<IafasPersona> listaPersonalAdm(){
    	 
    	 lista = new ArrayList<IafasPersona>();
    	 try {
    		 IafasPersona pe = new IafasPersona();
    		 List<IafasPersona> personal = personalDao.listaPersonal(pe);
    		 reg = (short) personal.size();
    		 for(IafasPersona p :personal) {   			 
    			 lista.add(p);
    		 }
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] lista Personal :"+e);
		}
    	 
    	 return lista;
     }
     
     
     
      public List<IafasPersona> listarMovimientoFam(int codigo){
		listaFamilia = new ArrayList<IafasPersona>();
		logger.info("[INICIO] Metodo listarMovimientoFam {}");
		try {
			 IafasPersona ca = new IafasPersona();
			 
			List<IafasPersona> familia = personalDao.listaFamilia(codigo);			
			reg = (short) familia.size();
			logger.info("Cantidad de PErsonas Encontrados "+reg);
			for(IafasPersona l : familia) {
				listaFamilia.add(l);
			}
			logger.info("[FIN] Metodo listarMovimientoFam {}");
		}
		catch(Exception e) {
			logger.error("[ERROR] Metodo listarMovimientoFam :"+e);
		}
		return listaFamilia;
	} 
      
      public void verDetalleFamilia() {
		try {
			 pSecuencia = (String) extContext().getRequestParameterMap().get("pSecuencia");			 
			 IafasPersona pe = new IafasPersona();
			 pe.setNpersonaCodigo(Integer.valueOf(pSecuencia));
			 System.out.println(" fILTRO DEL LA LISTA: "+pSecuencia);
			 listarMovimientoFam(pe.getNpersonaCodigo());
			 
		}
		catch(Exception e) {
			logger.error("[ERROR] Metodo verDetalleRegistroCA {} "+e);
		}

		
	}
      
      public String nuevoRegistro() {
    	  String page = "insRegistroPersonal.xhtml";	  
    	  return page;
    	  
      }
      
    
      public String nuevoRegFamilia() {
    	  String page = "insRegistroFamilia.xhtml";
    	  String paramCodPersona = (String) extContext().getRequestParameterMap().get("pSecDetalle");	
    	  pSecDetalle = Integer.valueOf(paramCodPersona);
    	  logger.info("Codigo de Persona para la Referencia {} "+pSecDetalle);
    	  return page;
    	  
      }
      
      // Combo dependediente
      public List<SelectItem> cargarGrados() {
      	grados = new ArrayList<>();
          IafasCombosDao cb = new IafasCombosDao(MySQLSessionFactory.getSqlSessionFactory());    
               List<IafasCombos> lista = cb.getTipoGrado(cgradoTipo);
               for(IafasCombos d : lista){
              	 grados.add(new SelectItem(d.getCodigo(),d.getCodigo()+"-"+ d.getDescripcion()));
               }
         
          return grados;
          }
      
       public String grabarPersonal() {
		int reg = 0;		
		try {
			logger.info("Ingreso al Metodo Grabar Personal");
			 IafasPersona ca = new IafasPersona();
	
			 ca.setVpersonaNumeroDoc(vpersonaNumeroDoc);
			 ca.setVpersonaPaterno(vpersonaPaterno);
			 ca.setVpersonaMaterno(vpersonaMaterno);
			 ca.setVpersonaNombres(vpersonaNombres);
			 ca.setCpersonaGenero(cpersonaGenero);
			 ca.setDpersonaNacimiento(dpersonaNacimiento);
			 ca.setCtipoDocumentocodigo(ctipoDocumentocodigo);
			 ca.setNestadoCivilCodigo(nestadoCivilCodigo);
			 ca.setVpersonaCelular(vpersonaCelular);
			 ca.setVpersonaTelefono(vpersonaTelefono);
			 ca.setVpersonaCorreo(vpersonaCorreo);
			 ca.setVpersonaDireccion(vpersonaDireccion);
			 ca.setVusuarioCreador(usuario);
			 ca.setVusuarioCodigo(usuario);
			 ca.setCperiodoCodigo(cperiodoCodigo);
			 ca.setNgradoCodigo(ngradoCodigo);
			 ca.setCgradoTipo(cgradoTipo);
			 ca.setCareaLaboralCodigo(areaLaboral);
			 ca.setVpersonalCargo(vpersonalCargo);
			 ca.setCestadoCodigo(cestadoCodigo);
			 ca.setVcip(vcip);			 
			 reg = personalDao.grabarPersonalAdm(ca); 
			 logger.info("Inserto Persona Correctamente {} "+vpersonaNumeroDoc);
			 updatePhoto(vpersonaNumeroDoc);
			 showMessages(1);
			 retorno();
		} catch (Exception e) {
			// TODO: handle exception
			typeMessages=0;
			showMessages(0);
			logger.error("[ERROR] No se Registro Personal : "+e);
		}
	
     	return "mainPersonal.xhtml";
	}
       
    public void updatePhoto(String numPersonal) {
    	try {
			if(imagenPersonal!=null) {
				Class.forName(BDCon.DRIVERBD);
				//Connection cn = DriverManager.getConnection(BDCon.CONEXION_LOCAL);
				Connection cn = DriverManager.getConnection(BDCon.CONEXION_PRODUCCION);
				PreparedStatement pst = cn.prepareStatement("UPDATE iafas_persona SET "
						+ "BPERSONA_FOTO = ? "
						+ "WHERE "
						+ "VPERSONA_NUMERO_DOCUMENTO = ?");
				pst.setBinaryStream(1,imagenPersonal.getInputStream());
				pst.setString(2, numPersonal);
				pst.executeUpdate();
				cn.close();
				logger.info("Se actualizo la Imagen del Personal {} " +numPersonal);
			}
			else {
				logger.info("No se selecciono Imagen");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR al Actualizar la Imagen : ", e.getCause());
		}
    }
    
   
    public void verImagen() throws ClassNotFoundException {
  	  String paramCodigo = (String) extContext().getRequestParameterMap().get("pSecDetallef");	
  	  pSecDetallef = Integer.valueOf(paramCodigo);
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
						+ "where NPERSONA_CODIGO ="+pSecDetallef;
				ResultSet rs = stm.executeQuery(sql);
				logger.info("Consultando Imagen del Personal : {}" +pSecDetallef);
				while(rs.next()) {
					b1 =  rs.getBytes("BPERSONA_FOTO");
					logger.info("blobBD : {}" +rs.getBytes("BPERSONA_FOTO"));
					if(rs.getBytes("BPERSONA_FOTO")==null){
						img = null;
						messageBlob = "El personal no tiene registrado la Foto";
					}
					else {
						logger.info("Leyendo stream del archivo Blob");
						InputStream in = new ByteArrayInputStream(b1);
						new DefaultStreamedContent();
					    img = DefaultStreamedContent.builder().stream(() -> in).build();
					    messageBlob = "";
					    logger.info("Se Cargo la Imagen del Personal {} " +pSecDetallef);
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
    
  public void cerrarFoto() {
	  setImg(null);
	  setB1(null);
  }
     public void LimpiarCampos() {
    	 
    	 vpersonaNumeroDoc ="";
 	    vpersonaPaterno ="";
 	    vpersonaMaterno ="";
 	    vpersonaNombres ="";
 	    cpersonaGenero ="";
 	    dpersonaNacimiento =null;
 	    npersonaCodigo =null;
 	    ctipoDocumentocodigo ="";
 	    nestadoCivilCodigo =null;
 	    vpersonaCelular ="";
 	    vpersonaTelefono ="";
 	    vpersonaCorreo ="";
 	    vpersonaDireccion ="";
 	    areaLaboral="";
 	    vpersonalCargo="";
 	    vcip="";
 	
     }
      
     
     public String grabarFamilia() {
 		int reg = 0;		
 		try {
 			logger.info("Ingreso al Metodo Grabar Personal");
 			 IafasPersona ca = new IafasPersona();
 	
 			 ca.setVpersonaNumeroDoc(vpersonaNumeroDoc);
 			 ca.setVpersonaPaterno(vpersonaPaterno);
 			 ca.setVpersonaMaterno(vpersonaMaterno);
 			 ca.setVpersonaNombres(vpersonaNombres);
 			 ca.setCpersonaGenero(cpersonaGenero);
 			 ca.setDpersonaNacimiento(dpersonaNacimiento);
 			 ca.setCtipoDocumentocodigo(ctipoDocumentocodigo);
 			 ca.setNestadoCivilCodigo(nestadoCivilCodigo);
 			 ca.setVpersonaCelular(vpersonaCelular);
 			 ca.setVpersonaTelefono(vpersonaTelefono);
 			 ca.setVpersonaCorreo(vpersonaCorreo);
 			 ca.setVpersonaDireccion(vpersonaDireccion);
 			 ca.setVusuarioCreador(usuario);
 			 ca.setVusuarioCodigo(usuario);
 			 ca.setNtipoFamiliaCodigo(ntipoFamiliaCodigo);
 			 ca.setNpersonaCodigo(pSecDetalle);				 
 			 reg = personalDao.grabarPersonalFamilia(ca); 
 			 logger.info("Inserto Persona Correctamente {} "+vpersonaNumeroDoc);
 			 updatePhoto(vpersonaNumeroDoc);
 			 showMessages(1);
 			 retorno();
 		} catch (Exception e) {
 			// TODO: handle exception
 			typeMessages=0;
 			showMessages(0);
 			logger.error("[ERROR] No se Registro Personal : "+e);
 		}
 	
      	return "mainPersonal.xhtml";
 	}
     
   
      	public void verPersonal(){
		pdetalleDoc = (String) extContext().getRequestParameterMap().get("pdetalleDoc");
	     logger.info("Obteniendo Parametros  LOOOO " +pdetalleDoc);
	    IafasPersona per = new IafasPersona();
	    per.setVpersonaNumeroDoc(pdetalleDoc);
	    System.out.println("pdetalleDoc : "+pdetalleDoc);
		List<IafasPersona> editPersonal = personalDao.listaEditarFamilia(per);
		for(IafasPersona l : editPersonal) {
			    setVpersonaNumeroDoc(l.getVpersonaNumeroDoc());
			    setVpersonaPaterno(l.getVpersonaPaterno());
			    setVpersonaMaterno(l.getVpersonaMaterno());
			    setVpersonaNombres(l.getVpersonaNombres());
			    setCpersonaGenero(l.getCpersonaGenero());
			    setDpersonaNacimiento(l.getDpersonaNacimiento());
			    setNestadoCivilCodigo(l.getNestadoCivilCodigo());
			    setVpersonaCelular(l.getVpersonaCelular());
			    setVpersonaTelefono(l.getVpersonaTelefono());
			    setVpersonaCorreo(l.getVpersonaCorreo());
			    setVpersonaDireccion(l.getVpersonaDireccion());
			    setCperiodoCodigo(l.getCperiodoCodigo());
			    setNgradoCodigo(l.getNgradoCodigo());
			    setCgradoTipo(l.getCgradoTipo());
			    setAreaLaboral(l.getCareaLaboralCodigo());
			    setVpersonalCargo(l.getVpersonalCargo());
			    setCestadoCodigo(l.getCestadoCodigo());
			    setVcip(l.getVcip());
			    cargarGrados();
			}
		
	}
      
      	 public String EditarPersonal() {
     		int reg = 0;		
     		try {
     			logger.info("Ingreso al Metodo Grabar Personal");
     			 IafasPersona ca = new IafasPersona();
     			 ca.setVpersonaNumeroDoc(pdetalleDoc);
     			 ca.setVpersonaPaterno(vpersonaPaterno);
     			 ca.setVpersonaMaterno(vpersonaMaterno);
     			 ca.setVpersonaNombres(vpersonaNombres);
     			 ca.setCpersonaGenero(cpersonaGenero);
     			 ca.setDpersonaNacimiento(dpersonaNacimiento);
     			 ca.setCtipoDocumentocodigo(ctipoDocumentocodigo);
     			 ca.setNestadoCivilCodigo(nestadoCivilCodigo);
     			 ca.setVpersonaCelular(vpersonaCelular);
     			 ca.setVpersonaTelefono(vpersonaTelefono);
     			 ca.setVpersonaCorreo(vpersonaCorreo);
     			 ca.setVpersonaDireccion(vpersonaDireccion);
     			 ca.setVusuarioCreador(usuario);
     			 ca.setVusuarioCodigo(usuario);
     			 ca.setCperiodoCodigo(cperiodoCodigo);
     			 ca.setNgradoCodigo(ngradoCodigo);
     			 ca.setCgradoTipo(cgradoTipo);
     			 ca.setCareaLaboralCodigo(areaLaboral);
     			 ca.setVpersonalCargo(vpersonalCargo);
     			 ca.setCestadoCodigo(cestadoCodigo);
     			 ca.setVcip(vcip);			 
     			 reg = personalDao.grabarEdit(ca); 
     			 logger.info("Actualizo Persona Correctamente {} "+vpersonaNumeroDoc);    			
     			 showMessages(1);
     			 retorno();
     		} catch (Exception e) {
     			// TODO: handle exception
     			typeMessages=0;
     			showMessages(0);
     			logger.error("[ERROR] No se Registro Personal : "+e);
     		}
     	
          	return "mainPersonal.xhtml";
     	}
     
     
   	private String showMessages(int opcion) {
		messages = "";
		switch (opcion) {
		case 0 : typeMessages=0;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 1 : typeMessages=1;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 2 : typeMessages=2;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		default : messages="";break;
		}
		return messages;
	}
  	
      private ExternalContext extContext() {
          FacesContext c = FacesContext.getCurrentInstance();
          ExternalContext ec = c.getExternalContext();
          return ec;
      }
     
}
