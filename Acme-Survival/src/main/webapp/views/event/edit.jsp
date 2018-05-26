<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<spring:message code="master.page.current.lang" var="currentLang" />
<spring:message code="event.close" var="close" />
	<div id="modal1" class="modal">
		<div class="modal-content">
			<h4>
				<spring:message code="lootTable.itemSelector" />
			</h4>
			<ul class="collection">
				<jstl:forEach items="${items}" var="item" varStatus="status">
				
					<li
						onclick="selectItem('${item.name[currentLang]}', '${item.imageUrl}', '${item.id}')"
						class="collection-item avatar listSelector"><img
						src="${item.imageUrl}" class="responsive-img circle" />
						<p>
						
							${item.name[currentLang]} <br>
							${item.description[currentLang]}
						</p></li>
				</jstl:forEach>
			</ul>
		</div>
		<div class="modal-footer">
			<a onclick=" $('#modalItemSelector').modal('close');"
				class="modal-close waves-effect waves-green btn-flat">${close}</a>
		</div>
	</div>
	
	<script>
	$( document ).ready(function() {
		if("${event}" != "" && "${event.itemDesign}" != ""){
			var nID = 0;
			nID=parseInt("${event.itemDesign.id}");
		   selectItem("${event.itemDesign.name[currentLang]}", "${event.itemDesign.imageUrl}", nID);
		};
		});
	function selectItem(name, image, id){
	$('#modal1').modal('close');
	$('#itemId').val(id);
	$('#itemName')[0].innerHTML = name;
	$('#itemImage')[0].src = image;
	};
	
	function deleteItem(){
		$('#itemId').val(0);
		$('#itemName')[0].innerHTML = "";
		$('#itemImage')[0].src = "";
	}
	
	
	</script>
	
<form:form id = "form" action="event/designer/edit.do" modelAttribute ="event">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden id="itemId" path="itemDesign" value="0"/>
	<acme:textboxMap code="event.name_en" path="name[en]" errorPath="name" required = "true"/>
	<acme:textboxMap code="event.name_es" path="name[es]" errorPath ="name" required = "true"/>
	
	<acme:textboxMap code="event.description_en" path="description[en]" errorPath="description" required = "true"/>
	<acme:textboxMap code="event.description_es" path="description[es]" errorPath="description" required = "true"/>
	
	<acme:textarea code="event.health" path="health" required = "true"/>
	<acme:textarea code="event.food" path="food" required = "true"/>
	<acme:textarea code="event.water" path="water" required = "true"/>
	
	<spring:message code='event.item'/>: <img id="itemImage" src='' width=48px/>
	<label id="itemName"></label>
	<br>
	<a class="waves-effect waves-light btn modal-trigger" href="#modal1"><spring:message code='event.selectItem'/></a>
	<a class="waves-effect waves-light btn" onclick="deleteItem()"><spring:message code='event.delete'/></a>
	<acme:checkbox id="findCharacter" code="event.findCharacter" path="findCharacter" />
	<acme:checkbox id="finalMode" code="event.finalMode" path="finalMode" />
	
	<acme:submit name="save" code="event.save"/>
		
	<jstl:if test="${event.id!=0}">
		<acme:delete clickCode="event.confirm.delete" name="delete" code="event.delete"/>
	</jstl:if>
	<acme:cancel url="event/designer/list.do" code="event.cancel"/>
</form:form>

	
