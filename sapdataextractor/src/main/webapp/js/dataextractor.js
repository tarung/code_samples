
function dbConfig() {
	jQuery("#dbConfigurationTable").jqGrid(
		{
			url : "data/dbconnection/get",
			datatype : "json",
			jsonReader : {
				repeatitems : true,
				id : "ref"
			},
			colNames : [
			        'ID', 'Name', 'Description', 'Driver Class',
					'URL', 'User Name', 'Password'
			],
			colModel : [
	             {name : 'id', hidden:true, index : 'id', editable : false, key:true},
	             {name : 'name', index : 'name', width : 120, editable : true, editoptions : { size : 10 }, editrules : { required : true } },
	             {name : 'description', index : 'description', width : 180, sortable: false, editable : true, editoptions : { size : 10 }, editrules : { required : true } },
	             {name : 'driverClassName', index : 'destinationName', width : 160, editable : true, editoptions : { size : 10 }, editrules : { required : true } },
				 {name : 'url', index : 'hostName', width : 160, editable : true, editoptions : { size : 10 }, editrules : { required : true } },
				 {name : 'userName', hidden:true, index : 'userName', editable : true, editoptions : { size : 10 }, editrules : { required : true, edithidden:true  } },
				 {name : 'password', hidden:true, index : 'password', editable : true, edittype:"password", editoptions : { size : 10 }, editrules : { required : true, edithidden:true  } }
			],
			rowNum  : 10,
			rowList : [ 10, 20, 30 ],
			height  : 300,
			pager   : "#dbConfigPagDiv",
			viewrecords : true,
			caption : "",
			onSelectRow: function(ids) {
				updateDBConfigDiv();
        	},
        	loadComplete : function(){
        		selectFirstRow("#dbConfigurationTable");
			},
			editurl : "data/dbconnection/operations"
		});

	jQuery("#dbConfigurationTable").jqGrid('navGrid', '#dbConfigPagDiv',{search:false,add:false,edit:false,del:false});

	$("#addDbConnection").click(function() {
		loading();
		setTimeout(function() {
			loadPopup( "#popup_content", "data/dbconnection/loadAdd");
			preparePopDivs();
		}, 500);
		closeloading();
		return false;
	});
	$("#editDbConnection").click(function() {
		var grid = jQuery("#dbConfigurationTable").jqGrid();
	    var rowData = grid.getGridParam('selrow');
	    if(rowData){
	    	loading();
			var item = grid.getRowData(rowData);
			setTimeout(function() {
				loadPopup( "#popup_content", "data/dbconnection/loadAdd?edit=true&id=" + item.id);
				preparePopDivs();
				closeloading();
			}, 500);
			return false;
		}
		else {
			alert("Please Select Row");
		}
	});
	$("#deleteDbConnection").click(function() {

		var grid = jQuery("#dbConfigurationTable").jqGrid();
	    var rowData = grid.getGridParam('selrow');

	    if(rowData){
	    	if(!confirm("Please confirm if you want to delete the selected SAP System configuraitons.")){
	    		return false;
	    	}
	    	loading();
			var item = grid.getRowData(rowData);
			$.ajax({
				type : 'POST',
				url : 'data/dbconnection/operations?oper=del&id=' + item.id,
				success : function(data, status) {
					closeloading();
					var sapgrid = jQuery("#dbConfigurationTable").jqGrid();
					var pge = sapgrid.getGridParam('page');
					sapgrid.trigger("reloadGrid",[{page:pge}]);
				},
				error: function (xhr, status) {
					closeloading();
					alert(xhr.responseText)
		        }
			});
	    }
		else {
			alert("Please Select Row");
		}
	});
}

function updateDBConfigDiv(){
	var grid = jQuery("#dbConfigurationTable").jqGrid();
    var rowData = grid.getGridParam('selrow');
    if(rowData){
    	var item = grid.getRowData(rowData);
    	$("#dbConfigName").html(item.name);
    	$("#dbConfigDescription").html(item.description);
    	$("#driverClassName").html(item.driverClassName);
    	$("#url").html(item.url);
    	$("#dbConfigUserName").html(item.userName);
		$("#dbConfigDescription").expander({
			slicePoint : 20,
			expandPrefix : ' ',
			expandText : 'more',
			collapseTimer : 0,
			userCollapseText : 'less'
		});
	}
}

