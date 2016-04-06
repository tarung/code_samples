<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table>
	<tr align="center">
		<td colspan="2">
			<table>
				<tr>
					<td>
						<div>
							<div>
								<table style="font-size: 12px;" id="tableFiltersGrid"></table>
								<div style="font-size: 11px;" id="tableFiltersNav"></div>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<button ${(!editProfile) ? 'disabled' : ''} class="commonButtonClass" id="addProfileFilters">Add Filter</button>
			<button ${(!editProfile) ? 'disabled' : ''} class="commonButtonClass" id="deleteProfileFilters">Delete Filter</button>
		</td>
	</tr>
</table>
<script>

	var selStr = '<select name="tableId" style="width: 200px" onChange="javascript:reloadFilterGrid()" id="selectTablesForFilters">';
	<c:forEach var="tb" items="${tables}">
		selStr +=   '<option value="${tb.id}">${tb.tableName}</option>';
	</c:forEach>
		selStr +=   '</select>';

	var tid = <%=request.getAttribute("firstTableId")%> ;
	profileFilters(tid);

	jQuery("#tableFiltersGrid").jqGrid('setGroupHeaders', {
		  useColSpanStyle: false,
		  groupHeaders:[
		    {startColumnName: 'fieldName', numberOfColumns: 1, titleText: '<p class="plainText">Table :</p>' },
			{startColumnName: 'operator', numberOfColumns: 4, titleText: '' + selStr}
		  ]
	});

	$("#addProfileFilters").click(function() {
		var tableId = $("#selectTablesForFilters").val();
		var sapId = $("#profileSapSystemId").val();
		loading();
		setTimeout(function() {
			loadPopup("#popup_content",
					"data/profilelist/loadAddFilters?tableId=" + tableId + '&sapSystemId='+sapId);
		}, 500);
		preparePopDivs();
		return false;
	});

	$("#deleteProfileFilters").click(function() {

		var tableId = $("#selectTablesForFilters").val();
		var sapId = $("#profileSapSystemId").val();
		var grid = jQuery("#tableFiltersGrid").jqGrid();
	    var rowData = grid.getGridParam('selrow');
	    if (rowData){
	    	var item = grid.getRowData(rowData);
	    	loading();
			setTimeout(function() {
				loadPopup("#popup_content",
						"data/profilelist/deleteFilter?tableId=" + tableId + '&filterId='+item.id );
				console.debug('reload grid');
				grid.jqGrid('delRowData',item.id);
				preparePopDivs();
			}, 500);
			return false;
		}
	    else {
	    	alert('Please select a row.');
	    }
	});


	function reloadFilterGrid(){
		var tableId = $("#selectTablesForFilters").val();
    	jQuery("#tableFiltersGrid").jqGrid('setGridParam',{url:'data/profilelist/loadFilters?tableId=' + tableId});
		jQuery("#tableFiltersGrid").jqGrid().trigger("reloadGrid");
	}
</script>

