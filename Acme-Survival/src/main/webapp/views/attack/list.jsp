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
<spring:message code="attack.attacker" var="titleAttacker" />
<spring:message code="attack.defendant" var="titleDefendant" />
<spring:message code="attack.startDate" var="titleStartDate" />
<spring:message code="attack.endMoment" var="titleEndMoment" />
<spring:message code="attack.results.title" var="titleResults" />
<spring:message code="master.page.moment.format" var="formatMoment" />
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}"
	pattern="yyyy-MM-dd HH:mm" />

<!-- Pagination -->
<acme:pagination requestURI="${requestUri}page=" pageNum="${pageNum}"
	page="${page}" />
	
<!-- Table -->
<display:table name="${attacks}" id="attackList"
	requestURI="${requestUri}page=${page}">

	<jstl:if test="${attackList.attacker.id == myRefugeId}">
		<jstl:set var="attackRowColor" value ="ATTACKER"/>
	</jstl:if>
	<jstl:if test="${attackList.attacker.id != myRefugeId}">
		<jstl:set var="attackRowColor" value ="DEFENDANT"/>
	</jstl:if>

	<display:column title="${titleAttacker}" class="${attackRowColor}">
		<jstl:out value="${attackList.attacker.name}"/>
	</display:column>
	
	<display:column title="${titleDefendant}" class="${attackRowColor}">
		<jstl:out value="${attackList.defendant.name}"/>
	</display:column>
	
	<display:column property="startDate" title="${titleStartDate}" format="${formatMoment}" sortable="true" class="${attackRowColor}"/>
	
	<display:column property="endMoment" title="${titleEndMoment}" format="${formatMoment}" sortable="true" class="${attackRowColor}"/>
	
	<jstl:if test="${attackList.endMoment < currentDate}">
		<display:column title="${titleResults}" class="${attackRowColor}">
			<a href="attack/player/results.do?attackId=${attackList.id}"><spring:message code="attack.results"/></a>
		</display:column>
	</jstl:if>
	
</display:table>


