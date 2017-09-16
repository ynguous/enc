<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js" ></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#fileUpload123').change(function() {
		var input = this.files[0]
		alert(input)
		var reader = new FileReader();
		reader.readAsDataURL(input);
		reader.onload = function (e) {
	    	alert(e.target.result);
	    };
	    //reader.readAsDataURL(input);
	    alert(reader.result);
	    
	    //$.post('http://localhost:8080/tools/enc', {s: "AAA"}, function(data, status) {alert(data);});
	    
	    $.ajax({
		    url : "/tools//encry?s=AAA",
		    type: "GET",
		    //data : formData,
		    success: function(data, textStatus, jqXHR)
		    {
		        alert(data);
		    },
		    error: function (jqXHR, textStatus, errorThrown)
		    {
		 		alert("WWWWW");
		    }
	    });
	});
});


</script>

</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/process" enctype="multipart/form-data">
	<input id="key" name="key" type="text" style="width:300px;" placeholder="Key"  />
	
	<input id="fileUpload" name="fileUpload" type="file" style="width:300px;padding-left:20px;" />
	<input type="submit" id="submit" name="submit" value="Submit"/>

</form>
<br /><br/ >
<!-- <input id="fileUploadSubmit" type="file" style="width:300px;padding-left:300px;padding-top:300px;" /> -->

<form method="post" action="${pageContext.request.contextPath}/decrypt" enctype="multipart/form-data">
	<input id="key2" name="key2" type="text" style="width:300px;" placeholder="Key" />
	<input id="fileUpload2" name="fileUpload2" type="file" style="width:300px;padding-left:20px;" />
	<input type="submit" id="submit2" name="submit2" value="Submit"/>

</form>

</body>
</html>