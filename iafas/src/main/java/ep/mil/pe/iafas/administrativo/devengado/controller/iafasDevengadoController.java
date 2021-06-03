package ep.mil.pe.iafas.administrativo.devengado.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import ep.mil.pe.iafas.administrativo.compromiso.model.ViewIafasCompromisoMensual;
import ep.mil.pe.iafas.administrativo.devengado.dao.iafasDevengadoDao;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasComprobanteRetencion;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasCompromiso;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasCompromisoDet;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasDevengado;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasDevengadoDet;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import ep.mil.pe.iafas.seguridad.controller.IafasUsuariosController;
import lombok.Data;

@Data
@ManagedBean(name="mbDevengado")
@SessionScoped
public class iafasDevengadoController implements Serializable{
	private List<IafasDevengado> listaDevC;
	private List<IafasDevengadoDet> listaDevDet;
	private List<IafasCompromiso> listaComp;
	private List<IafasCompromiso> listaDev;
	private List<IafasCompromisoDet> listaDet;
	private List<IafasComprobanteRetencion> listaRetencion;
	private int registros;
	private String periodo="2021";
	private String vexpediente;
	private String siaf;
	private short reg;
	private String vtipoDocumento;
	private String ruc;
	private String desRuc;
	private Date fechaCom;
	private String nroCertificado;
	private String nroDocumento;
	private String vcodProcesoSel;
	
	private String secFun;
	private String vidClasificador;
	private BigDecimal Importe;	
	private String cadena;
	private String nomCla;
	
	
	// devengado
	private String glosa;
	private String vcodtipoFinanciamiento;
	private String fuenteFinanciamiento;
	private double ntipCam;
	private String codProcesoSel;
	private String tipDocCom;
	private String nroDocCom;
	private String nroSerieCom;
	private Date fecDocCom;
	private String tipDocumentoMen;
	private String nroDocumentoMen;
	private String vcodMoneda;
	private String psecuencia;
	private String pregSiaf;
	private String pcorrelativo;
	private String pexpediente;
	private BigDecimal nimpMontoSol;
	private Date fecDevengado;
	
	private String nroDoc;
	private String serieDoc;
	private String tipoDoc;
	private BigDecimal montoDevengado;
	
	/* retenciones */
	private String vsecuencia;
	private String vcodImp;
	private BigDecimal nporImp;
	private String rucRet;
	
	private BigDecimal importeRet;
	
	private String ctipoActa;
	private String vnroActa;
	private Date  dfechaActa;
	
    private String messages;
    private int typeMessages;
	
    private String descImpu;
    private String glosaMensual;
    private String desMoneda;
    private String desDocumento;
	private String usuario;
	private String psecuenciaEn;
	private String pcorrelativoEn;
	private String pexpedienteEn;
	
	
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(iafasDevengadoController.class);
	
	iafasDevengadoDao devengadoDao = new iafasDevengadoDao(MySQLSessionFactory.getSqlSessionFactory());
	
	public iafasDevengadoController() {
		HttpSession session=null; 
		session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		IafasUsuariosController usuarioSession = new IafasUsuariosController();
		usuarioSession=(IafasUsuariosController)session.getAttribute("iafasUsuariosController");
		usuario = usuarioSession.getIdUsuario();
		listarCompMensuales();
	}
	
	public String nuevoRegistro() {
		String page = "mainDevengadoRet.xhtml";
	    return page;
	}
	
	public String retornar() {
		LimpiarCampos();
		listarCompMensuales();
		return "mainDevengado.xhtml";
	}
	
	public String retornarDev() {
		LimpiarCampos();
		return "mainDevengado.xhtml";
	}
	
	public List<IafasCompromiso> listarCompMensuales(){
		listaDev = new ArrayList<IafasCompromiso>();
		List<IafasCompromiso> devengado = devengadoDao.buscarDevengado(periodo);
			for(IafasCompromiso l : devengado) {
				listaDev.add(l);
			}
	
		return listaDev;
	}
	
