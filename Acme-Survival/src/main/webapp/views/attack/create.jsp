<%--
 * create.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<spring:message var="format" code="master.page.moment.format.out" />
<fmt:formatDate var="formatAttackStartDate"
	value="${attack.startDate}" pattern="${format}" />
<fmt:formatDate var="formatAttackEndMoment"
	value="${attack.endMoment}" pattern="${format}" />

<form:form action="attack/player/create.do" modelAttribute="attack">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="defendant" />
	
	<dl>
  		<dt><spring:message code="attack.attacker"/>:</dt>
  		<dd><jstl:out value="${attack.attacker.name}"/></dd>
		
		
		<dt><spring:message code="attack.defendant"/>:</dt>
		<dd><jstl:out value="${attack.defendant.name}"/></dd>
		
		<dt><spring:message code="attack.startDate.estimated"/>:</dt>
		<dd><jstl:out value="${formatAttackStartDate}"/></dd>
	
		<dt><spring:message code="attack.endMoment.estimated"/>:</dt>
		<dd><jstl:out value="${formatAttackEndMoment}"/></dd>
	
	</dl>
	
	<jstl:if test="${isMoving and !isAttacking}">
		<acme:submit name="save" code="attack.start" />
	</jstl:if>
	
	<acme:cancel url="map/player/display.do" code="attack.cancel" />
	

</form:form>