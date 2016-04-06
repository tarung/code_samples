<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>

	<title>SAP Data Extractor</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<link rel="stylesheet" href="http://trirand.com/blog/jqgrid/themes/redmond/jquery-ui-custom.css">
	<link rel="stylesheet" href="http://trirand.com/blog/jqgrid/themes/ui.jqgrid.css">
	<link rel="stylesheet" href="../../css/style.css">
	<link rel="stylesheet" href="../../css/redmond/jquery-ui.css">
	<link rel="stylesheet" href="../../css/redmond/jquery.ui.theme.css">
	<link rel="stylesheet" href="../../css/jqgrid/ui.jqgrid.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="../../css/jquery.fileupload-ui.css">
	<link rel="stylesheet" href="../../css/multi-select.css">
	<link rel="stylesheet" href="../../css/nodeunit.css">

	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" type="text/javascript"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" type="text/javascript"></script>
	<script src="http://www.trirand.com/blog/jqgrid/js/i18n/grid.locale-en.js" type="text/javascript"></script>
	<script src="http://www.trirand.com/blog/jqgrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js"></script>
	<script src="../../js/prettyprint.js"></script>
	<script src="../../js/jquery.expander.js"></script>
	<script src="../../js/jquery.hashchange.min.js" type="text/javascript"></script>
	<script src="../../js/jquery.easytabs.js" type="text/javascript"></script>
	<script src="../../js/jquery.ui.widget.js"></script>
	<script src="../../js/jquery.iframe-transport.js"></script>
	<script src="../../js/jquery.fileupload.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<script src="http://malsup.github.com/jquery.form.js"></script>
	<script src="../../js/jquery.multi-select.js" type="text/javascript"></script>
	<script src="../../js/async.js"></script>
	<script src="../../js/nodeunit.js"></script>
	<script src="http://www.leapbeyond.com/ric/jsUtils/TaskQueue.js" type="text/javascript"></script>
	<script src="../../js/dataextractor.js"></script>

</head>
<body align="center">
<p align="center">
	<div>
		<table align="center" width="80%" cellpadding="5">
			<tr>
				<td class="slelectedLable" width="110px">Task status : </td>
				<td class="slelectedValue" align="left"><div id="statusDiv">${task.status}</div></td>
			</tr>
			<tr>
				<td colspan="2">
					<table border="1" >
						<tr><td>
							<div id="taskLog" style="height:300px; width:600px; overflow:scroll;"></div>
						</td></tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2"><div width="100%" id="progressBar"><div></div></div> </td>
			</tr>
		</table>
	</div>
</body>
<script>

	var taskId = "${task.id}";
	loadLogs(taskId);
	progress(${task.percentCompletion}, $('#progressBar'));

	console.debug($('#statusDiv').html());

	var count = 0;

	async.whilst(
		function () {
	    	var inprg = ($('#statusDiv').html() == 'INPROGRESS');
    		console.debug(inprg);
    		return inprg;
    	},
	    function (callback) {
	    	console.debug('count :' + count);
	        count++;
	        $.ajax({
    			type : 'GET',
    			url : 'getStatus?taskId=' + taskId,
    			success : function(data, status) {
    				console.debug(data);
    				console.debug("data.percentCompletion " + data.percentCompletion);
    				progress(data.percentCompletion, $('#progressBar'));
    				$('#statusDiv').html(data.status);
    				loadLogs(taskId);
    			},
    			error: function (xhr, status) {
    				console.debug('Error in error: function : ' + xhr);
    				console.debug('Error in error: function : ' + status);
    		    }
    		});
	        setTimeout(callback, 5000);
	    },
	    function (err) {
	    	console.debug("Finished updating progress bar :" + err);
	    }
	);

	function loadLogs(taskId){
		$.ajax({
			type : 'GET',
			url : 'taskLog?taskId=' + taskId,
			success : function(data, status) {
				$('#taskLog').html(data);
			},
			error: function (xhr, status) {
				console.debug('Error in error: function : ' + xhr);
				console.debug('Error in error: function : ' + status);
		    }
		});
	}
</script>
