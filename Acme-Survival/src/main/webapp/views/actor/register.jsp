<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" src="scripts/checkboxTermsAndConditions.js"></script>

<div class="row">
<p><em><spring:message code = "form.required.params"/></em></p>

<form:form id = "form" action="${actionURL}" modelAttribute ="actor" class="col s12">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	
	
	<acme:textbox code="actor.name" path="name" required="true"/>
	
	<acme:textbox code="actor.surname" path="surname" required="true"/>
	
	<acme:textbox code="actor.birthDate" path="birthDate" placeholder="dd/MM/yyyy" required="true"/>
	
	<acme:textbox code="actor.avatar" path="avatar"/>
		
	<acme:textbox code="actor.email" path="email" required="true"/>
	
	<acme:textbox code="actor.phoneNumber" path="phoneNumber"/>
	

	<acme:textbox code="actor.username" path="userAccount.username" required="true"/>

	<acme:password code="actor.password" path="userAccount.password" required="true"/>
	
	<acme:confirmPassword name="confirmPassword" code="actor.confirm.password" required = "true"/>
	
	<p>
		<input type="checkbox" name="check" id="check">
		<label for="check"><spring:message code="register.login.terms&conditions1"/><a href = "law/termsAndConditions.do" target="_blank"><spring:message code="register.login.terms&conditions2"/></a></label>
	</p>
	

	<div>
	<acme:submit name="save" code="actor.save"/>
	<acme:cancel url="/" code="actor.cancel" />
	</div>
	

</form:form>
</div>
