<p align='center'>
    <img src="https://capsule-render.vercel.app/api?type=venom&height=300&color=FFDC00&text=GIGA%20COFFEE&textBg=false&animation=fadeIn&fontColor=452613&fontSize=80&reversal=false&desc=기억%20속,%20가장%20맛있었던%20한%20모금&descAlignY=80"/>
</p>

### 🛠️ 기술 스택

---
<div style="margin: 0 auto; text-align: center;" align= "center">
    <img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white">
    <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">
    <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white">
    <img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=Linux&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
</div>

<br>

<br><br>

### 사용자 별 시나리오

---

## 1 ) 시나리오

<br>

> 가맹점 관리자는 다음과 같은 기능을 가지고 있습니다.

<br>

&nbsp;　**1. 가맹점 관리자는 Email, Password, 이름 등을 입력하여 회원가입이 가능합니다.**

&nbsp;　**2. 가맹점 관리자는 회원가입 시 입력한 ID 와 PW를 통해 로그인이 가능합니다.**

&nbsp;　**3. 가맹점 관리자는 재고가 부족한 상품을 주문할 수 있습니다.**

&nbsp;　**4. 가맹점 관리자는 자신의 매장에 필요한 상품을 검색할 수 있습니다.**

&nbsp;　**5. 가맹점 관리자는 현재 매장에 있는 제품들의 재고를 확인하고 수정할 수 있습니다.**

&nbsp;　**6. 가맹점 관리자는 자신의 주문 내역을 조회할 수 있습니다.**

&nbsp;　**7. 가맹점 관리자는 문의사항을 작성할 수 있습니다.**

&nbsp;　**8. 가맹점 관리자는 공지사항을 확인할 수 있습니다.**

<br> <br>

> 본사 관리자는 다음과 같은 기능을 가지고 있습니다.

<br>

&nbsp;　**1. 본사 관리자는 지급 받은 Email, Password를 통해 로그인이 가능합니다.**

&nbsp;　**2. 본사 관리자는 일일/월별 매출 추이 그래프와 발주 총액 표를 확인할 수 있습니다.**

&nbsp;　**3. 본사 관리자는 재고 현황 그래프와 가맹점 별 발주액 표를 확인할 수 있습니다.**

&nbsp;　**4. 본사 관리자는 가맹점들의 정보를 조회할 수 있습니다.**

&nbsp;　**5. 본사 관리자는 가맹점들이 주문한 주문 내역들을 확인할 수 있습니다.**

&nbsp;　**6. 본사 관리자는 상품을 등록하고 수정할 수 있습니다.**

&nbsp;　**7. 본사 관리자는 창고를 등록할 수 있습니다.**

&nbsp;　**8. 본사 관리자는 창고들의 목록을 조회할 수 있고 창고 별 상품 재고 조회를 할 수 있습니다.**

&nbsp;　**9. 본사 관리자는 가맹점 관리자가 작성한 문의사항에 답변을 작성할 수 있습니다.**

&nbsp;　**10. 본사 관리자는 공지사항을 작성할 수 있습니다.**

<br><br>

### API 명세서

---
<a href="https://www.notion.so/API-3680b3a4d3b641108f2686515dfc2222" target="_blank">➡ API 명세서 바로가기</a>

<br><br>

### API 테스트 결과 (Post Man)

---
<br>

