<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta charset="utf-8">
<script src="webjars/jquery/<spring:eval expression='@environment["jquery.version"]'/>/jquery.js"></script>

<script>
    $(function() {
      $.ajax({url: "sayHello", 
              data: {name: 'Ralph'}
      }).done(function(data) 
      { 
          $('#serverresponse').html(data); 
      });
    });    
</script>

</head>
<body>
	<h1>
		WebJar Demoapplikation: JQuery version:
		<spring:eval expression='@environment["jquery.version"]' />
	</h1>
	<div id="serverresponse"></div>
</body>
</html>