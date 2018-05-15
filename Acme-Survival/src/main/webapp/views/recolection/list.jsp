<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Variable declaration -->
<spring:message code="recolection.startDate" var="titleStartDate" />
<spring:message code="recolection.endMoment" var="titleEndMoment" />
<spring:message code="master.page.moment.format" var="formatMoment" />
<spring:message code="recolection.results.title" var="titleResults" />
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}"
	pattern="yyyy-MM-dd HH:mm" />

<!-- Pagination -->
<acme:pagination requestURI="${requestUri}page=" pageNum="${pageNum}"
	page="${page}" />
	
<!-- Table -->
<display:table name="${recolections}" id="recolectionList"
	requestURI="${requestUri}page=${page}">

	<display:column property="startDate" title="${titleStartDate}" format="${formatMoment}" sortable="true" />
	
	<display:column property="endMoment" title="${titleEndMoment}" format="${formatMoment}" sortable="true" />
	
	<jstl:if test="${recolectionList.endMoment < currentDate}">
		<display:column title="${titleResults}">
			<a href="recolection/player/results.do?attackId=${recolectionList.id}"><spring:message code="recolection.results"/></a>
		</display:column>
	</jstl:if>
	
</display:table>


