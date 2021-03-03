package ep.mil.pe.iafas.seguridad.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.seguridad.dao.IafasUsuariosMenuDao;
import ep.mil.pe.iafas.seguridad.model.IafasUsuariosMenu;
import lombok.Data;

@Data
@ManagedBean(name = "iafasUsuariosMenuController")
@SessionScoped
public class IafasUsuarioMenuController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(IafasUsuarioMenuController.class.getName());

	private List<IafasUsuariosMenu> listaMenuNivel1;
	private List<IafasUsuariosMenu> listaMenuNivel2;

	public IafasUsuarioMenuController() {
		obtenerPrivilegiosUsuario();
	}

	public void obtenerPrivilegiosUsuario() {
		logger.debug("[INICIO]: Metodo: obtenerPrivilegiosUsuario");
		listaMenuNivel1 = new ArrayList<IafasUsuariosMenu>();
		listaMenuNivel2 = new ArrayList<IafasUsuariosMenu>();
		
		HttpSession session=null; 
 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		logger.debug("Codigo de Usuario : "+ codUsu);
		
		IafasUsuariosMenuDao objMenuDao = new IafasUsuariosMenuDao(MySQLSessionFactory.getSqlSessionFactory());
		try {
			List<IafasUsuariosMenu> moduloBuscadoN1 = objMenuDao.SelectListFiltro1(codUsu);
			logger.debug("Cantidad de modulos que el usuarios posee: " + moduloBuscadoN1.size());
			if (moduloBuscadoN1.size() > 0) {
				for (IafasUsuariosMenu objetoBeanN1 : moduloBuscadoN1) {
					logger.debug("Codigo de modulo en objetoBeanN1 " + objetoBeanN1.getCmoduloCodigo());
					listaMenuNivel1.add(objetoBeanN1);
					
					List<IafasUsuariosMenu> moduloBuscadoN2 = objMenuDao.SelectListFiltro2(objetoBeanN1);
						for(IafasUsuariosMenu objetoBeanN2 : moduloBuscadoN2) {
							logger.debug("Codigo de modulo en objetoBeanN2 " + objetoBeanN2.getCmoduloCodigo());
							listaMenuNivel2.add(objetoBeanN2);
						}
				}
			} else {
				logger.debug("No cuenta con modulos Asignados");
			}
		} catch (Exception e) {
			logger.error("Hubo problemas en la peticion "+e);
		}

		logger.debug("[FIN]: Metodo: obtenerPrivilegiosUsuario");
	}

	public void cerraSession() {
		logger.debug("[INICIO]: Metodo: cerraSession");
		HttpSession session = null;
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		httpSession.invalidate();
		logger.debug("[FIN]: Metodo: cerraSession");
	}

}