	public String verMensuales() {
		// TODO Auto-generated method stub
		String page="mainDevengadoRet";
		try {
			listaComp = new ArrayList<IafasCompromiso>();
			IafasCompromiso ca = new IafasCompromiso();
			ca.setVano(periodo);
			ca.setVexpediente(vexpediente);
			List<IafasCompromiso> compromiso = devengadoDao.listaMensual(ca);
			reg = (short) compromiso.size();
			for(IafasCompromiso l : compromiso) {
				setVtipoDocumento(compromiso.get(0).getVtipDocumentoMen());
				setVtipoDocumento(compromiso.get(0).getVtipDocumentoMen());
				setNroCertificado(compromiso.get(0).getVnroCertificado());
				setRuc(compromiso.get(0).getCproveedorRuc());
				setDesRuc(compromiso.get(0).getRazonSocial());
				setFechaCom(compromiso.get(0).getDfechaDocumento());
				setNroDocumento(compromiso.get(0).getVnroDocumentoMen());
				setVcodProcesoSel(compromiso.get(0).getVcodProcesoSel());
				setNtipCam(compromiso.get(0).getNtipCam());
				setVcodMoneda(compromiso.get(0).getVcodMoneda());
				setGlosaMensual(compromiso.get(0).getGlosaMensual());
				setDesMoneda(compromiso.get(0).getDesMoneda());
				setDesDocumento(compromiso.get(0).getDesDocumento());
				setGlosa(compromiso.get(0).getGlosaMensual());
				listaComp.add(l);
			}
		}
		catch(Exception e) {
			System.out.println("Ver Error :"+e.getMessage());
		}
		buscarMensualesDet();
		return page;
	}
	
	public List<IafasCompromisoDet> buscarMensualesDet(){
		listaDet = new ArrayList<IafasCompromisoDet>();
		IafasCompromisoDet vcm = new IafasCompromisoDet();
		// String psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
		vcm.setVano(periodo);
		vcm.setVexpediente(vexpediente);
		//vcm.setSecuencia(psecuencia);
		List<IafasCompromisoDet> registros = devengadoDao.buscaCompromisoMensualDet(vcm);
		for(IafasCompromisoDet l : registros)
		{
			listaDet.add(l);
			setSecFun(l.getSecFun());
			setVidClasificador(l.getIdClasificador());
			setImporte(l.getImpSol());
			setCadena(l.getCadena());
			setNomCla(l.getNomCla());
			
		}
		return listaDet;
	}
	
