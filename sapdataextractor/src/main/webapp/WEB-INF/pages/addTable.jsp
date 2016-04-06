<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<p align="center">
	<c:choose>
		<c:when test="${tableFound}">
			<form id="FORM_2" class="lightBluePanel" action="data/profilelist/${actionURL}" method="post">
				<input type="hidden" id="tableName1" name="tableName" value="${table.tableName}"/>

				<div id="DIV_3">
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
							<td>Table Name :</td>
							<td colspan="2"><div>${table.tableName}</div>
							</td>
						</tr>
						<tr>
							<td>Table Description :</td>
							<td colspan="2"><div>${table.description}</div>
								<input type="hidden" id="tableDescription" name="tableDescription" value="${table.description}"/>
							</td>
						</tr>
						<tr>
							<td>Columns :</td>
							<td colspan="2">
								<select multiple="multiple" id="selTableColumns" name="selectedColumns">
										<c:forEach var="fld" items="${table.fields}">
											<c:choose>
												<c:when test="${ fn:contains(selectedfieldList, fld.fieldName)}">
													<option selected="selected" value="${fld.fieldName}">${fld.fieldName}</option>
												</c:when>
												<c:otherwise>
													<option value="${fld.fieldName}">${fld.fieldName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="center"><a href='#' id='select-all'>Select all</a></td>
							<td align="center"><a href='#' id='deselect-all'>De-select all</a></td>
						</tr>
						<tr>
							<td align="center" colspan="3">
							<input class="commonButtonClass" type="button" id="saveAddTable" value="Save" /></td>
						</tr>
					</table>
				</div>
			</form>
		</c:when>
		<c:otherwise>
			<p align="center">
				<c:out value="${message}" />
			</p>
		</c:otherwise>
	</c:choose>
</p>
<script>

	//initializes table fields multi-select.
	$('#selTableColumns').multiSelect({});
	//initializes select all link
	$('#select-all').click(function(){
		  console.debug('select-all');
		  $('#selTableColumns').multiSelect('select_all');
		  return false;
	});
	//initializes de-select all link
	$('#deselect-all').click(function(){
	  $('#selTableColumns').multiSelect('deselect_all');
	  return false;
	});

	$("#saveAddTable").click(function() {
		loading();
		setTimeout(function() {
			var $form = $("#FORM_2");
			var actionUrl = $form.attr('action');
			$.ajax({
				type : $form.attr('method'),
				url : actionUrl,
				data : $form.serialize(),
				success : function(data, status) {
					disablePopup();
					<c:if test="${actionURL=='editTableSave'}">
						reloadColumnGrid();
					</c:if>
					<c:if test="${actionURL=='addTableSave'}">
						jQuery("#profileTableData").jqGrid().trigger("reloadGrid");
						$("#tableName").val('');
					</c:if>

					closeloading();
				},
				error: function (xhr, status) {
					closeloading();
					console.debug(addTags(xhr.responseText));
					$('#errorMessageDiv').html(addTags(xhr.responseText));
		        }
			});
		}, 500);
		return false;
	});

	$("#tableDescription").expander({
		slicePoint : 30,
		expandPrefix : ' ',
		expandText : 'more',
		collapseTimer : 0,
		userCollapseText : 'less'
	});
</script>
