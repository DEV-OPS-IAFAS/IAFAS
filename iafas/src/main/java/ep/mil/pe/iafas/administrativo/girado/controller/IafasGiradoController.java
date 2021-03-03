package ep.mil.pe.iafas.administrativo.girado.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import ep.mil.pe.iafas.administrativo.girado.dao.IafasGiradoDao;
import ep.mil.pe.iafas.administrativo.girado.dao.IafasMovimientoCadenasDao;
import ep.mil.pe.iafas.administrativo.girado.model.IafasGirado;
import ep.mil.pe.iafas.administrativo.girado.model.IafasMovimientoCadenas;
import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.configuracion.util.Constantes;
import lombok.Data;

@Data
@ManagedBean(name = "iafasGiradoController")
@SessionScoped
public class IafasGiradoController implements Serializable{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(IafasGiradoController.class.getName());
	
	private String vano;
	private String vregSiaf;
	private String vglosa;
	private BigDecimal impMonSol;
	private String vcci;
	private String cproveedorCuentaBanco;
	private String nombreBanco;
	private String vruc;
	private List<IafasGirado> listaPorGirar = new ArrayList<IafasGirado>();
	private List<IafasMovimientoCadenas> listaPorGirarCadenas = new ArrayList<IafasMovimientoCadenas>();
	private boolean muestraBotonGiro = false;
	private BigDecimal ntipCam;
	private String ctaCte;
	private String tipoGiro;
	private String tipMon;
	private String msgSP;
	
	
	public List<IafasGirado> buscarPorSiafAno() {

		logger.info("[INICIO:] Metodo : buscarPorSiafAno");

		this.listaPorGirar = new ArrayList<>();
		if (this.listaPorGirar != null)
			this.listaPorGirar.clear();

		IafasGiradoDao giradoDao = new IafasGiradoDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasGirado objBn = new IafasGirado();
		objBn.setVano(vano);
		objBn.setVregSiaf(vregSiaf);
		List<IafasGirado> lstporGirar = giradoDao.TraerDatosFaseDevengado(objBn);
		if (lstporGirar.size() > 0) {
			for (IafasGirado objBeanLista : lstporGirar) {
				setVglosa(objBeanLista.getVglosa());
				setImpMonSol(objBeanLista.getImpMonSol());
				setVruc(objBeanLista.getVruc());
				setVcci(objBeanLista.getVcci());
				setCproveedorCuentaBanco(objBeanLista.getCproveedorCuentaBanco());
				setMuestraBotonGiro(true);
				setNtipCam(objBeanLista.getNtipCam());
				this.listaPorGirar.add(objBeanLista);
			}
			
		} else {
			setMuestraBotonGiro(false);
			setVglosa(Constantes.VACIO);
			setVruc(Constantes.VACIO);
			setVcci(Constantes.VACIO);
			setCproveedorCuentaBanco(Constantes.VACIO);
			setImpMonSol(null);
		}
		obtenerCadenasPorGirar();
		logger.info("[FIN:] Metodo : buscarPorSiafAno");
		return listaPorGirar;
	}
	
	private void obtenerCadenasPorGirar() {
		logger.info("[INICIO:] Metodo : obtenerCadenasPorGirar");

		this.listaPorGirarCadenas = new ArrayList<>();
		if (this.listaPorGirarCadenas != null)
			this.listaPorGirarCadenas.clear();

		IafasMovimientoCadenasDao objDao = new IafasMovimientoCadenasDao(MySQLSessionFactory.getSqlSessionFactory());
		IafasMovimientoCadenas objBn = new IafasMovimientoCadenas();
		objBn.setVano(vano);
		objBn.setVregSiaf(vregSiaf);

		List<IafasMovimientoCadenas> lsts = objDao.obtenerCadenasPorGirar(objBn);

		if (lsts.size() > 0 || lsts!= null) {
			for (IafasMovimientoCadenas obj : lsts) {
				listaPorGirarCadenas.add(obj);
			}
		}
		logger.info("[FIN:] Metodo : obtenerCadenasPorGirar");
	}
	
	public String insRegistroGiradoCab() {
		int reg = 0;
		String retorno=Constantes.VACIO;
		logger.info("[INICIO:] Metodo : insRegistroGiradoCab");
		IafasGiradoDao giradoDao = new IafasGiradoDao(MySQLSessionFactory.getSqlSessionFactory());

		IafasGirado objBn = new IafasGirado();
		objBn.setVano(vano);
		objBn.setVregSiaf(vregSiaf);
		objBn.setVctaCodigo(ctaCte);
		objBn.setVcodTipGiro(tipoGiro);
		objBn.setVglosa(vglosa);
		objBn.setImpMonSol(impMonSol);
		objBn.setMode(Constantes.MODE_REGISTRO);
		objBn.setVruc(vruc);
		objBn.setNtipCam(ntipCam);
		objBn.setVtipMon(tipMon);

		try {
			int i = giradoDao.mantenimientoGiradoCab(objBn);
			if (i == 0) {
				setMsgSP("Tu registro de giro se realizó con éxito");
				retorno = "mainIafasConsultaGirados.xhtml";
			}

		} catch (Exception e) {
			logger.error("error : " + e.getMessage().toString());
		} finally {

			logger.info("[FIN:] Metodo : insRegistroGiradoCab");
		}

		return retorno;
	}
	
	private ExternalContext extContext() {
		FacesContext c = FacesContext.getCurrentInstance();
		ExternalContext ec = c.getExternalContext();
		return ec;
	}
}