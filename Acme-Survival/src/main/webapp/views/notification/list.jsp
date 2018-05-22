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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message code = "master.page.current.lang" var = "currentLang" />
<spring:message code="master.page.moment.format" var="formatMoment" />

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}?page="/>

<display:table name="notifications" id="notification"
	requestURI="${requestURI}"
	class="displayTag">

	<spring:message code="notification.moment" var="titleMoment"/>
	<display:column property="moment" title="${titleMoment}" sortable="true" format="${formatMoment}"/>


	<spring:message code="notification.title" var="titleTitle" />
	<display:column title="${titleTitle}">
		<jstl:out value = "${notification.title[currentLang]}"/>
	</display:column>
	
	<spring:message code="notification.body" var="titleBody" />
	<display:column title="${titleBody}" >
		<jstl:out value = "${notification.body[currentLang]}"/>
	</display:column>
	
	<spring:message code="notification.display" var="titleDislay" />
	<display:column title="${titleDislay}" sortable="false" >
		<acme:button url="notification/player/display.do?notificationId=${notification.id}" code="notification.display"/>
	</display:column>

</display:table>