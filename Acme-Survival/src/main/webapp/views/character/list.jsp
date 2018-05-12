
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<spring:message code="master.page.moment.format" var="formatDate" />
<spring:message code="master.page.birthDate.format" var="formatBirthDate" />


<!-- Pagination -->

<acme:pagination requestURI = "character/player/list.do?anonymous=${anonymous}&page=" pageNum = "${pageNum}" page = "${page}"/>

<display:table name="characters" id="row" requestURI="character/list.do"
	class="displaytag">

	<spring:message code="character.name" var="name" />
	<display:column property="name" title="${name}" sortable="true" />

	<spring:message code="player.surname" var="surname" />
	<display:column property="surname" title="${surname}" sortable="true" />

	<display:column>
		<security:authorize access="hasRole('PLAYER')">
			<acme:button
				url="character/player/display.do?characterId=${character.id}"
				code="character.display" />
		</security:authorize>
	</display:column>

</display:table>
