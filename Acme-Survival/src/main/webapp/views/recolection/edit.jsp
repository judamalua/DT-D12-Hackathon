<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<p><em><spring:message code = "form.required.params"/></em></p>

<form:form id = "form" action="recolection/player/edit.do" modelAttribute ="recolection">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="location"/>
	
	<acme:select code="recolection.character" path="character" items="${characters}" itemLabel="name"/>
	<br/>
	
	<spring:message code="recolection.startDate.estimated"/>:
	<jstl:out value="${recolection.startDate}"/>
	<br/>

	<spring:message code="recolection.endMoment.estimated"/>:
	<jstl:out value="${recolection.endMoment}"/>
	<br/>

	<acme:submit name="save" code="recolection.start"/>
		
	
	<button type="button" class="btn" onclick="javascript: window.history.back()" >
		<spring:message code="recolection.cancel" />
	</button>
</form:form>
