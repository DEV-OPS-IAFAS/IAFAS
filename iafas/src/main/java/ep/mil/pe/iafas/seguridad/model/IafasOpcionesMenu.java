package ep.mil.pe.iafas.seguridad.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IafasOpcionesMenu  implements Serializable {
	 private static final long serialVersionUID = 1L;
	 
	 private int	posMenu;  
	 private String codMenu; 
	 private String nivMenu; 
	 private String desMenu; 
	 private String estPrv; 
	
	

}
