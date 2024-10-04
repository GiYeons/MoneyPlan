# 🔍💰머니플랜 MoneyPlan

*머니플랜 MoneyPlan*은 올바른 소비 습관을 형성하고 싶은 사람들에게 길잡이가 되어주는 **예산 관리 서비스**입니다.

소비 내역을 기록하고 관리할 뿐 아니라 _예산 설계, 지출 컨설팅_ 등 다양한 재무 관리 경험을 제공합니다.

<br/>

## 📍목차

1. [개요](#개요)
2. [기술 스택](#기술-스택)
3. [기능 구현](#기능-구현)
4. [API 명세서](#API-명세서)
5. [프로젝트 관리](#프로젝트-관리)
6. [ERD 및 디렉토리 구조](#ERD-및-디렉토리-구조)
7. [트러블슈팅](#트러블슈팅)
8. [회고](#회고)

<br/>

## 🔍개요

*머니플랜 MoneyPlan*은 사용자의 현명한 소비 습관 형성을 위한 다양한 서비스를 제공합니다.

- **예산을 설정**하고 그에 따라 **지출 내역을 관리**해 보세요.
- 예산 설정이 어려운 사용자를 위해 **맞춤형 예산 추천 서비스**를 제공합니다.
- 지출 내역을 바탕으로 **지출 컨설팅 서비스**를 제공합니다. 오늘 소비할 수 있는 적정 금액을 추천하는 _오늘 지출 추천_, 오늘 지출한 총액과 위험도 등을 알려주는 _오늘 지출 안내_ 등의 기능을 체험해 보세요.

<br/>

## 🛠️기술 스택

<img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">

<img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.3.2-515151?style=for-the-badge"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">

<img src="https://img.shields.io/badge/Database-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/querydsl-6DB33F?style=for-the-badge&logo=&logoColor=white">

<img src="https://img.shields.io/badge/Build-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"><img src="https://img.shields.io/badge/8.8-515151?style=for-the-badge">

<img src="https://img.shields.io/badge/Deployment-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/aws%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"> <img src="https://img.shields.io/badge/flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white"> <img src="https://img.shields.io/badge/aws rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">

<img src="https://img.shields.io/badge/version control-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

<br/>

## 💻기능 구현

### 1. 회원가입

- 계정명(Account), 비밀번호(Password)를 입력하여 회원가입
- 계정명 중복체크 및 유효성 검증
- 비밀번호 입력 시, 정규식을 활용한 유효성 검증 및 BCrypt 암호화 처리

### 2. 로그인

- JWT 토큰 기반 인증/인가
- 로그인 시 **Access Token** 및 **Refresh Token** 발급
- **RTR(Refresh Token Rotation)** 기법을 적용하여 AT 만료 시 RT도 같이 재발급 => AT와 RT가 동시에 탈취당하는 시나리오에 대응, 보안성 강화
- **커스텀 어노테이션(@AuthUser)** 을 통해 로그인된 사용자 정보를 가져옴

### 3. 카테고리

- 총 11개의 기본 카테고리 (`식비`, `교통`, `주거/통신`, `생활`, `패션`, `여가`, `여행`, `쇼핑`, `의료`, `교육`, `기타`)

### 4. 예산 설정

- 예산은 기간별로 생성되며, 카테고리별 금액을 지정해야 함. 금액을 지정하지 않은 카테고리의 경우 기본값 0원 지정

### 5. 예산 설계 (추천)

- 카테고리 지정 없이 **예산 총액**만을 입력할 경우, **카테고리별 예산을 데이터 기반으로 설계**해주는 기능
- **30일 이내**에 생성된 예산 데이터를 바탕으로, 다른 사용자들의 `카테고리별 예산/총액` 평균 비율을 계산 => 비율에 따라 적정한 예산 설계
  - 예) 총액 100만원 입력, 유저들이 평균 30%를 `주거/통신`에 설정하였고 평균 40%를 `식비`에 설정하였다면 => `주거/통신` 30만원, `식비` 40만원
  - 10% 이하의 카테고리는 모두 묶어 `기타`로 합산
- **QueryDSL**을 통해 코드 기반의 쿼리를 작성함으로써 코드 안정성 향상

### 6. 지출

- 지출 기록(`카테고리`, `일시`, `금액`, `메모`, `합계제외`) 생성, 수정, 조회, 삭제 기능 (CRUD)
- 모든 지출 관련 기능은 생성한 사용자만 권한을 가짐
- **지출 조회**
  - **상세 조회**, **목록 조회**로 구분됨
  - **상세 조회**는 단건 조회로, 모든 필드를 반환
  - **목록 조회**는 지정된 조건에 부합하는 모든 지출을 반환하며, 조회된 모든 내용의 `지출 합계` 및 `카테고리별 지출 합계`를 함께 반환함.
  - 목록 조회 시 `기간`은 필수로 지정. 또한 `카테고리`, `최소/최대 금액`을 지정하여 검색할 수 있음 (선택)
  - `합계제외` 처리된 지출은 목록에는 포함되나 합계에서는 제외됨
  - 목록 조회 시 **페이지네이션**을 적용하여 페이지별로 조회할 수 있도록 하였음

### 7. 오늘 지출 추천

구현 예정

### 8. 오늘 지출 안내

구현 예정

<br/>

## ✍️API 명세서

<details>
<summary><b> 1. 회원가입 </b></summary>

#### URL

```java
POST /api/v1/members/
```

#### Request

| Field      | Type     | Description |
| ---------- | -------- | ----------- |
| `account`  | `String` | 계정명      |
| `password` | `String` | 비밀번호    |

#### Response

**1) 200 OK**

```json
{
  "id": 1,
  "account": "MoneyPlan"
}
```

**2) 400 Bad Request**

```json
// 유효하지 않은 비밀번호를 입력할 경우
{
  "message": "잘못된 요청입니다. 입력값을 확인하고 다시 시도해주세요.",
  "detail": ["비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함되어야 하며, 8자 ~ 16자까지 가능합니다."]
}
```

**3) 409 Conflict**

```json
// 계정명이 중복될 경우
{
  "message": "이미 사용중인 계정입니다."
}
```

<br/>
</details>

<details>
<summary><b>2. 사용자 로그인 API</b></summary>

#### URL

```java
POST /api/v1/members/login
```

#### Request

| Field      | Type     | Description |
| ---------- | -------- | ----------- |
| `account`  | `String` | 계정명      |
| `password` | `String` | 비밀번호    |

#### Response

**1) 200 OK**

```json
{
  "id": 1,
  "account": "MoneyPlan"
}
```

**2) 401 Unauthorized**

```json
// 계정명 또는 비밀번호가 잘못된 경우
{
  "message": "계정명 또는 비밀번호가 틀렸습니다."
}
```

<br/>

</details>

<details>
<summary><b> 3. 카테고리 목록 </b></summary>

#### URL

```java
GET /api/v1/categories
```

#### Request

| Field | Type | Description |
| ----- | ---- | ----------- |
| -     | -    | -           |

#### Response

**1) 200 OK**

```json
[
  {
    "id": 1,
    "name": "식비"
  },
  {
    "id": 2,
    "name": "교통"
  },
  {
    "id": 3,
    "name": "주거/통신"
  }
  ...
]
```

<br/>

</details>
<details>
<summary><b> 4. 예산 설정 </b></summary>

#### URL

```java
POST /api/v1/budgets
```

#### Request

| Field             | Type                   | Description     |
| ----------------- | ---------------------- | --------------- |
| `startDate`       | `LocalDate`            | 시작일          |
| `endDate`         | `LocalDate`            | 종료일          |
| `categoryBudgets` | `Map<String, Integer>` | 카테고리별 예산 |

#### Response

**1) 200 OK**

