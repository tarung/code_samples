<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>SAP Data Extractor</title>

<link rel="stylesheet" href="http://trirand.com/blog/jqgrid/themes/redmond/jquery-ui-custom.css">
<link rel="stylesheet" href="http://trirand.com/blog/jqgrid/themes/ui.jqgrid.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/redmond/jquery-ui.css">
<link rel="stylesheet" href="css/redmond/jquery.ui.theme.css">
<link rel="stylesheet" href="css/jqgrid/ui.jqgrid.css">

<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/jquery.fileupload-ui.css">
<link href="css/multi-select.css" media="screen" rel="stylesheet" type="text/css">
<link href="css/nodeunit.css" media="screen" rel="stylesheet" type="text/css">
<link href="css/jquery.simple-dtpicker.css" type="text/css" rel="stylesheet" />

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" type="text/javascript"></script>
<script src="http://www.trirand.com/blog/jqgrid/js/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="http://www.trirand.com/blog/jqgrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js"></script>
<script src="js/prettyprint.js"></script>

<script src="js/jquery.expander.js"></script>
<script src="js/jquery.hashchange.min.js" type="text/javascript"></script>
<script src="js/jquery.easytabs.js" type="text/javascript"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.iframe-transport.js"></script>
<script src="js/jquery.fileupload.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script src="js/jquery.multi-select.js" type="text/javascript"></script>
<script src="js/async.js"></script>
<script src="js/nodeunit.js"></script>
<script src="http://www.leapbeyond.com/ric/jsUtils/TaskQueue.js" type="text/javascript"></script>
<script src="js/dataextractor.js"></script>
<script src="js/jquery.simple-dtpicker.js" type="text/javascript"></script>

<script>

	$(document).ready(function() {
		$('#tab-container').easytabs();
		 $('#tab-container2')
	    .bind('easytabs:ajax:complete', function(e, clicked, panel, response, status, xhr){
	    	  var $this = $(clicked);
	    	  if($this.attr('id') == 'newProfileLink'){
	    		  var gr = jQuery("#sg1").jqGrid('getGridParam','selrow');
	    		  if(gr) {
	    			  var rw = jQuery("#sg1").jqGrid('getRowData', gr);
		    		  var pname = rw.profileName
		    		  loadProfileDetail(gr, panel, pname, clicked);
				  }
	    	  }
	    });
		$('#tab-container2').easytabs();
	});

	function loadProfileDetail(grid, panel, name, acnr){
		console.debug(grid);
		console.debug(panel);
		console.debug(name);
		console.debug(acnr);

		if(grid){
			var u = 'data/profilelist/loadDetails?id='+grid;
		}
		else{
			var u = 'data/profilelist/loadDetails' ;
		}
		$.ajax({
				type : 'GET',
				url : u,
				success : function(data, status) {
					acnr.attr('href', u);
					acnr.html(name)
					panel.html(data);
				},
				error: function (xhr, status) {
					alert("Sorry there was a problem!");
		        }
			});
	}

	function deleteRuleFunc(id, gridID) {
		var grid = jQuery(gridID).jqGrid();
		var rowNum = grid.getGridParam('selrow');
		if (rowNum) {
			var rule = grid.getRowData(rowNum);
			var del = confirm("Delete rule ID :" + rule.ruleId + " ?");
			if (del) {
				var data = "oper=del&ruleId=" + rule.ruleId;
				$.post("data/rules/operations", data, dataSentOK1);
			}
		} else {
			alert("Please select a row, before deleting.");
		}
	}

	function editError(data) {
		return data.responseText;
	}

	function deleteFunc(id, gridID, url, confirmationQuestion) {
		var grid = jQuery(gridID).jqGrid();
		var rowNum = grid.getGridParam('selrow');
		if (rowNum) {
			var rule = grid.getRowData(rowNum);
			var del = confirm(confirmMessage + rule.ruleId + " ?");
			if (del) {
				var data = "oper=del&id=" + rule.ruleObjectId + "&ruleId="
						+ rule.ruleId + "&ruleObjectFieldId="
						+ rule.ruleObjectFieldId;
				$.post(url, data, dataSentOK2);
			}
		} else {
			alert("Please select a row, before deleting.");
		}
		return [ true, ''];
	}

	function selectFirstRow(gridID) {
		var grid = jQuery(gridID);
		var ids = grid.jqGrid("getDataIDs");
		if (ids && ids.length > 0) {
			grid.jqGrid("setSelection", ids[0]);
		}
	}
</script>
</head>
<body>
	<p align="center">
	<div>
		<div id="tab-container" class="tab-container">
			<ul class='etabs'>
				<li class='tab'><a href="#tabs1">Extraction Profiles</a></li>
				<li class='tab'><a href="sapConfig.jsp" data-target="#tabs2">SAP Systems</a></li>
				<li class='tab'><a href="dbConfig.jsp"  data-target="#tabs3">DB Connections</a></li>
				<li class='tab'><a href="taskList.jsp"  data-target="#tabs4">Extraction Tasks</a></li>
				<li class='tab'><a href="scheduledTasksList.jsp"  data-target="#tabs5">Scheduled Tasks</a></li>
			</ul>
			<div id="tabs1">
				<div id="tab-container2" class="tab-container">
					<ul class='etabs'>
						<li class='tab'><a href="profileList.jsp" data-target="#subtabs1">Extraction Profile List</a></li>
						<li class='tab'><a href="data/profilelist/loadDetails" id="newProfileLink" data-target="#subtabs2">Add a New Profile</a></li>
					</ul>
					<div id="subtabs1"></div>
					<div id="subtabs2"></div>
				</div>
			</div>
			<div id="tabs2"></div>
			<div id="tabs3"></div>
			<div id="tabs4"></div>
			<div id="tabs5"></div>
		</div>
	</div>
	<div id="toPopup">
		<div class="close"></div>
		<span class="ecs_tooltip">Press Esc to close <span
			class="arrow"></span></span>
		<!-- Content will show up in the following div. -->
		<div id="popup_content"><div id="inner_popup_content"></div></div>
	</div>
	<div class="loader"></div>
	<div id="backgroundPopup"></div>
</body>
</html>

