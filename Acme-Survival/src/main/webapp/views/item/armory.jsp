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

<spring:message code="master.page.current.lang" var="currentLang" />

<acme:pagination requestURI="item/player/armory.do?page="
	pageNum="${pageNum}" page="${page}" />

<display:table name="items" id="item"
	requestURI="item/player/armory.do" class="displaytag">

	<display:column>
		<img class="forumImg" src="${item.tool.imageUrl}" />
	</display:column>

<spring:message code="item.name" var="nameTitle" />
	<display:column sortable = "true" title="${nameTitle}">
		<jstl:out value="${item.tool.name[currentLang]}" />
	</display:column>
	

	<spring:message code="item.description" var="descriptionTitle" />
	<display:column title="${descriptionTitle}">
		<jstl:out value="${item.tool.description[currentLang]}" />
	</display:column>

	
	<spring:message code="tool.strength" var="strengthTitle" />
	<display:column  sortable = "true" title="${strengthTitle}" property="tool.strength" />

	<spring:message code="tool.luck" var="luckTitle" />
	<display:column sortable = "true" title="${luckTitle}" property="tool.luck" />

	<spring:message code="tool.capacity" var="capacityTitle" />
	<display:column sortable = "true" title="${strengthTitle}" property="tool.capacity" />
	


	<display:column>
		<security:authorize access="hasRole('PLAYER')">
			<jstl:if test="${!(item.equipped)}">
				<acme:button
					url="item/player/remove.do?itemId=${item.id}"
					code="item.remove" />
			</jstl:if>
		</security:authorize>
	</display:column>

</display:table>


