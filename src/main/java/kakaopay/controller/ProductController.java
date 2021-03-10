package kakaopay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductController {
	
	   @RequestMapping("/product")
	    public String getProduct(){
	        log.debug("get investment product list");

	        return "success";
	    }
}
