<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<p><em><spring:message code = "form.required.params"/></em></p>

<form:form id = "form" action="roomDesign/designer/edit.do" modelAttribute ="roomDesign">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textboxMap code="roomDesign.name_en" path="name[en]" errorPath="name" required = "true"/>
	<acme:textboxMap code="roomDesign.name_es" path="name[es]" errorPath="name" required = "true"/>
	
	<acme:textareaMap code="roomDesign.description_en" path="description[en]" errorPath="description" required = "true"/>
	<acme:textareaMap code="roomDesign.description_es" path="description[es]" errorPath="description" required = "true"/>

	<acme:textbox code="roomDesign.maxResistance" path="maxResistance" required="true"/>
	
	<acme:textbox code="roomDesign.costWood" path="costWood" required="true"/>
	
	<acme:textbox code="roomDesign.costMetal" path="costMetal" required="true"/>
	
	<acme:textbox code="roomDesign.maxCapacityCharacters" path="maxCapacityCharacters" required="true"/>
	
	<jstl:if test="${isWarehouse}">
		<acme:textbox code="roomDesign.type.warehouse.itemCapacity" path="itemCapacity" required="true"/>
	</jstl:if>
	
	<jstl:if test="${isBarrack}">
		<acme:textbox code="roomDesign.type.barrack.characterCapacity" path="characterCapacity" required="true"/>
	</jstl:if>
	
	<jstl:if test="${isRestorationRoom}">
		<acme:textbox code="roomDesign.type.restorationRoom.health" path="health" required="true"/>
		
		<acme:textbox code="roomDesign.type.restorationRoom.food" path="food" required="true"/>
		
		<acme:textbox code="roomDesign.type.restorationRoom.water" path="water" required="true"/>
	</jstl:if>
	
	<jstl:if test="${isResourceRoom}">
		<acme:textbox code="roomDesign.type.resourceRoom.water" path="water" required="true"/>
		
		<acme:textbox code="roomDesign.type.resourceRoom.food" path="food" required="true"/>
		
		<acme:textbox code="roomDesign.type.resourceRoom.metal" path="metal" required="true"/>
		
		<acme:textbox code="roomDesign.type.resourceRoom.wood" path="wood" required="true"/>
	</jstl:if>

	<acme:submit name="saveBarrack" code="roomDesign.save"/>
		
	<jstl:if test="${roomDesign.id!=0}">
		<acme:delete clickCode="roomDesign.confirm.delete" name="delete" code="roomDesign.delete"/>
	</jstl:if>
	<acme:cancel url="roomDesign/designer/list.do" code="roomDesign.cancel"/>
</form:form>
