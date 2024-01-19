Before :  https://github.com/9nh5/Todo

<h1>투두앱 처음부터 새로 작성</h1>

댓글 추가, 사용자 추가, 인증, 인가, 할일 완료여부 추가

<h2>해야될거</h2>

할일카드 완료기능 API

로그인 한 사용자가 자신이 작성한 할 일, 댓글만 수정 가능


<h2>ERD</h2>
![image](https://github.com/9nh5/TodoProject/assets/151013731/a2bf8fe9-1029-4511-ab46-8420d4e5d4e0)

<h2>API</h2>
![image](https://github.com/9nh5/TodoProject/assets/151013731/440b42b7-bc11-4dd0-9eba-a5005ebc053b)


 <h2>안되는거</h2>


<h2>수정 완료</h2>

포스트 완료기능 -> false 상태를 true로 바꾸는 기능 필요
 -> status (Boolean)으로 추가, updatepoststatusrequest 추가, controller, service에 false-> true 바꾸는거 추가해서 작동

작성자 - 수정, 삭제 권한 추가
+로그인 후 포스트, 댓글 작성 시 user id 입력 필요없도록

리스트 조회
