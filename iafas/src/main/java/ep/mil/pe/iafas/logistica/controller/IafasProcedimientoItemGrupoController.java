package ep.mil.pe.iafas.logistica.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.logistica.dao.IafasProcedimientoItemGrupoDao;
import ep.mil.pe.iafas.logistica.model.IafasProcedimientoItemGrupo;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;
@Data
@ManagedBean(name="mbGrupoItem")
@SessionScoped
public class IafasProcedimientoItemGrupoController {
	
	private List<IafasProcedimientoItemGrupo> lista;
	private String cperiodoCodigo;
	private String ctipoProcedimientoCodigo;
	private String citemTipoCodigo;
	private String cestadocodigo;
	private String vusuarioCodigo;
	private String vusuarioCreador;
	private Date dusuarioCreador;
	private Date dusuarioFecha;
	private BigDecimal ntipoProcedimientoInicial;
	private BigDecimal ntipoProcedimientoMaximo;
	private String usuario;
	private String descriProceso;
	private String descripItem;

	
	 private static final Logger logger = Logger.getLogger(IafasProcedimientoItemGrupoController.class);
	 IafasProcedimientoItemGrupoDao grupoDao = new IafasProcedimientoItemGrupoDao(MySQLSessionFactory.getSqlSessionFactory());
	 
	 public IafasProcedimientoItemGrupoController() {
		 
		 HttpSession session=null; 
	 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	 		IafasUsuariosController usuarioSession = new IafasUsuariosController();
	 		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
	 		usuario = usuarioSession.getIdUsuario();
	 		verGrupo();
	 }
	 
	//BUSCAR PROVEEDORES
		
			public String verGrupo() {
				lista = new ArrayList<IafasProcedimientoItemGrupo>();
				logger.info("[INICIO] Metodo listaGrupo {}");
				try {
					IafasProcedimientoItemGrupo gru = new IafasProcedimientoItemGrupo();
					List<IafasProcedimientoItemGrupo> grupos =grupoDao.listaGrupoItem(gru);
					for (IafasProcedimientoItemGrupo l :grupos) {
						setCtipoProcedimientoCodigo(grupos.get(0).getCtipoProcedimientoCodigo());
						setCitemTipoCodigo(grupos.get(0).getCitemTipoCodigo());
						setNtipoProcedimientoInicial(grupos.get(0).getNtipoProcedimientoInicial());
						setNtipoProcedimientoMaximo(grupos.get(0).getNtipoProcedimientoMaximo());	
						setDescriProceso(grupos.get(0).getDescriProceso());
						setDescripItem(grupos.get(0).getDescripItem());
						logger.info("Datos a Registrar: " +grupos.toString());
						lista.add(l);
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Ver Error de grupo :"+e.getMessage());
				}
				
				 return "";
			}
	
			 //NUEVO PROVEEDORES
		    public String nuevoRegistro() {
		    	  String page = "insRegGrupoItem.xhtml";	
		    	  LimpiarCampos();
		    	  return page;
		    	  
		      }
		    
		    // GRABAR PROVEEDORE
		    
		    public String grabarGrupoItem() {
		    	
		    	try {
					IafasProcedimientoItemGrupo pro = new IafasProcedimientoItemGrupo();
					pro.setCperiodoCodigo("2021");	
					pro.setCtipoProcedimientoCodigo(ctipoProcedimientoCodigo);
					pro.setCitemTipoCodigo(citemTipoCodigo);
					pro.setVusuarioCreador(usuario);
					pro.setVusuarioCodigo(usuario);
					pro.setNtipoProcedimientoInicial(ntipoProcedimientoInicial);
					pro.setNtipoProcedimientoMaximo(ntipoProcedimientoMaximo);
					logger.info("Datos a Registrar: " +pro.toString());
					grupoDao.grabarGrupoItem(pro);
				} catch (Exception e) {
					// TODO: handle exception 
					System.out.println("Ver Error de procedimiento :"+e.getMessage());
				} 
		    	verGrupo();
		    	LimpiarCampos();
		    	 return "mainGrupoItem.xhtml";
		    	
		    }
		    
		    public void LimpiarCampos() {
		    	
		    	ntipoProcedimientoInicial=null;
		    	ntipoProcedimientoMaximo=null;
		    	ctipoProcedimientoCodigo= Constantes.VACIO;
		    	citemTipoCodigo= Constantes.VACIO;

		    }
		    
		    public String retornar() {
				LimpiarCampos();
				verGrupo();
				return "mainGrupoItem.xhtml";
			}
	 
}
