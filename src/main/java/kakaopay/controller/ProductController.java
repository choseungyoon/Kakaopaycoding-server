package kakaopay.controller;

import kakaopay.entity.Product;
import kakaopay.entity.ProductParameter;
import kakaopay.service.ProductService;
import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService){
		this.productService = productService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/products")
	public Product create(@RequestBody ProductParameter productParameter) throws  Exception{
		return productService.create(productParameter);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/products")
	public JSONArray get() throws Exception {
		return productService.get();
	}


}
