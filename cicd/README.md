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
<img src = "">

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
<h3>크론잡</h3>
<img src = "../img/cronJob.png">
<br>
<h3>디플로이먼트</h3>
<img src = "../img/deployment.png">
<img src = "../img/deployment2.png">   
<br>
<h3>파드</h3>
<img src = "../img/pod.png">
<img src = "../img/pod2.png">
<br>
<h3>레플리카 셋</h3>
<img src = "../img/replicaset.png">
<img src = "../img/replicaset2.png">
<br>
<h3>서비스</h3>
<img src = "../img/service.png">
<img src = "../img/service2.png">    
<br>    
<h3>컨피그 맵</h3>
<img src = "../img/configmap.png">
<br>
<h3>퍼시스턴스 볼륨 클레임</h3>
<img src = "../img/">
<br>
<h3>퍼시스턴스 볼륨</h3>
<img src = "../img/">
<br>
</details>

<br>
<br>

<details>
<summary style="font-size: 18px; font-weight: bold;">Jenkins</summary>
<img src = "../img/Jenkins.png">
</details>

<br>
<br>

### ✍️ CI/CD 시나리오

---
<details>
<summary style="font-size: 18px; font-weight: bold;">FrontEnd</summary>
<br>
<img src = "../img/cicd-frontend.png">
GitHub 저장소에 최신 코드를 Push한다.

GitHub 저장소는 Generic WebHook을 사용하여 Jenkins Server에 Pull Request Event를 전달한다.

Jenkins FrontEnd PipeLine은 다음과 같은 절차에 따라 작동한다

1. Jenkins 서버는 연결된 GitHub 저장소에서 최신 코드를 Clone 한다.
2. FrontEnd 프로젝트의 경우 npm i 명령어를 사용하여 필요한 종속성을 설치한다.
3. npm test 명령어를 사용하여 작성된 테스트 코드를 실행한다.
4. npm run build 명령어를 사용하여 프로젝트를 빌드한다.
5. 빌드된 dist 파일을 Dockerfile에 따라 Docker 이미지를 생성한다.
6. 생성된 Docker 이미지를 Docker Hub에 로그인 후 push한다.
7. Jenkins 서버는 등록된 K8S 마스터 노드에 배포에 사용될 Deployment.yml 파일을 전송한다.
8. K8S 마스터는 전송된 Deployment.yml 파일을 kubectl apply 명령어를 사용하여 적용한다.

배포 방식은 Rolling Update를 사용하여 이전 버전과 새 버전의 파드를 점진적으로 교체하여 가용성을 유지한다.
또한, 진행 중에 오류가 발생하면 해당 단계에서 배포가 중단되고 Slack으로 오류 알림이 전송된다. 성공적으로 완료되면 Slack으로 성공 알림이 전송되며 지정된 작업은 중단되지 않고 계속된다.
</details> 

<br>
<br>

<details>
<summary style="font-size: 18px; font-weight: bold;">BackEnd</summary>
<br>
GitHub 저장소에 최신 코드를 Push한다.
<img src = "../img/cicd-backend.png">

GitHub 저장소는 Generic WebHook을 사용하여 Jenkins Server에 Pull Request Event를 전달한다.

Jenkins BackEnd PipeLine은 다음과 같은 절차에 따라 작동한다

1. Jenkins 서버는 연결된 GitHub 저장소에서 최신 코드를 Clone 한다.
2. Backend 프로젝트의 경우 npm test 명령어를 사용하여 작성된 테스트 코드를 실행한다.
4. npm run build 명령어를 사용하여 프로젝트를 빌드한다.
5. 빌드된 dist 파일을 Dockerfile에 따라 Docker 이미지를 생성한다.
6. 생성된 Docker 이미지를 Docker Hub에 로그인 후 push한다.
7. Jenkins 서버는 등록된 K8S 마스터 노드에 배포에 사용될 Deployment.yml 파일을 전송한다.
8. K8S 마스터는 전송된 Deployment.yml 파일을 kubectl apply 명령어를 사용하여 적용한다.

배포 방식은 Rolling Update를 사용하여 이전 버전과 새 버전의 파드를 점진적으로 교체하여 가용성을 유지한다.
또한, 진행 중에 오류가 발생하면 해당 단계에서 배포가 중단되고 Slack으로 오류 알림이 전송된다. 성공적으로 완료되면 Slack으로 성공 알림이 전송되며 지정된 작업은 중단되지 않고 계속된다.
</details>
<br>
<br>

### 🎥 CI/CD 시연 영상

---
<details>
<summary style="font-size: 18px; font-weight: bold;">Sample</summary>
<img src = "../img/cicd시연영상-1.gif">    
</details>
<br>




