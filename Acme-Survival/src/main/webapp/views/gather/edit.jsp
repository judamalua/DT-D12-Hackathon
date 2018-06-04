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

<spring:message var="format" code="master.page.moment.format.out" />
<fmt:formatDate var="formatGatherStartDate" value="${gather.startDate}"
	pattern="${format}" />
<fmt:formatDate var="formatGatherEndMoment" value="${gather.endMoment}"
	pattern="${format}" />

<div class="form-group">
	<div class="row">
		<form:form id="form" action="gather/player/edit.do"
			modelAttribute="gather">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="location" />
			
			<br/>
			<jstl:if test="${isAttacking}">
				<div class="message">
					<spring:message code="gather.isAttacking" />
				</div>
			</jstl:if>
			<jstl:if test="${isMoving}">
				<div class="message">
					<spring:message code="gather.isAttacking" />
				</div>
			</jstl:if>
			<br/>
			<acme:select id="charId" code="gather.character" path="character"
				items="${characters}" itemLabel="fullName" />
			<br />
			<br />
			<br />
			<br />
			<br />
			<spring:message code="gather.startDate.estimated" />:
	<jstl:out value="${formatGatherStartDate}" />
			<br />

			<spring:message code="gather.endMoment.estimated" />:
	<jstl:out value="${formatGatherEndMoment}" />
			<br />
			<br />
			<div class="cleared-div">
				<jstl:if test="${!isMoving and !isAttacking}">
					<acme:submit name="save" code="gather.start" />
				</jstl:if>
				<acme:cancel url="map/player/display.do" code="gather.cancel" />
			</div>
		</form:form>
	</div>
</div>