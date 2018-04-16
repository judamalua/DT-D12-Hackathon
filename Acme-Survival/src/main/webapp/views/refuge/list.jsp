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

<acme:pagination requestURI="${requestURI}" pageNum="${pageNum}"
	page="${page}" />

<display:table name="refuges" id="refuge" requestURI="${requestURI}"
	class="displaytag">
	
	<spring:message code="refuge.name" var="nameTitle"/>
	<spring:message code="refuge.momentOfCreation" var="momentTitle"/>
	<spring:message code="master.page.moment.format	" var="momentFormat"/>

	<display:column title="${nameTitle}">
		<a href="refuge/display.do?refuge=${refuge.id}"><jstl:out
				value="${refuge.name}" /></a>
		<br />
	</display:column>
	
	<display:column property="momentOfCreation" title="${momentTitle}" format="${momentFormat}"/>

</display:table>

<security:authorize access="hasRole('PLAYER')">
	<jstl:if test="${!hasRefuge}">
		<acme:button url="refuge/actor/create.do" code="refuge.create" />
	</jstl:if>
</security:authorize>

