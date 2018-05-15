
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
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">








<!--Base Info-->
<strong><spring:message code="character.fullName" />:</strong>
<jstl:out value="${character.fullName}" />




<!--Property bars-->
<div style=" border-style: solid;
    border-width: 5px;">

<strong><spring:message code="character.currentHealth" />:</strong>
<div class="w3-section w3-light-grey">
   <i class="material-icons right">local_hospital</i>
  <div class="w3-container w3-padding-small w3-red w3-center" style="width:${character.currentHealth}%">
  	<jstl:out value="${character.currentHealth}" /></div> 
</div><br>

<strong><spring:message code="character.currentFood" />:</strong>
<div class="w3-section w3-light-grey">
	<i class="material-icons right">restaurant</i>
  <div class="w3-container w3-padding-small w3-red w3-center" style="width:${character.currentFood}%">
  	<jstl:out value="${character.currentFood}" /></div> 
</div><br>

<strong><spring:message code="character.currentWater" />:</strong>
<div class="w3-section w3-light-grey">
	<i class="material-icons right">free_breakfast</i>
  <div class="w3-container w3-padding-small w3-red w3-center" style="width:${character.currentWater}%">
  	<jstl:out value="${character.currentWater}" /></div> 
</div><br>

<strong><spring:message code="character.experience" />:</strong>
<div class="w3-section w3-light-grey">
  <div class="w3-container w3-padding-small w3-red w3-center" style="width:${character.experience}%">
  	<jstl:out value="${character.experience}" /><br/>Level:<jstl:out value="${character.level}" /></div> 
</div><br>

</div>







<!--Power-->
<i class="material-icons">fitness_center</i>
<strong><spring:message code="character.strength" />:</strong>
<jstl:out value="${character.strength}" />
<br />

<i class="material-icons">star</i>
<strong><spring:message code="character.luck" />:</strong>
<jstl:out value="${character.luck}" />
<br />

<i class="material-icons">build</i>
<strong><spring:message code="character.capacity" />:</strong>
<jstl:out value="${character.capacity}" />
<br />


	

