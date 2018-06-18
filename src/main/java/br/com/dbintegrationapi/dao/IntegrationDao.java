package br.com.dbintegrationapi.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.library.wlibrary.att.NonWorkdays;
import br.com.library.wlibrary.att.Salesman;
import br.com.library.wlibrary.att.Supplier;
import br.com.library.wlibrary.core.DataQuery;
import br.com.library.wlibrary.core.DataResult;

/**
 *
 * @author wenceslau.neto
 */
@Service
public class IntegrationDao extends Dao {

	private static final Logger logger = LoggerFactory.getLogger(IntegrationDao.class);

	public void testConnection(DataQuery dataQuery) throws ClassNotFoundException, SQLException {

		Connection con;
		Statement sts = null;

		print(dataQuery);

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

	public DataResult getComission(DataQuery dataQuery) throws Exception {

		Connection con = null;
		Statement sts = null;
		BigDecimal amount = new BigDecimal(0);
		DataResult dataResult = new DataResult();
		
		print(dataQuery);

		try {

			con = getConnection(dataQuery);
			sts = con.createStatement();

			try (ResultSet rs = sts.executeQuery(dataQuery.getQuery())) {
				while (rs.next())
					amount = rs.getBigDecimal("total_comissao");
			}
			
			dataResult.setType("DataResult");
			dataResult.setValue(amount);

		} finally {
			if (sts != null)
				sts.close();
			closeConnection();
		}
		return dataResult;

	}

	public DataResult insertPayment(DataQuery dataQuery) throws Exception {

		Connection con = null;
		Statement sts = null;

		print(dataQuery);
		
		DataResult dataResult = new DataResult();

		try {

			con = getConnection(dataQuery);
			sts = con.createStatement();
			PreparedStatement stmt = con.prepareStatement(dataQuery.getQuery()); // operacao READ
			stmt.executeUpdate();
			
			dataResult.setType("String");
			dataResult.setValue("Success");

		} finally {
			if (sts != null)
				sts.close();

			closeConnection();
		}
		
		return dataResult;
	}

	public List<Supplier> listSupplier(DataQuery dataQuery) throws ClassNotFoundException, SQLException {

		Connection con = null;
		Statement sts = null;
		Supplier supplier;
		List<Supplier> listSupplier = new ArrayList<>();

		print(dataQuery);

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

		print(dataQuery);

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

	public List<NonWorkdays> listNonWorkdays(DataQuery dataQuery) throws Exception {

		Connection con = null;
		Statement sts = null;
		NonWorkdays nonWorkdays;
		List<NonWorkdays> list = new ArrayList<>();

		print(dataQuery);

		try {

			con = getConnection(dataQuery);
			sts = con.createStatement();

			try (ResultSet rs = sts.executeQuery(dataQuery.getQuery())) {
				while (rs.next()) {
					nonWorkdays = new NonWorkdays();
					nonWorkdays.setDateNonWorkday(rs.getDate("data"));
					nonWorkdays.setDescription(rs.getString("descricao"));
					list.add(nonWorkdays);
				}
			}

		} finally {
			if (sts != null)
				sts.close();

			closeConnection();
		}

		return list;
	}

	private void print(DataQuery dataQuery) {

		logger.info("Executing query:");
		logger.info(dataQuery.getHost() + ":" + dataQuery.getNameDataBase() + ":" + dataQuery.getUserDataBase());
		logger.info(dataQuery.getQuery());

	}

}