```json
[
  {
    "id": 23,
    "category": {
      "id": 1,
      "name": "식비"
    },
    "startDate": "2024-10-02",
    "endDate": "2024-11-02",
    "amount": 300000
  },
  {
    "id": 24,
    "category": {
      "id": 2,
      "name": "교통"
    },
    "startDate": "2024-10-02",
    "endDate": "2024-11-02",
    "amount": 55000
  },
  ...
]
```

**2) 400 Bad Request**

```json
// 0 미만 금액을 입력했을 경우
{
  "message": "예산 금액은 0 이상이어야 합니다."
}
```

<br/>

</details>

<details>
<summary><b> 5. 예산 설계 </b></summary>

#### URL

```java
POST /api/v1/budgets/suggest
```

#### Request

| Field         | Type  | Description |
| ------------- | ----- | ----------- |
| `totalAmount` | `int` | 예산 총액   |

#### Response

**1) 200 OK**

```json
[
  {
    "category": {
      "id": 1,
      "name": "식비"
    },
    "amount": 201000
  },
  {
    "category": {
      "id": 2,
      "name": "교통"
    },
    "amount": 0
  },
  ...
]
```

<br/>

</details>

<details>
<summary><b> 6. 지출 생성 </b></summary>

#### URL

```java
POST /api/v1/expenses
```

