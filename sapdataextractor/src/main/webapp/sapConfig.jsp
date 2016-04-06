<table>
	<tr>
		<td>
			<div class="lightBluePanel">
				<table width="600" cellpadding="5">
					<tr>
						<td width="25%" class="slelectedLable">Destination Name:</td>
						<td width="25%" class="slelectedValue">
							<div id="destinationName"></div></td>
						<td width="25%" class="slelectedLable">Host Name:</td>
						<td width="25%" class="slelectedValue">
							<div id="hostName"></div>
						</td>
					</tr>
					<tr>
						<td width="25%" class="slelectedLable">Sys Nr:</td>
						<td width="25%" class="slelectedValue">
							<div id="sysNr"></div>
						</td>
						<td width="25%" class="slelectedLable">User Name:</td>
						<td width="25%" class="slelectedValue">
							<div id="userName"></div>
						</td>
					</tr>
					<tr>
						<td width="25%" class="slelectedLable">Language Code:</td>
						<td width="25%" class="slelectedValue"><div id="languageCode"></div></td>
						<td width="25%" class="slelectedLable">Is Pooled:</td>
						<td width="25%" class="slelectedValue"><div id="isPooled"></div></td>
					</tr>
					<tr>
						<td width="25%" class="slelectedLable">Pool Capacity:</td>
						<td width="25%" class="slelectedValue"><div id="poolCapacity"></div></td>
						<td width="25%" class="slelectedLable">Peak Limit:</td>
						<td width="25%" class="slelectedValue"><div id="peakLimit"></div></td>
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
					<table style="font-size: 12px;" id="sapSystemTable"></table>
					<div style="font-size: 11px;" id="sapConfigPagDiv"></div>
					<br>
					<button class="commonButtonClass" id="addSapConfig">Add a new Configuration</button>
					<button class="commonButtonClass" id="editSapConfig">Edit Configuration</button>
					<button class="commonButtonClass" id="deleteSapConfig">Delete Configuration</button>
					<br>
				</div>
			</div>
		</td>
	</tr>
</table>
<script>
	sapConfig();
</script>
