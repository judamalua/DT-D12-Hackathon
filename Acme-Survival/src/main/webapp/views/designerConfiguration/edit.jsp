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

<form:form action="designerConfiguration/admin/edit.do"
	modelAttribute="designerConfiguration">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<p>
		<em><spring:message code="configuration.all.fields.required" /></em>
	</p>
	
	<div>
		<acme:textarea code="configuration.movingWood" path="movingWood" />
		<acme:textarea code="configuration.movingMetal" path="movingMetal" />
		<acme:textbox code="configuration.movingFood" path="movingFood" />
		<acme:textbox code="configuration.kmPerSecond" path="kmPerSecond" />
		<acme:textbox code="configuration.foodWastedPerSecond" path="foodWastedPerSecond" />
		<acme:textbox code="configuration.waterWastedPerSecond"
			path="waterWastedPerSecond" />
	</div>
	<div>
		<acme:textbox code="configuration.refugeRecoverTime" path="refugeRecoverTime" />
		<acme:textbox code="configuration.waterFactorSteal" path="waterFactorSteal" />
		<acme:textbox code="configuration.foodFactorSteal" path="foodFactorSteal" />
		<acme:textbox code="configuration.metalFactorSteal" path="metalFactorSteal" />
		<acme:textbox code="configuration.woodFactorSteal" path="woodFactorSteal" />
		<acme:textbox code="configuration.foodLostGatherFactor" path="foodLostGatherFactor" />
		<acme:textbox code="configuration.waterLostGatherFactor" path="waterLostGatherFactor" />
		<acme:textbox code="configuration.experiencePerMinute" path="experiencePerMinute" />
	</div>

	<acme:submit name="save" code="configuration.save" />

	<acme:cancel url="/"
		code="configuration.cancel" />


</form:form>
