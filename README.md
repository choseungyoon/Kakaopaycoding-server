# 투자 REST API
> **2021 kakaopay Server** : 카카오 페이 부동산/신용 투자 서비스 API

## Getting Started
#### Backend
``` bash
cd /kakaopay
./gradlew bootRun -Dspring.profiles.active=dev
```

## Technical Requitements
### 기본 요건
* 전체 투자 상품 조회 , 투자하기 , 나의 투자상품 조회 API 구현합니다. (요청 식별값 : HTTP Header : X-USER-ID)
* 어플리케이션은 다수의 서버에서 다수의 인스턴스로 동작하더라도 기능에 문제가 없어야 합니다.
* 각 기능 및 제약사항에 대한 단위 테스트를 반드시 작성합니다. 

### 상세 기술 구현 사항
1. 전체 투자상품 조회 API
* 상품 모집 기한 내의 상품만 응답합니다. 

2. 투자 하기 API
* 다음의 요건을 만족하는 투자하기 API를 작성해주세요
 * 사용자 식별값, 상품 ID , 투자 금액을 입력값으로 받습니다. 
 * 총 투자모집 금액(total_investing_amount)을 넘어서면 sold-out 상태를 응답합니다. 

3. 나의 투자상품 조회 API
* 다음의 요건을 만족하는 나의 투자상품 조회 API를 작성해주세요
  * 내가 투자한 모든 상품을 반환합니다. 
  * 나의 투자상품 응답은 다음 내용을 포함합니다. 
    * 상품 ID, 상품 제목, 총 모집금액, 나의 투자금액, 투자일시

## 문제 해결 전략
