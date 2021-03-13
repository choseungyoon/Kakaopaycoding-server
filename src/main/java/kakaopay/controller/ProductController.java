package kakaopay.controller;

import kakaopay.entity.Product;
import kakaopay.entity.ProductParameter;
import kakaopay.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService){
		this.productService = productService;
	}

	@PostMapping("/products")
	public Product create(@RequestBody ProductParameter productParameter) throws  Exception{
		log.debug(productParameter.toString());
		return productService.create(productParameter);
	}

	@GetMapping("/products")
	public JSONArray get(@RequestParam(value = "startedat") String started_at, @RequestParam(value = "finishedat") String finished_at) throws Exception {
		return productService.get(started_at,finished_at);
	}


}
