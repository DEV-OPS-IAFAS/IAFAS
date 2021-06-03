package ep.mil.pe.iafas.administrativo.girado.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class IafasEntidadesCuentas {

	private String codigoEntidad;
	private String centidadCuenta;
	private String codigoTipoCuenta;
	private String cestadoCodigo;
	private BigDecimal nmontoCuentaTecho;
	private BigDecimal nmontoCuentaAvance;
	private BigDecimal nmontoCuentaSaldo;
	private String descripcionTipoCuenta;

}
