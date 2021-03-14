package kakaopay.controller;

import kakaopay.entity.Product;
import kakaopay.entity.ProductParameter;
import kakaopay.service.ProductService;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService){
		this.productService = productService;
	}

	@PostMapping("/products")
	public Product create(@RequestBody ProductParameter productParameter) throws  Exception{
		return productService.create(productParameter);
	}

	@GetMapping("/products")
	public JSONArray get(@RequestParam(value = "startedat", required = false) String started_at, @RequestParam(value = "finishedat",required = false) String finished_at) throws Exception {
		return productService.get(started_at,finished_at);
	}


}
