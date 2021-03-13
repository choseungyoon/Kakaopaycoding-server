package kakaopay.controller;

import kakaopay.entity.Product;
import kakaopay.entity.ProductParameter;
import kakaopay.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public List<Product> get(@RequestParam(value = "startedat") String started_at, @RequestParam(value = "finishedat") String finished_at) throws Exception {
		return productService.getInverstmentProduct(started_at,finished_at);
	}

	@PostMapping("/products/{id}")
	public Product edit(@PathVariable Long id , @RequestBody Product product) throws Exception{
		return null;
	}

	@DeleteMapping("/products/{id}")
	public void delete(@PathVariable Long id){

	}

}
