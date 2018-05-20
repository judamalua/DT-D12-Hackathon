<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<spring:message code = "master.page.current.lang" var = "currentLang" />

<security:authorize access="hasRole('PLAYER')">
	<script src="scripts/map/playerMap.js"></script>
</security:authorize>
<security:authorize access="hasRole('DESIGNER')">
	<script src="scripts/map/designerMap.js"></script>
</security:authorize>

<div id="mapElements" hidden="true"></div>
<div class="row">
	<div id="map" style="height: 800px;"></div>
</div>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?language=${currentLang}&key=AIzaSyBe0wmulZvK1IM3-3jIUgbxt2Ax_QOVW6c&callback=initMap">
	
</script>

<security:authorize access="hasRole('DESIGNER')">
	<acme:button url="location/designer/create.do" code="location.create"/>
</security:authorize>
