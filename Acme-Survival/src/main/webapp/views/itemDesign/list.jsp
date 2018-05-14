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
		
<acme:pagination requestURI="itemDesign/designer/list?page="
	pageNum="${pageNum}" page="${page}" />

<display:table name="itemDesigns" id="itemDesign"
	requestURI="${requestURI}" class="displaytag">

	<display:column>
		<img class="forumImg" src="${itemDesign.imageUrl}" />
	</display:column>

	<spring:message code="itemDesign.name" var="nameTitle" />
	<display:column title="${nameTitle}">
		<jstl:out value="${itemDesign.name[currentLang]}" />
	</display:column>

	<spring:message code="itemDesign.description" var="descriptionTitle" />
	<display:column title="${descriptionTitle}">
		<jstl:out value="${itemDesign.description[currentLang]}" />
	</display:column>

	<jstl:if test="${!tool}">
		<spring:message code="resource.water" var="waterTitle" />
		<display:column title="${strengthTitle}" property="water" />

		<spring:message code="resource.food" var="foodTitle" />
		<display:column title="${foodTitle}" property="food" />

		<spring:message code="resource.wood" var="woodTitle" />
		<display:column title="${woodTitle}" property="wood" />

		<spring:message code="resource.metal" var="metalTitle" />
		<display:column title="${metalTitle}" property="metal" />
	</jstl:if>
	<jstl:if test="${tool}">
		<spring:message code="tool.strength" var="strengthTitle" />
		<display:column title="${strengthTitle}" property="strength" />

		<spring:message code="tool.luck" var="luckTitle" />
		<display:column title="${luckTitle}" property="luck" />

		<spring:message code="tool.capacity" var="capacityTitle" />
		<display:column title="${strengthTitle}" property="capacity" />
	</jstl:if>

	<display:column>
		<jstl:if test="${tool}">
			<i class="material-icons">build</i>
		</jstl:if>
		<jstl:if test="${!tool}">
			<i class="material-icons">restaurant_menu</i>
		</jstl:if>
	</display:column>

	<display:column>
		<security:authorize access="hasRole('DESIGNER')">
			<acme:button
				url="itemDesign/designer/edit.do?itemDesignId=${itemDesign.id}&tool=${tool}"
				code="itemDesign.edit" />
		</security:authorize>
	</display:column>

</display:table>

<security:authorize access="hasRole('DESIGNER')">
	<acme:button url="itemDesign/designer/create.do?tool=true"
		code="tool.create" />

	<acme:button url="itemDesign/designer/create.do?tool=false"
		code="resource.create" />
</security:authorize>
