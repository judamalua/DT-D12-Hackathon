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
<div class="form-group">
	<div class="row">
		<form:form action="move/player/confirm.do" modelAttribute="move">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="location" />
			<br />
			<br />
			<jstl:if
				test="${(message eq \"move.confirm\") or (message eq \"move.resources.error\")}">
				<div>
					<b><spring:message code="move.requiredFood" /></b>: <strong><jstl:out
							value="${requiredFood}" /></strong>
				</div>
				<div>
					<b><spring:message code="move.requiredWater" /></b>: <strong><jstl:out
							value="${requiredWater}" /></strong>
				</div>
				<div>
					<b><spring:message code="move.requiredMetal" /></b>: <strong><jstl:out
							value="${requiredMetal}" /></strong>
				</div>
				<div>
					<b><spring:message code="move.requiredWood" /></b>: <strong><jstl:out
							value="${requiredWood}" /></strong>
				</div>
			</jstl:if>
			<br />
			<jstl:if test="${!error and !isAttacking}">
				<acme:submit name="confirm" code="move.save" />
			</jstl:if>

			<acme:cancel url="shelter/player/display.do" code="move.cancel" />
		</form:form>
	</div>
</div>
