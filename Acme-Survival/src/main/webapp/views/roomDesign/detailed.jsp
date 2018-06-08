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

<h2>
	<jstl:out value="${roomDesign.name[currentLang]}" />
</h2>

<h4>
	<spring:message code="roomDesign.description" />
</h4>

<p>
	<jstl:out value="${roomDesign.description[currentLang]}" />
</p>

<jstl:if test = "${roomDesign[\"class\"].simpleName eq \"Barrack\"}">
	<p>
		<b><spring:message code="roomDesign.type" />:</b> <spring:message code = "roomDesign.type.barrack"/>
	</p>
</jstl:if>
<jstl:if test = "${roomDesign[\"class\"].simpleName eq \"Warehouse\"}">
	<p>
		<b><spring:message code="roomDesign.type" />:</b> <spring:message code = "roomDesign.type.warehouse"/>
	</p>
</jstl:if>
<jstl:if test = "${roomDesign[\"class\"].simpleName eq \"RestorationRoom\"}">
	<p>
		<b><spring:message code="roomDesign.type" />:</b> <spring:message code = "roomDesign.type.restorationRoom"/>
	</p>
</jstl:if>
<jstl:if test = "${roomDesign[\"class\"].simpleName eq \"ResourceRoom\"}">
	<p>
		<b><spring:message code="roomDesign.type" />:</b> <spring:message code = "roomDesign.type.resourceRoom"/>
	</p>
</jstl:if>

<%-- <p>
	<b><spring:message code="roomDesign.maxResistance" />:</b> <jstl:out value="${roomDesign.maxResistance}" />
</p> --%>

<p>
	<b><spring:message code="roomDesign.costWood" />:</b> <jstl:out value="${roomDesign.costWood}" />
</p>

<p>
	<b><spring:message code="roomDesign.costMetal" />:</b> <jstl:out value="${roomDesign.costMetal}" />
</p>

<p>
	<b><spring:message code="roomDesign.maxCapacityCharacters" />:</b> <jstl:out value="${roomDesign.maxCapacityCharacters}" />
</p>

<!-- Showing each room attributes -->

<jstl:if test = "${roomDesign[\"class\"].simpleName eq \"Barrack\"}">
	<p>
		<b><spring:message code="roomDesign.type.barrack.characterCapacity" />:</b> <jstl:out value="${roomDesign.characterCapacity}" />
	</p>
</jstl:if>

<jstl:if test = "${roomDesign[\"class\"].simpleName eq \"RestorationRoom\"}">
	<p>
		<b><spring:message code="roomDesign.type.restorationRoom.health" />:</b> <jstl:out value="${roomDesign.health}" />
	</p>
	
	<p>
		<b><spring:message code="roomDesign.type.restorationRoom.food" />:</b> <jstl:out value="${roomDesign.food}" />
	</p>
	
	<p>
		<b><spring:message code="roomDesign.type.restorationRoom.water" />:</b> <jstl:out value="${roomDesign.water}" />
	</p>
</jstl:if>

<jstl:if test = "${roomDesign[\"class\"].simpleName eq \"ResourceRoom\"}">
	<p>
		<b><spring:message code="roomDesign.type.resourceRoom.water" />:</b> <jstl:out value="${roomDesign.water}" />
	</p>
	
	<p>
		<b><spring:message code="roomDesign.type.resourceRoom.food" />:</b> <jstl:out value="${roomDesign.food}" />
	</p>
	
	<p>
		<b><spring:message code="roomDesign.type.resourceRoom.metal" />:</b> <jstl:out value="${roomDesign.metal}" />
	</p>
	
	<p>
		<b><spring:message code="roomDesign.type.resourceRoom.wood" />:</b> <jstl:out value="${roomDesign.wood}" />
	</p>
</jstl:if>

<jstl:if test = "${roomDesign[\"class\"].simpleName eq \"Warehouse\"}">
	<p>
		<b><spring:message code="roomDesign.type.warehouse.itemCapacity" />:</b> <jstl:out value="${roomDesign.itemCapacity}" />
	</p>
</jstl:if>
