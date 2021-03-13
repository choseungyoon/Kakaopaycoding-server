package kakaopay.controller;

import kakaopay.entity.*;
import kakaopay.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaAuditing
public class InvestControllerTest {

    private static boolean setUpIsDone = false;
    private long testProductId = -1;
    private long testProductAmount = -1;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private ProductService productService;

    @Autowired
    private InvestController investController;

    @LocalServerPort
    int randomServerPort;

    @Before
    public void init() throws Exception {

        // 테스트 Product 만들기
        ProductParameter testProductParamter = new ProductParameter();
        StockParameter testStockParameter = new StockParameter();

        testProductParamter.setTitle("테스트 투자 상품");
        testStockParameter.setTotal(10000L);
        testProductParamter.setStock(testStockParameter);
        testProductParamter.setStartedAt(LocalDateTime.now());
        testProductParamter.setFinishedAt(LocalDateTime.now().plusMonths(2));

        Product testProduct = this.productService.create(testProductParamter);
        testProductId = testProduct.getId();
        testProductAmount = testProduct.getStock().getTotal();
        setUpIsDone = true;
    }

    @Test
    public void 나의투자상품조회API테스트() throws  Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X_USER_ID", "1");

        ResponseEntity<List<Invest>> response = this.testRestTemplate.exchange(
                "http://localhost:"+randomServerPort+ "/invest",
                HttpMethod.GET,
                new HttpEntity<Object>(headers),
                new ParameterizedTypeReference<List<Invest>>(){});

        assertThat(response).isNotNull();
        assertThat(response.getBody().get(0).getUserId()).isEqualTo(1L);

    }

    @Test
    public void 투자하기API테스트_성공() throws Exception{

        InvestParamter testInvest = new InvestParamter();
        testInvest.setProductId(this.testProductId);
        testInvest.setInvestAmount(this.testProductAmount);

        Invest response = this.investController.create(10,testInvest);

        assertThat(response).isNotNull();
        assertThat(response.getResult()).isEqualTo("SUCCESS");
    }

    @Test
    public void 투자하기API테스트_실패_SOLDOUT() throws Exception{

        InvestParamter testInvest = new InvestParamter();
        testInvest.setProductId(this.testProductId);
        testInvest.setInvestAmount(this.testProductAmount + 1);

        Invest response = this.investController.create(10,testInvest);

        //assertThat(response).isNotNull();
        assertThat(response.getResult()).isEqualTo("SOLD OUT");

    }


}