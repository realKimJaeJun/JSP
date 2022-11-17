<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String cate  = request.getParameter("cate");
%>
<div id="sub">
    <div><img src="../img/sub_top_tit4.png" alt="CROP TALK"></div>
    <section class="cate4">
        <aside>
            <img src="../img/sub_aside_cate4_tit.png" alt="이벤트"/>

            <ul class="lnb">
                <li class="<%= cate.equals("event") ? "on" : "off" %>"><a href="./list.jsp?group=event&cate=event.jsp">이벤트</a></li>
            </ul>

        </aside>
        <article>
            <nav>
                <img src="../img/sub_nav_tit_cate4_tit1.png" alt="이벤트"/>
                <p>
                    HOME > 이벤트 > 
                    <%
                    	switch(cate){
                    	case "event":
                    		out.print("<em>이벤트</em>");
                    		break;
                    	}
                    %>
                </p>
            </nav>