<details><br>
<summary><b span style="font-size: larger;">본사 관리자</b></summary>
    <div>
         <details>
         <summary>
         <b>B_MANAGER_001. 본사 회원가입</b></summary>
         <br>
         <p><b>➡ 본사 관리자가 Id, Password, Email, 이름, 핸드폰 번호, 부서명을 입력하여 회원가입을 한다.
         <br>
         </b></p>
         <p><img src="./postman/B_MANAGER_001 본사회원가입.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_MANAGER_002. 본사 로그인</b></summary>
         <br>
         <p><b>➡ 본사 관리자가 Id, Password를 입력하여 로그인을 한다.
         <br>
         </b></p>
         <p><img src="./postman/B_MANAGER_002 본사로그인.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_STORE_001. 가맹점 등록</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 새로운 가맹점을 등록할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_STORE_001 본사가맹점등록.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_STORE_002. 가맹점 정보 조회</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 가맹점 정보 조회 기능을 사용하여 시스템에 등록된 가맹점의 상세 정보를 확인할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_STORE_002 본사가맹점정보조회.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_STORE_003. 가맹점 전체 조회</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 가맹점 전체 조회 기능을 사용하여 시스템에 등록된 가맹점의 전체 정보를 확인할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_STORE_003 본사가맹점전체조회.png"/></p>
         </details>
    </div>
        <br>
    <div>
         <details>
         <summary>
         <b>B_ORDER_004. 가맹점 별 주문 내역 조회</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 가맹점 별 주문 내역을 조회할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_ORDER_004 가맹점 별 .png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_CONTAINER_001. 창고 목록 조회</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 창고 정보 조회 기능을 사용하여 시스템에 등록된 창고의 목록을 확인할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_CONTAINER_001 본사창고목록.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_CONTAINER_002. 창고 별 재고 조회</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 창고별 상품 재고 조회 기능을 사용하여 시스템에 등록된 창고별 상품 재고를 확인할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_CONTAINER_002 본사창고별.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_CONTAINER_003. 창고 등록</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 새로운 창고를 등록할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_CONTAINER_003 본사창고등록.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_PRODUCT_001. 상품 등록</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 새로운 창고를 등록할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_PRODUCT_001 본사상품등록.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_PRODUCT_002. 상품 검색</b></summary>
         <br>
         <p><b>➡ 본사 관리자 및 가맹점 관리자는 상품 검색 기능을 사용하여 등록된 모든 상품의 정보를 볼 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_PRODUCT_002 본사상품검색.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_PRODUCT_003. 상품 목록 조회</b></summary>
         <br>
         <p><b>➡ 본사 관리자 및 가맹점 관리자는 상품 조회 기능을 사용하여 등록된 모든 상품의 정보를 볼 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_PRODUCT_003 본사상품목록.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_PRODUCT_004. 상품 정보 수정</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 상품 수정 기능을 사용하여 기존 상품의 정보를 수정할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_PRODUCT_004 본사상품업뎃.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_PRODUCT_005. 상품 삭제</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 상품 삭제 기능을 사용하여 데이터베이스에서 상품을 제거할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_PRODUCT_005 본사상품삭제.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_NOTICE_001. 공지사항 작성</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 공지 사항을 작성할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_NOTICE_001 공지사항 작성.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_NOTICE_002. 공지사항 조회</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 공지 사항들을 조회할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_NOTICE_002 공지사항 조회.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_NOTICE_003. 공지사항 수정</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 공지 사항을 수정할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_NOTICE_003 공지사항 수정.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_NOTICE_004. 공지사항 삭제</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 공지 사항을 삭제할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_NOTICE_004 공지사항 삭제.png"/></p>
         </details>
    </div>
        <br>
    <div>
         <details>
         <summary>
         <b>B_DELIVERY_002. 배송 상태 수정</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 주문 상품에 대한 배송 상태를 수정할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_DELIVERY_002 배송 상태 수정.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_QUESTION_005. 문의사항 답변</b></summary>
         <br>
         <p><b>➡ 본사 관리자는 가맹자 관리자의 문의사항에 대한 답변을 작성할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_QUESTION_005 문의사항 답변.png"/></p>
         </details>
    </div>
</details>

<br>

