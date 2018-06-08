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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jstl:if test="${fatherForum!=null}">
	<jstl:if test="${fatherForum.forum!=null}">
		<h3>
			<a
				href="forum/list.do?forumId=${fatherForum.forum.id}&staff=${fatherForum.forum.staff}"><jstl:out
					value="${fatherForum.name}" /></a>
		</h3>
	</jstl:if>
	<jstl:if test="${fatherForum.forum==null}">
		<h3>
			<a href="forum/list.do?staff=${fatherForum.forum.staff}"><jstl:out
					value="${fatherForum.name}" /></a>
		</h3>
	</jstl:if>
</jstl:if>
<jstl:if test="${fn:length(forums)>0}">
	<acme:pagination requestURI="${requestURI}page=" pageNum="${pageNum}"
		page="${page}" />

	<display:table name="forums" id="forum" requestURI="${requestURI}"
		class="displaytag">

		<%-- 	<spring:message code="forum.image" var="image" /> --%>
		<%-- 	<display:column property="image" title="${image}" /> --%>

		<display:column title="">
			<jstl:if test="${forum.image!=null && forum.image!=\"\" }">
				<img class="forumImg" src="${forum.image}" />
			</jstl:if>
			<div class="forumNameDescription">
				<div class="forumName">
					<a href="forum/list.do?forumId=${forum.id}&staff=${forum.staff}"><jstl:out
							value="${forum.name}" /></a>
				</div>
				<br />
				<div class="forumDescrition">
					<jstl:out value="${forum.description}" />
				</div>
			</div>
		</display:column>

		<display:column>
			<security:authorize access="isAuthenticated()">
				<acme:button url="thread/actor/create.do?forumId=${forum.id}"
					code="thread.create" />
			</security:authorize>
		</display:column>

		<display:column>
			<jstl:if test="${ownForums!=null and ownForums[forum_rowNum-1]}">
				<acme:button url="forum/actor/edit.do?forumId=${forum.id}"
					code="forum.edit" />
			</jstl:if>
		</display:column>

		<display:column>
			<security:authorize access="hasRole('MODERATOR')">
				<acme:button url="forum/moderator/delete.do?forumId=${forum.id}"
					code="forum.delete" />
			</security:authorize>
		</display:column>

	</display:table>
</jstl:if>
<jstl:if test="${fn:length(threads)>0}">
	<acme:pagination requestURI="${requestURI}page="
		pageNum="${pageNumThread}" page="${pageThread}" />

	<display:table name="threads" id="thread" requestURI="${requestURI}"
		class="displaytag">

		<display:column>
			<a href="message/list.do?threadId=${thread.id}"><jstl:out
					value="${thread.name}" /></a>
			<br />
			<jstl:forEach items="${thread.tags}" var="tag" varStatus="loop">
				<div class="chip">
					<jstl:out value="${tag}" />
				</div>
			</jstl:forEach>
		</display:column>
		<display:column>
			<jstl:if test="${ownThreads != null and ownThreads[thread_rowNum-1]}">
				<acme:button url="thread/actor/edit.do?threadId=${thread.id}"
					code="thread.edit" />
			</jstl:if>
		</display:column>

		<display:column>
			<security:authorize access="hasRole('MODERATOR')">
				<acme:button url="thread/moderator/delete.do?threadId=${thread.id}"
					code="forum.delete" />
			</security:authorize>
		</display:column>

	</display:table>
</jstl:if>
<security:authorize access="isAuthenticated() and !hasRole('PLAYER')">
	<jstl:if test="${fatherForum==null}">
		<acme:button url="forum/actor/create.do" code="forum.create" />
		<br />
	</jstl:if>
	<jstl:if test="${fatherForum!=null}">
		<acme:button url="forum/actor/create.do?forumId=${fatherForum.id}"
			code="forum.create" />
		<br />
	</jstl:if>
	<acme:button url="forum/list.do?staff=true" code="forum.staff" />
	<br />
</security:authorize>
<security:authorize access="isAuthenticated()">
	<acme:button url="forum/list.do?support=true" code="forum.support" />
</security:authorize>
