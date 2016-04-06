<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<p align="center">
<form id="FORM_5" class="lightBluePanel" action="data/sapsystem/operations" method="post">
	<div id="DIV_6">
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
				<td>Destination Name :</td>
				<td>
					<c:choose>
						<c:when test="${oper == 'edit'}">
							<input type="text" disabled="disabled" id="sapSystemDestinationName" name="destinationName1" value="${sapSystem.destinationName}" />
							<input type="hidden" name="destinationName" value="${sapSystem.destinationName}">
						</c:when>
						<c:otherwise>
							<input type="text" id="sapSystemDestinationName" name="destinationName" value="${sapSystem.destinationName}" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>Host Name :</td>
				<td>
					<input type="text" id="sapSystemHostName" name="hostName" value="${sapSystem.hostName}" />
				</td>
			</tr>
			<tr>
				<td>User Name :</td>
				<td>
					<input type="text" id="sapSystemUserName" name="userName" value="${sapSystem.userName}" />
				</td>
			</tr>
			<tr>
				<td>Password :</td>
				<td>
					<input type="password" id="sapSystemPassword" name="password" value="${sapSystem.password}" />
				</td>
			</tr>
			<tr>
				<td>Client Nr :</td>
				<td>
					<input type="text" id="sapSystemClientNumber" name="clientNumber" value="${sapSystem.clientNumber}" />
				</td>
			</tr>
			<tr>
				<td>Sys Nr :</td>
				<td>
					<input type="text" id="sapSystemSysNr" name="sysNr" value="${sapSystem.sysNr}" />
				</td>
			</tr>
			<tr>
				<td>Language Code :</td>
				<td>
					<select id="sapSystemLanguageCode" name="languageCode">
						<c:forEach var="lang" items="${languagMap}">
							<c:choose>
								<c:when
									test="${(sapSystem.languageCode != null && sapSystem.languageCode == lang.key) || lang.key == 'E'}">
									<option selected="selected" value="${lang.key}">${lang.value}</option>
								</c:when>
								<c:otherwise>
									<option value="${lang.key}">${lang.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Is Pooled ? :</td>
				<td>
					<select id="sapSystemIsPooled" name="isPooled">
						<c:choose>
							<c:when test="${sapSystem.isPooled != null && sapSystem.isPooled}">
								<option selected="selected" value="true">Yes</option>
								<option value="false">No</option>
							</c:when>
							<c:otherwise>
								<option value="true">Yes</option>
								<option selected="selected" value="false">No</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			<tr>
				<td>Pool Capacity :</td>
				<td>
					<c:choose>
						<c:when test="${sapSystem.isPooled != null && sapSystem.isPooled}">
							<input type="text" id="sapSystemPoolCapacity" name="poolCapacity" value="${sapSystem.poolCapacity}" />
						</c:when>
						<c:otherwise>
							<input type="text" disabled="disabled" id="sapSystemPoolCapacity" name="poolCapacity" value="${sapSystem.poolCapacity}" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>Peak Limit :</td>
				<td>
					<c:choose>
						<c:when test="${sapSystem.isPooled != null && sapSystem.isPooled}">
							<input type="text" id="sapSystemPeakLimit" name="peakLimit" value="${sapSystem.peakLimit}" />
						</c:when>
						<c:otherwise>
							<input type="text" disabled="disabled" id="sapSystemPeakLimit" name="peakLimit" value="${sapSystem.peakLimit}" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr height="50px">
				<td align="center" colspan="2">
					<button class="commonButtonClass" id="saveSapSystem">Save</button>
					&nbsp;
					<button class="commonButtonClass" id="testConnection">Test Connection</button></td>
			</tr>
		</table>
	</div>
</form>
<script>

function validateForm(){
	var msg = checkSysNr();
	if(msg){
		$('#errorMessageDiv').html(addTags(msg));
		return false;
	}
	msg = checkClientNr();
	if(msg){
		$('#errorMessageDiv').html(addTags(msg));
		return false;
	}
	var pcap = $('#sapSystemPoolCapacity').val();
	if(pcap){
		if(isNaN(pcap) || pcap <2 && pcap > 20){
			$('#errorMessageDiv').html(addTags('Pool capacity must be a valid integer between 2 and 20.'));
			return false;
		}
	}
	var plim = $('#sapSystemPeakLimit').val();
	if(plim){
		if(isNaN(plim) || plim < 2 && plim > 20){
			$('#errorMessageDiv').html(addTags('Peak limit must be a valid integer between 2 and 20.'));
			return false;
		}
	}
	return true;
}

$('#testConnection').click(function(){

	if(!validateForm())
		return false;

	var $form = $("#FORM_5");
	var $target = $("#popup_content");
	loading();
	$.ajax({
		type : $form.attr('method'),
		url : 'data/sapsystem/testConnection',
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


$('#saveSapSystem').click(function(){
	if(!validateForm())
		return false;
	var $form = $("#FORM_5");
	var $target = $("#popup_content");
	var actionUrl = $form.attr('action');
	loading();
	$.ajax({
		type : $form.attr('method'),
		url : actionUrl,
		data : $form.serialize(),
		success : function(data, status) {
			closeloading();
			disablePopup();
			var sapgrid = jQuery("#sapSystemTable").jqGrid();
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

$('#sapSystemIsPooled').change(function(){
	console.debug($('#sapSystemIsPooled').val());
	if($('#sapSystemIsPooled').val() == 'false'){
		$('#sapSystemPoolCapacity').prop('disabled', true);
		$('#sapSystemPeakLimit').prop('disabled', true);
	} else {
		$('#sapSystemPoolCapacity').prop('disabled', false);
		$('#sapSystemPeakLimit').prop('disabled', false);
	}
});

function checkSysNr(){
	var value = $('#sapSystemSysNr').val();
	if (isNaN(value) || value < 10 || value >99)
		   return "Please enter a two digit number for System Number.";
	else
		return null;
}

function checkClientNr(){
	var value = $('#sapSystemClientNumber').val();
	if (isNaN(value) || value < 100 || value >999)
		   return "Please enter a three digit number for Client Number.";
	else
		return null;
}

</script>
