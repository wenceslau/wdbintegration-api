package br.com.dbintegrationapi;

import br.com.dbintegrationapi.dao.IntegrationDao;
import br.com.library.wlibrary.core.DataQuery;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Main.main()");

		IntegrationDao dao = new IntegrationDao();

		DataQuery dataQuery = new DataQuery();
		dataQuery.setProvider("ODBC");
		dataQuery.setNameDataBase("ritz");
		dataQuery.setUserDataBase("root");
		dataQuery.setPasswordDataBase("master");
		dataQuery.setQuery("select * from feriado");
		
		dao.listNonWorkdays(dataQuery);
	}

}
