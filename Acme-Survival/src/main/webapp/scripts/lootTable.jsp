<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<script>
<!-- Load objects in javascript -->
var events = new Array();
var probabilitiesEvent = new Array();


<jstl:forEach items="${requestScope.lootTable.probabilityEvents}" var="probEvent" varStatus="status">
events.push("${probEvent.event.name[currentLang]}");
probabilitiesEvent.push(${probEvent.value});
</jstl:forEach> 


var items = new Array();
var probabilitiesItem = new Array();
<jstl:forEach items="${requestScope.lootTable.probabilityItems}" var="probItem" varStatus="status">
items.push("${probItem.itemDesign.name[currentLang]}");
probabilitiesItem.push(${probItem.value});
</jstl:forEach> 


 
<!---------------------------->



function redirectPost(url, data) {
    var form = document.createElement('form');
    document.body.appendChild(form);
    form.method = 'post';
    form.action = url;
    for (var name in data) {
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = name;
        input.value = data[name];
        form.appendChild(input);
    }
    form.submit();
}

$(document).ready(function() {
console.log(events);
 $('#save-loottable').click(()=>{

	var data = {};
	$("#table-item tr").each(function(index, element) {
		if (!element.className.includes("hide") && $(this).find("td:first").text() != "" ){
	    data["item" +$(this).find("td:first").text()] = $(this).find("td:eq(3)").text();
		}
	    // compare id to what you want
	});
	
		$("#table-event tr").each(function(index, element) {
		if (!element.className.includes("hide") && $(this).find("td:first").text() != "" ){
	    data["event" + $(this).find("td:first").text()] = $(this).find("td:eq(2)").text();
		}
	    // compare id to what you want
	});
	data["name"] = document.getElementById("lootTableName").innerText;
	data["save"] = ""; //Needed for spring save
	redirectPost("lootTable/designer/edit.do?tableId=${requestScope.lootTable.id}", data);


  });
var $TABLEEVENT = $('#table-event');
var eventsCount = probabilitiesEvent.length;
 
  
$('#table-add-event').click(function () {
	
  var $clone = $TABLEEVENT.find('tr.hide').clone(true).removeClass('hide table-line');
  $clone.children('#clone1').attr("id","eventTable" + eventsCount).attr("onclick","eventSelector(" + eventsCount+ ")");
  $clone.children('#clone3').attr("id","IdEventTable" + eventsCount);
  $TABLEEVENT.find('table').append($clone);
  eventSelector(eventsCount);
  eventsCount++;
});

$('.table-remove-event').click(function () {
  $(this).parents('tr').detach();
  eventsCount--;
});

$('.table-up-event').click(function () {
  var $row = $(this).parents('tr');
  if ($row.index() === 1) return; // Don't go above the header
  $row.prev().before($row.get(0));
});

$('.table-down-event').click(function () {
  var $row = $(this).parents('tr');
  $row.next().after($row.get(0));
});
  
  
  var $TABLEITEM = $('#table-item');
var itemsCount = probabilitiesItem.length;
 
  
$('#table-add-item').click(function () {
	
  var $clone = $TABLEITEM.find('tr.hide').clone(true).removeClass('hide table-line');
  $clone.children('#clone1').attr("id","itemTable" + itemsCount).attr("onclick","itemSelector(" + itemsCount+ ")");
  $clone.children('#clone2').attr("id","imgItemTable" + itemsCount).attr("onclick","itemSelector(" + itemsCount+ ")");
  $clone.children('#clone3').attr("id","IdItemTable" + itemsCount);
  $TABLEITEM.find('table').append($clone);
  itemSelector(itemsCount);
  itemsCount++;
});

$('.table-remove-item').click(function () {
  $(this).parents('tr').detach();
  itemsCount--;
});

$('.table-up-item').click(function () {
  var $row = $(this).parents('tr');
  if ($row.index() === 1) return; // Don't go above the header
  $row.prev().before($row.get(0));
});

$('.table-down-item').click(function () {
  var $row = $(this).parents('tr');
  $row.next().after($row.get(0));
});
  
 
  
  
});

var currentEvent=0;
function eventSelector(id){
	   $('#modalEventSelector').modal();
	   $('#modalEventSelector').modal('open'); 
	   currentEvent = id;
};

function selectEvent(name, id){
	$('#modalEventSelector').modal('close');
	$('#IdEventTable' + currentEvent)[0].innerHTML = id;
	$('#eventTable' + currentEvent)[0].innerHTML = name;
};

var currentItem=0;
function itemSelector(id){
	   $('#modalItemSelector').modal();
	   $('#modalItemSelector').modal('open'); 
	   currentItem = id;
};

function selectItem(name,image, id){
	$('#modalItemSelector').modal('close');
	$('#IdItemTable' + currentItem)[0].innerHTML = id;
	$('#itemTable' + currentItem)[0].innerHTML = name;
	$('#imgItemTable' + currentItem)[0].innerHTML = "<img src='"+ image + "' style='width: 32px'>"
	
}
</script>
