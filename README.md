## 1. 실행 방법
  
  ### 실행
  ```bash
  ./gradlew bootRun
  ```

## 2. 설계한 REST API 명세 요약
  
  ### 1. 공휴일 검색 조회
  - GET http://localhost:8080/api/v1/holidays?year={year}&code={code}
    #### Parameter
    ```json
    {
      "year": 2025,
      "code": "KR",
      "searchFrom": "2025-01-01",
      "searchTo": "2025-12-31"
    }
    ```
    #### Response
    ```json
    {
      "content": [
          {
              "id": 5566,
              "name": "New Year's Day",
              "localName": "새해",
              "date": "2025-01-01",
              "year": 2025,
              "country": {
                  "code": "KR",
                  "name": "South Korea"
              }
          }
      ],
      "pageable": {
          "pageNumber": 0,
          "pageSize": 20,
          "sort": [],
          "offset": 0,
          "paged": true,
          "unpaged": false
      },
      "last": false,
      "totalPages": 500,
      "totalElements": 10000,
      "first": true,
      "size": 20,
      "number": 0,
      "sort": [],
      "numberOfElements": 15,
      "empty": false
    }
    ```

  ### 2. 공휴일 재동기화
  - POST http://localhost:8080/api/v1/holidays
    #### Request
    ```json
    {
      "year": 2025,
      "code": "KR"
    }
    ```
    
    #### Response void

  ### 3. 공휴일 삭제
  - DELETE http://localhost:8080/api/v1/holidays/{year}/{code}
    #### Parameter
    ```json
    {
      "year": 2025,
      "code": "KR"
    }
    ```

    #### Response void

  ### 4. 기타
  - 초기 데이터 적재: DataLoader 클래스
  - 스케줄러: HolidayScheduler 클래스

## 3. Swagger UI & H2 Console
  - Swagger API Spec: http://localhost:8080/swagger-ui/index.html
  - H2 Console: http://localhost:8080/h2-console/
