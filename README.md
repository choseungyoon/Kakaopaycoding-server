# 투자 REST API
> **2021 kakaopay Server** : 카카오 페이 부동산/신용 투자 서비스 API

## Technical Requitements
### 기본 요건
* 전체 투자 상품 조회 , 투자하기 , 나의 투자상품 조회 API 구현합니다. (요청 식별값 : HTTP Header : X-USER-ID)
* 어플리케이션은 다수의 서버에서 다수의 인스턴스로 동작하더라도 기능에 문제가 없어야 합니다.
* 각 기능 및 제약사항에 대한 단위 테스트를 반드시 작성합니다. 

### 상세 기술 구현 사항
1. 전체 투자상품 조회 API
* 상품 모집 기한 내의 상품만 응답합니다. (현재 날짜 기준 모집기간에 포함된 상품만 반환합니다)

2. 투자 하기 API
* 다음의 요건을 만족하는 투자하기 API를 작성해주세요
 * 사용자 식별값, 상품 ID , 투자 금액을 입력값으로 받습니다. 
 * 총 투자모집 금액(total_investing_amount)을 넘어서면 sold-out 상태를 응답합니다. 

3. 나의 투자상품 조회 API
* 다음의 요건을 만족하는 나의 투자상품 조회 API를 작성해주세요
  * 내가 투자한 모든 상품을 반환합니다. 
  * 나의 투자상품 응답은 다음 내용을 포함합니다. 
    * 상품 ID, 상품 제목, 총 모집금액, 나의 투자금액, 투자일시

## API Specifications
| Action | API | Parameter | Body | Response | EXAMPLE | 
|--------|-----|-----------|------|------------------| --------------- |
|Get Product List | GET /api/v1/products| N/A | N/A | (JSONArray) 투자상품 리스트 | [{"duration":"2021-03-01T00:00 ~ 2021-05-30T00:00","total_investing_amount":100,"current_investing_amount":100,"product_id":1,"investers":1,"title":"해외 주식 포트폴리오","status":"모집 완료"},{"duration":"2021-03-01T00:00 ~ 2021-05-30T00:00","total_investing_amount":100000,"current_investing_amount":1000,"product_id":2,"investers":1,"title":"해외 부동상 포트폴리오","status":"모집중"}] |
| Invest | POST /api/v1/invest ,HTTP HEADER X_USER_ID : {id}|  N/A | { "productId" : {상품ID}, "investAmount" : {투자금액} } | (string) SUCCESS or SOLD OUT | {"result":"SUCCESS"} |
| Get My Invest List | /api/v1/invest ,HTTP HEADER X_USER_ID : {id} | N/A | N/A | (JSONArray) 나의 투자 리스트 | [{"My_investing_amount":50,"productId":1,"Total_investing_amount":100,"title":"해외 주식 포트폴리오","InvestAt":"2021-03-14T21:11:34"},{"My_investing_amount":1000,"productId":2,"Total_investing_amount":100000,"title":"해외 부동상 포트폴리오","InvestAt":"2021-03-14T21:12:49"}] |

## 문제 해결 전략
### System Architecture
![image](https://user-images.githubusercontent.com/16661906/111061607-99f1bb00-84e7-11eb-8108-3dcb005bc40c.png)

* KeyPoint
  * 투자금액의 설정과 모집금액의 처리는 분리되어야 합니다. 
  * 운영이 종료된 상품은 RDBMS에 저장하고 In-Memory DB를 Cleasnsing하여 가용 메모리를 유지합니다.
  * 상품의 투자금액 조회시, 실시간 모집금액 처리 영역에 부하를 주지 않아야 합니다.
  * 투자의 CRUD는 시간복잡도 O(1)을 만족해야 합니다. 
  * 투자하기 시 모집금액 변동의 동시성 이슈를 해소해야 합니다. 

* Solution
  * 전체 투자상품 조회 API /나의 투자상품 조회하기 API
    * 상품 및 나의 투자상품 조회 Data는 RDMS에서 추출합니다. 실시간 투자금액 모집을 산정하는 Redis와 분리되어 있어 부하 주지 않도록 설계했습니다. 
  * 투자하기 API
    * 사용자가 상품에 투자 시, 현재 모집금액과 총 모집금액을 확인해서 SUCCESS / SOLD-OUT을 응답합니다. 
    * 투자상품 오픈 시, 다수의 사용자가 동시에 투자하는 것을 처리 시 동시성 문제가 발생합니다.
    * 동시성 문제는 Redis를 통해 해소했습니다. 
    * Redis는 Single Thread로 동작하여 decrease/increase 명령의 순차적 처리를 보장합니다.
  
## Further More
- [ ] Spring WebFlux 통한 비동기 방식
- [ ] TCC 활용 REST API 분산 트랜잭션 구현
