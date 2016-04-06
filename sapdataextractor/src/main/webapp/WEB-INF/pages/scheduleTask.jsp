<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="scheduledTask">
<p align="center">
<form id="FORM_4" class="lightBluePanel" action="data/tasks/saveScheduledTask" method="post">
	<input type="hidden" name="staskId" value="${staskId}"/>
	<input type="hidden" name="operation" value="${operation}"/>
	<div id="DIV_4">
		<table width="80%" class="slelectedLable" cellpadding="5">
			<c:if test="${errorMsg != null}">
				<tr>
					<td colspan="2" class="ui-state-error"><c:out value="${errorMsg}"/></td>
				</tr>
			</c:if>
			<tr>
				<td>Profile Name :</td>
				<td>
					<input type="text" disabled="disabled" id="profileName" name="profileName1" value="${profile.profileName}" />
					<input type="hidden" name="profileId" value="${profile.id}"/>
					<input type="hidden" name="profileName" value="${profile.profileName}"/>
				</td>
			</tr>
			<tr>
				<td>Start Date and Time :</td>
				<td>
					 <input type="text" id="startDate" name="startDate" value="${startDate}">
					 <script type="text/javascript">
	                        $(function(){
	                                $('#startDate').appendDtpicker({
	                                        "dateFormat": "DD/MM/YYYY h:m",
	                                        "futureOnly": true,
	                                        "closeOnSelected": true
	                                });
	                        });
	                </script>
				</td>
			</tr>
			<tr>
				<td>End Date and Time :</td>
				<td>
					 <input type="text" id="endDate" name="endDate" value="${endDate}">
					 <script type="text/javascript">
	                        $(function(){
	                        		var laterDate = new Date(Date.now() + 30 * 24 * 60 * 60 * 1000);
	                        		var yr = laterDate.getFullYear();
	                        		var formattedDate = laterDate.getDate()+'/'+(laterDate.getMonth()+1)+'/' + yr + ' 00:00';
	                        		$('#endDate').appendDtpicker({
	                                        "dateFormat": "DD/MM/YYYY h:m",
	                                        "futureOnly": true,
	                                        "closeOnSelected": true,
	                                        'current' : formattedDate
	                                });
	                        });
	                </script>
				</td>
			</tr>
			<tr>
				<td>Repeat Periodically ? : </td>
				<td>
					<c:choose>
						<c:when test="">
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
					<input id="repeatPeriodically" name="repeatPeriodically" checked="checked" value="true" type="checkbox"/>
				</td>
			</tr>
			<tr>
				<td>Repeat After Days :</td>
				<td>
					<select id="repeatAfterDays" name="repeatAfterDays">
						<option value="">Days</option>
						<c:forEach var="count" begin="1" end="31" step="1">
							<c:choose>
								<c:when test="${repeatAfterDays != null && repeatAfterDays == count}">
									<option selected="selected" value="${count}">${count}</option>
								</c:when>
								<c:otherwise>
									<option value="${count}">${count}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Repeat After Hours :</td>
				<td>
					<select id="repeatAfterHours" name="repeatAfterHours">
						<option value="">Hours</option>
						<c:forEach var="count" begin="1" end="23" step="1">
							<c:choose>
								<c:when test="${repeatAfterHours != null && repeatAfterHours == count}">
									<option selected="selected" value="${count}">${count}</option>
								</c:when>
								<c:otherwise>
									<option value="${count}">${count}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Repeat After Minutes :</td>
				<td>
					<select id="repeatAfterMins" name="repeatAfterMins">
						<option value="">Mins</option>
						<c:forEach var="count" begin="1" end="59" step="1">
							<c:choose>
								<c:when test="${repeatAfterMins != null && repeatAfterMins == count}">
									<option selected="selected" value="${count}">${count}</option>
								</c:when>
								<c:otherwise>
									<option value="${count}">${count}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="button" class="commonButtonClass" id="saveScheduledTask" size="40"  value="Save" />
				</td>
			</tr>
		</table>
	</div>
</form>
</div>
<script>
	$('#repeatPeriodically').click(function(){
		if(!$('#repeatPeriodically').prop('checked')){
			$('#repeatAfterDays').prop('disabled', true);
			$('#repeatAfterHours').prop('disabled', true);
			$('#repeatAfterMins').prop('disabled', true);
		}
		else{
			$('#repeatAfterDays').prop('disabled', false);
			$('#repeatAfterHours').prop('disabled', false);
			$('#repeatAfterMins').prop('disabled', false);
		}
	})

	$('#saveScheduledTask').click(function() {
		var $form = $("#FORM_4");
		var $target = $("#scheduledTask");
		console.debug($form.serialize());
		console.debug($form.attr('method'));

		loading();
		$.ajax({
			type : $form.attr('method'),
			url : $form.attr('action'),
			data : $form.serialize(),
			success : function(data, status) {
				$target.html(data);
				closeloading();
			},
			error: function (xhr, status) {
				var errorMessage =  '<table border="0">' +
									'<tr><td class="ui-state-error">' + xhr + '</td></tr>' +
									'</table>';
				$("#errorDiv").html(errorMessage);
				closeloading();
	        }
		});
	}
)
</script>
