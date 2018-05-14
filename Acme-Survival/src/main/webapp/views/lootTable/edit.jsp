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
<script>
var events = new Array();
var probabilitiesEvent = new Array();
<jstl:forEach items="${lootTable.probabilityEvents}" var="probEvent" varStatus="status">
events.push("${probEvent.event.name["en"]}");
probabilitiesEvent.push(${probEvent.value});
</jstl:forEach> 

var items = new Array();
var probabilitiesItem = new Array();
<jstl:forEach items="${lootTable.probabilityItems}" var="probItem" varStatus="status">
items.push("${probItem.event.name["en"]}");
probabilitiesItem.push(${probItem.value});
</jstl:forEach> 

var events = new Array();
var probabilitiesEvent = new Array();
<jstl:forEach items="${lootTable.probabilityEvents}" var="probEvent" varStatus="status">
events.push("${probEvent.event.name["en"]}");
probabilitiesEvent.push(${probEvent.value});
</jstl:forEach> 


</script>




<div class="container">



   <!-- Modal Structure -->
  <div id="modalEventSelector" class="modal">
    <div class="modal-content">
      <h4>Event selector (Traducir)</h4>
      
      <ul class="collection">
      
    
    
    <jstl:forEach items="${events}" var="event" varStatus="status">
		
		<li onclick="selectEvent('${event.name['en']}')" class="collection-item avatar listSelector">
		<i class="material-icons circle green">explicit</i>
     <p>${event.name["en"]} <br>
      ${event.description["en"]} </p>
      <div class="info-container">  
      ${event.health} <i class="material-icons">favorite</i>
         ${event.food} <i class="material-icons">local_dining</i>
        ${event.water} <i class="material-icons">local_drink</i>
     </div>
      
   
      
      
    
    </li>
 
</jstl:forEach> 
    
   
  </ul>
      
      
    </div>
    <div class="modal-footer">
      <a onclick=" $('#modalEventSelector').modal('close');" class="modal-close waves-effect waves-green btn-flat">Close (traducir)</a>
    </div>
  </div>
  
  
  <div id="table" class="table-editable">
    <span><i class="table-add material-icons">add_box</i></span>
    <table class="table">
      <tr>
        <th>Name</th>
        <th>Value</th>
        <th></th>
        <th></th>
      </tr>
      <tr>
        
        <jstl:forEach items="${lootTable.probabilityEvents}" var="probEvent" varStatus="status">
 		<td id="eventTable${status.index}" onclick="eventSelector(${status.index})">${probEvent.event.name["en"]}</td>
 		
 		<td contenteditable="true">${probEvent.value}</td>
		</jstl:forEach> 
        <td>
           <span><i class="table-remove material-icons">delete_forever</i></span>
        </td>
        <td>
         <span><i class="table-up material-icons">arrow_upward</i></span>
         <span><i class="table-down material-icons">arrow_downward</i></span>
        </td>
      </tr>
      <!-- This is our clonable table line -->
      <tr class="hide">
        <td contenteditable="true">Item</td>
         
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
  
  <button id="export-btn" class="btn btn-primary">Export Data</button>
  <p id="export"></p>
</div>

<script>
var $TABLE = $('#table');
var $BTN = $('#export-btn');
var $EXPORT = $('#export');

$('.table-add').click(function () {
  var $clone = $TABLE.find('tr.hide').clone(true).removeClass('hide table-line');
  $TABLE.find('table').append($clone);
});

$('.table-remove').click(function () {
  $(this).parents('tr').detach();
});

$('.table-up').click(function () {
  var $row = $(this).parents('tr');
  if ($row.index() === 1) return; // Don't go above the header
  $row.prev().before($row.get(0));
});

$('.table-down').click(function () {
  var $row = $(this).parents('tr');
  $row.next().after($row.get(0));
});

// A few jQuery helpers for exporting only
jQuery.fn.pop = [].pop;
jQuery.fn.shift = [].shift;

$BTN.click(function () {
  var $rows = $TABLE.find('tr:not(:hidden)');
  var headers = [];
  var data = [];
  
  // Get the headers (add special header logic here)
  $($rows.shift()).find('th:not(:empty)').each(function () {
    headers.push($(this).text().toLowerCase());
  });
  
  // Turn all existing rows into a loopable array
  $rows.each(function () {
    var $td = $(this).find('td');
    var h = {};
    
    // Use the headers from earlier to name our hash keys
    headers.forEach(function (header, i) {
      h[header] = $td.eq(i).text();   
    });
    
    data.push(h);
  });
  
  // Output the result
  $EXPORT.text(JSON.stringify(data));
});

var currentEvent=0;
function eventSelector(id){
	   $('#modalEventSelector').modal();
	   $('#modalEventSelector').modal('open'); 
	   currentEvent = id;
};

function selectEvent(name){
	$('#modalEventSelector').modal('close');
	$('#eventTable' + currentEvent)[0].innerHTML = name;
	
}

</script>
<%-- <form:form id = "form" action="event/designer/edit.do" modelAttribute ="event">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textboxMap code="event.name_en" path="name[en]" errorPath="name" required = "true"/>
	<acme:textboxMap code="event.name_es" path="name[es]" errorPath ="name" required = "true"/>
	
	<acme:textboxMap code="event.description_en" path="description[en]" errorPath="description" required = "true"/>
	<acme:textboxMap code="event.description_es" path="description[es]" errorPath="description" required = "true"/>
	
	<acme:textarea code="event.health" path="health" required = "true"/>
	<acme:textarea code="event.food" path="food" required = "true"/>
	<acme:textarea code="event.water" path="water" required = "true"/>

	<acme:checkbox id="findCharacter" code="event.findCharacter" path="findCharacter" />
	<acme:checkbox id="finalMode" code="event.finalMode" path="finalMode" />
	
	<acme:submit name="save" code="event.save"/>
		
	<jstl:if test="${event.id!=0}">
		<acme:delete clickCode="event.confirm.delete" name="delete" code="event.delete"/>
	</jstl:if>
	<acme:cancel url="event/designer/list.do" code="event.cancel"/>
</form:form>
 --%>