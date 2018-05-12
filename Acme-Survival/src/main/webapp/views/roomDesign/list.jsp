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

<!-- This view lists final mode room designs and draft mode room designs depending on the request URI -->

<spring:message code = "master.page.current.lang" var = "currentLang" />
<spring:message code="master.page.price.format" var="formatPrice" />

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}?page="/>

<display:table name="roomDesigns" id="roomDesign"
	requestURI="${requestURI}"
	class="displayTag">

	<spring:message code="roomDesign.name" var="name" />
	<display:column title="${name}" sortable="true">
		<a href = "roomDesign/detailed.do?roomDesignId=${roomDesign.id}"><jstl:out value = "${roomDesign.name[currentLang]}"/></a>
	</display:column>
	
	<spring:message code="roomDesign.description" var="description" />
	<display:column value="${roomDesign.description[currentLang]}" title="${description}" sortable="false" />
	
	<security:authorize access="hasRole('DESIGNER')">
	<jstl:if test="${designerDraftModeView && !product.finalMode}">
		<!-- Checking if the principal is a designer and this is the view of the draft mode room designs, if so, he or she can edit the room designs -->
		<display:column>
			<acme:button url="roomDesign/designer/edit.do?roomDesignId=${roomDesign.id}" code="roomDesign.edit"/>
		</display:column>
	</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('DESIGNER')">
	<jstl:if test="${designerDraftModeView && !roomDesign.finalMode}">
		<!-- Checking if the principal is a designer and this is the view of the draft mode room designs, if so, he or she can set the room designs to final
		 mode -->
		<display:column>
			<acme:button url="roomDesign/designer/final-mode.do?roomDesignId=${roomDesign.id}" code="roomDesign.mark.final.mode"/>
		</display:column>
	</jstl:if>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('DESIGNER')">
	<!-- Checking if the principal is a designer, so he or she can create new room designs -->
	<br />
	<acme:button url="roomDesign/designer/create.do" code="roomDesign.create"/>
</security:authorize>