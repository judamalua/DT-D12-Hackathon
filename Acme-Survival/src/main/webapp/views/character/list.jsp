
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

	<spring:message code="character.fullName" var="name" />
	<display:column class="characterName" property="fullName" title="${name}" sortable="true" />
	<display:column>
	<div class="characterGenre" hidden="true">
	<jstl:if test="${character.male}">Male</jstl:if>
	<jstl:if test="${!character.male}">Female</jstl:if>
	</div>
	<div class="characterImage" style="height: 100px; width: 100px;"></div>
	</display:column>

	<display:column>
		<security:authorize access="hasRole('PLAYER')">
			<acme:button
				url="character/player/display.do?characterId=${character.id}"
				code="character.display" />
		</security:authorize>
	</display:column>

</display:table>
