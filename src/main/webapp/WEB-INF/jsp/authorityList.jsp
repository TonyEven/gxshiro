<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN"><head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Signin Template for Bootstrap</title>

    <%@include file="/WEB-INF/common/header.jsp"%>
    <!-- Custom styles for this template -->
    <%--<link href="../static/css/signin.css" rel="stylesheet">--%>


</head>

<body>

    <div class="container">
        <div class="table-responsive">
            <table class="table table-striped">
                <c:if test="${not empty list}">
                    <thead>
                        <th>
                            <td></td>
                        </th>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="">
                            <tr>
                                <td>${item}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </c:if>
            </table>
        </div>
    </div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<%--<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>--%>


</body></html>
