<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form>
	<input type="hidden" name="profileIdHidden" id="profileIdHidden" value="${id}" />
</form>
<table>
	<tr align="center">
		<td colspan="2">
			<table>
				<tr>
					<td>
						<div>
							<div>
								<table style="font-size: 12px;" id="profileTableData"></table>
								<div style="font-size: 11px;" id="profileTableNav"></div>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="text" id="tableName" name="tName" />&nbsp;
			<button ${(!editProfile) ? 'disabled' : ''} id="searchTables" class="commonButtonClass">Add Table</button>&nbsp;
			<button ${(!editProfile) ? 'disabled' : ''} id="deleteSelectedTable" class="commonButtonClass">Delete Selected Table</button>
			</td>
	</tr>
</table>
<script>
	var pid = $('#profileIdHidden').val();

	$("#deleteSelectedTable").click(function() {
		var gr = jQuery("#profileTableData").jqGrid('getGridParam','selrow');
	 	if(gr) {
		  	var rw = jQuery("#profileTableData").jqGrid('getRowData', gr);
		  	$.ajax({
				type : 'GET',
				url :  'data/profilelist/deleteTable?tableId='+rw.id,
				success : function(data, status) {
					jQuery("#profileTableData").jqGrid().trigger("reloadGrid");
				},
				error: function (xhr, status) {
		            alert("Sorry, there was a problem!");
		        }
			});
		}
    	else {
    		alert("Please select a Table.");
    	}
	});

	$("#searchTables").click(function() {
		var tname = $("#tableName").val();
		var sapId = $("#profileSapSystemId").val();
		if (tname){
			loading();
			setTimeout(function() {
				loadPopup( "#popup_content",
						"data/profilelist/searchTable?tableName="
						+ tname + '&sapSystemId='+sapId);
				preparePopDivs();
			}, 500);
			return false;
		}
		else{
			alert('Table name cannot be empty!');
			return false;
		}
	});


	jQuery("#profileTableData").jqGrid({
		url:'data/profilelist/loadTables?id='+pid,
		datatype: "json",
		height: 180,
		colNames: ['ID','Table Name','Description'],
		colModel: [
					{name:"id",index:"id", hidden:true, key:true},
					{name:"tableName",index:"tableName",width:250},
					{name:"description", index:"description",width:340}
		],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	pager: '#profileTableNav',
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
		multiselect: false,
		caption: "",
	});
	jQuery("#profileTableData").jqGrid('navGrid','#profileTableNav',{search:false,add:false,edit:false,del:false});
	$("#profileTableData > .ui-jqgrid-titlebar").hide();

</script>

