package ep.mil.pe.iafas.configuracion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SQLServer2SessionFactory {
	
	private static SqlSessionFactory sqlServer2SessionFactory;

	  static {
		    try {
		      String resource = "ep/mil/pe/iafas/configuracion/xml/SQLServer2SessionFactory.xml";
		      Reader reader = Resources.getResourceAsReader(resource);
		      if (sqlServer2SessionFactory == null)
		    	  sqlServer2SessionFactory = (new SqlSessionFactoryBuilder()).build(reader); 
		    } catch (FileNotFoundException fileNotFoundException) {
		      fileNotFoundException.printStackTrace();
		    } catch (IOException iOException) {
		      iOException.printStackTrace();
		    } 
		  }

		public static SqlSessionFactory getSqlServerSessionFactory() {
			return sqlServer2SessionFactory;
		}

		  

}
