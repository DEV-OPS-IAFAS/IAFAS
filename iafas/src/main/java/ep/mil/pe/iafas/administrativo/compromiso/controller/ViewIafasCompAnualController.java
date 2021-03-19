package ep.mil.pe.iafas.administrativo.compromiso.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;

import ep.mil.pe.iafas.administrativo.compromiso.dao.IafasCompromisoAnualDetDao;
import ep.mil.pe.iafas.administrativo.compromiso.model.ViewIafasCompAnual;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

@Data
@ManagedBean(name="mbMovCA")
@SessionScoped
public class ViewIafasCompAnualController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String periodo ="2021";
	private String tipoDocAnual;
	private String nroDocumentoAnual;
	private String ruc;
	private String razonSocial;
	private double tipCam;
	private String codMoneda;
	private String certificado;
	private String tipCodFun;
	private String justificacion;
	private String tipProcesoSel;
	private String tipoMovimiento;
	
	// parametros Externos
	   private String certificadoP;
	   private String secuenciaP;
	   
	private boolean saldoReb;
	private boolean saldoAmp;
	private boolean saldoAnu;
	
	private static Logger logger = Logger.getLogger(ViewIafasCompAnualController.class);
	private List<ViewIafasCompAnual> listaMovimiento;
	
	IafasCompromisoAnualDetDao movDao = new IafasCompromisoAnualDetDao(MySQLSessionFactory.getSqlSessionFactory());

	public ViewIafasCompAnualController() {
		// TODO Auto-generated constructor stub
	}
	
	public String nuevoMovimientoAnual() {

	  try {
		   certificadoP = (String) extContext().getRequestParameterMap().get("certificadoP");
		   secuenciaP = (String) extContext().getRequestParameterMap().get("secuenciaP");
		  certificado = certificadoP;
		  logger.info("Consulta Movimiento de Comp Anual "+certificadoP +" "+secuenciaP);
		  ViewIafasCompAnual mov = new ViewIafasCompAnual();
		  mov.setVanoDocumento(periodo);
		  mov.setVnroCertificado(certificadoP);
		  mov.setVsecuenciaA(secuenciaP);
		  List<ViewIafasCompAnual> registros = movDao.mostrarMovimientoCA(mov);
		  for(ViewIafasCompAnual movimientos : registros) {
			  setTipoDocAnual(movimientos.getVtipoDocumentoA());
			  setNroDocumentoAnual(movimientos.getVnroDocumentoPagoA());
			  setTipCam(movimientos.getNtipCam());
			  setTipCodFun(movimientos.getVcodTipoFinanciamiento());
			  setCodMoneda(movimientos.getVcodMoneda());
			  setRuc(movimientos.getProveedorRuc());
			  setTipProcesoSel(movimientos.getVcodProcesoSel());
			  //listaMovimiento.add(movimientos);
		  }
	  }
	  catch(Exception e) {
		  
	  }
	   return "insMovimientoCompAnual";
	}
	
	public void verSaldoMovimiento() {
		
		  listaMovimiento = new ArrayList<ViewIafasCompAnual>();
		  if(listaMovimiento.size()>0) {
			  listaMovimiento.clear();
		  }
		  ViewIafasCompAnual mov = new ViewIafasCompAnual();
		  mov.setVanoDocumento(periodo);
		  mov.setVnroCertificado(certificadoP);
		  mov.setVsecuenciaA(secuenciaP);
		  List<ViewIafasCompAnual> registros = movDao.mostrarMovimientoCA(mov);
		  if(tipoMovimiento.equals(Constantes.TMOV_AMPLIACION)) {saldoAmp=true;saldoReb=false;saldoAnu=false;}
		  if(tipoMovimiento.equals(Constantes.TMOV_REBAJA)) {saldoReb=true;saldoAmp=false;saldoAnu=false;}
		  if(tipoMovimiento.equals(Constantes.TMOV_ANULACION)) {saldoReb=false;saldoAmp=false;saldoAnu=true;}
		  for(ViewIafasCompAnual r : registros) {
			  listaMovimiento.add(r);
		  }
	}
	
	public String retornar() {
		limpiarCampos();
		return "mainCompromisoAnual";
	}
	
	public String grabarMovimientoAnual() {
		String p ="";
		logger.info("[Inicio]: Metodo grabarMovimientoAnual {}");
		HttpSession session=null; 
 		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		String codUsu = usuarioSession.getIdUsuario();
		try {
			ViewIafasCompAnual mov = new ViewIafasCompAnual();
			mov.setVanoDocumento(periodo);
			mov.setVsecuenciaA(secuenciaP);
			mov.setVnroCertificado(certificadoP);
			mov.setVtipMovimiento(tipoMovimiento);
			mov.setNtipCam(tipCam);
			mov.setVusuarioIng(codUsu);
			mov.setVglosa(justificacion);
			int j=0;
			for(int i=0; i<listaMovimiento.size(); i++) {
				if(getListaMovimiento().get(i).getImporteIngresado().doubleValue()>0) {
					mov.setVcodSecFunc(getListaMovimiento().get(i).getVcodSecFunc());
					mov.setVidClasificador(getListaMovimiento().get(i).getVidClasificador());
					mov.setImporteIngresado(getListaMovimiento().get(i).getImporteIngresado());
				    j = movDao.grabarMovAnual(mov);
				}
				
			}
			if(j==0) {
			logger.info("[FIN:] Metodo grabarMovimientoAnual {} Se registro correctamente Movimiento Tipo : "+tipoMovimiento+" en Certificado "+certificadoP);
			limpiarCampos();
		    p="mainCompromisoAnual";
			
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR:] Metodo grabarMovimientoAnual {} ",e);
		}
		return p;
	}
	
	public void limpiarCampos() {
		tipoMovimiento = Constantes.VACIO;
		justificacion = Constantes.VACIO;
		listaMovimiento.clear();
	}
	
    private ExternalContext extContext() {
        FacesContext c = FacesContext.getCurrentInstance();
        ExternalContext ec = c.getExternalContext();
        return ec;
    }

}
