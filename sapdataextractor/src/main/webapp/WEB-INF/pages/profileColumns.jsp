<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table>
	<tr align="center">
		<td colspan="2">
			<table>
				<tr>
					<td>
						<div>
							<div>
								<table style="font-size: 12px;" id="profileColumnGrid"></table>
								<div style="font-size: 11px;" id="profileColumnNav"></div>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<button ${(!editProfile) ? 'disabled' : ''} class="commonButtonClass" id="addTableFields">Add-Remove Fields</button>
		</td>
	</tr>
</table>
<script>

	var selStr = '<select name="tableId" style="width: 200px" onChange="javascript:reloadColumnGrid()" id="selectTables">';
		<c:forEach var="tb" items="${tables}">
			selStr += '<option value="${tb.id}">${tb.tableName}</option>';
		</c:forEach>
		selStr += '</select>';

	var tid = <%=request.getAttribute("firstTableId")%> ;
	profileColumns(tid);

	profileFilters();
	jQuery("#profileColumnGrid").jqGrid('setGroupHeaders', {
		  useColSpanStyle: false,
		  groupHeaders:[
		    {startColumnName: 'fieldName', numberOfColumns: 1, titleText: '<p class="plainText">Table :</p>' },
			{startColumnName: 'fieldType', numberOfColumns: 1, titleText: '' + selStr}
		  ]
	});
	function reloadColumnGrid(){
		var tableId = $("#selectTables").val();
    	jQuery("#profileColumnGrid").jqGrid('setGridParam',{url:'data/profilelist/loadFields?tableId=' + tableId});
		jQuery("#profileColumnGrid").jqGrid().trigger("reloadGrid");
	}

	$('#addTableFields').click(function(){
		var tableId = $("#selectTables").val();
    	loading();
		setTimeout(function() {
			loadPopup( "#popup_content", "data/profilelist/editTable?tableId=" + tableId );
			preparePopDivs();
		}, 500);
		return false;
	});

</script>