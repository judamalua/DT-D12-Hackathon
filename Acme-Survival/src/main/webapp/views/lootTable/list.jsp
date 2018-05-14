
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- This view lists final mode lootTables and discontinued lootTables -->

<spring:message code = "master.page.current.lang" var = "currentLang" />
<spring:message code="master.page.price.format" var="formatPrice" />

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}?page="/>

<display:table name="lootTables" id="lootTable"
	requestURI="${requestURI}"
	class="displayTag">


	<spring:message code="lootTable.name" var="name" />
	<spring:message code="lootTable.description" var="description" />
	<spring:message code="lootTable.items" var="item" />
	<spring:message code="lootTable.events" var="events" />
	<spring:message code="lootTable.locations" var="locations" />
	
	<display:column property="name" title="${name}" sortable="false" />
	<display:column property="description" title="${description}" sortable="false" />
	<display:column property="items" title="${fn:length(probabilityItems)}" sortable="false" />
	<display:column property="events" title="${fn:length(probabilityEvents)}" sortable="false" />
	<display:column property="locations" title="${fn:length(locations)}" sortable="false" />
	

</display:table>

<security:authorize access="hasRole('DESIGNER')">
	<!-- Checking if the principal is a designer , if so, he or she can create new lootTables -->
	<br />
	<acme:button url="lootTable/designer/create.do" code="lootTable.create"/>
</security:authorize>