#### Request

| Field             | Type            | Description     |
| ----------------- | --------------- | --------------- |
| `categoryId`      | `Long`          | 카테고리 아이디 |
| `spentAt`         | `LocalDateTime` | 지출일시        |
| `amount`          | `int`           | 지출금액        |
| `memo`            | `String`        | 메모            |
| `isTotalExcluded` | `Boolean`       | 합계제외        |

#### Response

**1) 200 OK**

```json
{
  "id": 22,
  "member": {
    "id": 1,
    "account": "MoneyPlan"
  },
  "category": {
    "id": 1,
    "name": "식비"
  },
  "spentAt": "2024-10-04T04:14:18.043",
  "amount": 9000,
  "memo": "점심으로 빅맥",
  "isTotalExcluded": false
}
```

**2) 404 Not Found**

```json
// 존재하지 않는 카테고리 아이디를 넘겼을 경우
{
  "message": "카테고리를 찾을 수 없습니다."
}
```

<br/>

</details>

<details>
<summary><b> 7. 지출 수정 </b></summary>

#### URL

```java
PUT /api/v1/expenses/{id}
```

#### Request

| Field             | Type            | Description     |
| ----------------- | --------------- | --------------- |
| `categoryId`      | `Long`          | 카테고리 아이디 |
| `spentAt`         | `LocalDateTime` | 지출일시        |
| `amount`          | `int`           | 지출금액        |
| `memo`            | `String`        | 메모            |
| `isTotalExcluded` | `Boolean`       | 합계제외        |

#### Response

**1) 200 OK**

```json
{
  "id": 22,
  "member": {
    "id": 1,
    "account": "MoneyPlan"
  },
  "category": {
    "id": 1,
    "name": "식비"
  },
  "spentAt": "2024-10-04T04:14:18.043",
  "amount": 9000,
  "memo": "빅맥이 아니라 와퍼",
  "isTotalExcluded": true
}
```

**2) 403 Forbidden**

```json
// 권한이 없는 사용자가 요청을 보낼 경우
{
  "message": "접근 권한이 없습니다."
}
```

**3) 404 Not Found**

```json
// 존재하지 않는 id를 요청할 경우
{
  "message": "지출을 찾을 수 없습니다."
}
```

</br>

</details>

<details>
<summary><b> 8. 지출 삭제 </b></summary>

#### URL

```java
DELETE /api/v1/expenses/{id}
```

#### Request

| Field | Type | Description |
| ----- | ---- | ----------- |
| -     | -    | -           |

#### Response

**1) 200 OK**

```json
success
```

**2) 403 Forbidden**

```json
// 권한이 없는 사용자가 요청을 보낼 경우
{
  "message": "접근 권한이 없습니다."
}
```

**3) 404 Not Found**

```json
// 존재하지 않는 id를 요청할 경우
{
  "message": "지출을 찾을 수 없습니다."
}
```

</br>

</details>

<details>
<summary><b> 9. 지출 조회 (상세) </b></summary>

#### URL

```java
GET /api/v1/expenses/{id}
```

#### Request

| Field | Type | Description |
| ----- | ---- | ----------- |
| -     | -    | -           |

#### Response

**1) 200 OK**

```json
{
  "id": 16,
  "member": {
    "id": 2,
    "account": "MoneyMap"
  },
  "category": {
    "id": 5,
    "name": "패션"
  },
  "spentAt": "2024-09-13T17:20:00",
  "amount": 10000,
  "memo": "무지티 1장",
  "isTotalExcluded": false
}
```

**2) 403 Forbidden**

```json
// 권한이 없는 사용자가 요청을 보낼 경우
{
  "message": "접근 권한이 없습니다."
}
```

**3) 404 Not Found**

```json
// 존재하지 않는 id를 요청할 경우
{
  "message": "지출을 찾을 수 없습니다."
}
```

</br>

</details>

<details>
<summary><b> 10. 지출 조회 (목록) </b></summary>

#### URL

```
GET /api/v1/expenses?startDate=2024-09-01T00:00:00&endDate=2024-09-30T23:59:59&categoryId=1&minAmount=0&maxAmount=50000&page=0&size=20'
```

#### Request

| Field | Type | Description |
| ----- | ---- | ----------- |
| -     | -    | -           |

