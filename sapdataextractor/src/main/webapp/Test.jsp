<head>
    <meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Rule Management App</title>

	<link rel="stylesheet" href="http://trirand.com/blog/jqgrid/themes/redmond/jquery-ui-custom.css">
	<link rel="stylesheet" href="http://trirand.com/blog/jqgrid/themes/ui.jqgrid.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/redmond/jquery-ui.css">
    <link rel="stylesheet" href="css/redmond/jquery.ui.theme.css">
    <link rel="stylesheet" href="css/jqgrid/ui.jqgrid.css">


	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/jquery.fileupload-ui.css">

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" type="text/javascript"></script>
    <script src="http://www.trirand.com/blog/jqgrid/js/i18n/grid.locale-en.js" type="text/javascript"></script>
    <script src="http://www.trirand.com/blog/jqgrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js"></script>
	<script src="js/jquery.hashchange.min.js" type="text/javascript"></script>
	<script src="js/jquery.easytabs.js" type="text/javascript"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.iframe-transport.js"></script>
	<script src="js/jquery.fileupload.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>


	<style>
	    .etabs { margin: 0; padding: 0; }
	    .tab { display: inline-block; zoom:1; *display:inline; background: #eee; border: solid 1px #999; border-bottom: none; -moz-border-radius: 4px 4px 0 0; -webkit-border-radius: 4px 4px 0 0; }
	    .tab a { font-size: 14px; line-height: 2em; display: block; padding: 0 10px; outline: none; }
	    .tab a:hover { text-decoration: underline; }
	    .tab.active { background: #fff; padding-top: 6px; position: relative; top: 1px; border-color: #666; }
	    .tab a.active { font-weight: bold; }
	    .tab-container .panel-container { background: #fff; border: solid #666 1px; padding: 10px; -moz-border-radius: 0 4px 4px 4px; -webkit-border-radius: 0 4px 4px 4px; }
	    .panel-container { margin-bottom: 10px; }
	  	.ui-paging-info {font-size: 11px;}
	  	.ui-pg-table {font-size: 11px;}
	  	.ui-jqgrid-sortable {font-size: 11px;}
	  	.progress{width:500}
	  	.progress-bar-success {background-color: #5c9ccc; }
	  	.fileinput-button {background-color: #5c9ccc; font-size:10px}
  </style>

    <script>

        $(document).ready(function() {
        	setupGrid1();
        });


        function dataSentOK1(){
            jQuery("#ruleTable").jqGrid().trigger("reloadGrid");
        }

        function setupGrid1(){

            jQuery("#ruleTable").jqGrid({

            	url: "data/test/get",

            	datatype: "json",
                jsonReader: {repeatitems: true, id: "ref"},
                colNames:['SN','Rule Description','Business Process', 'Sub process', 'Rule Type'],

                colModel:[
                    {name:'number',index:'number', width:50, editable:true,editoptions:{size:10}, editrules:{required:true}},
                    {name:'uid',index:'uid', width:220,editable:true,editoptions:{size:10}, editrules:{required:true}},
                    {name:'name',index:'name', width:180,editable:true,editoptions:{size:10}, editrules:{required:true}},
                    {name:'type',index:'type', width:180,editable:true,editoptions:{size:10}, editrules:{required:true}},
                    {name:'group',index:'group', width:80,editable:true,editoptions:{size:10}, editrules:{required:true}}
                ],

                rowNum:0,
                //rowList:[10,20,30],
                height:800,
                pager: "#pagingDiv",
                viewrecords: true,
                multiselect: true,
                caption: "Add Update or Delete Rules",
            	editurl:"data/test/edit"
            });

            jQuery("#ruleTable").jqGrid('navGrid','#pagingDiv',
            		{
            		 search:false,
            		 delfunc:deleteRuleFunc
            		}, // options
            		{	closeAfterEdit:true,
            			height:280,
            			reloadAfterSubmit:true,
            			errorTextFormat:editError
            		}, // edit options
            		{
            			closeAfterAdd:true,
            			height:280,
            			reloadAfterSubmit:true,
            			errorTextFormat:editError
            		}, // add options
            		{reloadAfterSubmit:false,errorTextFormat:editError}, // Del options
            		{reloadAfterSubmit:true} // Search options
            );
        }

        function editError(data){
       		return data.responseText;
	 	}

    </script>
</head>
<body>
<p align="center">
<div>
	<div id="tab-container" class="tab-container" >
			<br>
			<div>
				<div>
					<table style="font-size:12px;" id="ruleTable"></table>
					<div style="font-size:11px;" id="pagingDiv"></div>
					<br>
				</div>
			</div>
	 </div>
</div>
</p>
</body>
</html>