	public String registroDevengado() {
		int reg =0;
		if(validarCampos()==true) {
			showMessages(2);
		}
		else {
		try {
			IafasDevengado men = new IafasDevengado();
			IafasDevengadoDet det = new IafasDevengadoDet();
			men.setVano(periodo);
			System.out.println(periodo);
			men.setVglosa(glosa);
			System.out.println(glosa);
			men.setVfuenteFinanciamiento(fuenteFinanciamiento);
			men.setVcodTipoFuncionamiento(vcodtipoFinanciamiento);
			men.setVcodEstado(Constantes.ESTADO_REGISTRADO);
			men.setNtipCam(ntipCam);
			men.setVcodMoneda(vcodMoneda);
			men.setVtipDocumentoMen(vtipoDocumento);
			men.setVnroDocumentoMen(nroDocumentoMen);
			men.setVanoDocumento(periodo);
			men.setVcodProcesoSel(codProcesoSel);
			men.setCproveedorRuc(ruc);
			men.setVtipDocumentoCom(tipDocCom);
			men.setVnroDocumentoCom(nroDocCom);
			men.setVserieCom(nroSerieCom);
			men.setDfechaDevengado(fecDocCom);
			men.setVusuarioIng(usuario);
			men.setVexpediente(vexpediente);
			men.setCtipoActa(ctipoActa);
			men.setVnroActa(vnroActa);
			men.setDfechaActa(dfechaActa);
			logger.info("Datos a Registrar: " +men.toString());
			reg= devengadoDao.registroDevengado(men);
			for(int j =0;j <listaDet.size(); j++) {
					det.setVano(periodo);
					det.setVcodSecFunc(listaDet.get(j).getSecFun());
					System.out.println(listaDet.get(j).getSecFun());
					det.setVidClasificador(listaDet.get(j).getIdClasificador());
					System.out.println(listaDet.get(j).getIdClasificador());
					det.setNimpMonSol(listaDet.get(j).getMontoIngresado());
					System.out.println(listaDet.get(j).getMontoIngresado());
					det.setVusuarioIng(usuario);
				if(getListaDet().get(j).getMontoIngresado().doubleValue()>0) {
					if(getListaDet().get(j).getImpSol().doubleValue() < getListaDet().get(j).getMontoIngresado().doubleValue()) {
						 FacesContext.getCurrentInstance().addMessage(null, new 
					     FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!", "El Monto Ingresado Excede al Saldo del Clasificador!"));
						 validaDevengadoCab();
						 return "mainDevengadoRet";
					}
					else {
					reg = 	devengadoDao.registroDevengadoDet(det);
					    typeMessages=1;
					
					} 
				}	
					
				}
			
			LimpiarCampos();
			listarCompMensuales();
			// retornar();
			 showMessages(1);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+e.getMessage());
			typeMessages=0;
			showMessages(0);
		}
	}
		
		//return "mainDevengado.xhtml";
		return "";
	}
	
	public void verDevengado(){
		pregSiaf = (String) extContext().getRequestParameterMap().get("p_regSiaf");
	     psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
	     logger.info("Obteniendo Parametros " +pregSiaf+" "+psecuencia);
	    IafasCompromiso men = new IafasCompromiso();
	    men.setVano(periodo);
	    men.setVexpediente(pregSiaf);
	    men.setSecuencia(psecuencia);
		List<IafasCompromiso> devengado = devengadoDao.obtenerDevengado(men);
		for(IafasCompromiso l : devengado) {
				setTipoDoc(l.getVtipDocumentoCom());
				setNroDoc(l.getVnroDocumentoCom());
				setSerieDoc(l.getVserieCom());
				setMontoDevengado(l.getNimpMonSol());
				setRucRet(l.getCproveedorRuc());
				
			}
		buscarRetencion();
		LimpiarCamposRet();
	}
	
	public String registrarRetencion() {
		try{
			if(isDuplicadoRet()==false) {
				IafasComprobanteRetencion retencion = new IafasComprobanteRetencion();	
				retencion.setVano(periodo);
				retencion.setVtipDocumentoCom(tipoDoc);
				retencion.setVnroCom(nroDoc);
				retencion.setVserieCom(serieDoc);
				retencion.setVexpediente(pregSiaf);
				retencion.setVsecuencia(psecuencia);
				retencion.setVcodImp(vcodImp);
				retencion.setNporImp(nporImp);
				retencion.setVusuarioIng(usuario);
				System.out.println("paramtros"+retencion.toString());
				// donde esta el insert
				devengadoDao.registroRetencionComprobante(retencion);
				logger.info("Se registro la retencion del comprobante :"+serieDoc+"-"+tipoDoc);
				buscarRetencion();
			}
			else {
				return "";
			}
		}
	
		
		catch(Exception e) {
			logger.error(e.getStackTrace());
		}
		
		LimpiarCamposRet();
		return null;
	}
	
	public void LimpiarCamposRet() {
		
		vcodImp =null;
		nporImp = null;
	}
	
