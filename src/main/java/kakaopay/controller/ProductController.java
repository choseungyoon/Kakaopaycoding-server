package kakaopay.controller;

import kakaopay.entity.InvestmentProductEntity;
import kakaopay.service.InvestmentProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

	@Autowired
	private InvestmentProductService investmentProductService;

	@GetMapping("/product")
	public List<InvestmentProductEntity> getProduct(
			@RequestParam(value = "startedat") String started_at, @RequestParam(value = "finishedat") String finished_at) throws Exception {
		log.debug("get investment product list");
		return investmentProductService.getInverstmentProduct(started_at,finished_at);
	}

	@PostMapping("/product")
	public String insertInvestmentProduct(@RequestBody InvestmentProductEntity investmentProductEntity) throws  Exception{
		log.debug("Insert product : Controller");
		System.out.println(investmentProductEntity);
		return investmentProductService.saveInvestmentProduct(investmentProductEntity);
	}

}
