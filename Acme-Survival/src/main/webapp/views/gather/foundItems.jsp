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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<spring:message code="master.page.current.lang" var="currentLang" />




<style>
#div1 {
	width: 650px;
	height: 800px;
	padding: 10px;
	border: 1px solid #aaaaaa;
	float: left;
}
.divd {
	width: 650px;
	height: 800px;
	padding: 10px;
	border: 1px solid #aaaaaa;
	float: left;
}


#div3 {
	width: 800px;
	height: 300px;
	padding: 10px;
	border: 1px solid #aaaaaa;
}

.div{
	width: 800px;
	height: 300px;
	padding: 10px;
	border: 1px solid #aaaaaa;
}

#div4 {
	width: 800px;
	height: 300px;
	padding: 10px;
	border: 1px solid #aaaaaa;
}
</style>
<script>
	function allowDrop(ev) {
		ev.preventDefault();
	}

	function drag(ev) {
		ev.dataTransfer.setData("text", ev.target.id);
	}

	function drop(ev) {
		ev.preventDefault();
		var data = ev.dataTransfer.getData("text");
		ev.target.appendChild(document.getElementById(data));
	}

	function save() {

		var parent = document.getElementById('div2');
		var notSelected = document.getElementById('div1');
		var notSelected2 = document.getElementById('div3');
		var itemsSelected = new Array();
		var itemsNotSelected = new Array();
		

		if (parent != undefined) {
			var images = parent.getElementsByClassName('img');
			for ( var i = 0; i < images.length; i++) {
				itemsSelected[i] = images[i].getAttribute('name');
			}
		}
			
		if (notSelected != undefined) {
			var imagesNotSelected = notSelected.getElementsByClassName('img');
			for ( var i = 0; i < imagesNotSelected.length; i++) {
				itemsNotSelected[i] = imagesNotSelected[i].getAttribute('name');
			}
		} else if (notSelected2 != undefined){
			var imagesNotSelected2 = notSelected2.getElementsByClassName('img');
			for ( var i = 0; i < imagesNotSelected2.length; i++) {
				itemsNotSelected[i] = imagesNotSelected2[i].getAttribute('name');
			}
		}
		
		var id = document.getElementById('notificationId').innerHTML;
		
		$.post("gather/player/finishMission.do", {
			selected : JSON.stringify(itemsSelected),
			notSelected : JSON.stringify(itemsNotSelected),
			notificationId : id
		}, function(data, status) {
			relativeRedir(data);
		});
	}
</script>
<div id="notificationId" hidden="true"><jstl:out value="${notificationId}" /></div>
<h4>
	<spring:message code="foundresources"></spring:message>
</h4>
<div id="div4">
	<jstl:forEach items="${resources}" var="resource">
		<img name="${resource.id}" class="img" id="${resource.id}"
			style="float: left;" src="${resource.imageUrl}" width="75"
			height="75">
	</jstl:forEach>
</div>
<br>

<jstl:if test="${currentCapacityShelter>=totalTools}">
	<h4>
		<spring:message code="founditems"></spring:message>
	</h4>
	<div id="div2" class ="div" >
		<jstl:forEach items="${items}" var="item">
			<img name="${item.id}" class="img" id="${item.id}"
				style="float: left;" src="${item.tool.imageUrl}" width="75"
				height="75">
		</jstl:forEach>
	</div>
</jstl:if>


<jstl:if test="${currentCapacityShelter<totalTools && currentCapacityShelter == 0}">


	<h4>
		<spring:message code="founditems"></spring:message>
	</h4>
	<spring:message code="mission.descartedItems"></spring:message>
	<div id="div3" class="div">
		<jstl:forEach items="${items}" var="item">
			<img name="${item.id}" class="img" id="${item.id}"
				style="float: left;" src="${item.tool.imageUrl}" width="75"
				height="75">
		</jstl:forEach>
	</div>
</jstl:if>




<jstl:if test="${currentCapacityShelter<totalTools && currentCapacityShelter>0}">
	<p><spring:message code="item.message1"></spring:message><br></p>
	<p><spring:message code="item.message2"></spring:message><b>  <jstl:out value="${currentCapacityShelter}"></jstl:out></b></p><br>
	<p><spring:message code="item.message3"></spring:message></p>
<jstl:if test="${failedSelection}">	
	<p style="color=#F00;"><spring:message code="error.message"></spring:message>!!!</p>
</jstl:if>	
	<div id="div2" class="divd" ondrop="drop(event)" ondragover="allowDrop(event)">
	</div>
	<div id="div1" ondrop="drop(event)" ondragover="allowDrop(event)">
		<jstl:forEach items="${items}" var="item">
			<img name="${item.id}" class="img" id="${item.id}"
				style="float: left;" src="${item.tool.imageUrl}" draggable="true"
				ondragstart="drag(event)" width="75" height="75">

		</jstl:forEach>
	</div>
</jstl:if>
<br><br><br>
<button class="btn" onclick="save();">
	<spring:message code="mission.finish"></spring:message>
</button>
