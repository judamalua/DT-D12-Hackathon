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

<p>
	<strong><spring:message code="configuration.movingWood" /></strong>
	<jstl:out value="${designerConfiguration.movingWood}" />
</p>

<p>
	<strong><spring:message code="configuration.movingMetal" /></strong>
	<jstl:out value="${designerConfiguration.movingMetal}" />
</p>

<p>
<strong><spring:message code="configuration.movingFood" /></strong>:
&#160;&#160;<jstl:out value="${designerConfiguration.movingFood}" />
</p>

<p>
	<strong><spring:message code="configuration.kmPerSecond" /></strong>
	<jstl:out value="${designerConfiguration.kmPerSecond}" /> (Km/s)
</p>
<p>
	<strong><spring:message code="configuration.foodWastedPerSecond" /></strong>
	<jstl:out value="${designerConfiguration.foodWastedPerSecond}" />
</p>
<p>
	<strong><spring:message code="configuration.waterWastedPerSecond" /></strong>
	<jstl:out value="${designerConfiguration.waterWastedPerSecond}" />
</p>
<p>
	<strong><spring:message code="configuration.refugeRecoverTime" /></strong>
	<jstl:out value="${designerConfiguration.refugeRecoverTime}" />
</p>
<p>
	<strong><spring:message code="configuration.waterFactorSteal" /></strong>
	<jstl:out value="${designerConfiguration.waterFactorSteal}" />
</p>
<p>
	<strong><spring:message code="configuration.foodFactorSteal" /></strong>
	<jstl:out value="${designerConfiguration.foodFactorSteal}" />
</p>
<p>
	<strong><spring:message code="configuration.metalFactorSteal" /></strong>
	<jstl:out value="${designerConfiguration.metalFactorSteal}" />
</p>
<p>
	<strong><spring:message code="configuration.woodFactorSteal" /></strong>
	<jstl:out value="${designerConfiguration.woodFactorSteal}" />
</p>
<p>
	<strong><spring:message code="configuration.foodLostGatherFactor" /></strong>
	<jstl:out value="${designerConfiguration.foodLostGatherFactor}" />
</p>
<p>
	<strong><spring:message code="configuration.waterLostGatherFactor" /></strong>
	<jstl:out value="${designerConfiguration.waterLostGatherFactor}" />
</p>
<p>
	<strong><spring:message code="configuration.experiencePerMinute" /></strong>
	<jstl:out value="${designerConfiguration.experiencePerMinute}" />
</p>
<p>
	<strong><spring:message code="configuration.refugeDefaultCapacity" /></strong>
	<jstl:out value="${designerConfiguration.refugeDefaultCapacity}" />
</p>
<p>
	<strong><spring:message code="configuration.initialFood" /></strong>
	<jstl:out value="${designerConfiguration.initialFood}" />
</p>
<p>
	<strong><spring:message code="configuration.initialWater" /></strong>
	<jstl:out value="${designerConfiguration.initialWater}" />
</p>
<p>
	<strong><spring:message code="configuration.initialWood" /></strong>
	<jstl:out value="${designerConfiguration.initialWood}" />
</p>
<p>
	<strong><spring:message code="configuration.initialMetal" /></strong>
	<jstl:out value="${designerConfiguration.initialMetal}" />
</p>
<p>
	<strong><spring:message code="configuration.maxInventoryFood" /></strong>
	<jstl:out value="${designerConfiguration.maxInventoryFood}" />
</p>
<p>
	<strong><spring:message code="configuration.maxInventoryWater" /></strong>
	<jstl:out value="${designerConfiguration.maxInventoryWater}" />
</p>
<p>
	<strong><spring:message code="configuration.maxInventoryWood" /></strong>
	<jstl:out value="${designerConfiguration.maxInventoryWood}" />
</p>
<p>
	<strong><spring:message code="configuration.maxInventoryMetal" /></strong>
	<jstl:out value="${designerConfiguration.maxInventoryMetal}" />
</p>
<p>
	<strong><spring:message code="configuration.numInitialCharacters" /></strong>
	<jstl:out value="${designerConfiguration.numInitialCharacters}" />
</p>
<acme:button url="designerConfiguration/designer/edit.do" code="configuration.edit"/>
