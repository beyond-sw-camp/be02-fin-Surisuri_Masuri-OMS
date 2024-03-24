<br>

![header](https://capsule-render.vercel.app/api?type=venom&height=300&color=FFDC00&text=GIGA%20COFFEE&textBg=false&animation=fadeIn&fontColor=452613&fontSize=80&reversal=false&desc=기억%20속,%20가장%20맛있었던%20한%20모금&descAlignY=80)

> <h2 style="color:black; font-family: 'Nanum Pen Script', cursive;">[플레이 데이터] 한화시스템 BEYOND SW캠프 / Team : SuriSuri_Masuri</h3>

<br>
<br>

### 🛠️ Tech Stacks

---
<div style="margin: 0 auto; text-align: center;" align= "center">
    <img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white">
    <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
    <img src="https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=Kubernetes&logoColor=blue&color=skyblue">
    <br>
    <img src="https://img.shields.io/badge/Selenium-C21325?style=for-the-badge&logo=Selenium&logoColor=black&color=green">
    <img src="https://img.shields.io/badge/Jest-C21325?style=for-the-badge&logo=Jest&logoColor=black&color=orange">
    <br>
    <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white">
</div>

<br>
<br>

### 💻 Architecture

---
### System Architecture
<img src = "../img/systemArchitecture.png">

### Cluster Architecture

<br>
<br>

### ⚙️ 운영환경

---
<details>

<summary style="font-size: 18px; font-weight: bold;">DockerHub Images</summary>

<h3><a href="https://hub.docker.com/repository/docker/beomiya/final_store_frontend/general">FrontEnd - Store</a></h3>
<img src="../img/store.png">
<br>

<h3><a href="https://hub.docker.com/repository/docker/beomiya/final_manager_frontend/general">FrontEnd - Manager</a></h3>
<img src="../img/manager.png">
<br>

<h3><a href="https://hub.docker.com/repository/docker/beomiya/final_backend/general">BackEnd</a></h3>
<img src="../img/backend.png">
<br>

</details>

<br>
<br>

<details>
<summary style="font-size: 18px; font-weight: bold;">Kubernetes</summary>

</details>

<br>
<br>

<details>
<summary style="font-size: 18px; font-weight: bold;">Jenkins</summary>

</details>

<br>
<br>

### 🐳 CI/CD의 필요성

---
<details>
<summary style="font-size: 18px; font-weight: bold;">CI: 지속적 통합</summary>

CI(Continuous Integration)는 개발자들이 코드를 변경할 때마다 자동으로 빌드되어 통합되는 프로세스를 의미한다. <br>이를 통해 여러 명의 개발자가 동시에 작업할 때 발생할 수 있는 통합 오류를 미리 발견하고 해결할 수 있다.

### 주요 특징:
- 코드 변경 시 자동으로 빌드 및 테스트
- 빌드 실패 시 즉시 알림
- 지속적인 통합으로 품질 향상 및 빠른 배포 가능

</details>

<br>
<br>

<details>
<summary style="font-size: 18px; font-weight: bold;">CD: 지속적 배포</summary>

CD(Continuous Deployment/Delivery)는 CI에서 빌드된 소프트웨어를 자동으로 테스트, 패키징하여 프로덕션 환경에 자동으로 배포하는 프로세스를 의미한다.<br>이는 GitHub의 원격 저장소에 코드를 push할 때마다 자동으로 빌드 및 배포되어 사용자에게 신속한 업데이트를 제공한다.

### 주요 특징:
- 자동화된 배포 프로세스
- 사용자 피드백에 따른 지속적인 개선
- 빠르고 신속한 업데이트 제공으로 사용자 만족도 향상

</details>


<br>
<br>

### ✍️ CI/CD 시나리오

---
<details>
<summary style="font-size: 18px; font-weight: bold;">FrontEnd</summary>

GitHub 저장소에 최신 코드를 Push한다.

GitHub 저장소는 WebHook을 사용하여 Jenkins에 최신 코드 Push 이벤트를 전달한다.

Jenkins 파이프라인은 다음과 같은 절차에 따라 작동한다

1. Jenkins 서버는 연결된 GitHub 저장소에서 최신 코드를 복제한다.
2. FrontEnd 프로젝트의 경우 npm i 명령어를 사용하여 필요한 종속성을 설치한다.
3. npm run build 명령어를 사용하여 프로젝트를 빌드한다.
4. 빌드된 dist 파일을 Dockerfile에 따라 Docker 이미지를 생성한다.
5. 생성된 Docker 이미지를 Docker Hub에 업로드하기 위해 로그인한다.
6. Jenkins 서버는 등록된 K8S 마스터 노드에 배포에 사용할 Deployment.yml 파일을 전송한다.
7. K8S 마스터는 전송된 Deployment.yml 파일을 kubectl apply 명령어를 사용하여 적용한다.
8. Jenkins 서버는 작성된 파이프라인의 각 단계별 실행 결과를 Slack에 전송한다.

또한, 배포 방식은 Rolling Update를 사용하여 이전 버전과 새 버전의 파드를 점진적으로 교체하여 가용성을 유지한다.
</details> 

<br>
<br>

<details>
<summary style="font-size: 18px; font-weight: bold;">BackEnd</summary>

GitHub 저장소에 최신 코드를 Push한다.

GitHub 저장소는 WebHook을 사용하여 Jenkins에 최신 코드 Push 이벤트를 전달한다.

Jenkins 파이프라인은 다음과 같은 절차에 따라 작동한다

1. Jenkins 서버는 연결된 GitHub 저장소에서 최신 코드를 복제한다.
2. BackEnd 프로젝트의 경우 mvn test 명령어를 사용하여 테스트를 실행하여 코드의 품질을 확인한다.
3. 이후 테스트가 통과되면 mvn package 명령어를 실행하여 아티팩트를 생성한다.
4. 빌드된 jar 파일을 Dockerfile에 따라 Docker 이미지를 생성한다.
5. 생성된 Docker 이미지를 Docker Hub에 업로드하기 위해 로그인한다.
6. Jenkins 서버는 등록된 K8S 마스터 노드에 배포에 사용할 Deployment.yml 파일을 전송한다.
7. K8S 마스터는 전송된 Deployment.yml 파일을 kubectl apply 명령어를 사용하여 적용한다.
8. Jenkins 서버는 작성된 파이프라인의 각 단계별 실행 결과를 Slack에 전송한다.

또한, 배포 방식은 Rolling Update를 사용하여 이전 버전과 새 버전의 파드를 점진적으로 교체하여 가용성을 유지한다.
</details>
<br>
<br>

### 🎥 CI/CD 시연 영상

---
<details>
<summary style="font-size: 18px; font-weight: bold;">FrontEnd</summary>
</details>
<br>
<br>

<details>
<summary style="font-size: 18px; font-weight: bold;">BackEnd</summary>
</details>



