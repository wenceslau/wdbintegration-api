package br.com.dbintegrationapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.library.wlibrary.core.DataQuery;
import br.com.library.wlibrary.ritz.Salesman;
import br.com.library.wlibrary.ritz.Supplier;

/**
 *
 * @author wenceslau.neto
 */
@Service
public class IntegrationDao extends Dao {

	//private static final Logger logger = LoggerFactory.getLogger(IntegrationDao.class);

	public void testConnection(DataQuery dataQuery) throws ClassNotFoundException, SQLException {

		Connection con;
		Statement sts = null;

		try {
			con = getConnection(dataQuery);
			sts = con.createStatement();
			try (ResultSet rs = sts.executeQuery("SELECT 1")) {
				if (rs.next()) {
					System.out.println(rs.getString(1));
				}
			}

		} finally {
			if (sts != null) {
				sts.close();
			}
			closeConnection();
		}
	}

//	public BigDecimal getComission(Query query) throws Exception {
//
//		List<String> listData = run("commission", query);
//
//		if (listData.size() == 1) {
//			return new BigDecimal(listData.get(0));
//		}
//		return new BigDecimal(0);
//	}
//
//	public String insertPayment(Query query) throws Exception {
//
//		List<String> listData = run("payment", query);
//
//		return listData.get(0);
//	}
//
	public List<Supplier> listSupplier(DataQuery dataQuery) throws ClassNotFoundException, SQLException {

		Connection con = null;
		Statement sts = null;
		Supplier supplier;
		List<Supplier> listSupplier = new ArrayList<>();

		try {

			con = getConnection(dataQuery);
			sts = con.createStatement();

			try (ResultSet rs = sts.executeQuery(dataQuery.getQuery())) {

				while (rs.next()) {
					supplier = new Supplier();
					supplier.setId(rs.getInt("fornecedor_id"));
					supplier.setBusinessName(rs.getString("nome"));
					supplier.setName(rs.getString("desc_abrev"));
					listSupplier.add(supplier);
				}
			}

		} finally {
			if (sts != null)
				sts.close();

			closeConnection();
		}
		return listSupplier;
	}

	public List<Salesman> listSalesman(DataQuery dataQuery) throws ClassNotFoundException, SQLException {

		Connection con = null;
		Statement sts = null;
		Salesman salesman;
		List<Salesman> listSalesman = new ArrayList<>();

		try {

			con = getConnection(dataQuery);
			sts = con.createStatement();

			try (ResultSet rs = sts.executeQuery(dataQuery.getQuery())) {
				while (rs.next()) {
					salesman = new Salesman();
					salesman.setId(rs.getInt("vendedor_id"));
					salesman.setNickname(rs.getString("apelido"));
					listSalesman.add(salesman);					
				}
			}

		} finally {
			if (sts != null)
				sts.close();

			closeConnection();
		}
		return listSalesman;
	}

}
