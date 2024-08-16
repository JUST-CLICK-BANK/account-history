# 📝Click Bank 계좌 거래 내역 Server

- 계좌의 거래 내역을 확인할 수 있음
- 거래 내역의 카테고리와 메모를 수정할 수 있음
- 계좌의 지출 분석, 예산을 설정할 수 있음

## Front
[💵Click Bank 프론트](https://github.com/JUST-CLICK-BANK/fe-click-bank)

</br>

## 사용 기술
#### 서버 구축
![Spring-Boot](https://img.shields.io/badge/spring--boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)


#### 데이터베이스
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)

#### CI/CD
![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)
![Google Cloud](https://img.shields.io/badge/GoogleCloud-%234285F4.svg?style=for-the-badge&logo=google-cloud&logoColor=white)
![Kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![ArgoCD](https://img.shields.io/badge/ArgoCD-EF7B4D?style=for-the-badge&logo=Argo&logoColor=white)



#### 그 외
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
## Diagram

### Architecture Diagram
![거래내역서버](https://github.com/user-attachments/assets/5ce46724-ba9c-48c5-96e8-473193243360)



</br>

### Data Flow Diagram
![거래 내역 DFD](https://github.com/user-attachments/assets/11084e3f-a173-4065-b1d4-dcb71c5be0e9)


</br>

## Trouble Shooting
### 대용량 데이터로 인한 성능 저하
모든 사용자의 거래 내역이 하나의 RDB 테이블로 저장되었기 때문에, 거래 내역 테이블에 많은 양의 데이터가 쌓이고, 그 경우 조회하는데 시간이 오래 걸릴 것이라 판단했습니다.

이 문제를 해결하기 위해 [배치 서버](https://github.com/JUST-CLICK-BANK/account-history-batch)를 하나 두어, 매일 밤 자정 배치 서버를 실행시켜 RDB 테이블의 데이터를 MongoDB로 옮겨주는 로직을 구현했습니다.

대용량 데이터를 조회하기에는 RDB인 MySQL보다는 NoSQL인 MongoDB가 더 적합할 것 같다는 판단으로 선택했습니다.

자정에 배치 서버에서 AccountHistories 테이블을 조회하여 모든 데이터를 MongoDB의 PastRecord 컬렉션에 저장합니다.

MongoDB에 저장 후 AccountHistories 테이블의 데이터를 모두 삭제합니다.

MongoDB에서 데이터를 조회할 때는 Paging을 사용하여 10개의 데이터씩 끊어서 조회하게 됩니다.


## 그 외

Spring-batch를 사용한 Batch 서버입니다.
[Batch](https://github.com/JUST-CLICK-BANK/account-history-batch)
