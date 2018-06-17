package br.com.dbintegrationapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbintegrationapi.dao.IntegrationDao;
import br.com.library.wlibrary.core.DataQuery;
import br.com.library.wlibrary.ritz.Salesman;
import br.com.library.wlibrary.ritz.Supplier;

@RestController
@RequestMapping("/object")
public class ObjectResource {

	@Autowired
	private IntegrationDao integrationDao;

	@PostMapping("/salesman")
	public List<Salesman> salesman(@RequestBody DataQuery dataQuery) {

		try {
			return integrationDao.listSalesman(dataQuery);
		} catch (Exception e) {
			throw new RuntimeException("Problemas ao recuperar a lista de vendedores. " + e.getMessage(), e);
		}
	}

	@PostMapping("/supplier")
	public List<Supplier> supplier(@RequestBody DataQuery dataQuery) {

		try {
			return integrationDao.listSupplier(dataQuery);
		} catch (Exception e) {
			throw new RuntimeException("Problemas ao recuperar a lista de forncedores. " + e.getMessage(), e);
		}
	}

}
