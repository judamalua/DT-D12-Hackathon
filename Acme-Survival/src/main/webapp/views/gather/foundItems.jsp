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
    float:right;
}
#div2 {
    width: 650px;
    height: 800px;
    padding: 10px;
    border: 1px solid #aaaaaa;
    float:left;
}
#div3 {
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

function save(){
	
	var parent = document.getElementById('div2');
    var images = parent.getElementsByClassName('img');
    window.alert('Hola');
    var attributes = [];
    for(var i=0;i<images.length;i++){
    	attributes[i]=images[i].getAttribute('name');
    }
    
    window.alert(attributes);
}
</script>

<h3><spring:message code="founditems"></spring:message></h3>
<div id="div3">
<jstl:forEach items="${items}" var="item" >
<img name="${item.id}" class="img" id="${item.id}" style ="float:left;"src="${item.tool.imageUrl}" 
 width="75" height="75">
</jstl:forEach>
</div>

<h3><spring:message code="foundresources"></spring:message></h3>
<div id="div4">
<jstl:forEach items="${resources}" var="resource" >
<img name="${resource.id}" class="img" id="${resource.id}" style ="float:left;"src="${resource.imageUrl}" 
 width="75" height="75">
</jstl:forEach>
</div>




<jstl:if test="${fullCapacityRefuge}">
<div id="div1" ondrop="drop(event)" ondragover="allowDrop(event)">
<jstl:forEach items="${items}" var="item" >
<img name="${item.id}" class="img" id="${item.id}" style ="float:left;"src="${item.tool.imageUrl}" 
draggable="true" ondragstart="drag(event)" width="75" height="75">

</jstl:forEach>
</div>

<div id="div2" ondrop="drop(event)" ondragover="allowDrop(event)">
</div>


<button class="btn"onclick="save();">Go</button>
</jstl:if>	