<details><br>
<summary><b span style="font-size: larger;">가맹점 관리자</b></summary>
    <div>
         <details>
         <summary>
         <b>B_USER_001. 가맹점 회원가입</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 회원 정보를 입력하여 회원가입을 진행한다.
         <br>
         </b></p>
         <p><img src="./postman/B_USER_001 가맹점 관리자 회원가입.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_USER_002. Email 찾기</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 이름과 전화번호를 입력하여 Email을 찾을 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_USER_002 Email 찾기.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_USER_003. 회원 정보 수정</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 자신의 회원정보를 수정할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_USER_003 회원 정보 수정.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_USER_004. 가맹점 로그인</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 Email과 Password를 입력해 토큰을 발급 받아 로그인할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_USER_004 가맹점 관리자 로그인.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_USER_005. Password 재설정</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 Password를 재설정할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_USER_005 Password 재설정.png"/></p>
         </details>
    </div>
     <br>
    <div>
         <details>
         <summary>
         <b>B_USER_006. Password 찾기</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 이름과 Email을 입력하여 Password를 찾을 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_USER_006 Password 찾기.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_DELIVERY_001. 배송 상태 조회</b></summary>
         <br>
         <p><b>➡ 가맹자 관리자는 자신의 주문 상품에 대한 배송 상태를 조회할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_DELIVERY_001 배송 상태 조회.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_ORDER_001. 주문 목록 조회</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 주문했던 목록들을 조회 할수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_ORDER_001 주문 목록 조회.png"/></p>
         </details>
    </div>
        <br>
    <div>
         <details>
         <summary>
         <b>B_ORDER_002. 결제 및 주문 내역 생성</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 Portone API 결제 기능을 통해 카트 안의 상품에 대해 결제를 진행할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_ORDER_003 결제.png"/></p>
         <p><img src="./postman/B_ORDER_003 주문내역.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_ORDER_003. 결제 취소</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 배송을 시작하지 않은 주문 내역의 결제 취소가 가능하다.
         <br>
         </b></p>
         <p><img src="./postman/B_ORDER_004 결제 취소.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_CART_001. 카트 담기 및 수정</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 원하는 상품을 장바구니에 담을 수 있고, 카트에서 상품을 선택하고 수량을 조절할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_CART_001 카트 담기.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_CART_002. 카트 삭제</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 카트에서 상품을 삭제할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_CART_002 카트 삭제.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_CART_003. 카트 조회</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 카트에 담겨 있는 상품을 조회 할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_CART_003 카트 목록.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_QUESTION_001. 문의사항 작성</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 문의사항을 작성할 수 있다. 
         <br>
         </b></p>
         <p><img src="./postman/B_QUESTION_001 문의사항 작성.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_QUESTION_002. 문의사항 조회</b></summary>
         <br>
         <p><b>➡ 본사 관리자 및 가맹점 관리자는 모든 문의사항을 조회할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_QUESTION_002 문의사항 조회.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_QUESTION_003. 문의사항 수정</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 자신이 작성한 문의사항을 수정할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_QUESTION_003 문의사항 수정.png"/></p>
         </details>
    </div>
    <br>
    <div>
         <details>
         <summary>
         <b>B_QUESTION_004. 문의사항 삭제</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 작성한 문의사항을 삭제할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_QUESTION_004 문의사항 삭제.png"/></p>
         </details>
    </div>
     <br>
    <div>
         <details>
         <summary>
         <b>B_STOCK_001. 재고 등록</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 상품의 재고를 등록할 수 있다. 
         <br>
         </b></p>
         <p><img src="./postman/B_STOCK_001 재고 등록.png"/></p>
         </details>
    </div>
     <br>
    <div>
         <details>
         <summary>
         <b>B_STOCK_002. 전체 재고 조회</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 전체 상품의 재고를 조회 할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_STOCK_002 전체 재고 조회.png"/></p>
         </details>
    </div>
     <br>
    <div>
         <details>
         <summary>
         <b>B_STOCK_003. 단일 재고 조회</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 특정 상품의 재고를 조회 할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_STOCK_003 재고 단일 재고 조회.png"/></p>
         </details>
    </div>
     <br>
    <div>
         <details>
         <summary>
         <b>B_STOCK_004. 재고 수정</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 특정 상품의 재고 정보를 수정할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_STOCK_004 재고 수정.png"/></p>
         </details>
    </div>
     <br>
    <div>
         <details>
         <summary>
         <b>B_STOCK_005. 재고 삭제</b></summary>
         <br>
         <p><b>➡ 가맹점 관리자는 특정 상품의 재고를 삭제 할 수 있다.
         <br>
         </b></p>
         <p><img src="./postman/B_STOCK_005 재고 삭제.png"/></p>
         </details>
    </div>

</details>
