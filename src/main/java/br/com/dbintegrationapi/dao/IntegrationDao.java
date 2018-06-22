package br.com.dbintegrationapi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		Double amount = new Double(0);
		DataResult dataResult = new DataResult();

		print(dataQuery);

		try {

			con = getConnection(dataQuery);
			sts = con.createStatement();

			try (ResultSet rs = sts.executeQuery(dataQuery.getQuery())) {
				while (rs.next())
					amount += rs.getDouble("total_comissao");
			}

			dataResult.setType("DataResult");
			dataResult.setValue(amount);

			print(dataResult);

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

			print(dataResult);

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

			logger.info("listSupplier: " + listSupplier);

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

			logger.info("listSalesman: " + listSalesman);

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
		List<NonWorkdays> listNonWorkdays = new ArrayList<>();

		print(dataQuery);

		try {

			con = getConnection(dataQuery);
			sts = con.createStatement();
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			try (ResultSet rs = sts.executeQuery(dataQuery.getQuery())) {
				while (rs.next()) {
					nonWorkdays = new NonWorkdays();
					nonWorkdays.setDateNonWorkday(rs.getDate("data"));
					nonWorkdays.setStrDateNonWorkday(format.format(nonWorkdays.getDateNonWorkday()));
					nonWorkdays.setDescription(rs.getString("descricao"));
					listNonWorkdays.add(nonWorkdays);
				}
			}

			logger.info("listNonWorkdays: " + listNonWorkdays);

		} finally {
			if (sts != null)
				sts.close();

			closeConnection();
		}

		return listNonWorkdays;
	}

	private void print(DataQuery dataQuery) {

		logger.info("Executing DataQuery:");
		logger.info(dataQuery.getHost() + ":" + dataQuery.getNameDataBase() + ":" + dataQuery.getUserDataBase());
		logger.info(dataQuery.getQuery());

	}

	private void print(DataResult dataResult) {
		logger.info("Return DataResult:");
		logger.info("dataResult.getType: " + dataResult.getType());
		logger.info("dataResult.getValue: " + dataResult.getValue());
	}

}
