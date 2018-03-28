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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${fatherForum!=null}">
	<h3>
		<jstl:out value="${fatherForum.name}" />
	</h3>
</jstl:if>

<acme:pagination requestURI="${requestURI}" pageNum="${pageNum}"
	page="${page}" />

<display:table name="forums" id="forum" requestURI="${requestURI}"
	class="displaytag">

	<spring:message code="forum.image" var="image" />
	<display:column property="image" title="${image}" />

	<display:column>
		<security:authorize access="isAnonymous()">
			<a href="forum/list.do?forumId=${forum.id}"><jstl:out
					value="${forum.name}" /></a>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<a href="forum/actor/list.do?forumId=${forum.id}"><jstl:out
					value="${forum.name}" /></a>
		</security:authorize>
		<br />
		<jstl:out value="${forum.description}" />
	</display:column>

	<spring:message code="forum.fatherForum" var="fatherForum" />
	<display:column title="${fatherForum}">
		<jstl:if test="${forum.forum!=null}">
			<a href="forum/list.do?forumId=${forum.forum.id}"><jstl:out
					value="${forum.forum.name}" /></a>
		</jstl:if>
	</display:column>

	<display:column>
		<acme:button url="thread/actor/create.do?forumId=${forum.id}"
			code="thread.create" />
	</display:column>

	<display:column>
		<jstl:if test="${ownForums!=null and ownForums[forum_rowNum-1]}">
			<acme:button url="forum/actor/edit.do?forumId=${forum.id}"
				code="forum.edit" />
		</jstl:if>
	</display:column>

</display:table>

<acme:pagination requestURI="${requestURI}" pageNum="${pageNumThread}"
	page="${pageThread}" />

<display:table name="threads" id="thread" requestURI="${requestURI}"
	class="displaytag">

	<spring:message code="forum.image" var="image" />
	<display:column property="image" title="${image}" />

	<display:column>
		<a href="message/list.do?threadId=${thread.id}"><jstl:out
				value="${thread.name}" /></a>

		<jstl:forEach items="${thread.tag}" var="tag" varStatus="loop">
			<jstl:out value="${tag}" />
			<jstl:if test="${loop.index != (fn:length(thread.tag))-1}">
				,
			</jstl:if>
		</jstl:forEach>
	</display:column>

	<display:column>
		<jstl:if test="${ownThreads != null and ownThreads[thread_rowNum-1]}">
			<acme:button url="thread/actor/edit.do?threadId=${row.id}"
				code="thread.edit" />
		</jstl:if>
	</display:column>

</display:table>

<security:authorize access="isAuthenticated()">
	<acme:button url="forum/actor/create.do" code="forum.create" />
</security:authorize>

<security:authorize access="isAuthenticated()">
	<acme:button url="thread/actor/create.do?forumId=${fatherForum.id}"
		code="thread.create" />
</security:authorize>