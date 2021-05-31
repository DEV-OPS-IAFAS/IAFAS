package ep.mil.pe.iafas.logistica.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.logistica.dao.IafasPacAdemdaCFDao;
import ep.mil.pe.iafas.logistica.model.IafasPacAdemdaCF;
import lombok.Data;

@Data
@ManagedBean(name = "mbContACF")
@SessionScoped
public class IafasPacAdemdaCFController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String periodo;
	private int idContrato;
	private int idAdemda;
	private Date fechaAdemda;
	private String documentoAdemda;
	private String motivoAdemda;
	private String archivoAdemda;
	private String estadoAdemda;
	
	// Carta Fianza
	private int idCartaFianza;
	private String numeroCF;
	private BigDecimal montoCF;
	private BigDecimal montoEXCF;
	private int codMoneda;
	private BigDecimal tipCam;
	private Date fechaVigencia;
	private String idBanco;
	private String archivoCF;
	private String estadoCF;
	
	
	private static final Logger logger = Logger.getLogger(IafasPacAdemdaCFController.class);
     IafasPacAdemdaCFDao cfaDao = new IafasPacAdemdaCFDao(MySQLSessionFactory.getSqlSessionFactory());
	public IafasPacAdemdaCFController() {
		// TODO Auto-generated constructor stub
	}
	
	public void captureId() {
		String pIdContrato = (String) extContext().getRequestParameterMap().get("pContratoID");
		periodo = (String) extContext().getRequestParameterMap().get("pPeriodoID");
		idContrato = Integer.valueOf(pIdContrato);
		logger.info("Parametros del Contrato Obtenidos {} " + periodo +" "+idContrato);
	}
	
	public String saveBailLetter() {
		try {
			logger.info("[INICIO] Registro Carta Fianza : ");
			IafasPacAdemdaCF cf = new IafasPacAdemdaCF();
			cf.setNcontratoCodigo(idContrato);
			cf.setCperiodoCodigo(periodo);
			cf.setNpacContCFCod(1);
			cf.setNpacContCFNumero(numeroCF);
			cf.setNpacContCFMonto(montoCF);
			cf.setNmonedaCodigo(codMoneda);
			cf.setNpacContCFMontoEx(montoEXCF);
			cf.setNpacContCFTipCam(tipCam);
			cf.setNpacContCFVigencia(fechaVigencia);
			cf.setCbancoCodigo(idBanco);
			cf.setCestadoCodigo(estadoCF);
			cf.setVpacContCFArchivo(archivoCF);
			cf.setVusuarioCodigo("44330586");
			cf.setTipo("I");
			cfaDao.registrarCartaFianza(cf);
			logger.info("[FIN] : Se registro Carta Fianza N° {} " +numeroCF);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] No se registro Carta Fianza : "+e.getMessage());
		}
		return retornar();
	}

	public String retornar() {
		limpiarCampos();
		return "mainContratosPaac.xhtml";
	}
	
	public void limpiarCampos() {
		logger.info("Limpiando Campos");
		idContrato = 0;
		periodo="";
		numeroCF="";
		montoCF = null;
		codMoneda=0;
		montoEXCF=null;
		tipCam=null;
		fechaVigencia=null;
		idBanco="";
		estadoCF="";
		archivoCF="";
		
		fechaAdemda = null;
		documentoAdemda="";
		motivoAdemda="";
		archivoAdemda = "";
		estadoAdemda = "";
	}
	
	public String saveAdemda() {
		try {
			logger.info("[INICIO] Registro Ademda : ");
			IafasPacAdemdaCF ad = new IafasPacAdemdaCF();
			ad.setNcontratoCodigo(idContrato);
			ad.setCperiodoCodigo(periodo);
			ad.setNpacContratoAdemdaCodigo(1);
			ad.setDpacContratoAdemdaFecha(fechaAdemda);
			ad.setVpacContratoAdemdaDocumento(documentoAdemda);
			ad.setVpacContratoAdemdaMotivo(motivoAdemda);
			ad.setVpacContratoAdemdaArchivo(archivoAdemda);
			ad.setCestadoCodigo(estadoAdemda);
			ad.setVusuarioCodigo("44330586");
			ad.setTipo("I");
			cfaDao.registrarAdemda(ad);
			logger.info("[FIN] Registro Ademda : "+documentoAdemda);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] :"+e.getMessage());
		}
		return retornar();
	}
    private ExternalContext extContext() {
        FacesContext c = FacesContext.getCurrentInstance();
        ExternalContext ec = c.getExternalContext();
        return ec;
    }
}
