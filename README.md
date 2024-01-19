Before :  https://github.com/9nh5/Todo

<h1>투두앱 처음부터 새로 작성</h1>

<h3>기간</h3>
2024.01.16~2024.01.19

댓글 추가, 사용자 추가, 인증, 인가, 할일 완료여부 추가

<h2>해야될거</h2>

할일카드 완료기능 API

로그인 한 사용자가 자신이 작성한 할 일, 댓글만 수정 가능


<h2>API</h2>

![image](https://github.com/9nh5/TodoProject/assets/151013731/7a1c945f-518b-4834-b98c-4b363230b6b4)





<h2>ERD</h2>

![image](https://github.com/9nh5/TodoProject/assets/151013731/72552a0a-0fa7-43fe-9741-6173e2db853c)




 <h2>안되는거</h2>


<h2>수정 완료</h2>

포스트 완료기능 -> false 상태를 true로 바꾸는 기능 필요
 -> status (Boolean)으로 추가, updatepoststatusrequest 추가, controller, service에 false-> true 바꾸는거 추가해서 작동

작성자 - 수정, 삭제 권한 추가
+로그인 후 포스트, 댓글 작성 시 user id 입력 필요없도록

리스트 조회

<h2>기능</h2>
+ 회원가입-로그인
+ 프로필 수정(본인만 가능)
+ 프로필 조회(아이디, 이름으로 조회 가능)
+ 할일카드(post) - 생성,  수정,삭제(작성자만 가능)
+ 할일카드 조회 - 작성자 이름, 할일카드 제목으로 조회, 단건 조회 (댓글 함께 조회)
+ 전체 카드 조회 (댓글 함께 조회) - 조회 목록 설정(초기설정 생성일 기준 내림차순, 1개 조회): sort (createAt, id, title)로 설정 가능
+ 할일카드 완료로 변경 - 기본 생성 시 false 상태에서 true 상태로 변경 (작성자만 가능)
+ 댓글 - 생성,   수정, 삭제(작성자만 가능)
+ 댓글 조회 - 할일카드 번호(아이디)로 조회
