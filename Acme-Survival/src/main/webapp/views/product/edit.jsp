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

<form:form id = "form" action="product/manager/edit.do" modelAttribute ="product">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textarea code="product.name_en" path="name_en" required = "true"/>
	<acme:textarea code="product.name_es" path="name_es" required = "true"/>
	
	<acme:textarea code="product.description_en" path="description_en" required = "true"/>
	<acme:textarea code="product.description_es" path="description_es" required = "true"/>
	
	<acme:textbox code="product.price" path="price" required="true" placeholder="1.50"/> <p>ADD PRICE FORMAT</p>
	
	<acme:submit name="save" code="product.save"/>
		
	<jstl:if test="${product.id!=0}">
		<acme:delete clickCode="product.confirm.delete" name="delete" code="product.delete"/>
	</jstl:if>
	<acme:cancel url="product/manager/list.do" code="product.cancel"/>
</form:form>
