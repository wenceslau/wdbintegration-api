package br.com.dbintegrationapi.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbintegrationapi.dao.IntegrationDao;
import br.com.library.wlibrary.att.NonWorkdays;
import br.com.library.wlibrary.att.Salesman;
import br.com.library.wlibrary.att.Supplier;
import br.com.library.wlibrary.core.DataQuery;

@RestController
@RequestMapping("/object")
public class ObjectResource {

	private static final Logger logger = LoggerFactory.getLogger(IntegrationDao.class);
	
	@Autowired
	private IntegrationDao integrationDao;

	@GetMapping()
	public ResponseEntity<?> getDataQuery() {

		DataQuery dataQuery = new DataQuery();
		dataQuery.setProvider("ODBC");
		dataQuery.setNameDataBase("ritz");
		dataQuery.setUserDataBase("root");
		dataQuery.setPasswordDataBase("master");

		// Retorna o recurso e retorna status OK
		return ResponseEntity.status(HttpStatus.OK).body(dataQuery);
	}

	@PostMapping("/salesman")
	public List<Salesman> salesman(@RequestBody DataQuery dataQuery) {

		try {
			return integrationDao.listSalesman(dataQuery);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Problemas ao recuperar a lista de vendedores. " + e.getMessage(), e);
		}
	}

	@PostMapping("/supplier")
	public List<Supplier> supplier(@RequestBody DataQuery dataQuery) {

		try {
			return integrationDao.listSupplier(dataQuery);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Problemas ao recuperar a lista de forncedores. " + e.getMessage(), e);
		}
	}

	@PostMapping("/payment")
	public ResponseEntity<?> payment(@RequestBody DataQuery dataQuery) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(integrationDao.insertPayment(dataQuery));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Problemas ao executar insert de pagamento. " + e.getMessage(), e);
		}
	}

	@PostMapping("/comission")
	public ResponseEntity<?> comission(@RequestBody DataQuery dataQuery) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(integrationDao.getComission(dataQuery));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Problemas ao recuperar comissao. " + e.getMessage(), e);
		}
	}
	

	@PostMapping("/nonWorkdays")
	public List<NonWorkdays> nonWorkdays(@RequestBody DataQuery dataQuery) {

		try {
			return integrationDao.listNonWorkdays(dataQuery);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Problemas ao recuperar a lista de forncedores. " + e.getMessage(), e);
		}
	}

	
}
