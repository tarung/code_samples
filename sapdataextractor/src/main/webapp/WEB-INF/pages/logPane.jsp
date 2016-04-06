<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table border="0" align="center" width="100%" cellpadding="5">
	<c:forEach var="log" items="${tasklog}">
		<c:if test="${!log.error}">
			<tr>
				<td width="75px" class="logText">${log.timeStamp}</td>
				<td class="logText">${log.message}</td>
			</tr>
		</c:if>
		<c:if test="${log.error}">
			<tr>
				<td width="75px" class="errorText">${log.timeStamp}</td>
				<td class="errorText">${log.message}</td>
			</tr>
		</c:if>
	</c:forEach>
</table>