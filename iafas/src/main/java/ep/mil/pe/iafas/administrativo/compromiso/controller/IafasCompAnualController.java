package ep.mil.pe.iafas.administrativo.compromiso.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.administrativo.compromiso.dao.IafasCompromisoAnualDao;
import ep.mil.pe.iafas.administrativo.compromiso.dao.IafasCompromisoAnualDetDao;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnual;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoAnualDet;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;	
import lombok.Data;

@Data
@ManagedBean(name="mbCompAnual")
@SessionScoped
public class IafasCompAnualController implements Serializable {
	
	private List<IafasCompromisoAnual> lista;
	private List<IafasCompromisoAnualDet> listaCertDet;
	
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
    private String ruc = "10443305861";
    private String razonSocial="PRODUCTOS ACME S.A.";
    private String tipCodFun;
    private String desPresupuesto;
    private BigDecimal nimpCompSol;
    private Date fechaCert;    
    //Parametros
    private String psecuencia;
    private String pcorrelativo;
    
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(IafasCompAnualController.class);

	public IafasCompAnualController() {
		// TODO Auto-generated constructor stub
		//listarCompAnualCab();
	}
	
	public List<IafasCompromisoAnual> listarCompAnualCab(){
		logger.info("metodo : listarCompAnualCab");
		lista = new ArrayList<IafasCompromisoAnual>();
		try {
			 IafasCompromisoAnual ca = new IafasCompromisoAnual();
			IafasCompromisoAnualDao compAnualDao = new IafasCompromisoAnualDao(MySQLSessionFactory.getSqlSessionFactory());
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
			System.out.println("Error222 :"+e.getCause());
		}
		return lista;
	}
	
	public void verDetalleRegistroCA() {
		try {
			  psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
			  pcorrelativo = (String) extContext().getRequestParameterMap().get("p_correlativo");
			 IafasCompromisoAnual ca = new IafasCompromisoAnual();
			 IafasCompromisoAnualDao compAnualDao = new IafasCompromisoAnualDao(MySQLSessionFactory.getSqlSessionFactory());
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
			
		}

		 
	}
	
	public List<IafasCompromisoAnualDet> listarCompAnualDet(){
		listaCertDet = new ArrayList<IafasCompromisoAnualDet>();
		try {
			IafasCompromisoAnualDet ca = new IafasCompromisoAnualDet();
			IafasCompromisoAnualDetDao compAnualDetDao = new IafasCompromisoAnualDetDao(MySQLSessionFactory.getSqlSessionFactory());
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
		listarCompAnualDet();
	    return page;
	}
	
	public String retornar(){
		String page="mainCompromisoAnual.xtml";
		return page;
	}

	public int grabarCompAnual() {
		int reg = 0;
		 IafasCompromisoAnual ca = new IafasCompromisoAnual();
		 IafasCompromisoAnualDao compAnualDao = new IafasCompromisoAnualDao(MySQLSessionFactory.getSqlSessionFactory());
		 IafasCompromisoAnualDet det = new IafasCompromisoAnualDet();
		 IafasCompromisoAnualDetDao compAnualDetDao = new IafasCompromisoAnualDetDao(MySQLSessionFactory.getSqlSessionFactory());
		 
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
		 ca.setVusuarioIng("44330586");
		 ca.setVglosa(concepto);
		 ca.setNimpMonSol(BigDecimal.ZERO);
		 reg = compAnualDao.grabarCompAnual(ca);
		 
		 for(int j =0;j<getListaCertDet().size();j++){
			 if(getListaCertDet().get(j).getMontoIngresado().doubleValue()>0 || 
			    getListaCertDet().get(j).getMontoIngresado()!=null) {
				 det.setVanoDocumento(periodo);
				 det.setVnroCertificado(certificado);
				 det.setVcodSec(getListaCertDet().get(j).getVcodSec());
				 det.setVidClasificador(getListaCertDet().get(j).getVidClasificador());
				 det.setNimpMontoSol(getListaCertDet().get(j).getMontoIngresado());
				 det.setVusuarioIng("44330586");
				// nimpCompSol.add(getListaCertDet().get(j).getMontoIngresado().doubleValue());
				 compAnualDetDao.grabarCompAnualDet(det);
			 }
			 
		 }

		return reg;
	}
	
	public String obtener() {
		String page="";
		  psecuencia = (String) extContext().getRequestParameterMap().get("p_secuencia");
		  pcorrelativo = (String) extContext().getRequestParameterMap().get("p_correlativo");
		  return page;
	}
	
	
	public int enviarCompromisoAnual() {
		int envio = 0;
		try {

			IafasCompromisoAnual ca = new IafasCompromisoAnual();
			IafasCompromisoAnualDao compAnualDao = new IafasCompromisoAnualDao(MySQLSessionFactory.getSqlSessionFactory());
			ca.setVanoDocumento(periodo);
			ca.setVnroCertificado(certificado);
			ca.setVsecuenciaA(psecuencia);
			ca.setVcorrelativoA(pcorrelativo);
			ca.setVusuarioIng("44330686");
			
			envio = compAnualDao.enviarCompAnual(ca);
		}
		catch (Exception e) {
			System.out.println("Error Envio : "+e.getMessage());
		}

		return envio;
	}
	
	public boolean validarCampos() {
		
		return false; 
	}
	
    private ExternalContext extContext() {
        FacesContext c = FacesContext.getCurrentInstance();
        ExternalContext ec = c.getExternalContext();
        return ec;
    }
}
