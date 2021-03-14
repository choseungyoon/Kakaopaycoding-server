package kakaopay.controller;

import kakaopay.entity.*;
import kakaopay.service.ProductService;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InvestControllerTest {

    private static boolean setUpIsDone = false;
    private long testProductId = -1;
    private long testProductAmount = -1;

    @Autowired
    private ProductService productService;

    @Autowired
    private InvestController investController;

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
        List<Invest> response = this.investController.get(1);
        assertThat(response).isNotNull();
        assertThat(response.get(0).getUserId()).isEqualTo(1L);
    }

    @Test
    public void 투자하기API테스트_성공() throws Exception{

        InvestParamter testInvest = new InvestParamter();
        testInvest.setProductId(this.testProductId);
        testInvest.setInvestAmount(this.testProductAmount);

        JSONObject response = this.investController.create(10,testInvest);

        assertThat(response).isNotNull();
        assertThat(response.get("result")).isEqualTo("SUCCESS");
    }

    @Test
    public void 투자하기API테스트_실패_SOLDOUT() throws Exception{

        InvestParamter testInvest = new InvestParamter();
        testInvest.setProductId(this.testProductId);
        testInvest.setInvestAmount(this.testProductAmount + 1);

        JSONObject response = this.investController.create(10,testInvest);

        //assertThat(response).isNotNull();
        assertThat(response.get("result")).isEqualTo("SOLD OUT");

    }


}