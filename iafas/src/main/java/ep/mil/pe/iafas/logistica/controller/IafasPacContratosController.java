package ep.mil.pe.iafas.logistica.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.logistica.dao.IafasPaacContratoDao;
import ep.mil.pe.iafas.logistica.dao.IafasPacAdemdaCFDao;
import ep.mil.pe.iafas.logistica.dao.ViewPaacProcesoDetalleDao;
import ep.mil.pe.iafas.logistica.model.IafasPacAdemdaCF;
import ep.mil.pe.iafas.logistica.model.IafasPacContratos;
import ep.mil.pe.iafas.logistica.model.ViewPaacProcesoDetalle;
import lombok.Data;

@Data
@ManagedBean(name="mbContratosPaac")
@SessionScoped
public class IafasPacContratosController implements Serializable{
	
	private String periodo;
	private String periodoProceso;
	private int tipoFunFin;
	private int codProcPaac;
	private String nroContrato;
	private String descContrato;
	private Date fechaInicio;
	private Date fechaFin;
	private BigDecimal tipoCambio;
	private int monedaCodigo;
	private BigDecimal montoContrato;
	private BigDecimal montoExtContrato;
	private String ruc;
	private String razonSocial;
	private String nomArchivo;
	       
	
	private String typeMessages;
	private IafasPacContratos selectedPaac;
	
	private BigDecimal cantidadDetalle;
	private BigDecimal montoMNDet;
	private BigDecimal montoMEDet;
	
	
	private List<IafasPacContratos> listaProcesos;
	private List<ViewPaacProcesoDetalle> listadoDetalle;
	private List<IafasPacContratos> lista;
	
	private List<IafasPacAdemdaCF> listaCF;
	private List<IafasPacAdemdaCF> listaAdemda; 
	private List<IafasPacContratos> listaContDetalle;
	
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(IafasPacContratosController.class);

	IafasPaacContratoDao contratosDao = new IafasPaacContratoDao(MySQLSessionFactory.getSqlSessionFactory());
	ViewPaacProcesoDetalleDao detailsDao = new ViewPaacProcesoDetalleDao(MySQLSessionFactory.getSqlSessionFactory());
	IafasPacAdemdaCFDao extrasDao = new IafasPacAdemdaCFDao(MySQLSessionFactory.getSqlSessionFactory());
	public IafasPacContratosController() {
		// TODO Auto-generated constructor stub
		
	}
	
