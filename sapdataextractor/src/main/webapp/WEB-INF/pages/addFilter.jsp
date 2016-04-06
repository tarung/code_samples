<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<p align="center">
<form id="FORM_3" class="lightBluePanel" action="data/profilelist/addFilter" method="post">
	<div id="DIV_3">
		<table width="80%" class="slelectedLable" cellpadding="5">
			<c:if test="${errorMsg != null}">
				<tr>
					<td colspan="2" class="ui-state-error">
						<c:out value="${errorMsg}"/>
					</td>
				</tr>
			</c:if>
			<tr>
				<td>Table Name :</td>
				<td>
					<input type="text" disabled="disabled" id="tableName2" name="tableName2" value="${table.tableName}" />
					<input type="hidden" name="tableName" value="${table.tableName}"/>
					<input type="hidden" name="tableId" value="${tableId}"/>
				</td>
			</tr>
			<tr>
				<td>Table Description :</td>
				<td>
					<input type="text" disabled="disabled" id="tableDescription" name="tableDescription" value="${table.description}" />
				</td>
			</tr>
			<tr>
				<td>Selected Filters :</td>
				<td>
					<select multiple="multiple" size="6" style="width: 350px" id="selectedFilterDisplay" name="filterColumnDisplay">
						<c:forEach var="fltr" items="${filterList}">
							<c:if test="${fltr.joinBy != null}">
								<option disabled="disabled" value="${fltr.id}">
									<c:out value="${fltr.joinBy}"/>
								</option>
							</c:if>
							<option disabled="disabled" value="${fltr.id}">
								<c:out value="${fltr.fieldName}"/> <c:out value="${fltr.operator}"/> <c:out value="${fltr.criteria}"/>
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Add New Filter :</td>
				<td>
					<div class="lightBluePanel">
						<table>
							<c:if test="${filterList != null && fn:length(filterList) gt 0 }">
								<tr>
									<td class="slelectedValue">Join by :</td>
									<td>
										<select id="join" name="joinBy">
											<option value="AND">AND</option>
											<option value="OR">OR</option>
										</select>
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="slelectedValue">Filter Field:</td>
								<td>
									<select id="filterColumn" name="filterColumn">
										<c:forEach var="fld" items="${table.fields}">
											<option value="${fld.fieldName}">${fld.fieldName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="slelectedValue">Filter Operator:</td>
								<td>
									<select id="filterOperator" name="filterOperator">
											<option value="=">Equals</option>
											<option value="&lt;&gt;">Not Equals</option>
											<option value="&lt;">Less than</option>
											<option value="&lt;=">Less than or equals</option>
											<option value="&gt;">Greater than</option>
											<option value="&gt;=">Greater than or equals</option>
											<option value="Like">Like</option>
											<option value="Between">Between</option>
									 </select>
								</td>
							</tr>
							<tr>
								<td class="slelectedValue">Filter Pattern :</td>
								<td><input type="text" id="filterCriteria" title="Filter Criteria" name="filterCriteria"/> </td>
							</tr>
							<tr>
								<td class="slelectedValue">Filter Pattern :</td>
								<td>
									<input type="text" id="filterCriteria2" title="In case of Between" name="filterCriteria2" />
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<button class="commonButtonClass" id="saveAddFilters">Add</button>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="button" class="commonButtonClass" id="saveFiltersClose" size="40"  value="Save" />
				</td>
			</tr>
		</table>
	</div>
</form>
<script>
	addFilterSave();
</script>
