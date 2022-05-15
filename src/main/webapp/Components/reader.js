$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateReaderForm();
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var type = ($("#hidReaderIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "ReaderAPI",
		type : type,
		data : $("#formReader").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onReaderSaveComplete(response.responseText, status);
		}
	});
});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidReaderIDSave").val($(this).data("readerid"));
	$("#readerName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#readerEmail").val($(this).closest("tr").find('td:eq(1)').text());
	$("#readerPhone").val($(this).closest("tr").find('td:eq(2)').text());
	$("#readerPassword").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function(event)
{
	 $.ajax(
	 {
		 url : "ReaderAPI",
		 type : "DELETE",
		 data : "readerID=" + $(this).data("readerid"),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 	onReaderDeleteComplete(response.responseText, status);
		 }
	 });
});

// CLIENT-MODEL================================================================
function validateReaderForm()
{
	// NAME
	if ($("#readerName").val().trim() == "")
	{
		return "Insert Reader Name.";
	}
	
	// EMAIL
	if ($("#readerEmail").val().trim() == "")
	{
		return "Insert Reader Email.";
	}
	 
	// PHONE-------------------------------
	if ($("#readerPhone").val().trim() == "")
	{
		return "Insert Reader Phone.";
	}
	
	// is numerical value
	var tmpPhone = $("#readerPhone").val().trim();
	if (!$.isNumeric(tmpPhone))
	{
		return "Insert a numerical value for Phone Number.";
	}
	
	// Password------------------------
	if ($("#readerPassword").val().trim() == "")
	{
		return "Insert reader Password.";
	}
	
	return true;
}

function onReaderSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divReaderGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	} 
	$("#hidReaderIDSave").val("");
	$("#formReader")[0].reset();
}

function onReaderDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divReaderGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
