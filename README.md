<h1>
<p align="center">
  <img src="https://user-images.githubusercontent.com/59993347/166405369-0d610a83-68d5-4d31-8215-6eba806fba06.png" height="250">
</p>
</h1>
<p align="center">
    넓은 반경의 위성 사진을 고화질로 제작해주는 서비스
    <br />
</p>
<p align="center">
<img src="https://img.shields.io/badge/Made%20with-SpringBoot-blue">
<img src="https://img.shields.io/badge/Service%20begun%20in-2021.02-brigntgreen">
</p>

## 서비스 개요
<div style="text-align: center">
<img width="200" style="margin:10px;" src="https://user-images.githubusercontent.com/59993347/164415956-f8a6a057-8943-4656-bd94-e8a5ffdec329.jpg">
<img width="200" style="margin:10px;" src="https://user-images.githubusercontent.com/59993347/164415966-d33b7751-cdfe-4a65-8b72-03b1a6b4cae9.jpg">
</div>

도시 공학 계열에서 계획도를 그릴 때 사용되는 배경 위성 이미지를 제공해주는 서비스입니다.<br>
직접 한 구역씩 캡쳐해서 포토샵으로 합치는 프로세스를 자동화해서, <br>
기존 작업 대비 효율적인 업무가 가능합니다. (약 1시간 -> 10초)

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
- Jenkins, Github WebHook
- Nginx 
- Slack API

