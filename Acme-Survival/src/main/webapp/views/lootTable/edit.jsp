<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<link rel="stylesheet" href="styles/lootTable.css" type="text/css">
<jsp:include page="../../scripts/lootTable.jsp"/>

<spring:message code = "master.page.current.lang" var = "currentLang" />
<spring:message code="lootTable.save" var="save" />
<spring:message code="lootTable.close" var="close" />

<div class="container">

   <!-- Modal EVENT Structure -->
  <div id="modalEventSelector" class="modal">
    <div class="modal-content">
      <h4><spring:message code="lootTable.eventSelector" /></h4>
      <ul class="collection">
    <jstl:forEach items="${events}" var="event" varStatus="status">
		<li onclick="selectEvent('${event.name[currentLang]}')" class="collection-item avatar listSelector">
		<i class="material-icons circle green">explicit</i>
     <p>${event.name[currentLang]} <br>
      ${event.description[currentLang]} </p>
      <div class="info-container">  
      ${event.health} <i class="material-icons red-text text-darken-3">favorite</i>
         ${event.food} <i class="material-icons orange-text text-darken-1">local_dining</i>
        ${event.water} <i class="material-icons teal-text text-lighten-2">local_drink</i>
     </div> 
    </li> 
</jstl:forEach> 
  </ul>
    </div>
    <div class="modal-footer">
      <a onclick=" $('#modalEventSelector').modal('close');" class="modal-close waves-effect waves-green btn-flat">${close}</a>
    </div>
  </div>
   <!-- FIN  Modal Structure -->
    
    
   <!-- Modal ITEM Structure -->
  <div id="modalItemSelector" class="modal">
    <div class="modal-content">
      <h4><spring:message code="lootTable.itemSelector" /></h4>
      <ul class="collection">
    <jstl:forEach items="${items}" var="item" varStatus="status">
		<li onclick="selectItem('${item.name[currentLang]}', '${item.imageUrl}')" class="collection-item avatar listSelector">
		<img src="${item.imageUrl}" class="responsive-img circle" />
     <p>${item.name[currentLang]} <br>
      ${item.description[currentLang]} </p>
    </li> 
</jstl:forEach> 
  </ul>
    </div>
    <div class="modal-footer">
      <a onclick=" $('#modalItemSelector').modal('close');" class="modal-close waves-effect waves-green btn-flat">${close}</a>
    </div>
  </div>
   <!-- FIN  Modal Structure -->
   
   <h3 class="center-align"><i class="material-icons medium amber-text">flash_on</i><spring:message code="lootTable.events"/></h3>
  <div id="table-event" class="table-editable">
    <span><i id="table-add-event" class="table-add material-icons">add_box</i></span>
    <table class="table">
      <tr>
        <th ><spring:message code="lootTable.event"/></th>
        <th><spring:message code="lootTable.probability"/></th>
        <th></th>
        <th></th>
      </tr>
      
        
        <jstl:forEach items="${lootTable.probabilityEvents}" var="probEvent" varStatus="status">
        <tr>
 		<td class="hide">${probEvent.event.id}</td>
 		<td style="cursor: pointer; " id="eventTable${status.index}" onclick="eventSelector(${status.index})">${probEvent.event.name[currentLang]}</td>
 		
 		<td contenteditable="true">${probEvent.value}</td>
		
        <td>
           <span><i id="table-remove-event" class="table-remove material-icons">delete_forever</i></span>
        </td>
        <td>
         <span><i id="table-up-event" class="table-up material-icons">arrow_upward</i></span>
         <span><i id="table-down-event" class="table-down material-icons">arrow_downward</i></span>
        </td>
      </tr>
      </jstl:forEach> 
      <!-- This is our clonable table line -->
      <tr class="hide">
       <td class="hide">${events[0].id}</td>
        <td style="cursor: pointer; " id="clone1" onclick="eventSelector()">${events[0].name[currentLang]}</td>
 		
         
        <td contenteditable="true">0</td>
        <td>
          <span><i id="table-remove-event" class="table-remove material-icons">delete_forever</i></span>
        </td>
        <td>
         <span><i id="table-up-event" class="table-up material-icons">arrow_upward</i></span>
         <span><i id="table-down-event" class="table-down material-icons">arrow_downward</i></span>
        </td>
      </tr>
    </table>
  </div>
  <br>
  <br>
  <br>
<h3 class="center-align"><i class="material-icons medium cyan-text">build</i><spring:message code="lootTable.items"/></h3>
  <div id="table-item" class="table-editable">
    <span><i id="table-add-item"  class="table-add material-icons">add_box</i></span>
    <table class="table">
      <tr>
      <th></th>
        <th ><spring:message code="lootTable.item"/></th>
        <th><spring:message code="lootTable.probability"/></th>
        <th></th>
        <th></th>
      </tr>
      
        
        <jstl:forEach items="${lootTable.probabilityItems}" var="probItem" varStatus="status">
        <tr>
 		<td class="hide">${probItem.item.id}</td>
 		<td style="cursor: pointer; " id="imgItemTable${status.index}" onclick="itemSelector(${status.index})"><img  src="${probItem.item.imageUrl}" class="responsive-img"/></td>
 		<td style="cursor: pointer; " id="itemTable${status.index}" onclick="itemSelector(${status.index})">${probItem.item.name[currentLang]}</td>
 		
 		<td contenteditable="true">${probItem.value}</td>
		
        <td>
           <span><i id="table-remove-item" class="table-remove material-icons">delete_forever</i></span>
        </td>
        <td>
         <span><i id="table-up-item" class="table-up material-icons">arrow_upward</i></span>
         <span><i id="table-down-item" class="table-down material-icons">arrow_downward</i></span>
        </td>
      </tr>
      </jstl:forEach> 
      <!-- This is our clonable table line -->
      <tr class="hide">
       <td class="hide">${items[0].id}</td>
       <td style="cursor: pointer; " id="clone2" onclick="itemSelector()"><img src="${items[0].imageUrl}"  style="width: 32px" class="responsive-img"/></td>
        <td style="cursor: pointer; " id="clone1" onclick="itemSelector()">${items[0].name[currentLang]}</td>
 		
         
        <td contenteditable="true">0</td>
        <td>
          <span><i class="table-remove material-icons">delete_forever</i></span>
        </td>
        <td>
         <span><i class="table-up material-icons">arrow_upward</i></span>
         <span><i class="table-down material-icons">arrow_downward</i></span>
        </td>
      </tr>
    </table>
  </div>
  
 
</div>

<br>
 <button id="save-loottable" class="btn btn-primary">${save}</button>
  
  <acme:cancel url="event/designer/list.do" code="event.cancel"/>
