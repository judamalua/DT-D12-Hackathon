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

<form:form action="configuration/admin/edit.do"
	modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<p>
		<em><spring:message code="configuration.all.fields.required" /></em>
	</p>

	<acme:textbox code="configuration.pagesize" path="pageSize" required="true"/>
	
	<spring:message code="master.page.commas.parameter" />
	<acme:textarea code="configuration.languages" path="languages" required="true"/>

	<div class="row">
		<div class="col s6">
			<div class="row">
				<div class="input-field col s3">
					<form:input id="businessNameFirst" type="text"
						path="businessNameFirst" />
					<label for="businessNameFirst"><spring:message
							code="configuration.businessName" />*</label>
				</div>
				<div class="input-field col s3">
					<i class="material-icons prefix">people</i>
					<form:input id="businessNameLast" type="text"
						path="businessNameLast" />
				</div>
			</div>
		</div>
	</div>

	<form:errors path="businessNameFirst" cssClass="error" />
	<br />
	<form:errors path="businessNameLast" cssClass="error" />
	<br />
	<br />

	<acme:submit name="save" code="configuration.save" />

	<acme:cancel url="configuration/admin/list.do"
		code="configuration.cancel" />


</form:form>