function extractionProfile() {

	$("#selectProfileNext").click(function() {
	    var gr = jQuery("#sg1").jqGrid('getGridParam','selrow');
	    if(gr) {
		  	var rw = jQuery("#sg1").jqGrid('getRowData', gr);
    		var pname = rw.profileName;
    		loadProfileDetail(gr, $("#subtabs2"), pname, $("#newProfileLink"));
		  	$('#tab-container2').easytabs('select', '#subtabs2');
		}
    	else {
    		alert("Please Select Row");
    	}
	});
	$("#newProfile").click(function() {
		loadProfileDetail(null, $("#subtabs2"), 'New Profile', $("#newProfileLink"));
	  	$('#tab-container2').easytabs('select', '#subtabs2');
	});
	jQuery("#sg1").jqGrid({
		url:'data/profilelist/get',
		datatype: "json",
		height: 300,
	   	colNames:['ID','Profile Name', 'Profile Description', 'SAP System', 'MySQL System'],
	   	colModel:[
	   		{name:'id', hidden:true, index:'id', width:80, key:true},
	   		{name:'profileName', index:'profileName', width:150},
	   		{name:'profileDescription', index:'profileDescription', width:150},
	   		{name:'sapSystemName', index:'sapSystemName', width:150},
	   		{name:'dbConnectionName', index:'dbConnectionName', width:150}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	pager: '#psg1',
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
		multiselect: false,
		caption: "",
	});
	jQuery("#sg1").jqGrid('navGrid','#psg1',{search:false,add:false,edit:false,del:false});
}

function addFilterSave() {

	$("#saveFiltersClose").click(function() {
		disablePopup();
		loading();
		setTimeout(function() {
			closeloading();
			jQuery("#tableFiltersGrid").jqGrid().trigger("reloadGrid");
		});
	});


	$("#saveAddFilters").click(function() {
		loading();
		setTimeout(function() {
			var $form = $("#FORM_3");
			var $target = $("#popup_content");
			var actionUrl = $form.attr('action');
			$.ajax({
				type : $form.attr('method'),
				url : actionUrl,
				data : $form.serialize(),
				success : function(data, status) {
					$target.html(data);
					closeloading();
				},
				error: function (xhr, status) {
					closeloading();
		        }
			});
		}, 500);
		return false;
	});
}

function addTags(msg){
	msg = msg.substring(0, 100) ;
	return '<table border="0">' +
	'<tr><td class="ui-state-error" style="font-size:13px;">' + msg + '</td></tr>' +
	'</table>';
}


function preparePopDivs(){

	/* event for close the pop-up */
	$("div.close").hover(
			function() {
				$('span.ecs_tooltip').show();
			},
			function() {
				$('span.ecs_tooltip').hide();
			});

	$("div.close").click(function() {
		disablePopup();
	});

	$(this).keyup(function(event) {
		if (event.which == 27) {
			disablePopup();
		}
	});

	$("div#backgroundPopup").click(function() {
		disablePopup();
	});

	$('a.livebox').click(function() {
		alert('Hello World!');
		return false;
	});
}



function profileColumns(tableId) {

	jQuery("#profileColumnGrid").jqGrid({

		url:'data/profilelist/loadFields?tableId=' + tableId,
		datatype: "json",
		height: 180,
		colNames: ['ID','Field Name','Field Type'],
		colModel: [
					{name:"id",index:"id", hidden:true, width:80,key:true},
					{name:"fieldName",index:"fieldName",width:250},
					{name:"fieldType", index:"fieldType",width:340}
				],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	pager: '#profileColumnNav',
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
		multiselect: false,
		caption: ""
	});
	jQuery("#profileColumnGrid").jqGrid('navGrid','#profileColumnNav',{search:false,add:false,edit:false,del:false});
	$("#profileColumnGrid > .ui-jqgrid-titlebar").hide();
}


function profileFilters(tableId) {

	jQuery("#tableFiltersGrid").jqGrid({
		url:'data/profilelist/loadFilters?tableId=' + tableId,
		datatype: "json",
		height: 180,
		colNames: ['ID','Field Name','Operator', 'Criteria', 'Criteria2', 'Join By'],
		colModel: [
					{name:"id",index:"id", hidden:true, width:80,key:true},
					{name:"fieldName",index:"fieldName",width:150},
					{name:"operator", index:"operator",width:140},
					{name:"criteria", index:"criteria",width:140},
					{name:"criteria2", index:"criteria2",width:140},
					{name:"joinby", index:"joinby",width:140}
				],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	pager: '#tableFiltersNav',
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
		multiselect: false,
		caption: ""
	});
	jQuery("#tableFiltersGrid").jqGrid('navGrid','#tableFiltersNav',{search:false,add:false,edit:false,del:false});
	$("#tableFiltersGrid > .ui-jqgrid-titlebar").hide();
}

function loading() {
	$("div.loader").show();
}
function closeloading() {
	$("div.loader").fadeOut('normal');
}

var popupStatus = 0; // set value

function loadPopup(popUpId, url) {

	if(popupStatus == 0) {
	    $.ajax({
	        url: url,
	        type: "GET",
	        dataType: "html",
	        success: function (data) {
	        	$(popUpId).html(data);
	        },
	        error: function (xhr, status) {
	        	$(popUpId).html("Sorry, there was a problem: " + xhr.responseText);
	        },
	        complete: function (xhr, status) {
	        	displayPopup();
	        }
	    });
	}
}

function displayPopup(){
	closeloading();
	$("#toPopup").fadeIn(0500);
	$("#backgroundPopup").css("opacity", "0.7");
	$("#backgroundPopup").fadeIn(0001);
	popupStatus = 1;
}
function disablePopup() {
	if(popupStatus == 1) {
		$("#toPopup").fadeOut("normal");
		$("#backgroundPopup").fadeOut("normal");
		popupStatus = 0;  // and set value to 0
	}
	closeloading();
}

function dataSentOK1() {
	jQuery("#sapSystemTable").jqGrid().trigger("reloadGrid");
}

function sapConfig() {

	jQuery("#sapSystemTable").jqGrid(
		{
			url : "data/sapsystem/get",
			datatype : "json",
			jsonReader : {
				repeatitems : true,
				id : "ref"
			},
			colNames : [
			        'ID', 'Destination Name',
					'Host Name', 'User Name', 'Password', 'Client Nr', 'Sys Nr',
					'Language Code',
					'Is Pooled?', 'Pool Capacity',
					'Peak Limit'
			],
			colModel : [
	             {name : 'id', index : 'id',  hidden:true,  width : 20, editable : false, key:true },
	             {name : 'destinationName', index : 'destinationName', width : 150, editable : true, editoptions : { size : 10 }, editrules : { required : true } },
				 {name : 'hostName', index : 'hostName', width : 180, editable : true, editoptions : { size : 10 }, editrules : { required : true } },
				 {name : 'userName', index : 'userName', width : 120, editable : true, editoptions : { size : 10 }, editrules : { required : true } },
				 {name : 'password', index : 'password', width : 80, hidden : true, editable:true, edittype:"password", editoptions : { size : 10 }, editrules : { required : true, edithidden:true } },
				 {name : 'clientNumber', index : 'clientNumber', hidden : true},
				 {name : 'sysNr', index : 'sysNr', width : 100, },
				 {name : 'languageCode', index : 'languageCode', width : 100, editable : true, edittype:"select", editoptions:{value:"en:E;fr:F"}, editrules : { required : true } },
				 {name : 'isPooled', index : 'isPooled', width : 30, hidden : true, editable : true, edittype:"select",editoptions:{value:"true:true;false:false"}, editrules : { required : true, edithidden:true } },
				 {name : 'poolCapacity', index : 'poolCapacity', width : 70, hidden : true, editable : true, editoptions : { size : 10 }, editrules : { required : false, integer:true, edithidden:true } },
				 {name : 'peakLimit', index : 'peakLimit', width : 70, hidden : true, editable : true, editoptions : { size : 10 }, editrules : { required : false, integer:true, edithidden:true } }
			],
			rowNum  : 10,
			rowList : [ 10, 20, 30 ],
			height  : 150,
			pager   : "#sapConfigPagDiv",
			viewrecords : true,
			caption : "",
			onSelectRow: function(ids) {
				updateSapConfigDiv();
        	},
        	loadComplete : function(){
        		selectFirstRow("#sapSystemTable");
			},
		});

		jQuery("#sapSystemTable").jqGrid('navGrid', '#sapConfigPagDiv',{search:false,add:false,edit:false,del:false});

		$("#addSapConfig").click(function() {
			loading();
			setTimeout(function() {
				loadPopup( "#popup_content", "data/sapsystem/loadAdd");
				preparePopDivs();
			}, 500);
			closeloading();
			return false;
		});

	$("#editSapConfig").click(function() {
		var grid = jQuery("#sapSystemTable").jqGrid();
	    var rowData = grid.getGridParam('selrow');
	    if(rowData){
	    	loading();
			var item = grid.getRowData(rowData);
			setTimeout(function() {
				loadPopup( "#popup_content", "data/sapsystem/loadAdd?edit=true&id=" + item.id);
				preparePopDivs();
				closeloading();
			}, 500);
			return false;
		}
		else {
			alert("Please Select Row");
		}
	});
	$("#deleteSapConfig").click(function() {
		var grid = jQuery("#sapSystemTable").jqGrid();
	    var rowData = grid.getGridParam('selrow');
	    if(rowData){
	    	if(!confirm("Please confirm if you want to delete the selected SAP System configuraitons.")){
	    		return false;
	    	}
	    	loading();
			var item = grid.getRowData(rowData);

			$.ajax({
				type : 'POST',
				url : 'data/sapsystem/operations?oper=del&id=' + item.id,
				success : function(data, status) {
					closeloading();
					var sapgrid = jQuery("#sapSystemTable").jqGrid();
					var pge = sapgrid.getGridParam('page');
					sapgrid.trigger("reloadGrid",[{page:pge}]);
				},
				error: function (xhr, status) {
					closeloading();
					alert(xhr.responseText)
		        }
			});

	    }
		else {
			alert("Please Select Row");
		}
	});
}


function updateSapConfigDiv(){

	var grid = jQuery("#sapSystemTable").jqGrid();
    var rowData = grid.getGridParam('selrow');
    if(rowData){
    	var item = grid.getRowData(rowData);
    	$("#destinationName").html(item.destinationName);
    	$("#hostName").html(item.hostName);
    	$("#sysNr").html(item.sysNr);
    	$("#userName").html(item.userName);
    	$("#isPooled").html(item.isPooled);
    	$("#languageCode").html(item.languageCode);
    	$("#poolCapacity").html(item.poolCapacity);
    	$("#peakLimit").html(item.peakLimit);
    }
}

function taskListTable(percentCompletion) {

	jQuery("#taskListTable").jqGrid({

		url:'data/tasks/get',
		datatype: "json",
		height: 180,
		colNames: ['ID','Profile Name', 'Status', 'Started On', 'Completed On', 'Percentage Completion'],
		colModel: [
					{name:"id",index:"id", hidden:true, key:true},
					{name:"profileName",index:"profileName",width:150},
					{name:"status", index:"status",width:130},
					{name:"startedOnStr", index:"startedOnStr",width:130},
					{name:"completedOnStr", index:"completedOnStr",width:130},
					{name:"percentCompletion", index:"percentCompletion",width:140}
				],
	   	rowNum:10,
	   	pager: '#taskListPagDiv',
	   	rowList:[10,20,30],
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "desc",
		multiselect: false,
		height : 300,
		caption: ""
	});
	jQuery("#taskListTable").jqGrid('navGrid','#taskListPagDiv',{search:false,add:false,edit:false,del:false});
	$("#profileColumnGrid > .ui-jqgrid-titlebar").hide();


	$("#showTaskDetail").click(function() {
		var grid = jQuery("#taskListTable").jqGrid();
	    var rowData = grid.getGridParam('selrow');
	    if(rowData){
	    	var item = grid.getRowData(rowData);
	    	loading();
			setTimeout(function() {
				loadPopup("#popup_content", "taskPopup.jsp?taskId=" + item.id);
				preparePopDivs();
			}, 500);
			return false;
		}
		else{
			alert('Please select a row!');
			return false;
		}
	});
}


function scheduledTaskList() {

	jQuery("#scheduledTaskListTable").jqGrid({

		url:'data/tasks/scheduledTasks',
		datatype: "json",
		height: 180,
		colNames: ['ID','Profile Name', 'Started Date', 'End Date',
		           'Repeat after', 'Last Executed On', 'Next Execution'],
		colModel: [
					{name:"id",index:"id", hidden:true, key:true},
					{name:"name",index:"name",width:150},
					{name:"startDateStr", index:"startDateStr",width:130},
					{name:"endDateStr", index:"endDateStr",width:130},
					{name:"repeatAfterStr", index:"repeatAfterStr",width:130},
					{name:"lastExecutionTimeStr", index:"lastExecutionTimeStr",width:140},
					{name:"nextExecutionTimeStr", index:"nextExecutionTimeStr",width:140}
				],
	   	rowNum:10,
	   	pager: '#scheduledTaskListPagDiv',
	   	rowList:[10,20,30],
	   	sortname: 'id',
	    viewrecords: true,
	    sortorder: "desc",
		multiselect: false,
		height : 300,
		caption: ""
	});
	jQuery("#scheduledTaskListTable").jqGrid('navGrid','#scheduledTaskListPagDiv',{search:false,add:false,edit:false,del:false});


	$("#editTaskDetail").click(function() {
		var grid = jQuery("#taskListTable").jqGrid();
	    var rowData = grid.getGridParam('selrow');
	    if(rowData){
	    	var item = grid.getRowData(rowData);
	    	loading();
			setTimeout(function() {
				loadPopup("#popup_content", "data/tasks/editScheduleTask?id=" + item.id);
				preparePopDivs();
			}, 500);
			return false;
		}
		else{
			alert('Please select a row!');
			return false;
		}
	});
}


function progress(percent, $element) {
	var progressBarWidth = percent * $element.width() / 100;
	$element.find('div').animate({
		width : progressBarWidth
	}, 500).html(percent + "%&nbsp;");
}

