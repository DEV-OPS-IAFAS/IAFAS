package ep.mil.pe.iafas.administrativo.compromiso.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import ep.mil.pe.iafas.administrativo.compromiso.dao.IafasCompromisoAnualDao;
import ep.mil.pe.iafas.administrativo.compromiso.dao.IafasCompromisoAnualDetDao;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnual;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnualDet;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.SQLServerSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.integracion.dao.OrdenesCSDao;
import ep.mil.pe.iafas.integracion.model.OrdenesCS;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

@Data
@ManagedBean(name="mbCompAnual")
@SessionScoped
public class IafasCompAnualController implements Serializable {
	
	private List<IafasCompromisoAnual> lista;
	private List<IafasCompromisoAnualDet> listaCertDet;
	private List<IafasCompromisoAnual> listaMovCA;
	
	private String periodo="2021";
	private String secuencia;
	private String correlativo;
	private String certificado;
	private short reg;
	private String tipProcesoSel;
	private String tipDocumentoA;
	private String nroDoc;
	private Date fecDocumento;
	private String tipOpe;
	private String idProv;
	private String concepto;
	private String tipMon;
    private double ntipCam = 1.0;
    private String ruc;
    private String razonSocial;
    private String tipCodFun;
    private String desPresupuesto;
    private BigDecimal nimpCompSol;
    private Date fechaCert;    
    private String usuario;
    //Parametros
    private String psecuencia;
    private String pcorrelativo;
    
    private String messages;
    private int typeMessages;
    private String tipOrden;
    
    Date hoy = new Date();
    
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(IafasCompAnualController.class);
	
	IafasCompromisoAnualDetDao compAnualDetDao = new IafasCompromisoAnualDetDao(MySQLSessionFactory.getSqlSessionFactory());
	IafasCompromisoAnualDao compAnualDao = new IafasCompromisoAnualDao(MySQLSessionFactory.getSqlSessionFactory());
	
	OrdenesCSDao OrderDao = new OrdenesCSDao(SQLServerSessionFactory.getSqlServerSessionFactory());
	
	// Sessiones
     
	 
	public IafasCompAnualController() {
		// TODO Auto-generated constructor stub
		//listarCompAnualCab();
		HttpSession session=null; 
		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		usuario = usuarioSession.getIdUsuario();
	}
	
	public String validarOCSTest() {
		IafasCompromisoAnual ca = new IafasCompromisoAnual();
		ca.setVanoDocumento("2021");
		ca.setVnroDocumentoPagoA(nroDoc);
		List<IafasCompromisoAnual> ordenes = compAnualDao.verOC(ca);
		for(IafasCompromisoAnual registros: ordenes) {
				ruc = ordenes.get(0).getCproveedorRuc();
				razonSocial = ordenes.get(0).getRazonSocial();
			}
		return "";
	}
	
	public String validarOCS() throws SQLException {
		try {
			logger.info("Validando Ordenes {} " +nroDoc);
			OrdenesCS oc = new OrdenesCS();
			if(tipDocumentoA.equals(Constantes.ID_ORDEN_COMPRA)) {
				setTipOrden("OC");
			}
			else 
				if(tipDocumentoA.equals(Constantes.ID_ORDEN_SERVICIO)) {
					setTipOrden("OS");
				}
			
			oc.setPeriodo(Integer.valueOf(periodo));
			oc.setNumeroOrden(nroDoc);
			oc.setTipoDocumento(tipOrden);
			List<OrdenesCS> ordenes = OrderDao.findPurchaseOrder(oc);
			logger.info("Parametros Busqueda {} "+periodo+" "+tipOrden+" "+nroDoc);
			if(ordenes.size()==0) {ruc="";razonSocial="";}
			else {
				for(OrdenesCS registros: ordenes) {
					ruc = ordenes.get(0).getRuc();
					razonSocial = ordenes.get(0).getProveedor();
				}
				logger.info("Valores Obtenidos {} "+ruc+" "+razonSocial);
			}

		} catch (Exception e) {
			logger.error("Error Valida ORdenes : {} "+e.getMessage()+" "+e.getCause());
		}
		return "";
	}
	
