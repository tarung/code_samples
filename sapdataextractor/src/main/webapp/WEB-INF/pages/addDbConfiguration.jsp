<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<p align="center">
<form id="FORM_6" class="lightBluePanel" action="data/dbconnection/operations" method="post">
	<div id="DIV_7">
		<input type="hidden" name="oper" value="${oper}">
		<input type="hidden" name="id" value="${id}">
		<table width="80%" class="slelectedLable" cellpadding="5">
			<div id="errorMessageDiv"></div>
			<c:if test="${errorMsg != null}">
				<tr>
					<td colspan="2" class="ui-state-error">
						<c:out value="${errorMsg}"/>
					</td>
				</tr>
			</c:if>
			<tr>
				<td>Connection Name :</td>
				<td>
				<c:choose>
					<c:when test="${oper == 'edit'}">
						<input type="text" disabled="disabled" id="dbConnectionName" name="name1" value="${dbConnection.name}" />
						<input type="hidden" name="name" value="${dbConnection.name}">
					</c:when>
					<c:otherwise>
						<input type="text" id="dbConnectionName" name="name" value="${dbConnection.name}" />
					</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>Description :</td>
				<td>
					<input type="text" id="dbConnectionDescription" name="description" value="${dbConnection.description}" />
				</td>
			</tr>
			<tr>
				<td>Driver Class Name :</td>
				<td>
					<c:choose>
						<c:when test="${dbConnection.driverClassName != null}">
							<input type="text" id="dbConnectionDriverClassName" name="driverClassName" value="${dbConnection.driverClassName}" />
						</c:when>
						<c:otherwise>
							<input type="text" id="dbConnectionDriverClassName" name="driverClassName" value="com.mysql.jdbc.Driver" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>Url :</td>
				<td>
					<input type="text" id="dbConnectionUrl" name="url" value="${dbConnection.url}" />
				</td>
			</tr>
			<tr>
				<td>User Name :</td>
				<td>
					<input type="text" id="dbConnectionUserName" name="userName" value="${dbConnection.userName}" />
				</td>
			</tr>
			<tr>
				<td>Password :</td>
				<td>
					<input type="password" id="dbConnectionPassword" name="password" value="${dbConnection.password}" />
				</td>
			</tr>
			<tr height="50px">
				<td align="center" colspan="2">
					<button  class="commonButtonClass" id="saveDbConnection">Save</button>
					&nbsp;
					<button class="commonButtonClass" id="testConnection">Test Connection</button>
				</td>
			</tr>
		</table>
	</div>
</form>
<script>

$('#saveDbConnection').click(function(){

	var $form = $("#FORM_6");
	var $target = $("#popup_content");
	loading();
	$.ajax({
		type : $form.attr('method'),
		url : 'data/dbconnection/operations',
		data : $form.serialize(),
		success : function(data, status) {
			closeloading();
			disablePopup();
			var sapgrid = jQuery("#dbConfigurationTable").jqGrid();
			var pge = sapgrid.getGridParam('page');
			sapgrid.trigger("reloadGrid",[{page:pge}]);
		},
		error: function (xhr, status) {
			closeloading();
			$('#errorMessageDiv').html(addTags(xhr.responseText));
        }
	});
	return false;
});

$('#testConnection').click(function(){

	var $form = $("#FORM_6");
	var $target = $("#popup_content");
	loading();
	$.ajax({
		type : $form.attr('method'),
		url : 'data/dbconnection/testConnection',
		data : $form.serialize(),
		success : function(data, status) {
			closeloading();
			$('#errorMessageDiv').html('<table border="0">' +
					'<tr><td class="ui-state-success" style="font-size:13px;">' + data + '</td></tr>' +
					'</table>');
		},
		error: function (xhr, status) {
			closeloading();
			$('#errorMessageDiv').html(addTags(xhr.responseText));
        }
	});
	return false;
});


function validateForm(){
	var val = $('#dbConnectionName').val();
	if(val == null || val.length()==0){
		$('#errorMessageDiv').html('DB connection name cannot be empty.');
		return false;
	}
	val = $('#dbConnectionDriverClassName').val()
	if(val == null || val.length()==0){
		$('#errorMessageDiv').html('Driver class name cannot be empty.');
		return false;
	}
	val = $('#dbConnectionUrl').val()
	if(val == null || val.length()==0){
		$('#errorMessageDiv').html('URL cannot be empty.');
		return false;
	}
	val = $('#dbConnectionUserName').val()
	if(val == null || val.length()==0){
		$('#errorMessageDiv').html('User name cannot be empty.');
		return false;
	}
	val = $('#dbConnectionPassword').val()
	if(val == null || val.length()==0){
		$('#errorMessageDiv').html('Password cannot be empty.');
		return false;
	}
	return true;
}


</script>
