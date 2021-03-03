package ep.mil.pe.iafas.maestras.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.maestras.dao.IasfasMonedaDao;
import ep.mil.pe.iafas.maestras.model.IafasMonedas;
import lombok.Data;

@Data
@ManagedBean(name = "mbMonedas")
@SessionScoped
public class IafasMonedasController {

	private List<IafasMonedas> lista;
	private int registros;
	
	
	 public List<IafasMonedas> listarMonedas() {
		    this.lista = new ArrayList<>();
		    if (this.lista != null)
		      this.lista.clear(); 
		    try {
		      IasfasMonedaDao monedaDao = new IasfasMonedaDao(MySQLSessionFactory.getSqlSessionFactory());
		      List<IafasMonedas> monedas = monedaDao.listaMonedas();
		      this.registros = monedas.size();
		      System.out.println("Listado: " + this.registros);
		      for (IafasMonedas d : monedas)
		        this.lista.add(d); 
		    } catch (Exception e) {
		      System.out.println("Error : " + e.getMessage());
		    } 
		    return this.lista;
		  }
		  
		  public String insert() {
		    String page = "insMonedas.xhtml";
		    return page;
		  }
	
	
}