	public List<IafasCompromisoAnual> listarMovimientoCA(String secuencia){
		listaMovCA = new ArrayList<IafasCompromisoAnual>();
		logger.info("[INICIO] Metodo listarMovimientoCA {}");
		try {
			 IafasCompromisoAnual ca = new IafasCompromisoAnual();
			ca.setVanoDocumento(periodo);
			ca.setVnroCertificado(certificado);
			ca.setVsecuenciaA(secuencia);
			List<IafasCompromisoAnual> compromiso = compAnualDao.verMovimientoCA(ca);			
			reg = (short) compromiso.size();
			logger.info("Cantidad de Movimientos Encontrados "+reg);
			for(IafasCompromisoAnual l : compromiso) {
				listaMovCA.add(l);
			}
			logger.info("[FIN] Metodo listarMovimientoCA {}");
		}
		catch(Exception e) {
			logger.error("[ERROR] Metodo listarMovimientoCA :"+e);
		}
		return listaMovCA;
	}
	
	public List<IafasCompromisoAnual> listarCompAnualCab(){
		lista = new ArrayList<IafasCompromisoAnual>();
		try {
			 IafasCompromisoAnual ca = new IafasCompromisoAnual();
			ca.setVanoDocumento(periodo);
			ca.setVnroCertificado(certificado);
			List<IafasCompromisoAnual> compromiso = compAnualDao.listaCompromisoAnual(ca);			
			reg = (short) compromiso.size();
			for(IafasCompromisoAnual l : compromiso) {
				setTipCodFun(compromiso.get(0).getVcodTipoFinanciamiento());
				setTipProcesoSel(compromiso.get(0).getVcodProcesoSel());
				setNtipCam(compromiso.get(0).getNtipCam());
				setFechaCert(compromiso.get(0).getDfechaDocumento());
				lista.add(l);
				
			}
		}
		catch(Exception e) {
			logger.error("[ERROR] lista Compromiso Anual :"+e);
		}
		return lista;
	}
	
	public void verDetalleRegistroCA() {
		try {
			  psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
			  pcorrelativo = (String) extContext().getRequestParameterMap().get("p_correlativo");
			 IafasCompromisoAnual ca = new IafasCompromisoAnual();
			 ca.setVanoDocumento(periodo);
			 ca.setVnroCertificado(certificado);
			 ca.setVsecuenciaA(psecuencia);
			 ca.setVcorrelativoA(pcorrelativo);
			 List<IafasCompromisoAnual> registro = compAnualDao.listaDetalleCompromisoAnual(ca);
			 for(IafasCompromisoAnual p: registro) {
				 setNroDoc(p.getVnroDocumentoPagoA());
				 setSecuencia(p.getVsecuenciaA());
				 setFecDocumento(p.getDfechaDocumento());
				 setConcepto(p.getVglosa());
				 setRuc(p.getCproveedorRuc());
				 setRazonSocial(p.getRazonSocial());
				 setDesPresupuesto(p.getDesPto());
			 }
			 
		}
		catch(Exception e) {
			logger.error("[ERROR] Metodo verDetalleRegistroCA {} "+e);
		}

		 listarMovimientoCA(psecuencia);
	}
	
	public List<IafasCompromisoAnualDet> listarCompAnualDet(){
		listaCertDet = new ArrayList<IafasCompromisoAnualDet>();
		try {
			IafasCompromisoAnualDet ca = new IafasCompromisoAnualDet();
			ca.setVanoDocumento(periodo);
			ca.setVnroCertificado(certificado);
			List<IafasCompromisoAnualDet> compromiso = compAnualDetDao.listaCompromisoAnualDet(ca);			
			for(IafasCompromisoAnualDet l : compromiso) {
				listaCertDet.add(l);
			}
		}
		catch(Exception e) {
			System.out.println("ErrorListaDetalle :"+e.getCause());
		}
		return listaCertDet;
	}
	
	public String nuevoRegistro() {
		String page = "insRegCompAnual";
		if(certificado==null || certificado.trim().equals("")) {
			showMessages(3);
			page = "mainCompromisoAnual.xtml";
		}
		limpiarCampos();
		listarCompAnualDet();
	    return page;
	}
	
	public String retornar(){
		String page="mainCompromisoAnual.xtml";
		return page;
	}
	
	public boolean validarCampos() {
		if(concepto.equals(Constantes.VACIO)) {showMessages(2);return true;}
		if(nroDoc.equals(Constantes.VACIO)) {showMessages(2);return true;}
		if(tipDocumentoA.equals(Constantes.VACIO)) {showMessages(2);return true;}
		if(fecDocumento==null) {showMessages(2);return true;}
		else {
			typeMessages=1;
			showMessages(1);
			return false;
			
		}		 
	}
	
