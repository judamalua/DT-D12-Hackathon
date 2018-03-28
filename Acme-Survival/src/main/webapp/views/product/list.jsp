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

<!-- This view lists final mode products and discontinued products -->

<spring:message code = "master.page.current.lang" var = "currentLang" />
<spring:message code="master.page.price.format" var="formatPrice" />

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}page="/>

<display:table name="products" id="product"
	requestURI="${requestURI}?page=${page}"
	class="displayTag">

	<spring:message code="product.name" var="name" />
	<jstl:if test="${currentLang == \"en\"}">
	<display:column title="${name}" sortable="true">
		<a href = "product/detailed.do?productId=${product.id}"><jstl:out value = "${product.name_en}"/></a>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${currentLang == \"es\"}">
	<display:column title="${name}" sortable="true">
		<a href = "product/detailed.do?productId=${product.id}"><jstl:out value = "${product.name_es}"/></a>
	</display:column>
	</jstl:if>
	
	<spring:message code="product.description" var="description" />
	<jstl:if test="${currentLang == \"en\"}">
	<display:column property="description_en" title="${description}" sortable="false" />
	</jstl:if>
	
	<jstl:if test="${currentLang == \"es\"}">
	<display:column property="description_es" title="${description}" sortable="false" />
	</jstl:if>
	
	<spring:message code="product.price" var="price" />
	<display:column property="price" title="${price}" format="${formatPrice}" sortable="true" />
	
	<display:column>
		<jstl:if test="${product.discontinued}">
			<i class="material-icons">remove_shopping_cart</i>
			<spring:message code="product.discontinued" />
		</jstl:if>
	</display:column>
	
	<jstl:if test="${principalIsManager && product.finalMode}">
		<!-- Checking if the principal is a manager, if so, he or she can mark the product as discontinued -->
		<display:column>
			<a href="product/manager/discontinue.do?productId=${product.id}">
				<button class="btn">
					<spring:message code="product.discontinue" />
				</button>
			</a>
		</display:column>
	</jstl:if>

</display:table>

<jstl:if test="${principalIsManager && managerDraftModeView}">
	<!-- Checking if the principal is a manager and he is in the draft mode list view, if so, he or she can create new draft mode products -->
	<br />
	<acme:button url="product/manager/create.do" code="product.create"/>
</jstl:if>