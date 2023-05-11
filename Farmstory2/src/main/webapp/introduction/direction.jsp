<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../_header.jsp"/>
<div id="sub">
    <div>
        <img src="../img/sub_top_tit1.png" alt="INTRODUCTION">
    </div>
    <section class="cate1">
        <aside>
            <img src="../img/sub_aside_cate1_tit.png" alt="팜스토리 소개">
            <ul class="lnb">
                <li><a href="./hello.do">인사말</a></li>
                <li class="on"><a href="./direction.do">찾아오시는 길</a></li>
            </ul>
        </aside>
        <article>
            <nav>
                <img src="../img/sub_nav_tit_cate1_tit2.png" alt="인사말">
                <p>HOME > 팜스토리소개 > <em>인사말</em></p>
            </nav>
            <p>
                <strong>팜스토리</strong><br/>
                주소: 경기도 이천시 잘한다구 신난다동 123<br/>
                전화: 01-234-5678<br/>
            </p>
            <p>
            	<strong>찾아오시는길</strong><br/>
            	<div id="daumRoughmapContainer1668214668575" class="root_daum_roughmap root_daum_roughmap_landing"></div>
            </p>
            
            <script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>
            
            <script charset="UTF-8">
			    new daum.roughmap.Lander({
			        "timestamp" : "1668214668575",
			        "key" : "2ci7x",
			        "mapWidth" : "760",
			        "mapHeight" : "400"
			    }).render();
			</script>
        </article>
    </section>
</div>
<jsp:include page="../_footer.jsp"/>