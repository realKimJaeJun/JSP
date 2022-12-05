<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="./_header.jsp"/>
<script src="/Jboard2/js/zipcode.js"></script>
<script src="/Jboard2/js/validation.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<main id="user">
    <section class="register">

        <form action="/Jboard2/user/myInfo.do" method="post">
            <table border="1">
                <caption>회원정보 설정</caption>
                <tr>
                    <td>아이디</td>
                    <td>${sessUser.uid}</td>
                </tr>
                <tr>
                    <td>비밀번호</td>
                    <td>
                    <input type="password" name="pass1" placeholder="비밀번호 입력"/>
                    <span class="resultPass"></span>
                    </td>
                </tr>
                <tr>
                    <td>비밀번호 수정</td>
                    <td><input type="password" name="pass2" placeholder="비밀번호 입력 확인"/>
                    	<button type="button">비밀번호 수정</button>
                    </td>
                </tr>
                <tr>
                	<td>회원가입일</td>
                	<td>2022-12-05 11:20:12</td> 
                </tr>
            </table>

            <table border="1">
                <caption>개인정보 수정</caption>
                <tr>
                    <td>이름</td>
                    <td>
                        <input type="text" name="name" placeholder="${sessUser.name}"/>
                        <span class="resultName"></span>                        
                    </td>
                </tr>
                <tr>
                    <td>별명</td>
                    <td>
                        <p class="nickInfo">공백없는 한글, 영문, 숫자 입력</p>
                        <input type="text" name="nick" placeholder="${sessUser.nick}"/>
                        <button type="button" id="btnNickCheck"><img src="../img/chk_id.gif" alt="중복확인"/></button>
                        <span class="resultNick"></span>
                    </td>
                </tr>
                <tr>
                    <td>이메일</td>
                    <td>
                        <input type="email" name="email" placeholder="${sessUser.email}"/>
                        <span class="resultEmail"></span>
                        <button type="button" id="btnEmail"><img src="../img/chk_auth.gif" alt="인증번호 받기"/></button>
                        <div class="auth">
                            <input type="text" name="auth" placeholder="인증번호 입력"/>
                            <button type="button" id="btnEmailConfirm"><img src="../img/chk_confirm.gif" alt="확인"/></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>휴대폰</td>
                    <td>
                    <input type="text" name="hp" placeholder="${sessUser.hp}"/>
                    <span class="resultHp"></span>
                    </td>
                </tr>
                <tr>
                    <td>주소</td>
                    <td>
                        <input type="text" name="zip" id="zip" placeholder="${sessUser.zip}"/>
                        <button type="button" onclick="zipcode()"><img src="../img/chk_post.gif" alt="우편번호찾기"/></button>
                        <input type="text" name="addr1" placeholder="${sessUser.addr1}" readonly/>
                        <input type="text" name="addr2" placeholder="${sessUser.addr2}"/>
                    </td>
                </tr>
                <tr>
                	<td>회원탈퇴</td>
                	<td>
                		<button type="button" style="padding: 6px; background: #ed2f2f; color: #fff;">탈퇴하기</button>
                	</td>
                </tr>
            </table>

            <div>
                <a href="/Jboard2/list.do" class="btn btnCancel">취소</a>
                <input type="submit" value="회원수정" class="btn btnRegister"/>
            </div>

        </form>

    </section>
</main>
<jsp:include page="./_footer.jsp"/>