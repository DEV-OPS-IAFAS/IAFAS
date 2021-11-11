package ep.mil.pe.iafas.configuracion.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import net.sf.jasperreports.engine.JRException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperRunManager;


@WebServlet("/GenerarReporte")
public class GenerarReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ServletConfig config=null;   
    private ServletContext context=null;
 
    public GenerarReporte() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (JRException ex) {
            	System.out.println("ex:"+ex);
            }
        } catch (SQLException ex) {
        	System.out.println("ex:"+ex);
        }					
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                processRequest(request, response);
            } catch (JRException ex) {
              System.out.println("ex:"+ex);
            }
        } catch (SQLException ex) {
        	 System.out.println("ex:"+ex);
        }		
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException, SQLException, JRException{
		
		config = this.getServletConfig();
	    context = config.getServletContext();
	    
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}
	    
	    Connection objConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/iafas", "root", "Esc240789.");
	    
	    String cPeriodo = request.getParameter("cPeriodo");
	    String tipoActa = request.getParameter("tipoActa");
	    String codigoActa = request.getParameter("codigoActa");
	    String nomRep =Constantes.VACIO;
	    String nombre = Constantes.VACIO;
	    String codigoIngDoc = request.getParameter("codigoIngDoc");
	    
	    
	    if(codigoIngDoc!=null){
			 
	    	cPeriodo = codigoIngDoc.substring(0,4);
	    	tipoActa = codigoIngDoc.substring(4,5);
	    	codigoActa =  codigoIngDoc.substring(5,6);
			nomRep = codigoIngDoc.substring(6,15);
			System.out.println("valores del reporte: "+cPeriodo+ "-"+tipoActa+" - "+codigoActa+" - "+nomRep );
		}   
	    
	   if(nomRep.equals("REP001LOG")) nombre = "REP001LOG.jasper";
	    InputStream stream = null;
	    
	    stream = context.getResourceAsStream("/reportes/"+ nombre);
	    System.out.println("stream:"+stream);
	    if (stream == null) { 
	    throw new IllegalArgumentException("No se encuentra el reporte con nombre: " + nombre); 
	    } 

	       Map<String, Object> parameters = new  HashMap();  //HashMap()<String, Object>();
	  
	       parameters.put("CPERIODO", cPeriodo);
	       parameters.put("TIPO_ACTA", tipoActa);
	       parameters.put("CODIGO_ACTA", codigoActa);
	       
	       byte[] bytes = JasperRunManager.runReportToPdf(stream, parameters, objConnection);
		    
		    response.setContentType("application/pdf");
		    response.setContentLength(bytes.length);  
		    ServletOutputStream ouputStream = response.getOutputStream();
		    objConnection.close();
		    ouputStream.write(bytes, 0, bytes.length);
		    ouputStream.flush();
		    ouputStream.close();
	}
	
	public String getServletInfo() {
        return "Short description";
    }
	
}
