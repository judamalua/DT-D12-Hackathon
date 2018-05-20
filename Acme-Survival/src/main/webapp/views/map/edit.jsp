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

<security:authorize access="hasRole('DESIGNER')">
	<script src="scripts/map/designerMap.js"></script>
</security:authorize>

<div id="mapElements" hidden="true"></div>
<div class="row">
	<div id="map" style="height: 400px;"></div>
</div>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBe0wmulZvK1IM3-3jIUgbxt2Ax_QOVW6c&callback=initMap">
	
</script>

<p>
	<em><spring:message code="form.required.params" /></em>
</p>

<form:form id="form" action="location/designer/edit.do"
	modelAttribute="location">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="finalMode" />

	<jstl:forEach items="${languages}" var="lang">
		<acme:textboxMap errorPath="name" code="location.name_${lang}"
			path="name[${lang}]" required="true" />
	</jstl:forEach>

	<acme:textbox code="location.point_a" path="point_a" readonly="true" required="true" />

	<acme:textbox code="location.point_b" path="point_b" readonly="true" required="true" />

	<acme:textbox code="location.point_c" path="point_c" readonly="true" required="true" />

	<acme:textbox code="location.point_d" path="point_d" readonly="true" required="true" />

	<acme:select id="lootId" code="location.lootTable" path="lootTable" items="${lootTables}" itemLabel="name" />
	
	<acme:submit name="save" code="location.save" />
	<acme:delete clickCode="location.confirm.saveFinal" name="saveFinal" code="location.saveFinal" />
	<jstl:if test="${location.id!= 0}">
	<jstl:if test="${location.finalMode== false}">
	<acme:delete clickCode="location.confirm.delete"
		name="delete" code="location.delete" />
			</jstl:if>	
	</jstl:if>	
	<acme:cancel url="location/designer/display.do" code="location.cancel" />
</form:form>