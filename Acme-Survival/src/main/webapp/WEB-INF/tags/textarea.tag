<%--
 * textarea.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="customClass" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<jstl:if test="${required == null}">
	<jstl:set var="required" value="false" />
</jstl:if>
<jstl:if test="${customClass == null}">
	<jstl:set var="customClass" value="" />
</jstl:if>
<%-- Definition --%>

<div class="form-group">
	<div class="row">
	<div class="input-field col s3">
	<form:label path="${path}">
		<spring:message code="${code}" /><jstl:if test="${required}">*</jstl:if>
	</form:label>
	<form:textarea path="${path}" readonly="${readonly}" class="materialize-textarea ${customClass}" />
	<form:errors path="${path}" cssClass="error" />
	</div>
	</div>
</div>
