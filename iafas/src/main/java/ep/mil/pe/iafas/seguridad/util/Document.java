package ep.mil.pe.iafas.seguridad.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class Document implements Serializable, Comparable<Document>{

	private static final long serialVersionUID = 1L;

	private String nombreModulo;
	private String cmenuNombre;
	private String cmoduloCodigo;
	
	public Document(String nombreModulo, String cmenuNombre, String cmoduloCodigo) {
		this.nombreModulo = nombreModulo;
		this.cmenuNombre = cmenuNombre;
		this.cmoduloCodigo = cmoduloCodigo;
	}
	
	@Override
	public int compareTo(Document document) {
        return this.getNombreModulo().compareTo(document.getNombreModulo());
	}

	
}
