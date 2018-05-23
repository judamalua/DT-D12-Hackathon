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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- This view lists final mode events and discontinued events -->

<spring:message code = "master.page.current.lang" var = "currentLang" />
<spring:message code="master.page.price.format" var="formatPrice" />

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}?page="/>

<display:table name="events" id="event"
	requestURI="${requestURI}"
	class="displayTag">


	<spring:message code="event.name" var="name" />
	<spring:message code="event.description" var="description" />
	
<display:column title="${name}" sortable="false"><jstl:out value="${event.name[currentLang]}"/></display:column>
	<display:column title="${description}" sortable="false"><jstl:out value="${event.description[currentLang]}"/></display:column>
		<jstl:if test="${designerDraftModeView && !event.finalMode}">
		<display:column>
			<acme:button url="event/designer/edit.do?eventId=${event.id}" code="event.edit"/>
		</display:column>
		</jstl:if>

</display:table>

<security:authorize access="hasRole('DESIGNER')">
	<!-- Checking if the principal is a designer , if so, he or she can create new events -->
	<br />
	<acme:button url="event/designer/create.do" code="event.create"/>
</security:authorize>