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

<spring:message code="master.page.moment.format.out" var="format"/>

<jstl:if test="${fatherThread.forum==null}">
	<h3><a href="forum/list.do"><spring:message code="message.thread"/></a></h3>
</jstl:if>
<jstl:if test="${fatherThread.forum!=null}">
	<h3><a href="forum/list.do?forumId=${fatherThread.forum.id}"><jstl:out value="${fatherThread.forum.name}"/></a></h3>
</jstl:if>
<br/>
<h5><jstl:out value="${fatherThread.name}" /></h5>

<acme:pagination requestURI="${requestURI}" pageNum="${pageNum}"
	page="${page}" />

<display:table name="messages" id="row" requestURI="${requestURI}"
	class="displaytag">

	<spring:message code="message.image" var="image" />
	<display:column property="image" title="${image}" />

	<display:column>
		<img src="${row.image}" />
		<jstl:out value="${row.text}" />
		<br />
		<fmt:formatDate value="${row.moment}" pattern="${format}" />
	</display:column>

	<spring:message code="message.thread" var="thread"/>


	<display:column>
		<jstl:if test="${ownMessage!=null and ownMessage[row_rowNum-1]}">
			<acme:button url="message/actor/edit.do?messageId=${row.id}"
				code="message.edit" />
		</jstl:if>
	</display:column>

</display:table>

<security:authorize access="isAuthenticated()">

	<form:form action="message/actor/edit.do" modelAttribute="messageForm">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="thread" value="${fatherThread.id}" />

		<p>
			<em><spring:message code="form.required.params" /></em>
		</p>

		<acme:textbox code="message.text" path="text" required="true" />

		<acme:textarea code="message.image" path="image" />

		<acme:submit name="save" code="message.save" />

	</form:form>

</security:authorize>
<security:authorize access="isAnonymous()">
	<spring:message code="message.login" />
</security:authorize>
