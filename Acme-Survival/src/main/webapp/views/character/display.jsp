
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="styles/circleBar.css">









<!--Base Info-->
<br>
<h3 class="characterName"><jstl:out value="${character.fullName}" /></h3>





	<div style="width: 200px; float: left;">
		<div class="characterImage" style="height: 200px; width: 200px;">
		</div>
		<br>
		
	</div>


	





<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>



<div class="characterGenre" hidden="true">
	<jstl:if test="${character.male}">Male</jstl:if>
	<jstl:if test="${!character.male}">Female</jstl:if>
	
</div>




<br>
<!--Property bars-->
<div style="border-style: solid; border-width: 5px;">

	<strong><spring:message code="character.currentHealth" />:</strong>
	<div class="3-progress-container w3-light-green">
		<i class="material-icons right">local_hospital</i>
		<div class="w3-progressbar w3-green w3-center"
			style="width:${character.currentHealth}%">
			<jstl:out value="${character.currentHealth}" />
			%
		</div>
	</div>
	<br> <strong><spring:message code="character.currentFood" />:</strong>
	<div class="3-progress-container w3-light-red">
		<i class="material-icons right">restaurant</i>
		<div class="w3-progressbar w3-red w3-center"
			style="width:${character.currentFood}%">
			<jstl:out value="${character.currentFood}" />
			%
		</div>
	</div>
	<br> <strong><spring:message
			code="character.currentWater" />:</strong>
	<div class="w3-progress-container w3-light-blue">
		<i class="material-icons right">free_breakfast</i>
		<div class="w3-progressbar w3-blue w3-center"
			style="width:${character.currentWater}%">
			<jstl:out value="${character.currentWater}" />
			%
		</div>
	</div>
			
			<br> 
	
	
	<spring:message code="character.level" />:<jstl:out value="${character.level}" /><br> 
	
	
	<div class="w3-progress-container w3-light-orange">
		<i class="material-icons right">exposure_plus_1</i>
		<div class="w3-progressbar w3-orange w3-center"
			style="width:${(character.experience/((character.level+1)*(character.level+1)*100))*100}%">
			<jstl:out value="${(character.experience/((character.level+1)*(character.level+1)*100))*100}%" />
			
		</div>
		<spring:message code="character.experience" />:<jstl:out value="${character.experience}" />
	</div>


	<br>
	


</div>
<br>



<jstl:if test="${character.item !=null}">
<div>
	<img style="height: 200px; width: 200px;" src=" ${character.item.tool.imageUrl}"/>
</div>

<acme:button url="character/player/display.do?characterId=${character.id}&discard=true"
			code="item.discard" />

</jstl:if>


<acme:button url="item/player/list.do?characterId=${character.id} "
			code="item.equip" />


<!--Power-->

<br><br>

<i class="material-icons">fitness_center</i>
<strong><spring:message code="character.strength" />:</strong>
<jstl:out value="${character.strength}" />

<i class="material-icons">star</i>
<strong><spring:message code="character.luck" />:</strong>
<jstl:out value="${character.luck}" />

<i class="material-icons">build</i>
<strong><spring:message code="character.capacity" />:</strong>
<jstl:out value="${character.capacity}" />
<br />



