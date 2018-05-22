<%--
 * results.jsp
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

<h4>
	<spring:message code="attack.winner"/>:
	<br/>
	<jstl:choose>
		<jstl:when test="${attackerIsWinner}">
			<jstl:out value="${attack.attacker.name}"/>
		</jstl:when>
		
		<jstl:otherwise>
			<jstl:out value="${attack.defendant.name}"/>
		</jstl:otherwise>
		
	</jstl:choose>
</h4>

<h5>
	<spring:message code="attack.attacker.strenth" />:
</h5>
<p>
	<jstl:out value="${attackerStrength}" />
</p>
<br />

<h5>
	<spring:message code="attack.defendant.strenth" />
</h5>
<p>
	<jstl:out value="${defendantStrength}" />
</p>
<br />


	<jstl:choose>
		<jstl:when test="${attackerIsWinner}">
			<h5>
				<spring:message code="attack.resource.steal"/>:
			</h5>
			<p>
				TODO: RECURSOSSSSS
			</p>
		</jstl:when>
		
		<jstl:otherwise>
			<h5>
				<spring:message code="attack.resource.no.steal"/>
			</h5>
		</jstl:otherwise>
		
	</jstl:choose>

