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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(document).ready(function() {
		setInterval(function() {
			$.get("inventory/player/update.do", function(data, status) {
				var values = data.split(",");
				$("#food").text(values[0]);
				$("#water").text(values[1]);
				$("#metal").text(values[2]);
				$("#wood").text(values[3]);
			});
		}, 60000);
	});
</script>

<!-- Variables -->
<spring:message code="master.page.moment.format" var="formatDate" />
<spring:message var="format" code="master.page.moment.format.out" />
<spring:message var="lang" code="master.page.current.lang" />
<div class="inventory">
	<div class="inventoryElm">
		<i class="material-icons">local_pizza</i>
		<spring:message code="inventory.food" />
		:
		<div id="food">
			<jstl:out value="${inventory.food}" />
			/
			<jstl:out value="${inventory.foodCapacity}"></jstl:out>
		</div>
	</div>
	<div class="inventoryElm">
		<i class="material-icons">local_drink</i>
		<spring:message code="inventory.water" />
		:
		<div id="water">
			<jstl:out value="${inventory.water}" />
			/
			<jstl:out value="${inventory.waterCapacity}" />
		</div>
	</div>
	<div class="inventoryElm">
		<i class="material-icons">toys</i>
		<spring:message code="inventory.metal" />
		:
		<div id="metal">
			<jstl:out value="${inventory.metal}" />
			/
			<jstl:out value="${inventory.metalCapacity}" />
		</div>
	</div>
	<div class="inventoryElm">
		<i class="material-icons">spa</i>
		<spring:message code="inventory.wood" />
		:
		<div id="wood">
			<jstl:out value="${inventory.wood}" />
			/
			<jstl:out value="${inventory.woodCapacity}" />
		</div>
	</div>
</div>
<br />
<br />
<h2>
	<jstl:out value="${shelter.name}" />
</h2>
<br />
<strong> <spring:message code="shelter.player" />: <a
	href="actor/display.do?actorId=${shelter.player.id}"><jstl:out
			value="${shelter.player.name}" /></a>
</strong>
<br />
<strong> <spring:message code="shelter.momentOfCreation" />: <fmt:formatDate
		value="${shelter.momentOfCreation}" pattern="${format}" />
</strong>
<br />

<!-- Only a player who knows the sheltere can display this information -->
<security:authorize access="hasRole('PLAYER')">
	<div class="characterContainer">
		<h3>
			<spring:message code="shelter.character.list" />
		</h3>
		<spring:message code="shelter.capacity" />
		:
		<jstl:out
			value="${fn:length(characters)}/${characterCapacity+fn:length(characters)}" />
		<br /> <br />
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

					<spring:message code="shelter.character.gather" />
				</jstl:if>
				<br />
				<jstl:if
					test="${character.roomEntrance!=null and !character.currentlyInGatheringMission}">
					<i class="material-icons"> hotel </i>
					<spring:message code="shelter.in" />: ${character.room.roomDesign.name[lang]}
				</jstl:if>
				<jstl:if test="${character.currentWater<10}">
					<div class="message">
						<spring:message code="shelter.thirsty" />
					</div>
				</jstl:if>
				<jstl:if test="${character.currentFood<10}">
					<div class="message">
						<spring:message code="shelter.hungry" />
					</div>
				</jstl:if>
				<jstl:if
					test="${character.currentHealth<100 and character.currentHealth>=50}">
					<div class="message">
						<spring:message code="shelter.wounded" />
					</div>
				</jstl:if>
				<jstl:if
					test="${character.currentHealth<50 and character.currentHealth>=25}">
					<div class="message">
						<spring:message code="shelter.agonizing" />
					</div>
				</jstl:if>
				<jstl:if test="${character.currentHealth<25}">
					<div class="message">
						<spring:message code="shelter.ligth" />
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

	<div class="shelterRooms">
		<jstl:if test="${fn:length(rooms)>0}">
			<h3>
				<spring:message code="shelter.room" />
			</h3>
			<br />
			<acme:pagination page="${pageRoom}" pageNum="${pageNumRoom}"
				requestURI="shelter/player/display.do?shelterId=${shelter.id}&pageRoom=" />
			<display:table name="${rooms}" id="room"
				requestURI="shelter/display.do?shelterId=${shelter.id}">

				<spring:message code="shelter.room.name" var="nameRoomTitle" />
				<display:column title="${nameRoomTitle}">
					<jstl:out value="${room.roomDesign.name[lang]}" />
					<br />
					<jstl:out value="${room.roomDesign.description[lang]}" />
				</display:column>

				<display:column title="">
					<jstl:if
						test="${room.roomDesign[\"class\"].simpleName eq \"Barrack\"}">
						<spring:message code="inventory.capacity" />: +${room.roomDesign.characterCapacity}
				</jstl:if>
					<jstl:if
						test="${room.roomDesign[\"class\"].simpleName eq \"Warehouse\"}">
						<spring:message code="room.itemCapacity" />: +${room.roomDesign.itemCapacity}
				</jstl:if>
					<jstl:if
						test="${room.roomDesign[\"class\"].simpleName eq \"ResourceRoom\"}">
						<jstl:if test="${room.roomDesign.food>0}">
							<spring:message code="room.food" />: +${room.roomDesign.food}/min
						</jstl:if>
						<jstl:if test="${room.roomDesign.water>0}">
							<spring:message code="room.water" />: +${room.roomDesign.water}/min
						</jstl:if>
						<jstl:if test="${room.roomDesign.metal>0}">
							<spring:message code="room.metal" />: +${room.roomDesign.metal}/min
						</jstl:if>
						<jstl:if test="${room.roomDesign.wood>0}">
							<spring:message code="room.wood" />: +${room.roomDesign.wood}/min
						</jstl:if>
					</jstl:if>
					<jstl:if
						test="${room.roomDesign[\"class\"].simpleName eq \"RestorationRoom\"}">
						<jstl:if test="${room.roomDesign.health>0}">
							<spring:message code="room.health" />: +${room.roomDesign.health}/min
						</jstl:if>
						<jstl:if test="${room.roomDesign.food>0}">
							<spring:message code="room.restored.food" />: +${room.roomDesign.food}/min
						</jstl:if>
						<jstl:if test="${room.roomDesign.water>0}">
							<spring:message code="room.restored.water" />: +${room.roomDesign.water}/min
						</jstl:if>
					</jstl:if>
				</display:column>

				<display:column>
					<jstl:if test="${owner}">
						<a href="room/player/delete.do?roomId=${room.id}">
							<button class="btn"
								onclick="return confirm('<spring:message code="shelter.room.delete.confirm"/>')">
								<spring:message code="shelter.room.delete" />
							</button>
						</a>
					</jstl:if>
				</display:column>
			</display:table>
		</jstl:if>
		<br />
		<jstl:if test="${owner}">
			<acme:button url="room/player/create.do" code="shelter.room.create" />
		</jstl:if>
	</div>


</security:authorize>

