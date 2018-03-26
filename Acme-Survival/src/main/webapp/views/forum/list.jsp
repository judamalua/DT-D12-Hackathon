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

<display:table name="forums" id="row" requestURI="${requestURI}"
	class="displaytag">

	<spring:message code="forum.image" var="image" />
	<display:column property="image" title="${image}" />

	<display:column>
		<security:authorize access="isAnonymous()">
			<a href="forum/list.do?forumId=${row.id}"><jstl:out
					value="${row.name}" /></a>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<a href="forum/actor/list.do?forumId=${row.id}"><jstl:out
					value="${row.name}" /></a>
		</security:authorize>
		<br />
		<jstl:out value="${row.description}" />
	</display:column>

	<spring:message code="forum.fatherForum" var="fatherForum" />
	<display:column title="${fatherForum}">
		<jstl:if test="${row.forum!=null}">
			<a href="forum/list.do?forumId=${row.forum.id}"><jstl:out
					value="${row.forum.name}" /></a>
		</jstl:if>
	</display:column>

	<display:column>
		<acme:button url="thread/actor/create.do?forumId=${row.id}"
			code="thread.create" />
	</display:column>

	<display:column>
		<jstl:if test="${ownActor}">
			<acme:button url="forum/actor/edit.do?forumId=${row.id}" code="forum.edit" />
		</jstl:if>
	</display:column>

</display:table>

<security:authorize access="isAuthenticated()">
	<acme:button url="forum/actor/create.do" code="forum.create" />
</security:authorize>
<acme:button url="forum/actor/edit.do" code="forum.edit" />
