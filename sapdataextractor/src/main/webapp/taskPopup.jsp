<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<table>
	<tr>
		<td colspan="2"><iframe id="taskDetailFrame"
				src="data/tasks/getDetails?taskId=<%=request.getParameter("taskId")%>"
				height="400px" width="700px" scrolling="no" seamless="seamless"></iframe>
		</td>
	</tr>
	<tr>
		<td align="center"><a
			href="data/tasks/getDetails?taskId=<%=request.getParameter("taskId")%>"
			target="taskDetailFrame">Task Detail</a></td>
		<td align="center"><a id="showTaskLogs"
			href="data/tasks/getLog?taskId=<%=request.getParameter("taskId")%>"
			target="taskDetailFrame">Show Logs</a></td>
	</tr>
</table>