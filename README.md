투두앱 처음부터 새로 작성

댓글 추가, 사용자 추가, 인증, 인가, 할일 완료여부 추가

해야될거

할일카드 완료기능 API

로그인 한 사용자가 자신이 작성한 할 일, 댓글만 수정 가능


안되는거



작성자 - 수정, 삭제 권한 추가 해야됨


추가로

+ 로그인 시 비밀번호 틀렸을 때 나오는 JWT verifition failed로 나오는거 수정?


포스트 단건 조회 시 아무것도 안나옴 -> StopWatch 없애니까 작동

포스트 완료기능 -> false 상태를 true로 바꾸는 기능 필요
 -> status (Boolean)으로 추가, updatepoststatusrequest 추가, controller, service에 false-> true 바꾸는거 추가해서 작동

