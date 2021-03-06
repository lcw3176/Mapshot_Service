<p align="center">
  <img src="https://user-images.githubusercontent.com/59993347/166405369-0d610a83-68d5-4d31-8215-6eba806fba06.png" height="250">
</p>
<p align="center">
<img src="https://img.shields.io/badge/Made%20with-SpringBoot-blue">
<img src="https://img.shields.io/badge/Service%20begun%20in-2021.02-brigntgreen">
</p>
<p align="center">
  <a href="https://kmapshot.com">https://kmapshot.com</a>
</p>  
<p align="center">
  <a href="#서비스-소개">서비스 소개</a> •
  <a href="#결과-샘플">결과 샘플</a> •
  <a href="#기술-스택">기술 스택</a> •
  <a href="#서비스-구조도">서비스 구조도</a> •
  <a href="#관련-프로젝트">관련 프로젝트</a>
</p>  
<p align="center">
<img src="https://user-images.githubusercontent.com/59993347/166405868-46283603-7e8f-47dd-9029-8699e6c61a53.gif">
</p>


## 서비스 소개
<div style="text-align: center">
<img width="200" style="margin:10px;" src="https://user-images.githubusercontent.com/59993347/164415956-f8a6a057-8943-4656-bd94-e8a5ffdec329.jpg">
<img width="200" style="margin:10px;" src="https://user-images.githubusercontent.com/59993347/164415966-d33b7751-cdfe-4a65-8b72-03b1a6b4cae9.jpg">
</div>

도시 공학 계열에서 계획도를 그릴 때 사용되는 배경 위성 이미지를 제공해주는 서비스입니다.<br>
직접 한 구역씩 캡쳐해서 포토샵으로 합치는 프로세스를 자동화해서, <br>
기존 작업 대비 효율적인 업무가 가능합니다. (약 1시간 -> 10초)

## 결과 샘플
```
범위: 1km
종류: 위성
지도 타입: 네이버

사진 확대 시 이미지 화질 샘플,
빨간색 테두리가 확대된 범위
```
<img src="https://user-images.githubusercontent.com/59993347/166407436-3d96ad98-982d-4cd5-9d6a-47b43505602d.jpg" height=600>
<img src="https://user-images.githubusercontent.com/59993347/166406613-82de886c-fe96-4b2b-9671-11e867ea98a2.jpg">

## 기술 스택
### BackEnd
- Java 11, Spring Boot
- H2, Spring-Data-JPA
- Selenium, WebSocket

### FrontEnd
- HTML, CSS, Javascript
- thymeleaf
- Bulma

### Infra
- Oracle Cloud Server(CentOS 8), Cloud Flare(https, domain)
- Github Action
- Nginx 
- Slack API, Whatap


## 서비스 구조도

<details>
<summary>(구) Jenkins를 활용한 배포 환경 구축</summary>

![예전](https://user-images.githubusercontent.com/59993347/176195783-e9c6b652-f691-4b23-9ec0-00a5ed61710a.png)

</details>

### (현) Github Action을 활용한 배포 환경 구축
![현재](https://user-images.githubusercontent.com/59993347/176195801-7f12827f-09f3-40e0-8ef2-55a0c5bca798.png)

## 관련 프로젝트
- 이미지 api 서버: [Mapshot_Image_Api](https://github.com/lcw3176/Mapshot_Image_Api)
- 지도 조립 라이브러리: [Mapshot-lib](https://github.com/lcw3176/mapshot-lib)