package ep.mil.pe.iafas.configuracion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SQLServerSessionFactory {

	private static SqlSessionFactory sqlServerSessionFactory;
	
	  static {
		    try {
		      String resource = "ep/mil/pe/iafas/configuracion/xml/SQLSessionFactory.xml";
		      Reader reader = Resources.getResourceAsReader(resource);
		      if (sqlServerSessionFactory == null)
		    	  sqlServerSessionFactory = (new SqlSessionFactoryBuilder()).build(reader); 
		    } catch (FileNotFoundException fileNotFoundException) {
		      fileNotFoundException.printStackTrace();
		    } catch (IOException iOException) {
		      iOException.printStackTrace();
		    } 
		  }

		public static SqlSessionFactory getSqlServerSessionFactory() {
			return sqlServerSessionFactory;
		}

		  
		  
}