	public List<IafasPacContratos> findProcesosPaac(){
		listaProcesos = new ArrayList<IafasPacContratos>();
		List<IafasPacContratos> procesos = contratosDao.showPaacProcess(periodoProceso);
		procesos.forEach((p) -> listaProcesos.add(p));
		return listaProcesos;
	}
	
	
	public void selectProcessFilter(){
		try {
			//String pProccesoCod = (String) extContext().getRequestParameterMap().get("pProcessCod");
			//logger.info("Selecciona Proceso PAAC {}" +pProccesoCod);
			IafasPacContratos cont = new IafasPacContratos();
			cont.setCprocesoPeriodo(selectedPaac.getCprocesoPeriodo());
			cont.setNpacProcesosCodigo(selectedPaac.getNpacProcesosCodigo());
			List<IafasPacContratos> reg = contratosDao.showPaacProcessFilter(cont);
			for(IafasPacContratos c : reg) {
				setTipoFunFin(c.getNfuenteFinanciamiento());
				setCodProcPaac(c.getNpacProcesosCodigo());
				setPeriodoProceso(c.getCprocesoPeriodo());
			}
			logger.info("Datos Obtenidos {} "+codProcPaac+" "+periodoProceso+" "+tipoFunFin);
			viewDetailContract(selectedPaac.getCprocesoPeriodo(), selectedPaac.getNpacProcesosCodigo());
			logger.info("Cantidad de Items del Proceso {} "+viewDetailContract(selectedPaac.getCprocesoPeriodo(),
					                                        selectedPaac.getNpacProcesosCodigo()).size());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] "+e.getMessage());
		}
		
	}
	
	public List<ViewPaacProcesoDetalle> viewDetailContract(String periodo, int codigo) {
		listadoDetalle = new ArrayList<ViewPaacProcesoDetalle>();
		ViewPaacProcesoDetalle details = new ViewPaacProcesoDetalle();
		details.setPeriodo(periodo);
		details.setCodProceso(codigo);
		List<ViewPaacProcesoDetalle> detalle = detailsDao.showDetailsPaacProcess(details);
		detalle.forEach((p) -> listadoDetalle.add(p));
		return listadoDetalle;
	}
	
	public IafasPacContratos getSelectedPaac() {
		return selectedPaac;
	}
	
	public List<IafasPacContratos> showAllContrarcts(){
		lista = new ArrayList<IafasPacContratos>();
		List<IafasPacContratos> contracts = contratosDao.showContractsByYear(periodo);
		contracts.forEach((con) -> lista.add(con));
		return lista;
	}
	
    public void verProcesos() {
    	findProcesosPaac();
    }
    
    public void clearFields() {
    	periodoProceso = "";
    	tipoFunFin=0;
    	codProcPaac=0;
    	nroContrato="";
    	descContrato="";
    	fechaInicio = null;
    	fechaFin = null;
    	tipoCambio = new BigDecimal(0);
    	monedaCodigo=0;
    	montoContrato= new BigDecimal(0);
    	montoExtContrato = new BigDecimal(0);
         ruc= "";
    }
    
    public String retornar() {
    	if(listadoDetalle.size()>0) {
    		listadoDetalle.clear();
    	}
    	clearFields();
    	showAllContrarcts();
    	return "/mainContratosPaac.xhtml";
    }
    
	public String nuevoRegistro() {
		return "insRegContratosPaac.xhtml";
	}
	
	public String registroContratos() {
		try {
			IafasPacContratos ent = new IafasPacContratos();
			ent.setNcontratoCodigo(1);
			ent.setCprocesoPeriodo(periodoProceso);
			ent.setNfuenteFinanciamiento(tipoFunFin);
			ent.setNpacProcesosCodigo(codProcPaac);
			ent.setCperiodoCodigo(periodo);
			ent.setVnumeroContrato(nroContrato);
			ent.setVcontratoDescripcion(descContrato);
			ent.setDcontratoInicio(fechaInicio);
			ent.setDcontratoFin(fechaFin);
			ent.setNcontratoMonto(montoContrato);
			ent.setNmonedaCodigo(monedaCodigo);
			ent.setNcontratoTipoCambio(tipoCambio);
			ent.setNcontratoExtranjera(montoExtContrato);
			ent.setVcontratoArchivo("");
			ent.setVproveedorRuc(ruc);
			ent.setVusuarioCodigo("44330586");
			ent.setTipo("I");
		    contratosDao.saveContractPAAC(ent);
		    logger.info("Registro Contrato NRO :" +nroContrato);
		    registroContratoDetalle(listadoDetalle);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] : "+e.getMessage());
		}
		return retornar();
	}
	
	public String registroContratoDetalle(List<ViewPaacProcesoDetalle> detalles) {
		try {
			IafasPacContratos details = new IafasPacContratos();
			logger.info("PArametros Externos: "+periodo+" "+tipoCambio);
			logger.info("[INICIO] Proceso de Registro de Detalle de Contrato {} "+detalles.size());
			for(int d=0 ; d<detalles.size();d++) {
				if(detalles.get(d).getCantidadItem().doubleValue()>=detalles.get(d).getCantidadIng().doubleValue()) {
					details.setNcontratoCodigo(1);
					details.setCperiodoCodigo(periodo);
					details.setCodItem(detalles.get(d).getCodItem());
					details.setCantidad(detalles.get(d).getCantidadIng());
					details.setMontoMNDetalle(detalles.get(d).getPrecioItem().multiply(detalles.get(d).getCantidadIng()));
					if(tipoCambio.doubleValue()==0) {details.setMontoMEDetalle(BigDecimal.ZERO);}
					else {details.setMontoMEDetalle((detalles.get(d).getPrecioItem().multiply
							                       (detalles.get(d).getCantidadIng())).divide(tipoCambio, RoundingMode.HALF_DOWN));
					}
					details.setUnidadMedida(detalles.get(d).getUnidadMedidaCod());
					details.setVusuarioCodigo("44330586");
					details.setTipo("I");
					logger.info("Montos :" +details.getMontoMEDetalle());
					contratosDao.saveContractDetailsPAAC(details);
					logger.info("[FIN] Se registro detalle del Contrato {} ");
				}
				else {
					logger.info("Error la cantidad excede a la del Proceso");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR DETALLE] "+e.getMessage());
		}
		return null;
	}
	
	public void verMonto() {
		double MN = 0;
		double ME = 0;
		for(int i=0; i<listadoDetalle.size(); i++) {
			logger.info("P"+i+" "+listadoDetalle.get(i).getCantidadIng().multiply(listadoDetalle.get(i).getPrecioItem()));
			BigDecimal valorMN = listadoDetalle.get(i).getCantidadIng().multiply(listadoDetalle.get(i).getPrecioItem());
			MN = MN+valorMN.doubleValue();
			if(tipoCambio.doubleValue()>0) {
				logger.info("PExt"+i+" "+(listadoDetalle.get(i).getPrecioItem().multiply
						(listadoDetalle.get(i).getCantidadIng())).divide(tipoCambio, RoundingMode.HALF_DOWN));
				BigDecimal valorME =(listadoDetalle.get(i).getPrecioItem().multiply
						(listadoDetalle.get(i).getCantidadIng())).divide(tipoCambio, RoundingMode.HALF_DOWN);
				ME = ME+valorME.doubleValue();
			}
		}
		setMontoContrato(BigDecimal.valueOf(MN));
		setMontoExtContrato(BigDecimal.valueOf(ME));
		logger.info("Resiltado param:"+MN +" "+ME);
		logger.info("result en form: "+montoContrato + " "+montoExtContrato);

	}
	
	public List<IafasPacContratos> findDetContrato(int codigo, String anio){
		listaContDetalle = new ArrayList<IafasPacContratos>();
		IafasPacContratos pacc = new IafasPacContratos();
		pacc.setNcontratoCodigo(codigo);
		pacc.setCperiodoCodigo(anio);
		List<IafasPacContratos> procesos = contratosDao.showDetailContract(pacc);
		procesos.forEach((p) -> listaContDetalle.add(p));
		return listaContDetalle;
	}
	
	public List<IafasPacAdemdaCF> findCartaFianza(int codigo, String anio){
		listaCF = new ArrayList<IafasPacAdemdaCF>();
		
		IafasPacAdemdaCF cf = new IafasPacAdemdaCF();
		cf.setNcontratoCodigo(codigo);
		cf.setCperiodoCodigo(anio);
		List<IafasPacAdemdaCF> procesos = extrasDao.showPaacCartaFianza(cf);
		procesos.forEach((p) -> listaCF.add(p));
		return listaCF;
	}
	
	public List<IafasPacAdemdaCF> findAdemda(int codigo, String anio){
		listaAdemda = new ArrayList<IafasPacAdemdaCF>();
		IafasPacAdemdaCF cf = new IafasPacAdemdaCF();
		cf.setNcontratoCodigo(codigo);
		cf.setCperiodoCodigo(anio);
		List<IafasPacAdemdaCF> procesos = extrasDao.showPaacAdemda(cf);
		procesos.forEach((p) -> listaAdemda.add(p));
		return listaAdemda;
	}
	
	public void mostrarDetalles() {
	    String contratoID = (String) extContext().getRequestParameterMap().get("pId");
	    String periodoCOD = (String) extContext().getRequestParameterMap().get("pAnio");
	    logger.info("Parametros :"+contratoID +" "+periodoCOD);
	    logger.info("Cargando consultas Externas:");
	    try {
		    findDetContrato(Integer.valueOf(contratoID), periodoCOD);
		    findCartaFianza(Integer.valueOf(contratoID), periodoCOD);
		    findAdemda(Integer.valueOf(contratoID), periodoCOD);
		    logger.info("Se cargaron las consultas satisfactoriamente");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[ERROR] : "+e.getMessage());
		}

	}
	
    private ExternalContext extContext() {
        FacesContext c = FacesContext.getCurrentInstance();
        ExternalContext ec = c.getExternalContext();
        return ec;
    }

}
