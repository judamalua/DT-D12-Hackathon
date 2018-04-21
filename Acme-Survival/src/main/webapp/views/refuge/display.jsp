<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Variables -->
<spring:message code="master.page.moment.format" var="formatDate" />
<spring:message var="format" code="master.page.moment.format.out" />
<spring:message var="lang" code="master.page.current.lang" />

<br />
<h2>
	<jstl:out value="${refuge.name}" />
</h2>
<br />

<strong> <spring:message code="refuge.player" />: <a
	href="actor/display.do?actorId=${refuge.player.id}"><jstl:out
			value="${refuge.player.name}" /></a>
</strong>
<br />

<strong> <spring:message code="refuge.momentOfCreation" />: <fmt:formatDate
		value="${refuge.momentOfCreation}" pattern="${format}" />
</strong>
<br />

<jstl:if test="${owner}">
	<acme:button url="refuge/player/edit.do?refugeId=${refuge.id}"
		code="refuge.edit" />
</jstl:if>
<!-- Only a player who knows the refugee can display this information -->
<security:authorize access="hasRole('PLAYER')">
	<jstl:if test="${knowRefuge or owner}">
		<strong> <spring:message code="refuge.location" />
		</strong>
		<iframe class="map"
			src="https://www.google.com/maps/embed/v1/search?q=${refuge.gpsCoordinates}&key=AIzaSyBe0wmulZvK1IM3-3jIUgbxt2Ax_QOVW6c"></iframe>
		<br />

		<strong> <spring:message code="refuge.room" />
		</strong>
		<br />
		<acme:pagination page="${pageRoom}" pageNum="${pageNumRoom}"
			requestURI="refuge/player/display.do?refugeId=${refuge.id}&page=" />
		<display:table name="${rooms}" id="room"
			requestURI="refuge/display.do?refugeId=${refuge.id}">

			<spring:message code="refuge.room.name" var="nameRoomTitle" />
			<display:column title="${nameRoomTitle}">
				<jstl:if test="${lang==\"es\"}">
					<jstl:out value="${room.roomDesign.name_es}" />
					<br />
					<jstl:out value="${room.roomDesign.description_es}" />
				</jstl:if>
				<jstl:if test="${lang==\"en\"}">
					<jstl:out value="${room.roomDesign.name_en}" />
					<br />
					<jstl:out value="${room.roomDesign.description_en}" />
				</jstl:if>
			</display:column>

			<spring:message code="refuge.room.resistance"
				var="resistanceRoomTitle" />
			<display:column title="${resistanceRoomTitle}">
				<div class="ratio element">
					<div class="progress progress-striped active" aria-valuemin="0">
						<div class="bar"
							style="width: ${(room.resistance/room.roomDesign.maxResistance)*100}%;">
							<jstl:out
								value="${(room.resistance/room.roomDesign.maxResistance)*100}%" />
						</div>
					</div>
				</div>
			</display:column>

			<display:column>
				<jstl:if test="${owner}">
					<a href="room/player/delete.do?roomId=${room.id}">
						<button class="btn"
							onclick="return confirm('<spring:message code="refuge.room.delete.confirm"/>')">
							<spring:message code="refuge.room.delete" />
						</button>
					</a>
				</jstl:if>
			</display:column>
		</display:table>
		<jstl:if test="${owner}">
			<acme:button url="room/player/create.do" code="refuge.room.create" />
		</jstl:if>
	</jstl:if>
</security:authorize>