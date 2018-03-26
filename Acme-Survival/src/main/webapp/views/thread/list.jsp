<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:pagination requestURI="${requestURI}" pageNum="${pageNum}"
	page="${page}" />

<display:table name="threads" id="row" requestURI="${requestURI}"
	class="displaytag">

	<display:column>
		<a href="messages/list.do?threadId=${row.id}"><jstl:out
				value="${row.name}" /></a>
	</display:column>

	<spring:message code="thread.forum" var="forum" />
	<display:column title="${forum}">
		<a href="forum/list.do?forumId=${row.forum.id}"><jstl:out
				value="${row.forum.name}" /></a>
	</display:column>

	<display:column>
		<jstl:if test="${ownActor}">
			<acme:button url="thread/actor/edit.do" code="thread.edit"/>
		</jstl:if>
	</display:column>

</display:table>
