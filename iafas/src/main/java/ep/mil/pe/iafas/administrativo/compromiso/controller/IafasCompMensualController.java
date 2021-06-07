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
import ep.mil.pe.iafas.administrativo.compromiso.dao.IafasCompromisoMensualDao;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnual;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensual;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensualDet;
import ep.mil.pe.iafas.administrativo.compromiso.model.ViewIafasCompromisoMensual;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.SQLServerSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.integracion.dao.OrdenesCSDao;
import ep.mil.pe.iafas.integracion.model.OrdenesCS;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

@Data
@ManagedBean(name="mbCompMensual")
@SessionScoped
public class IafasCompMensualController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ViewIafasCompromisoMensual> regAnual;
	private List<ViewIafasCompromisoMensual> regAnualDet;
	private List<IafasCompromisoMensual> lista;
	private String periodo="2021";
	private String certificado;
	private String tipDocumentoMen;
	private String nroDocumentoMen;
	private Date fechaMensual;
	private String glosa;
	private String tipDocCom;
	private String nroDocCom;
	private String nroSerieCom;
	private Date fecDocCom;
	
	private String vcodtipoFinanciamiento;
	private String fuenteFinanciamiento;
	private double ntipCam;
	private String codProcesoSel;
	private String ruc;
	private String razonSocial;
	private String vcodMoneda;
	private String vanoDocumentoA;
	private String vsecuenciaA;
	private String vcorrelativoA;
	
	private String psecuencia;
	private String pcorrelativo;
	private String pexpediente;
	private int typeMessages = -1;
	private String messages="";
	private String usuario;
	
	private String numCertificado;
	private String tipOrden;
	
	private String tipDocAnual;
	private String codigoDocAnual;
	private String nroDocAnual;
	private String conceptoAnual;
	
	private BigDecimal montoOrden;
	private int reg;
	private BigDecimal montoCA;
	
	IafasCompromisoMensualDao mensualDao = new IafasCompromisoMensualDao(MySQLSessionFactory.getSqlSessionFactory());
	OrdenesCSDao OrderDao = new OrdenesCSDao(SQLServerSessionFactory.getSqlServerSessionFactory());
	IafasCompromisoAnualDao compAnualDao = new IafasCompromisoAnualDao(MySQLSessionFactory.getSqlSessionFactory());
	private static final Logger logger = Logger.getLogger(IafasCompMensualController.class);
	Date hoy = new Date();
	public IafasCompMensualController() {
		// TODO Auto-generated constructor stub
		HttpSession session=null; 
		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		usuario = usuarioSession.getIdUsuario();
		listadoMensual();
	}
	
	public List<IafasCompromisoMensual> listadoMensual(){
		lista = new ArrayList<IafasCompromisoMensual>();
		List<IafasCompromisoMensual> registros = mensualDao.listaMensual(periodo);
		reg = registros.size();
		for(IafasCompromisoMensual reg :registros) {
			lista.add(reg);
		}
		return lista;
	}
	
	public String retornar() {
		//limpiarSession();
		listadoMensual();		
		return "mainCompromisoMensual.xhtml";
	}
	
	public List<ViewIafasCompromisoMensual> buscarAnual(){
		
		regAnual = new ArrayList<ViewIafasCompromisoMensual>();
		if(regAnual.size()>0) {
			regAnual.clear();
		}
		ViewIafasCompromisoMensual vcm = new ViewIafasCompromisoMensual();
		vcm.setVano(periodo);
		vcm.setVnroCertificado(certificado);
		List<ViewIafasCompromisoMensual> registros = mensualDao.buscaCompromisoAnual(vcm);
		for(ViewIafasCompromisoMensual l : registros)
		{regAnual.add(l);}
		return regAnual;
	}
	
	public String validarOCSTest() {
		IafasCompromisoAnual ca = new IafasCompromisoAnual();
		ca.setVanoDocumento("2021");
		ca.setVnroDocumentoPagoA(nroDocumentoMen);
		List<IafasCompromisoAnual> ordenes = compAnualDao.verOC(ca);
		for(IafasCompromisoAnual registros: ordenes) {
				ruc = ordenes.get(0).getCproveedorRuc();
				razonSocial = ordenes.get(0).getRazonSocial();
				montoOrden = ordenes.get(0).getNimpMonSol();
			}
		logger.info("Valores Obtenidos {} "+ruc+" "+razonSocial+" "+montoOrden);
		return "";
	}
	
	public String validarOCS() throws SQLException {
		try {
			logger.info("Validando Ordenes {} " +nroDocumentoMen);
			OrdenesCS oc = new OrdenesCS();
			if(tipDocumentoMen.equals(Constantes.ID_ORDEN_COMPRA)) {
				setTipOrden("OC");
			}
			else 
				if(tipDocumentoMen.equals(Constantes.ID_ORDEN_SERVICIO)) {
					setTipOrden("OS");
				}
			
			oc.setPeriodo(Integer.valueOf(periodo));
			oc.setNumeroOrden(nroDocumentoMen);
			oc.setTipoDocumento(tipOrden);
			List<OrdenesCS> ordenes = OrderDao.findPurchaseOrder(oc);
			logger.info("Parametros Busqueda {} "+periodo+" "+tipOrden+" "+nroDocumentoMen);
			if(ordenes.size()==0) {ruc="";razonSocial="";}
			else {
				for(OrdenesCS registros: ordenes) {
					ruc = ordenes.get(0).getRuc();
					razonSocial = ordenes.get(0).getProveedor();
					montoOrden = ordenes.get(0).getMontoTotal();
				}
				logger.info("Valores Obtenidos {} "+ruc+" "+razonSocial);
			}

		} catch (Exception e) {
			logger.error("Error Valida ORdenes : {} "+e.getMessage()+" "+e.getCause());
		}
		
		return "";
	}
	
	public List<ViewIafasCompromisoMensual> buscarAnualXSecuencia(){
		regAnualDet = new ArrayList<ViewIafasCompromisoMensual>();
		ViewIafasCompromisoMensual vcm = new ViewIafasCompromisoMensual();
		String psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
		vcm.setVano(periodo);
		vcm.setVnroCertificado(certificado);
		vcm.setVsecuenciaA(psecuencia);
		List<ViewIafasCompromisoMensual> registros = mensualDao.buscaCompromisoAnualSecuencia(vcm);
		logger.info("[buscarAnualXSecuencia] Registros Encontrados :"+registros.size());
		for(ViewIafasCompromisoMensual l : registros)
		{
			regAnualDet.add(l);
			setRuc(l.getVruc());
			setRazonSocial(l.getRazonSocial());
			setNtipCam(l.getNtipCam());
			setVcodMoneda(l.getVcodMoneda());
			setCodProcesoSel(l.getVcodProcesoSel());
			setVcodtipoFinanciamiento(l.getVcodTipoFinanciamiento());
			setFuenteFinanciamiento(l.getVfuenteFinanciamiento());
			setVanoDocumentoA(periodo);
			setVsecuenciaA(l.getVsecuenciaA());
			setVcorrelativoA(l.getVcorrelativoA());
			setNumCertificado(l.getVnroCertificado());
			setCodigoDocAnual(l.getVtipoDocumentoA());
			setNroDocAnual(l.getVnroDocumentoPagoA());
			setConceptoAnual(l.getConcepto());
			setTipDocAnual(l.getAbTipoDoc());
			setGlosa(l.getConcepto());
			setMontoCA(l.getNimpMontoSol());
			
			if(l.getVtipoDocumentoA().equals(Constantes.ID_ORDEN_COMPRA) || 
			   l.getVtipoDocumentoA().equals(Constantes.ID_ORDEN_SERVICIO)) {
				setTipDocumentoMen(l.getVtipoDocumentoA());
				setNroDocumentoMen(l.getVnroDocumentoPagoA());
				PrimeFaces.current().executeScript("deshabilitar()");
				
			}
			else {
				   if(l.getVtipoDocumentoA().equals("060")) {
				   PrimeFaces.current().executeScript("habilitar()");
				}
			}
		}
			
		return regAnualDet;
	}
	
	public void verAnuales() {
		if(certificado==null || certificado.trim().equals("")) {
			typeMessages=3;
			showMessages(3);
		}
		else {buscarAnual();}
	}
	
	
	public String nuevoRegistro() {
		String page = "insRegCompMensual.xhtml";
		limpiarSession();
	    return page;
	}

	public void limpiarSession() {
		setCertificado(Constantes.VACIO);
		setTipDocumentoMen(Constantes.VACIO);
		setNroDocumentoMen(Constantes.VACIO);
		setFechaMensual(hoy);
		setGlosa(Constantes.VACIO);
		setConceptoAnual(Constantes.VACIO);
		setTipDocAnual(Constantes.VACIO);
		setNroDocAnual(Constantes.VACIO);
		setRuc(Constantes.VACIO);
		setRazonSocial(Constantes.VACIO);
		setMontoCA(new BigDecimal(0));
		setMontoOrden(new BigDecimal(0));
        regAnualDet =null;
	}
	
	private boolean validaCampos() {
		logger.info("Parametros : "+tipDocumentoMen+" "+nroDocumentoMen+" "+fechaMensual+" "+glosa);
		if(tipDocumentoMen.equals(Constantes.VACIO)) {typeMessages=2;showMessages(2);return true;}
		else
		if(nroDocumentoMen.equals(Constantes.VACIO)) {typeMessages=2;showMessages(2);return true;}
		else
		if(fechaMensual==null) {typeMessages=2;showMessages(2);return true;}
		else
		if(glosa.equals(Constantes.VACIO)) {typeMessages=2;showMessages(2);return true;}
		else {
			typeMessages=1;
			showMessages(1);
			return false;	
		}

	}
	
	public boolean validaMontoOC() {
		
		boolean flag=false;
		if(codigoDocAnual.equals("060")) {
			if(tipDocumentoMen.equals(Constantes.ID_ORDEN_COMPRA) ||
					tipDocumentoMen.equals(Constantes.ID_ORDEN_SERVICIO)) {
				 for(int oc=0; oc<regAnualDet.size();oc++) {
					 logger.info("Monto Ingresado "+regAnualDet.get(oc).getMontoIngresado().doubleValue());
			          if(regAnualDet.get(oc).getMontoIngresado().doubleValue() > 0) {
			        	  if(!(montoOrden.doubleValue() == regAnualDet.get(0).getMontoIngresado().doubleValue())) {
								 
								 PrimeFaces.current().executeScript("activateBoton()");
								 FacesContext.getCurrentInstance().addMessage(null, new 
										 FacesMessage(FacesMessage.SEVERITY_ERROR, "INFO!", 
												 "EL IMPORTE REGISTRADO DIFIERE DEL MONTO DE LA ORDEN.. VERIFIQUE!"));
								 flag = true;
							 }
			          }
				 }
				 logger.info("Probando Validadores Contando clasificadores:" + montoOrden);
				 
			}
		}
		else {
			if(codigoDocAnual.equals(Constantes.ID_ORDEN_COMPRA) ||
					codigoDocAnual.equals(Constantes.ID_ORDEN_SERVICIO)) {
				setTipDocumentoMen(codigoDocAnual);
				 for(int oc=0; oc<regAnualDet.size();oc++) {
					 logger.info("Monto Ingresado "+regAnualDet.get(oc).getMontoIngresado().doubleValue());
			          if(regAnualDet.get(oc).getMontoIngresado().doubleValue() > 0) {
			        	  if(!(montoCA.doubleValue() == regAnualDet.get(0).getMontoIngresado().doubleValue())) {
								 FacesContext.getCurrentInstance().addMessage(null, new 
										 FacesMessage(FacesMessage.SEVERITY_FATAL, "INFO!", 
												 "EL IMPORTE REGISTRADO DIFIERE DEL MONTO DEL COMPROMISO ANUAL.. VERIFIQUE!"));
								 flag = true;
							 }
			          }
				 }
				 logger.info("Probando Validadores Contando clasificadores:" + montoOrden);
				 
			}
		}

		return flag;
	}
	
	public boolean validaDuplicidad() {
		boolean flag = false;
		List<IafasCompromisoMensual> v = null;
		String nroDocBD ="";
		nroDocBD = nroDocumentoMen.substring(2, 10);
		logger.info("NroDoc: "+nroDocBD);
		IafasCompromisoMensual ca = new IafasCompromisoMensual();
		ca.setVano(periodo);
		ca.setVtipDocumentoMen(tipDocumentoMen);
		ca.setVnroDocumentoMen(nroDocBD);
		logger.info("Parametros Filtros : "+periodo+" "+tipDocumentoMen+" "+nroDocBD);
		   v = mensualDao.validaDuplicado(ca);
		   logger.info("Resultado COnsulta: "+ v.size());
		if(v.size()>0) {
			logger.info("Entro a validacion de Duplicidad");
			if(nroDocBD.equals(v.get(0).getVnroDocumentoMen())) {
				String resultado = v.get(0).getAbrevTipoDoc()+" "+v.get(0).getVnroDocumentoMen()+" SE ENCUENTRA REGISTRADO EN EL "
						+ "EXPEDIENTE N° "+v.get(0).getVexpediente()+". VERIFIQUE";
				 FacesContext.getCurrentInstance().addMessage(null, new 
						 FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR!", 
								 resultado));
				 PrimeFaces.current().executeScript("activateBoton()");
				 flag = true;
			}
		}
		

		return flag;
	}
	
	public String registroCompMensual() {
		
		if(validaMontoOC()==true) {
			return "";
		}
		if(validaDuplicidad()==true) {
			return "";
		}
		if(validaCampos()==true) {
			typeMessages=2;
			showMessages(2);
		}
		else {
			try {
				IafasCompromisoMensual men = new IafasCompromisoMensual();
				IafasCompromisoMensualDet det = new IafasCompromisoMensualDet();
				men.setVano(periodo);
				men.setVglosa(glosa);
				men.setVfuenteFinanciamiento(fuenteFinanciamiento);
				men.setVcodTipoFuncionamiento(vcodtipoFinanciamiento);
				men.setVcodEstado("1");
				men.setNtipCam(ntipCam);
				men.setVcodMoneda(vcodMoneda);
				men.setVtipDocumentoMen(tipDocumentoMen);
				men.setVnroDocumentoMen(nroDocumentoMen);
				men.setDfechaDocumento(fechaMensual);
				men.setVanoDocumento(vanoDocumentoA);
				men.setVsecuenciaA(vsecuenciaA);
				men.setVcorrelativo(vcorrelativoA);
				men.setVnroCertificado(numCertificado);
				men.setVcodProcesoSel(codProcesoSel);
				men.setCproveedorRuc(ruc);
				men.setVtipDocumentoCom(tipDocCom);
				men.setVnroDocumentoCom(nroDocCom);
				men.setVserieCom(nroSerieCom);
				men.setDfechaDevengado(fecDocCom);
				men.setVusuarioIng(usuario);
				mensualDao.registroCompMensual(men);
				for(int j =0;j <regAnualDet.size(); j++) {
						det.setVano(periodo);
						det.setVcodSecFunc(regAnualDet.get(j).getVcodSecFunc());
						det.setVidClasificador(regAnualDet.get(j).getVidClasificador());
						det.setNimpMonSol(regAnualDet.get(j).getMontoIngresado());
						det.setVusuarioIng(usuario);
					if(getRegAnualDet().get(j).getMontoIngresado().doubleValue()>0) {
						if(getRegAnualDet().get(j).getNimpMontoSol().doubleValue() < getRegAnualDet().get(j).getMontoIngresado().doubleValue()) {
							 FacesContext.getCurrentInstance().addMessage(null, new 
						     FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!", "El Monto Ingresado Excede al Saldo del Clasificador!"));
							 deleteCompromisoMensual();
							 return "insRegCompMensual";
						}
						else {
							mensualDao.registroCompMensualDet(det);
							typeMessages = 1;
						}
					}	
						
					}
				showMessages(1);		
				//return retornar();	
				//regAnualDet.clear();
				limpiarSession();
			}
			catch (Exception e) {
				// TODO: handle exception
				typeMessages=0;
				showMessages(0);
				logger.error("[ERROR] registroCompMensual :", e);
			}	
		}
		
		return "";
	}
	
	public String obtener() {
		String page="";
		  psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
		  pcorrelativo = (String) extContext().getRequestParameterMap().get("p_correlativo");
		  pexpediente = (String) extContext().getRequestParameterMap().get("p_expediente");
		  return page;
	}
	
	public String enviarCompromisoMensual() {
		String retorno = "mainCompromisoMensual.xhtml";
		int envio=0;
		try {

			IafasCompromisoMensual ca = new IafasCompromisoMensual();
			ca.setVano(periodo);
			ca.setSecuencia(psecuencia);
			ca.setCorrelativo(pcorrelativo);
			ca.setVexpediente(pexpediente);
			ca.setVusuarioIng(usuario);			
			envio = mensualDao.enviarCompromisoMensual(ca);
			logger.info("Se Valido el Compromiso Mensual {} "+pexpediente);
		}
		catch (Exception e) {
			logger.error("[ERROR] enviarCompromisoMensual :", e);
		}
         retornar();
         
		return retorno;
	}
	
	public int deleteCompromisoMensual() {
		int envio = 0;
		try {

			IafasCompromisoMensual ca = new IafasCompromisoMensual();
			ca.setVano(periodo);
			ca.setSecuencia("");
			ca.setVexpediente("");
			
			envio = mensualDao.dropCompromisoMensual(ca);
		}
		catch (Exception e) {
			System.out.println("Error Envio : "+e.getMessage());
		}

		return envio;
	}
	
	private String showMessages(int opcion) {
		logger.info("Tipo de Opcion y Mensaje :"+opcion+" "+typeMessages);
		messages = "";
		switch (opcion) {
		case 0 : typeMessages=0; messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 1 : typeMessages=1; messages = "";PrimeFaces.current().executeScript("verMensajes()");retornar();break;
		case 2 : typeMessages=2; messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 3 : typeMessages=3; messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
	    default : logger.info("No se muestra ningun mensaje");break;
		}	
        return messages;
	}
	
    private ExternalContext extContext() {
        FacesContext c = FacesContext.getCurrentInstance();
        ExternalContext ec = c.getExternalContext();
        return ec;
    }
}
