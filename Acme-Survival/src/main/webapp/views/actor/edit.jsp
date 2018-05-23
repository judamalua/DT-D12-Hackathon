<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Selecting request uri -->
<security:authorize access="hasRole('ADMIN')">
	<jstl:set var="requestURI" value="actor/admin/edit.do"/>
</security:authorize>
<security:authorize access="hasRole('PLAYER')">
	<jstl:set var="requestURI" value="actor/player/edit.do"/>
</security:authorize>
<security:authorize access="hasRole('DESIGNER')">
	<jstl:set var="requestURI" value="actor/designer/edit.do"/>
</security:authorize>
<security:authorize access="hasRole('MODERATOR')">
	<jstl:set var="requestURI" value="actor/moderator/edit.do"/>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
	<jstl:set var="requestURI" value="actor/manager/edit.do"/>
</security:authorize>


<form:form id = "form" action="${requestURI}" modelAttribute ="actor">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<p><em><spring:message code = "form.required.params"/></em></p>
	
	<acme:textbox code="actor.name" path="name" required = "true"/>
	
	<acme:textbox code="actor.surname" path="surname" required = "true"/>
	
	<acme:textbox code="actor.phoneNumber" path="phoneNumber"/>
	
	<acme:imageUpload code="actor.avatar" path="avatar"/>
	
	<acme:textbox code="actor.email" path="email" required = "true"/>
	
	<acme:textbox code="actor.birthDate" path="birthDate" required = "true" placeholder = "dd/MM/yyyy"/>
	
	<acme:submit name="save" code="actor.save"/>
	
	<acme:cancel url="/" code="actor.cancel"/>

</form:form>
