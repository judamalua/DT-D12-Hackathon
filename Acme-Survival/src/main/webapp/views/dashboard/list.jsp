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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<ul class="collapsible popout" data-collapsible="accordion">

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.players.number" />
		</div>
		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out value="${numPlayers == \"null\" ? 0 : numPlayers}" />
				</p>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.characters.number" />
		</div>

		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out value="${numCharacters == \"null\" ? 0 : numCharacters}" />
				</p>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.roomDesign.number" />
		</div>

		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out
						value="${numRoomDesigns == \"null\" ? 0 : numRoomDesigns}" />
				</p>
			</span>
		</div>
	</li>



	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.location.number" />
		</div>

		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out value="${numLocations == \"null\" ? 0 : numLocations}" />
				</p>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.itemDesign.number" />
		</div>
		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out
						value="${numItemDesigns == \"null\" ? 0 : numItemDesigns}" />
				</p>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.event.number" />
		</div>
		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out value="${numEvents == \"null\" ? 0 : numEvents}" />
				</p>
			</span>
		</div>
	</li>
	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.threadsByActor" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="threadByActor" name="threadsByActor"
					requestURI="dashboard/admin/list.do" pagesize="${pagesize}">
					<spring:message var="titleActor" code="dashboard.actor.title" />
					<display:column title="${titleActor}">${threadByActor[0]}</display:column>
					<spring:message var="titleNumThreads" code="dashboard.numThreads" />
					<display:column title="${titleNumThreads}">${threadByActor[1]}</display:column>
				</display:table>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.ordersByPlayer" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="orderByPlayer" name="ordersByPlayer"
					requestURI="dashboard/admin/list.do" pagesize="${pagesize}">
					<spring:message var="titleActor" code="dashboard.actor.title" />
					<display:column title="${titleActor}">${orderByPlayer[0]}</display:column>
					<spring:message var="titleNumOrders" code="dashboard.numOrders" />
					<display:column title="${titleNumOrders}">${orderByPlayer[1]}</display:column>

				</display:table>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.attacksByShelter" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="attackByShelter"
					name="attacksByShelter" requestURI="dashboard/admin/list.do"
					pagesize="${pagesize}">
					<spring:message var="titleShelter" code="dashboard.shelter.title" />
					<display:column title="${titleShelter}">${attackByShelter[0]}</display:column>
					<spring:message var="titleNumAttacks" code="dashboard.numAttacks" />
					<display:column title="${titleNumAttacks}">${attackByRefuge[1]}</display:column>

				</display:table>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.defensesByShelter" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="defenseByShelter"
					name="defensesByShelter" requestURI="dashboard/admin/list.do"
					pagesize="${pagesize}">
					<spring:message var="titleShelter" code="dashboard.shelter.title" />
					<display:column title="${titleShelter}">${defenseByRefuge[0]}</display:column>
					<spring:message var="titleNumDefenses" code="dashboard.numDefenses" />
					<display:column title="${titleNumDefenses}">${defenseByRefuge[1]}</display:column>
				</display:table>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.roomsByShelter" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="roomPerShelter" name="roomsPerShelter"
					requestURI="dashboard/admin/list.do" pagesize="${pagesize}">

					<spring:message var="titleShelter" code="dashboard.shelter.title" />
					<display:column title="${titleShelter}">${roomPerShelter[0]}</display:column>
					<spring:message var="titleNumRooms" code="dashboard.numRooms" />
					<display:column title="${titleNumRooms}">${roomPerShelter[1]}</display:column>
				</display:table>
			</span>
		</div>
	</li>
</ul>