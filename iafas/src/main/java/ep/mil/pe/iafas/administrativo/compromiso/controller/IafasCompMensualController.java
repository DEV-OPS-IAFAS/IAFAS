package ep.mil.pe.iafas.administrativo.compromiso.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import ep.mil.pe.iafas.administrativo.compromiso.dao.IafasCompromisoMensualDao;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensual;
import ep.mil.pe.iafas.administrativo.compromiso.model.IafasCompromisoMensualDet;
import ep.mil.pe.iafas.administrativo.compromiso.model.ViewIafasCompromisoMensual;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
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
	private String  razonSocial;
	private String vcodMoneda;
	private String vanoDocumentoA;
	private String vsecuenciaA;
	private String vcorrelativoA;
	
	
	IafasCompromisoMensualDao mensualDao = new IafasCompromisoMensualDao(MySQLSessionFactory.getSqlSessionFactory());

	public IafasCompMensualController() {
		// TODO Auto-generated constructor stub
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
			System.out.println("Param :"+ruc+" "+vcodMoneda);
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
	    return page;
	}

	public int registroCompMensual() {
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
				if(regAnualDet.get(j).getMontoIngresado()!=null || regAnualDet.get(j).getMontoIngresado().doubleValue()>0) {
					det.setVano(periodo);
					det.setVcodSecFunc(regAnualDet.get(j).getVcodSecFunc());
					det.setVidClasificador(regAnualDet.get(j).getVidClasificador());
					det.setNimpMonSol(regAnualDet.get(j).getMontoIngresado());
					det.setVusuarioIng("44330586");
					mensualDao.registroCompMensualDet(det);
				}
			}
			
			retornar();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+e.getMessage());
		}
		return reg;
	}
	
    private ExternalContext extContext() {
        FacesContext c = FacesContext.getCurrentInstance();
        ExternalContext ec = c.getExternalContext();
        return ec;
    }
}
