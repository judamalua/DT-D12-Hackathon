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
<div class="inventory">
	<div class="inventoryElm">
		<i class="material-icons">local_pizza</i>
		<spring:message code="inventory.food" />
		:
		<jstl:out value="${inventory.food}" />
	</div>
	<div class="inventoryElm">
		<i class="material-icons">local_drink</i>
		<spring:message code="inventory.water" />
		:
		<jstl:out value="${inventory.water}" />
	</div>
	<div class="inventoryElm">
		<i class="material-icons">toys</i>
		<spring:message code="inventory.metal" />
		:
		<jstl:out value="${inventory.metal}" />
	</div>
	<div class="inventoryElm">
		<i class="material-icons">spa</i>
		<spring:message code="inventory.wood" />
		:
		<jstl:out value="${inventory.wood}" />
	</div>
</div>
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
	<div class="characterContainer">
		<h3>
			<spring:message code="refuge.character.list" />
		</h3>
		<spring:message code="refuge.capacity" />: <jstl:out value="${characters.length}/${capacity/characters.length}"/>
		<jstl:forEach items="${characters}" var="character">
			<div class="character">
				<div class="characterName">
					<jstl:out value="${character.fullName}" />
				</div>
				<a href="character/player/display.do?characterId=${character.id}">
					<div class="characterImage"
						style="height: 200px; width: 200px; float: left;"></div>
				</a> <br />
				<jstl:if test="${character.currentlyInGatheringMission}">
					<i class="material-icons"> directions_walk </i>
					<spring:message code="refuge.character.gather" />
				</jstl:if>
				<br />
				<jstl:if test="${character.roomEntrance!=null and !character.currentlyInGatheringMission}">
					<i class="material-icons"> hotel </i>
					<spring:message code="refuge.in" />: ${character.room.roomDesign.name[lang]}
				</jstl:if>
				<jstl:if test="${character.currentWater<10}">
					<div class="error">
						<spring:message code="refuge.thirsty" />
					</div>
				</jstl:if>
				<jstl:if test="${character.currentFood<10}">
					<div class="error">
						<spring:message code="refuge.hungry" />
					</div>
				</jstl:if>
				<jstl:if
					test="${character.currentHealth<100 and character.currentHealth>=50}">
					<div class="error">
						<spring:message code="refuge.wounded" />
					</div>
				</jstl:if>
				<jstl:if
					test="${character.currentHealth<50 and character.currentHealth>=25}">
					<div class="error">
						<spring:message code="refuge.agonizing" />
					</div>
				</jstl:if>
				<jstl:if test="${character.currentHealth<25}">
					<div class="error">
						<spring:message code="refuge.ligth" />
					</div>
				</jstl:if>
				<div class="characterGenre" hidden="true">
					<jstl:if test="${character.male}">Male</jstl:if>
					<jstl:if test="${!character.male}">Female</jstl:if>
				</div>
			</div>
		</jstl:forEach>
	</div>
	<br />

	<div class="refugeRooms">
		<strong> <spring:message code="refuge.room" />
		</strong> <br />
		<acme:pagination page="${pageRoom}" pageNum="${pageNumRoom}"
			requestURI="refuge/player/display.do?refugeId=${refuge.id}&page=" />
		<display:table name="${rooms}" id="room"
			requestURI="refuge/display.do?refugeId=${refuge.id}">

			<spring:message code="refuge.room.name" var="nameRoomTitle" />
			<display:column title="${nameRoomTitle}">
				<jstl:out value="${room.roomDesign.name[lang]}" />
				<br />
				<jstl:out value="${room.roomDesign.description[lang]}" />
			</display:column>

			<spring:message code="refuge.room.resistance"
				var="resistanceRoomTitle" />
			<display:column title="${resistanceRoomTitle}">
				<jstl:out
					value="${(room.resistance/room.roomDesign.maxResistance)*100}%" />
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
	</div>
</security:authorize>
