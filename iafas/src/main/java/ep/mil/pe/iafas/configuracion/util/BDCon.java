package ep.mil.pe.iafas.configuracion.util;

public class BDCon {

	 public BDCon() {}
	
	public static final String DRIVERBD = "com.mysql.jdbc.Driver";
	public static final String CONEXION_LOCAL = "jdbc:mysql://localhost:3306/IAFAS?user=iafas&password=iafas&useSSL=false";
	public static final String CONEXION_PRODUCCION = "jdbc:mysql://localhost:3306/IAFASEP?user=iafasep&password=iafasEP$2021";
}
