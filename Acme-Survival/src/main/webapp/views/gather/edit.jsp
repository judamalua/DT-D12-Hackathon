<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message var="format" code="master.page.moment.format.out" />
<fmt:formatDate var="formatgatherStartDate"
	value="${gather.startDate}" pattern="${format}" />
<fmt:formatDate var="formatgatherEndMoment"
	value="${gather.endMoment}" pattern="${format}" />
	
<form:form id = "form" action="gather/player/edit.do" modelAttribute ="gather">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="location"/>
	
	<acme:select code="gather.character" path="character" items="${characters}" itemLabel="fullName"/>
	<br />

	<spring:message code="gather.startDate.estimated"/>:
	<jstl:out value="${formatgatherStartDate}"/>
	<br/>

	<spring:message code="gather.endMoment.estimated"/>:
	<jstl:out value="${formatgatherEndMoment}"/>
	<br/>

	<div class="cleared-div">
	<acme:submit name="save" code="gather.start"/>
	
	<button type="button" class="btn" onclick="javascript: window.history.back()" >
		<spring:message code="gather.cancel" />
	</button>
	</div>
</form:form>
