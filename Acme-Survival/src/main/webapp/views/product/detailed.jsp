<%--
 * action-2.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<!-- Variable declaration -->

<spring:message code = "master.page.current.lang" var = "currentLang" />


<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}"
	pattern="yyyy-MM-dd HH:mm" />
<spring:message var="format" code="master.page.moment.format.out" />
<spring:message code="master.page.moment.format" var="formatMoment" />


<!-- Display -->
<%-- <jstl:if test="${rendezvous.pictureUrl != \"\"}">
	<div class="parallax-container">
		<div class="parallax">
			<img src="${rendezvous.pictureUrl}">
		</div>
	</div>
</jstl:if> --%>
<h2>
<jstl:if test="${currentLang == \"en\"}">
	<jstl:out value="${product.name_en}" />
</jstl:if>

<jstl:if test="${currentLang == \"es\"}">
	<jstl:out value="${product.name_es}" />
</jstl:if>
</h2>
<br>
<h4>
	<spring:message code="product.description" />
</h4>
<p>
	<jstl:if test="${currentLang == \"en\"}">
	<jstl:out value="${product.description_en}" />
</jstl:if>

<jstl:if test="${currentLang == \"es\"}">
	<jstl:out value="${product.description_es}" />
</jstl:if>
</p>
