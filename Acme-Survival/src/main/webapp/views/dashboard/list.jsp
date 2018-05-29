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
					<jstl:out value="${numPlayers == \"null\" ? 0 : numPlayers}"/>
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
					<jstl:out value="${numCharacters == \"null\" ? 0 : numCharacters}"/>
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
					<jstl:out value="${numRoomDesigns == \"null\" ? 0 : numRoomDesigns}"/>
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
					<jstl:out value="${numLocations == \"null\" ? 0 : numLocations}"/>
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
						value="${numItemDesigns == \"null\" ? 0 : numItemDesigns}"/>
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


	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.threadsByActor" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="threadByActor" name="threadsByActor"
					requestURI="dashboard/admin/list.do" pagesize="${pagesize}">
					<jstl:set var="string" value="${fn:split(threadByActor, ',')}" />
					<spring:message var="titleActor" code="dashboard.actor.title" />
					<display:column title="${titleActor}">${threadByActor.toString()}</display:column>
					<spring:message var="titleNumThreads" code="dashboard.numThreads" />
					<display:column title="${titleNumThreads}">${string}</display:column>
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
					<jstl:set var="string" value="${fn:split(orderByPlayer, ',')}" />
					<spring:message var="titleActor" code="dashboard.actor.title" />
					<display:column title="${titleActor}">${string[0]}</display:column>
					<spring:message var="titleNumOrders" code="dashboard.numOrders" />
					<display:column title="${titleNumOrders}">${string[1]}</display:column>

				</display:table>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.attacksByRefuge" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="attackByRefuge"
					name="attacksByRefuge" requestURI="dashboard/admin/list.do"
					pagesize="${pagesize}">
					<jstl:set var="string" value="${fn:split(attackByRefuge, ',')}" />
					<spring:message var="titleRefuge" code="dashboard.refuge.title" />
					<display:column title="${titleRefuge}">${string[0]}</display:column>
					<spring:message var="titleNumAttacks" code="dashboard.numAttacks" />
					<display:column title="${titleNumAttacks}">${string[1]}</display:column>

				</display:table>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.defensesByRefuge" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="defenseByRefuge"
					name="defensesByRefuge" requestURI="dashboard/admin/list.do"
					pagesize="${pagesize}">
					<jstl:set var="string" value="${fn:split(defenseByRefuge, ',')}" />
					<spring:message var="titleRefuge" code="dashboard.refuge.title" />
					<display:column title="${titleRefuge}">${string[0]}</display:column>
					<spring:message var="titleNumDefenses" code="dashboard.numDefenses" />
					<display:column title="${titleNumDefenses}">${string[1]}</display:column>
				</display:table>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="dashboard.roomsByRefuge" />
		</div>

		<div class="collapsible-body">

			<span> <display:table id="roomPerRefuge" name="roomsPerRefuge"
					requestURI="dashboard/admin/list.do" pagesize="${pagesize}">
					<jstl:set var="string" value="${fn:split(roomPerRefuge, ',')}" />
					<spring:message var="titleRefuge" code="dashboard.refuge.title" />
					<display:column title="${titleRefuge}">${string[0]}</display:column>
					<spring:message var="titleNumRooms" code="dashboard.numRooms" />
					<display:column title="${titleNumRooms}">${string[1]}</display:column>
				</display:table>
			</span>
		</div>
	</li>
</ul>