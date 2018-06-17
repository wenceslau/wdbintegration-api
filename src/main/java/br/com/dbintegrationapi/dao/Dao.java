package br.com.dbintegrationapi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import br.com.library.wlibrary.core.DataQuery;

/**
 * Fabrica de conexões com o banco de dados
 *
 * @author Wenceslau
 */
@Service
public class Dao {

	private static Connection con;

	/**
	 * Instancia uma conexao com o banco de dados
	 *
	 * @param dataQuery
	 * @return
	 * @throws java.lang.ClassNotFoundException
	 * @throws java.sql.SQLException
	 */
	public static Connection getConnection(DataQuery dataQuery) throws ClassNotFoundException, SQLException {

		String url = "";
		String driver = "";

		if (con == null || con.isClosed()) {

			switch (dataQuery.getProvider()) {
			case "MSSQL":
				driver = "net.sourceforge.jtds.jdbc.Driver";
				url = "jdbc:jtds:sqlserver://" + dataQuery.getHost() + ":" + dataQuery.getPort() + "/instanceName="
						+ dataQuery.getInstance() + ";DatabaseName=" + dataQuery.getNameDataBase() + "";
				break;
			case "ORACLE":
				break;
			case "MYSQL":
				driver = "com.mysql.jdbc.Driver";
				Class.forName(driver); // carrega driver para a memória da JVM
				url = "jdbc:mysql://" + dataQuery.getHost() + ":" + dataQuery.getPort() + "/"
						+ dataQuery.getNameDataBase()+"?useSSL=false&allowPublicKeyRetrieval=true";
				break;
			case "POSTGREE":
				break;
			case "ODBC":
				driver = "sun.jdbc.odbc.JdbcOdbcDriver";
				url = "jdbc:odbc:" + dataQuery.getNameDataBase() + "";
				break;
			default:
				break;
			}
			Class.forName(driver);
			con = DriverManager.getConnection(url, dataQuery.getUserDataBase(), dataQuery.getPasswordDataBase());
		}

		return con;
	}

	public static void closeConnection() throws SQLException {
		if (con != null) {
			con.close();
			con = null;
		}
	}
}