	public List<IafasCompromisoDet> buscarRetencion(){
		listaRetencion = new ArrayList<IafasComprobanteRetencion>();
		IafasComprobanteRetencion ret = new IafasComprobanteRetencion();
		// String psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
		ret.setVtipDocumentoCom(tipoDoc);
		ret.setVnroCom(nroDoc);
		ret.setVserieCom(serieDoc);
		ret.setRuc(rucRet);
		List<IafasComprobanteRetencion> regRet = devengadoDao.buscaReten(ret);
		for(IafasComprobanteRetencion l : regRet)
		{
			listaRetencion.add(l);
			setVcodImp(l.getVcodImp());
			setNporImp(l.getNporImp());
			setImporteRet(l.getImporteRetencion());
		    setDescImpu(l.getDescImpu());
			
			
		}
		return listaDet;
	}
	
	public boolean isDuplicadoRet() {
		logger.info("Validando impuestos duplicados");
	   for(int i =0; i<listaRetencion.size();i++) {
		   if(vcodImp.equals(listaRetencion.get(0).getVcodImp())){
			   logger.info("Encontro duplicado : "+listaRetencion.get(0).getVcodImp());
			   FacesContext.getCurrentInstance().addMessage(null, new 
						 FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR!", 
								 "NO SE DEBE INGRESAR DOS VECES LA MISMA AFECTACION EN EL COMPROBANTE!"));
			   return true;
			   
		   }
	   }
	   return false;
	}
	
	private int validaDevengadoCab() {
		int d = 0;
		IafasDevengado devengado = new IafasDevengado();
		devengado.setVano(periodo);
		devengado.setVexpediente(vexpediente);
		d = devengadoDao.deleteDevengado(devengado);
		return d;
	}
	
	private void LimpiarCampos() {
		glosa="";
		tipDocCom="";
		nroSerieCom="";
		nroDocCom="";
		fecDocCom= null;
		ctipoActa=null;
		vnroActa="";
		dfechaActa=null;
		vexpediente="";
		desDocumento="";
		listaComp.clear();
		listaDet.clear();
		
		
		
		
	}
	
    private ExternalContext extContext() {
        FacesContext c = FacesContext.getCurrentInstance();
        ExternalContext ec = c.getExternalContext();
        return ec;
    }

	public String insert() {
		String page = "mainDevengadoRet.xhtml";
		return page;
	}
	
	private String showMessages(int opcion) {
		logger.info("Mensajes :"+opcion);
		messages = "";
		switch (opcion) {
		case 0 : typeMessages=0;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 1 : typeMessages=1;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		case 2 : typeMessages=2;messages = "";PrimeFaces.current().executeScript("verMensajes()");break;
		default : messages="";break;
		}
		return messages;
	}
	
	public boolean validarCampos() {
		if(nroDocCom.equals(Constantes.VACIO)) {showMessages(2);return true;}
		if(nroSerieCom.equals(Constantes.VACIO)) {showMessages(2);return true;}
		if(tipDocCom.equals(Constantes.VACIO)) {showMessages(2);return true;}
		if(fecDocCom==null) {showMessages(2);return true;}
		else {
			typeMessages=1;
			showMessages(1);
			return false;
			
		}		 
	}
	
      /*Enviar DEvengados*/
	 public String obtener() {
	  String page="";

		psecuenciaEn = (String) extContext().getRequestParameterMap().get("psecuenciaEn");
		pcorrelativoEn = (String) extContext().getRequestParameterMap().get("pcorrelativoEn");
		pexpedienteEn = (String) extContext().getRequestParameterMap().get("pexpedienteEn");
	  return page;
			}
			
			public String enviarDevengado() {
				String retorno = "mainDevengado.xhtml";
				int envio=0;
				try {
			
					IafasDevengado ca = new IafasDevengado();
					ca.setVano(periodo);
					ca.setSecuencia(psecuenciaEn);
					ca.setCorrelativo(pcorrelativoEn);
					ca.setVexpediente(pexpedienteEn);
					ca.setVusuarioIng(usuario);			
					envio = devengadoDao.enviarDeve(ca);
					logger.info("Se Valido el Devengado {} "+pexpedienteEn);
				}
				catch (Exception e) {
					logger.error("[ERROR] enviarDEvengado :", e);
				}
				listarCompMensuales();
			    
				return retorno;
			}

	
	
}
