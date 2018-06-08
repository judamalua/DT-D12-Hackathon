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

<acme:pagination requestURI="${requestURI}page=" pageNum="${pageNum}"
	page="${page}" />

<display:table name="shelters" id="shelter" requestURI="${requestURI}"
	class="displaytag">

	<spring:message code="shelter.name" var="nameTitle" />
	<spring:message code="shelter.momentOfCreation" var="momentTitle" />
	<spring:message code="master.page.moment.format" var="momentFormat" />

	<display:column title="${nameTitle}">
	
		<security:authorize access="hasRole('PLAYER')">
			<a href="shelter/player/display.do?shelterId=${shelter.id}"><jstl:out
					value="${shelter.name}" /></a>
			<br />
		</security:authorize>
		<security:authorize access="!hasRole('PLAYER')">
			<a href="shelter/display.do?shelterId=${shelter.id}"><jstl:out
					value="${shelter.name}" /></a>
			<br />
		</security:authorize>

	</display:column>

	<display:column property="momentOfCreation" title="${momentTitle}"
		format="${momentFormat}" />

</display:table>

<security:authorize access="hasRole('PLAYER')">
	<jstl:if test="${!hasShelter}">
		<acme:button url="shelter/player/create.do" code="shelter.create" />
	</jstl:if>
</security:authorize>

