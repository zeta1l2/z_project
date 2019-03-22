<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:url var="path" value="/"/>
<!DOCTYPE HTML>
<html>
<head>
<title><tiles:insertAttribute name="title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="Shoppy Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<script src="${pageContext.request.contextPath}//js/custom.js"></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all">
<!-- Custom Theme files -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all">
<!--js-->
<script src="js/jquery-2.1.1.min.js"></script> 
<!--icons-css-->
<link href="css/font-awesome.css" rel="stylesheet"> 
<!--Google Fonts-->
<link href="//fonts.googleapis.com/css?family=Carrois+Gothic" rel="stylesheet" type="text/css">
<link href="//fonts.googleapis.com/css?family=Work+Sans:400,500,600" rel="stylesheet" type="text/css">
<!--//skycons-icons-->
<link type="text/css" rel="stylesheet" charset="UTF-8" href="https://translate.googleapis.com/translate_static/css/translateelement.css"></head>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!--kendo ui-->
<link href="css/kendo.common.min.css" rel="stylesheet">
<link href="css/kendo.default.min.css" rel="stylesheet">
<link href="css/examples-offline.css" rel="stylesheet">
<script	src="js/kendo.all.min.js"></script>
<body>
	
	<div class="page-container">
			<div class="left-content">
				<tiles:insertAttribute name="header"/>
				<tiles:insertAttribute name="body"/>
				<tiles:insertAttribute name="footer"/>		
			</div>
			<tiles:insertAttribute name="sidebar"/>
			<div class="clearfix"> </div>
	</div>
	<tiles:insertAttribute name="scroll"/>
</body>
</html>