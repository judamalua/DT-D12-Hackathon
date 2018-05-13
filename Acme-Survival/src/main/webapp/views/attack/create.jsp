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

<form:form action="attack/player/create.do" modelAttribute="attack">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="defendant" />
	
	<spring:message code="attack.attacker"/>:
	<jstl:out value="${attack.attacker}"/>
	<br/>
	
	<spring:message code="attack.defendant"/>:
	<jstl:out value="${attack.defendant}"/>
	<br/>
	
	<spring:message code="attack.startDate.estimated"/>:
	<jstl:out value="${attack.startDate}"/>
	<br/>

	<spring:message code="attack.endMoment.estimated"/>:
	<jstl:out value="${attack.endMoment}"/>
	<br/>
	
	<acme:submit name="save" code="attack.start" />
	
	<button type="button" class="btn" onclick="javascript: window.history.back()" >
		<spring:message code="attack.cancel" />
	</button>
	

</form:form>