#### Response

**1) 200 OK**

```json
{
  "expenses": [
    {
      "id": 14,
      "category": {
        "id": 1,
        "name": "식비"
      },
      "spentAt": "2024-09-26T14:00:00",
      "amount": 3000
    },
    {
      "id": 13,
      "category": {
        "id": 1,
        "name": "식비"
      },
      "spentAt": "2024-09-24T09:00:00",
      "amount": 5400
    },
    ...
  ],
  "pageInfo": {
    "page": 1,
    "size": 20,
    "totalElements": 4,
    "totalPages": 1
  },
  "totalAmount": 27600,
  "expenseCategoryTotals": [
    {
      "name": "식비",
      "totalAmount": 27600
    }
  ]
}
```

</details>

<details>
<summary>11. 오늘 지출 추천</summary>

</details>

<details>
<summary>12. 오늘 지출 안내</summary>

</details>

<br/>

## 🔥프로젝트 진행 및 이슈 관리

업데이트 예정

<br/>

## 📂ERD 및 디렉토리 구조

<details>
<summary><b>ERD 모델링</b></summary>

![moneyplanERD_V1](https://github.com/user-attachments/assets/2fd331bf-5825-4c3c-b8cf-a2c2bef7569b)

</details>

<details>
<summary><b>디렉토리 구조</b></summary>

```
📦moneyplan
┣ 📂common
┃ ┣ 📂auth
┃ ┣ 📂config
┃ ┣ 📂exception
┃ ┣ 📂model
┃ ┗ 📂util
┣ 📂budget
┃ ┣ 📂controller
┃ ┣ 📂domain
┃ ┣ 📂dto
┃ ┣ 📂repository
┃ ┗ 📂service
┣ 📂category
┣ 📂category_average_budget
┣ 📂expense
┣ 📂member
┣ 📂refresh_token
```

</details>

<br/>

## 💣트러블슈팅

<details>
<summary><b>⚡ boolean 타입 필드에 요청값이 정상적으로 되지 않는 문제 </b></summary> 
 
</br>

지출 관련 CRUD 기능을 구현하던 중, 다른 필드들은 정상적으로 Request Body를 통해 요청받은 값과 매핑되었지만, isTotalExcluded 필드만 매핑되지 않는 문제가 발생했다.

처음에는 단순한 문법 오류이거나 DB에서 타입 관련 에러가 발생한 거라고 생각했으나, 로깅을 통해 원인을 추적해 보니 Request Body에서 잘못된 값이 들어오고 있었다. 뿐만 아니라 Swagger에서는 `totalExcluded`와 같이 접두사가 빠진 채 표시되고 있었다.

이를 바탕으로 알아본 결과 **Java에서는 boolean 타입에 is 접두사를 붙이면 안 된다**는 것을 알게 되었다.

Java에서는 jackson을 사용해 json 데이터를 직렬화/역직렬화한다. 이 과정에서 jackson은 getter/setter를 사용한다. 그런데 Lombok의 @Getter 어노테이션은 is로 시작하는 primitive boolean field의 getter를 생성할 때 이름을 'getIs~'가 아닌 'is~'로 생성한다. 이 'is~'접두어가 붙은 boolean getter 메서드를 발견한 jackson은 'is'를 뺀 이름으로 Json에 직렬화해 버린다.

이렇듯 직렬화 과정상의 Getter 네이밍 문제로 매핑이 제대로 되지 않는 듯했다.

해결 방법은

> 1. is 접두어를 사용하지 않는다.
> 2. primitive 타입이 아닌 wrapper 타입의 Boolean을 사용한다.
> 3. 해당 필드의 getter를 직접 생성한다.

위와 같이 3가지가 있다. 가장 이상적인 방법은 1번처럼 'is' 접두사를 사용하지 않는 것이다. 다른 방법들은 NullPointerException(NPE)과 같은 문제를 일으킬 수 있고, 지나치게 번거롭다.

그러나 나는 is 접두어를 쓰지 않으면 가독성이 많이 하락한다고 느꼈다. **읽었을 때 바로 어떤 일을 하는 필드인지 알게 하고 싶었기 때문에 2번 방법을 선택했다.**

다만 is를 쓰지 않고도 어떤 필드인지 바로 알 수 있게끔 변수 네이밍을 명확하게 했다면 1번 방법을 활용할 수 있었을 것 같아 아쉬움이 남는다.

</details>

<br/>

## 🏃회고

업데이트 예정
