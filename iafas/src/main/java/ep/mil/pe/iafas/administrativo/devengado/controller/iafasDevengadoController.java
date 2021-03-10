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

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.administrativo.compromiso.model.ViewIafasCompromisoMensual;
import ep.mil.pe.iafas.administrativo.devengado.dao.iafasDevengadoDao;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasComprobanteRetencion;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasCompromiso;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasCompromisoDet;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasDevengado;
import ep.mil.pe.iafas.administrativo.devengado.model.IafasDevengadoDet;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
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
	
	
	
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(iafasDevengadoController.class);
	
	iafasDevengadoDao devengadoDao = new iafasDevengadoDao(MySQLSessionFactory.getSqlSessionFactory());
	
	public iafasDevengadoController() {
		
		listarCompMensuales();
	}
	
	public String nuevoRegistro() {
		String page = "mainDevengadoRet.xhtml";
	    return page;
	}
	
	public String retornar() {
		listarCompMensuales();
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
			men.setVusuarioIng("44330586");
			men.setVexpediente(vexpediente);
			logger.info("Datos a Registrar: " +men.toString());
			devengadoDao.registroDevengado(men);
			for(int j =0;j <listaDet.size(); j++) {
					det.setVano(periodo);
					det.setVcodSecFunc(listaDet.get(j).getSecFun());
					System.out.println(listaDet.get(j).getSecFun());
					det.setVidClasificador(listaDet.get(j).getIdClasificador());
					System.out.println(listaDet.get(j).getIdClasificador());
					det.setNimpMonSol(listaDet.get(j).getMontoIngresado());
					System.out.println(listaDet.get(j).getMontoIngresado());
					det.setVusuarioIng("44330586");
				if(getListaDet().get(j).getMontoIngresado().doubleValue()>0) {
					if(getListaDet().get(j).getImpSol().doubleValue() < getListaDet().get(j).getMontoIngresado().doubleValue()) {
						 FacesContext.getCurrentInstance().addMessage(null, new 
					     FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!", "El Monto Ingresado Excede al Saldo del Clasificador!"));
						 validaDevengadoCab();
						 return "mainDevengadoRet";
					}
					else {
						devengadoDao.registroDevengadoDet(det);
					}
				}	
					
				}
			
			retornar();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+e.getMessage());
		}
		return "mainDevengado";
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
	}
	
	public String registrarRetencion() {
		try{
			IafasComprobanteRetencion retencion = new IafasComprobanteRetencion();	
			retencion.setVano(periodo);
			retencion.setVtipDocumentoCom(tipoDoc);
			retencion.setVnroCom(nroDoc);
			retencion.setVserieCom(serieDoc);
			retencion.setVexpediente(pregSiaf);
			retencion.setVsecuencia(psecuencia);
			retencion.setVcodImp(vcodImp);
			retencion.setNporImp(nporImp);
			retencion.setVusuarioIng("44330586");
			System.out.println("paramtros"+retencion.toString());
			// donde esta el insert
			devengadoDao.registroRetencionComprobante(retencion);
			logger.info("Se registro la retencion del comprobante :"+serieDoc+"-"+tipoDoc);
			buscarRetencion();
		}
		catch(Exception e) {
			logger.error(e.getStackTrace());
		}
		
		
		return null;
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
			
			
		}
		return listaDet;
	}
	
	private int validaDevengadoCab() {
		int d = 0;
		IafasDevengado devengado = new IafasDevengado();
		devengado.setVano(periodo);
		devengado.setVexpediente(vexpediente);
		d = devengadoDao.deleteDevengado(devengado);
		return d;
	}
	
	private void limpiarCampos() {
		setGlosa("");
		setNroDocCom("");
		setTipDocCom("");
		setFecDocCom(null);
		setNroSerieCom("");
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
	
}
