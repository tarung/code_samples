<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="profileDetail" class="tabRootDiv">
		<div id="DIV_2" class="lightBluePanel">
			<form id="FORM_1" action="data/profilelist/saveProfile" method="post">
				<input type="hidden" name="profileId" id="profileId" value="${profile.id}" />
				<table width="575px" border="0">
					<c:if test="${errorMsg != null}">
						<tr>
							<td colspan="2" class="ui-state-error" style="font-size: 12px"><c:out value="${errorMsg}"/></td>
						</tr>
					</c:if>
					<tr>
						<td colspan="2">
							<table border="0" height="250px" width="100%" cellpadding="5" >
								<tr>
									<td width="35%" class="slelectedLable">Profile Name :</td>
									<td class="slelectedValue">
										<c:if test="${editProfile}">
											<input type="text" name="profileName" value="${profileName}" />
										</c:if>
										<c:if test="${!editProfile}">
											<div>${profile.profileName}</div>
											<input type="hidden" name="profileName" value="${profile.profileName}" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="slelectedLable">Profile Description :</td>
									<td class="slelectedValue">
										<c:if test="${editProfile}">
											<textarea style="resize: none" name="profileDescription">${profileDescription}</textarea>
										</c:if> <c:if test="${!editProfile}">
											<div id="descriptionDiv">${profile.profileDescription}</div>
											<input type="hidden" name="profileDescription"
												value="${profile.profileDescription}" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="slelectedLable">Source SAP System :</td>
									<td class="slelectedValue">
										<input type="hidden" name="profileSapSystemId" id="profileSapSystemId" value="${profile.sapSystemId}" />
										<c:if test="${editProfile}">
											<select name="sapSystemId">
												<option value="-1">Please Select</option>
												<c:forEach var="sap" items="${sapSystemList}">
													<c:if test="${sapSystemId == sap.id}">
														<option selected="selected" value="${sap.id}">${sap.destinationName}</option>
													</c:if>
													<c:if test="${sapSystemId != sap.id}">
														<option value="${sap.id}">${sap.destinationName}</option>
													</c:if>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${!editProfile}">
											<div class="slelectedValue">${profile.sapSystemName}</div>
											<input type="hidden" name="sapSystemId" id="profileSapSystemId" value="${profile.sapSystemId}"/>
										</c:if></td>
								</tr>
								<tr>
									<td class="slelectedLable">Destination Database :</td>
									<td class="slelectedValue">
										<c:if test="${editProfile}">
											<select name="dbConnectionId">
												<option value="-1">Please Select</option>
												<c:forEach var="db" items="${dbConnectionList}">
													<c:if test="${dbConnectionId == db.id}">
														<option selected="selected" value="${db.id}">${db.name}</option>
													</c:if>
													<c:if test="${dbConnectionId != db.id}">
														<option value="${db.id}">${db.name}</option>
													</c:if>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${!editProfile}">
											<div class="slelectedValue">${profile.dbConnectionName}</div>
											<input type="hidden" name="dbConnectionId" value="${profile.dbConnectionId}" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="slelectedLable">Continue on Failure :</td>
									<td class="slelectedValue">
										<c:if test="${editProfile}">
											<input type="checkbox" name="continueOnFailure" value="true" checked="checked"/>
										</c:if>
										<c:if test="${!editProfile}">
											<div class="slelectedValue">${profile.continueOnFailure}</div>
											<input type="hidden" name="continueOnFailure" value="${profile.continueOnFailure}" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="slelectedLable">Batch Size :</td>
									<td class="slelectedValue">
										<c:if test="${editProfile}">
											<input type="text" name="writeBatchSize"/>
										</c:if>
										<c:if test="${!editProfile}">
											<div class="slelectedValue">${profile.writeBatchSize}</div>
											<input type="hidden" name="writeBatchSize" value="${profile.writeBatchSize}" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="slelectedLable">If Tables Exist :</td>
									<td class="slelectedValue">
										<c:if test="${editProfile}">
											<select name="ifTableExists">
												<option value="DROP">Drop tables if Exist</option>
												<option value="ABORT">Abort data extraction</option>
												<option value="APPEND">Insert data into the existing Tables</option>
												<option value="SKIP">Skip and continue to other tables</option>
											</select>
										</c:if>
										<c:if test="${!editProfile}">
											<div class="slelectedValue">${profile.ifTableExists}</div>
											<input type="hidden" name="ifTableExists" value="${profile.ifTableExists}" />
										</c:if>
									</td>
								</tr>
								<tr align="center">
									<td colspan="2">
										<c:if test="${editProfile}">
											<input type="button" id="saveProfile" class="commonButtonClass" value="Save Profile" />
												&nbsp;
											<input type="button" id="editProfile" class="commonButtonClass" disabled="disabled" value="Edit Profile" />
										</c:if>
										<c:if test="${!editProfile}">
											<input type="button" id="saveProfile" class="commonButtonClass" disabled="disabled" value="Save Profile" />
											&nbsp;
											<input type="submit" id="editProfile" class="commonButtonClass" value="Edit Profile" />
											&nbsp;
											<input type="submit" id="startExtraction" class="commonButtonClass" value="Start Extraction" />
											&nbsp;
											<input type="submit" id="scheduleExtraction" class="commonButtonClass" value="Schedule Extraction" />
										</c:if>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<br/>
		<table>
			<tr>
				<td colspan="2">
					<div id="bottomtabs2" class="tab-container">
						<ul class='etabs'>
							<li class='tab'><a href="data/profilelist/profileTables?editProfile=${!editProfile}&id=${profile.id}" data-target="#ttabs1">Tables</a></li>
							<li class='tab'><a href="data/profilelist/profileColumns?editProfile=${!editProfile}&id=${profile.id}" data-target="#ttabs2">Columns</a></li>
							<li class='tab'><a href="data/profilelist/profileFilters?editProfile=${!editProfile}&id=${profile.id}" data-target="#ttabs3">Filters</a></li>
						</ul>
						<div id="ttabs1"></div>
						<div id="ttabs2"></div>
						<div id="ttabs3"></div>
					</div>
				</td>
			</tr>
		</table>
