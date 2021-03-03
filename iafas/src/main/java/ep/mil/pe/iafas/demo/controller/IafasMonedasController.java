package ep.mil.pe.iafas.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ep.mil.pe.iafas.configuracion.MySQLSessionFactory;
import ep.mil.pe.iafas.demo.dao.IasfasMonedaDao;
import ep.mil.pe.iafas.demo.model.IafasMonedas;
import lombok.Data;

@Data
@ManagedBean(name="mbMonedas")
@SessionScoped
public class IafasMonedasController {
	
	private List<IafasMonedas> lista;
	private int registros;

	public IafasMonedasController() {
		// TODO Auto-generated constructor stub
		listarMonedas();
	}

	public List<IafasMonedas> listarMonedas() {
		lista = new ArrayList<IafasMonedas>();
		if(lista!=null) {
			lista.clear();
		}
		try{
			//instancia el DAO
			IasfasMonedaDao monedaDao = new IasfasMonedaDao(MySQLSessionFactory.getSqlSessionFactory());
			List<IafasMonedas> monedas = monedaDao.listaMonedas();
			registros = monedas.size();
			System.out.println("Listado: " +registros);
			for(IafasMonedas d : monedas) {
				lista.add(d);
			}
		}
		catch(Exception e) {
			System.out.println("Error : "+e.getMessage());
		}
       return lista;
	}
	
	public String insert() {
		String page = "insMonedas.xhtml";
		return page;
	}
}
