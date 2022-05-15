<%@page import="model.Reader"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reader Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/reader.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row justify-content-center align-items-center">
			<div class="col-4">
				<br>
				<h1 class="row justify-content-center">Reader Management</h1>
				<br>
				<form id="formReader" name="formReader">
					Reader Name: <input id="readerName" name="readerName" type="text"
						class="form-control form-control-sm"> <br> Reader
					Email: <input id="readerEmail" name="readerEmail" type="email"
						class="form-control form-control-sm"> <br> Reader
					Phone: <input id="readerPhone" name="readerPhone" type="text"
						class="form-control form-control-sm"> <br> Reader
					Password: <input id="readerPassword" name="readerPassword"
						type="password" class="form-control form-control-sm"> <br>
					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary btn-block"> <input type="hidden"
						id="hidReaderIDSave" name="hidReaderIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<div class="container-fluid">
		<div class="row justify-content-center align-items-center">
			<div id="divReaderGrid">
				<%
				Reader readerObj = new Reader();
				out.print(readerObj.readReader());
				%>
			</div>
		</div>
	</div>
</body>
</html>