</div>
<script>

	$('#bottomtabs2').easytabs();
	$("#saveProfile").click(function() {
		sbmtProfileForm("data/profilelist/saveProfile");
	});
	$("#editProfile").click(function() {
		sbmtProfileForm("data/profilelist/editProfile");
	});

	$("#startExtraction").click(function() {
		var profileId = $("#profileId").val();
		if (profileId){
			loading();
			setTimeout(function() {
				loadPopup("#popup_content",
						"data/profilelist/startExtraction?id=" + profileId);
				preparePopDivs();
			}, 500);
			closeloading();
			return false;
		}
		else{
			alert('Table name cannot be empty!');
			return false;
		}
	});
	$("#scheduleExtraction").click(function() {
		var profileId = $("#profileId").val();
		if (profileId){
			loading();
			setTimeout(function() {
				loadPopup("#popup_content",
						"data/tasks/loadScheduleTask?profileId=" + profileId);
				preparePopDivs();
			}, 500);
			return false;
		}
		else{
			alert('Table name cannot be empty!');
			return false;
		}
	});

	$("#descriptionDiv").expander({
		slicePoint : 50,
		expandPrefix : ' ',
		expandText : 'more',
		collapseTimer : 0,
		userCollapseText : 'less'
	});

	function sbmtProfileForm(actionUrl) {
		var $form = $("#FORM_1");
		var $target = $("#profileDetail");
		console.debug($form.serialize());
		$.ajax({
			type : $form.attr('method'),
			url : actionUrl,
			data : $form.serialize(),
			success : function(data, status) {
				$target.html(data);
			},
			error: function (xhr, status) {
				var errorMessage =  '<table border="0">' +
									'<tr><td class="ui-state-error">' + xhr + '</td></tr>' +
									'</table>';
				$("#errorDiv").html(errorMessage);
	        }
		});
	}

</script>
