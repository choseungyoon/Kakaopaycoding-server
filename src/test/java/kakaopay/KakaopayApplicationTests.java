package kakaopay;

import kakaopay.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KakaopayApplicationTests {

	@Autowired
	private ProductController productController;

	@Test
	void contextLoads() throws Exception {
		//productController.getProduct();
	}

}
