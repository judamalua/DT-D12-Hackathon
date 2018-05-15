
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

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}?page="/>

<!-- Pagination -->



<display:table name="characters" id="character" requestURI="character/player/list.do"
	class="displaytag">

	<spring:message code="character.fullName" var="fullName" />
	<display:column property="fullName" title="${name}" sortable="true" />
	

	<display:column>
		<security:authorize access="hasRole('PLAYER')">
			<acme:button
				url="character/player/display.do?characterId=${character.id}"
				code="character.display" />
		</security:authorize>
	</display:column>

</display:table>
