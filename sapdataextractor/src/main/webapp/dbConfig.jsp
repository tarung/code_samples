<table>
	<tr>
		<td>
			<div class="lightBluePanel">
				<table width="600" cellpadding="5">
					<tr>
						<td width="25%" class="slelectedLable">Name :</td>
						<td width="25%" class="slelectedValue"><div
								id="dbConfigName"></div></td>
						<td width="25%" class="slelectedLable">Description :</td>
						<td width="25%" class="slelectedValue"><div id="dbConfigDescription" class="expandable"></div></td>
					</tr>
					<tr>
						<td width="25%" class="slelectedLable">Driver class Name :</td>
						<td width="25%" class="slelectedValue"><div
								id="driverClassName"></div></td>
						<td width="25%" class="slelectedLable">Database URL :</td>
						<td width="25%" class="slelectedValue"><div id="url"></div></td>
					</tr>
					<tr>
						<td width="25%" class="slelectedLable">User Name :</td>
						<td width="25%" class="slelectedValue"><div id="dbConfigUserName"></div></td>
						<td width="25%" class="slelectedLable">Password :</td>
						<td width="25%" class="slelectedValue"><div id="dbConfigPassword"></div></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<div>
				<div>
					<table style="font-size: 12px;" id="dbConfigurationTable"></table>
					<div style="font-size: 11px;" id="dbConfigPagDiv"></div>
					<br>
					<button id="addDbConnection" class="commonButtonClass" >Add a new Configuration</button>
					<button id="editDbConnection" class="commonButtonClass" >Edit Configuration</button>
					<button id="deleteDbConnection" class="commonButtonClass" >Delete Configuration</button>
					<br>
				</div>
			</div>
		</td>
	</tr>
</table>
<script>
	dbConfig();
</script>
