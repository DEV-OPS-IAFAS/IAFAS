package ep.mil.pe.iafas.administrativo.compromiso.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import ep.mil.pe.iafas.administrativo.compromiso.dao.IafasCompromisoMensualDao;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensual;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensualDet;
import ep.mil.pe.iafas.administrativo.compromiso.model.ViewIafasCompromisoMensual;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
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
	
	
	IafasCompromisoMensualDao mensualDao = new IafasCompromisoMensualDao(MySQLSessionFactory.getSqlSessionFactory());

	public IafasCompMensualController() {
		// TODO Auto-generated constructor stub
		listadoMensual();
	}
	
	public List<IafasCompromisoMensual> listadoMensual(){
		lista = new ArrayList<IafasCompromisoMensual>();
		List<IafasCompromisoMensual> registros = mensualDao.listaMensual(periodo);
		for(IafasCompromisoMensual reg :registros) {
			lista.add(reg);
		}
		return lista;
	}
	
	public String retornar() {
		limpiarSession();
		listadoMensual();		
		return "mainCompromisoMensual.xhtml";
	}
	
	public List<ViewIafasCompromisoMensual> buscarAnual(){
		
		regAnual = new ArrayList<ViewIafasCompromisoMensual>();
		ViewIafasCompromisoMensual vcm = new ViewIafasCompromisoMensual();
		vcm.setVano(periodo);
		vcm.setVnroCertificado(certificado);
		List<ViewIafasCompromisoMensual> registros = mensualDao.buscaCompromisoAnual(vcm);
		for(ViewIafasCompromisoMensual l : registros)
		{regAnual.add(l);}
		return regAnual;
	}
	
	public List<ViewIafasCompromisoMensual> buscarAnualXSecuencia(){
		regAnualDet = new ArrayList<ViewIafasCompromisoMensual>();
		ViewIafasCompromisoMensual vcm = new ViewIafasCompromisoMensual();
		String psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
		vcm.setVano(periodo);
		vcm.setVnroCertificado(certificado);
		vcm.setVsecuenciaA(psecuencia);
		List<ViewIafasCompromisoMensual> registros = mensualDao.buscaCompromisoAnualSecuencia(vcm);
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
		}
		return regAnualDet;
	}
	
	public void verAnuales() {
		//String page = "insRegCompMensual.xhtml";
		buscarAnual();
		//return page;
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
		setFechaMensual(null);
		setGlosa(Constantes.VACIO);
		regAnualDet.clear();
	}
	
	
	public String registroCompMensual() {
		int reg =0;
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
			men.setVnroCertificado(certificado);
			men.setVcodProcesoSel(codProcesoSel);
			men.setCproveedorRuc(ruc);
			men.setVtipDocumentoCom(tipDocCom);
			men.setVnroDocumentoCom(nroDocCom);
			men.setVserieCom(nroSerieCom);
			men.setDfechaDevengado(fecDocCom);
			men.setVusuarioIng("44330586");
			mensualDao.registroCompMensual(men);
			for(int j =0;j <regAnualDet.size(); j++) {
					det.setVano(periodo);
					det.setVcodSecFunc(regAnualDet.get(j).getVcodSecFunc());
					det.setVidClasificador(regAnualDet.get(j).getVidClasificador());
					det.setNimpMonSol(regAnualDet.get(j).getMontoIngresado());
					det.setVusuarioIng("44330586");
				if(getRegAnualDet().get(j).getMontoIngresado().doubleValue()>0) {
					if(getRegAnualDet().get(j).getNimpMontoSol().doubleValue() < getRegAnualDet().get(j).getMontoIngresado().doubleValue()) {
						 FacesContext.getCurrentInstance().addMessage(null, new 
					     FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!", "El Monto Ingresado Excede al Saldo del Clasificador!"));
						 deleteCompromisoMensual();
						 return "insRegCompMensual";
					}
					else {
						mensualDao.registroCompMensualDet(det);
					}
				}	
					
				}
			
			return retornar();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+e.getMessage());
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
	
	public int enviarCompromisoMensual() {
		int envio = 0;
		try {

			IafasCompromisoMensual ca = new IafasCompromisoMensual();
			ca.setVano(periodo);
			ca.setSecuencia(psecuencia);
			ca.setCorrelativo(pcorrelativo);
			ca.setVexpediente(pexpediente);
			ca.setVusuarioIng("44330686");
			
			envio = mensualDao.enviarCompromisoMensual(ca);
		}
		catch (Exception e) {
			System.out.println("Error Envio : "+e.getMessage());
		}
         retornar();
		return envio;
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
	
    private ExternalContext extContext() {
        FacesContext c = FacesContext.getCurrentInstance();
        ExternalContext ec = c.getExternalContext();
        return ec;
    }
}
