# BuskMate
<br>

## 개요
BuskMate는 동네에서 같이 연주할 사람을 빠르게 모으고, 일정·장소를 간단히 맞춘 뒤, 바로 대화까지 이어갈 수 있는 가벼운 밴드 매칭 서비스입니다.

기존 모집 서비스는 입력할 것도 많고 절차도 길어서, “일단 한 번 맞춰볼까?” 같은 가벼운 합주에는 시작하기가 부담스러웠습니다. BuskMate는 꼭 필요한 정보만으로 모집을 올리고, 가까운 사람들과 바로 연결되도록 흐름을 최대한 단순하게 만들어 빠르게 매칭하고 바로 소통하는 데 집중합니다.

<img width="1160" height="717" alt="image" src="https://github.com/user-attachments/assets/a78b8574-e3a0-4f55-a61d-b80a78652e4e" />


<br><br>

## Team

| 이름 | 역할 | 주요 담당 |  
| --- | --- | --- | 
| 안지철 | BE, 팀 리더 |Oauth2 + JWT 기반 로그인 구현, 메시징 시스템 개발 |  
| 김유정 | BE, 인프라 구축 | 밴드 시스템 개발 |  
| 김에스더 | BE, 인프라 구축 | 밴드 시스템 개발 |  
| 안희건 | BE, PPT 제작 | 커뮤니티 시스템 개발|  
| 신수호 | BE, 발표| 커뮤니티 시스템 개발, 지도 시스템 개발 |  

<br><br>

## Tech Stack

- Backend: Spring Boot, Java 21, Spring Data JPA
- DB: MySQL
- Infra: AWS EC2, RDS

<br><br>

## 아키텍처 & ERD

<img width="937" height="875" alt="image" src="https://github.com/user-attachments/assets/fa6c80d1-9be5-45e2-b965-7be490425a11" />

<br><br>

<img width="1763" height="1116" alt="image" src="https://github.com/user-attachments/assets/737bcc61-8232-4ffe-8ae3-7087fb5d7255" />

<br><br>

## 작동
1) 밴드(권한/역할)
* 밴드는 OWNER / MEMBER 두 역할로 운영합니다.
* OWNER가 밴드를 만들고 멤버를 관리하며, 모집글(포스트)을 등록하면 해당 밴드의 모집 흐름이 시작됩니다.
* MEMBER는 모집글을 보고 참여/탈퇴하는 식으로 합주 인원을 구성합니다.

2) 모집글 → 지도 마커(카카오 지도)
* OWNER가 모집글을 올릴 때 모집 위치(위·경도) 를 함께 저장합니다.
* 프론트는 카카오 지도 SDK로 지도를 그리고, 서버에서 받은 좌표로 마커를 찍어 “모집 중인 위치”를 보여줍니다.
* 백엔드는 지도 SDK를 직접 다루지 않고 좌표만 보관/조회하며, 별도 지도 API 호출 없이도 마커 노출이 가능합니다.
* 마커에는 모집글 제목/공고 정보 + 모집 지역이 같이 보이도록 연결됩니다.

3) 메시징(STOMP)
* 모집글로 연결된 사용자들은 채팅에서 WebSocket + STOMP로 실시간 대화를 합니다.
* ChannelInterceptor에서 JWT를 파싱해 사용자 식별만 합니다.

<br><br>


## 🧱 패키지/모듈 구조

```text
org.example.buskmate
  ├─ BuskMateApplication.java
  ├─ auth                        // 인증/인가, OAuth2 로그인/토큰, 사용자 식별·세션/Principal 처리
  │  ├─ config                   
  │  ├─ exception                
  │  └─ -mvc                     
  ├─ band                        // 밴드 도메인: 밴드 생성/수정, 멤버(OWNER/MEMBER) 관리, 모집글/지원 관리
  │  └─ -mvc                     
  ├─ community                   // 커뮤니티: 게시글 CRUD, 피드/목록/상세, (필요시) 댓글/좋아요 확장 지점
  │  ├─ exception                
  │  └─ -mvc                    
  ├─ map                         // 지도: 좌표 기반 마커 조회/표시 데이터 제공
  │  └─ -mvc                    
  └─ messenger                   // 메신저: 실시간 채팅, 방/메시지 관리, STOMP/WebSocket 설정
     ├─ config                   
     ├─ chat                      // 채팅 메시지
     │  └─ -mvc                   
     └─ room                      // 채팅방: 방 생성/삭제, 멤버 참여/탈퇴, 권한(OWNER/MEMBER), 방 목록/조회
        └─ -mvc                  
```
<br><br>

## [API 명세서](./BuskBate_API_명세서.pdf)
