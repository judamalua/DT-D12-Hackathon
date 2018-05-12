<%--
 * action-1.jsp
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

<form:form action="tool/designer/edit.do" modelAttribute="tool">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<p>
		<em><spring:message code="form.required.params" /></em>
	</p>

	<jstl:forEach items="${languages}" var="lang">
		<acme:textbox code="itemDesign.name_${lang}" path="name[${lang}]"
			required="true" />
	
	<acme:textarea code="itemDesign.description_${lang}" path="description[${lang}]"
		required="true" />
	</jstl:forEach>
	<acme:textbox code="itemDesign.imageUrl" path="imageUrl"
		required="true" />

	<jstl:if test="${tool}">
		<acme:textbox code="tool.strength" path="strength" required="true" />
		<acme:textbox code="tool.luck" path="luck" required="true" />
		<acme:textbox code="tool.capacity" path="capacity" required="true" />
	</jstl:if>
	<jstl:if test="${!tool}">
		<acme:textbox code="resource.water" path="water" required="true" />
		<acme:textbox code="resource.food" path="food" required="true" />
		<acme:textbox code="resource.metal" path="metal" required="true" />
		<acme:textbox code="resource.wood" path="wood" required="true" />
	</jstl:if>

	<acme:checkbox code="itemDesign.finalMode" path="finalMode"
		id="finalMode" />

	<acme:submit name="save" code="tool.save" />

	<jstl:if test="${tool.id!=0}">
		<acme:delete clickCode="tool.delete.message" name="delete"
			code="tool.delete" />
	</jstl:if>
	<acme:cancel url="itemDesign/designer/list.do" code="tool.cancel" />

</form:form>