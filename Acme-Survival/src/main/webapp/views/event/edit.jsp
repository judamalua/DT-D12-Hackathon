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

<form:form id = "form" action="event/designer/edit.do" modelAttribute ="event">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textarea code="event.name_en" path="name_en" required = "true"/>
	<acme:textarea code="event.name_es" path="name_es" required = "true"/>
	
	<acme:textarea code="event.description_en" path="description_en" required = "true"/>
	<acme:textarea code="event.description_es" path="description_es" required = "true"/>
	
	<acme:textarea code="event.health" path="health" required = "true"/>
	<acme:textarea code="event.food" path="food" required = "true"/>
	<acme:textarea code="event.water" path="water" required = "true"/>

	<acme:checkbox id="findCharacter" code="event.findCharacter" path="findCharacter" />
	<acme:checkbox id="finalMode" code="event.finalMode" path="finalMode" />
	
	<acme:submit name="save" code="event.save"/>
		
	<jstl:if test="${event.id!=0}">
		<acme:delete clickCode="event.confirm.delete" name="delete" code="event.delete"/>
	</jstl:if>
	<acme:cancel url="event/designer/list.do" code="event.cancel"/>
</form:form>