	public String grabarCompAnual() {
		int reg = 0;
		if(validarCampos()==true) {
			showMessages(2);
		}
		else {
		try {
			logger.info("Ingreso al Metodo Grabar Anual");
			 IafasCompromisoAnual ca = new IafasCompromisoAnual();
			 IafasCompromisoAnualDet det = new IafasCompromisoAnualDet();
			 
			 
			 ca.setVanoDocumento(periodo);
			 ca.setVnroCertificado(certificado);
			 ca.setVtipoDocumentoA(tipDocumentoA);
			 ca.setVnroDocumentoPagoA(nroDoc);
			 ca.setDfechaDocumento(fecDocumento);
			 ca.setVtipoMovimiento(Constantes.TMOV_INICIAL);
			 ca.setVtipoOperacion(tipOpe);
			 ca.setVfuenteFinanciamiento("00");
			 ca.setCproveedorRuc(ruc);
			 ca.setVidProv(idProv);
			 ca.setNtipCam(ntipCam);
			 ca.setVcodTipoFinanciamiento(tipCodFun);
			 ca.setVcodProcesoSel(tipProcesoSel);
			 ca.setVcodMoneda(tipMon);	 
			 ca.setVusuarioIng(usuario);
			 ca.setVglosa(concepto);
			 ca.setNimpMonSol(BigDecimal.ZERO);
			 reg = compAnualDao.grabarCompAnual(ca);
			 logger.info("Inserto Compronmiso Anual {} " +certificado);
			 for(int j =0;j<getListaCertDet().size();j++){
					 det.setVanoDocumento(periodo);
					 det.setVnroCertificado(certificado);
					 det.setVcodSec(getListaCertDet().get(j).getVcodSec());
					 det.setVidClasificador(getListaCertDet().get(j).getVidClasificador());
					 det.setNimpMontoSol(getListaCertDet().get(j).getMontoIngresado());
					 det.setVusuarioIng(usuario);
					// nimpCompSol.add(getListaCertDet().get(j).getMontoIngresado().doubleValue());
					 logger.info("Monto del Clasificador "+ getListaCertDet().get(j).getVidClasificador()+" "+getListaCertDet().get(j).getMontoIngresado().doubleValue());
					 if(getListaCertDet().get(j).getMontoIngresado().doubleValue()>0) {
						 if(getListaCertDet().get(j).getNimpMontoSol().doubleValue() < getListaCertDet().get(j).getMontoIngresado().doubleValue()) {
							 FacesContext.getCurrentInstance().addMessage(null, new 
						     FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!", "El Monto Ingresado Excede al Saldo del Clasificador!"));
							 deleteCompAnual();
							 logger.info("Se elimino Compromiso Anual sin Detalle");
							 return "insRegCompAnual";
						 }
						 else {
							 reg = compAnualDetDao.grabarCompAnualDet(det);
							 typeMessages=1;
						 }				 
					 }		 
			 }
			 showMessages(1);
		} catch (Exception e) {
			// TODO: handle exception
			typeMessages=0;
			showMessages(0);
			logger.error("[ERROR] No se Registro Compromiso Anual : "+e);
		}
	} 
     	return "";
	}
	
	public String obtener() {
		String page="";
		  psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
		  pcorrelativo = (String) extContext().getRequestParameterMap().get("p_correlativo");
		  return page;
	}
	
	
	public String enviarCompromisoAnual() {
		String retorno ="mainCompromisoAnual.xhtml";
		int envio = 0;
		try {

			IafasCompromisoAnual ca = new IafasCompromisoAnual();
			ca.setVanoDocumento(periodo);
			ca.setVnroCertificado(certificado);
			ca.setVsecuenciaA(psecuencia);
			ca.setVcorrelativoA(pcorrelativo);
			ca.setVusuarioIng(usuario);
			
			envio = compAnualDao.enviarCompAnual(ca);
			 retornar();
			listarCompAnualCab();
		}
		catch (Exception e) {
			logger.error("[ERROR] Ocurrio un Error al enviar CompromisoAnual "+e);
		}

		return retorno;
	}
	
	public void limpiarCampos() {
		nroDoc="";
		fecDocumento = hoy;
		tipDocumentoA = "";
		concepto="";
	}
	
	public int deleteCompAnual() {
		int d =0;
		IafasCompromisoAnual ca = new IafasCompromisoAnual();
		ca.setVanoDocumento(periodo);
		ca.setVnroCertificado(certificado);
		d = compAnualDao.deleteCompAnual(ca);
		return d;
	}
	
	private String showMessages(int opcion) {
		messages = "";
		switch (opcion) {
		case 0 : typeMessages=0;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 1 : typeMessages=1;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 2 : typeMessages=2;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 3 : typeMessages=3;messages = "";
							PrimeFaces.current().executeScript("validaCertificado()");break;
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
