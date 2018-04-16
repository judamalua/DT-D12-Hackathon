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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<ul class="collapsible popout" data-collapsible="accordion">

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="inventory.water" />
		</div>

		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out value="${inventory.water}"></jstl:out>
				</p>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="inventory.food" />
		</div>

		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out value="${inventory.food}"></jstl:out>
				</p>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="inventory.metal" />
		</div>

		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out value="${inventory.metal}"></jstl:out>
				</p>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="inventory.wood" />
		</div>

		<div class="collapsible-body">
			<span>
				<p class="element">
					<jstl:out value="${inventory.wood}"></jstl:out>
				</p>
			</span>
		</div>
	</li>

	<li class="dashboard-expander">
		<div class="collapsible-header">
			<spring:message code="inventory.capacity" />
		</div>

		<div class="ratio element">
			<div class="progress progress-striped active" aria-valuemin="0">
				<div class="bar" style="width: ${ratioCapacity*100}%;">
					<jstl:out value="${ratioCapacity*100}%" />
				</div>
			</div>
		</div>
	</li>
</ul>
