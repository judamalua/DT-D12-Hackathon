<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
$(document).ready(function(){
	$('#roomDesign').change(function(){
		
		$.get('room/player/resources.do?roomDesignId='+$('select option:selected').val(), function(result){
			$('#resources').html(result);
		});
	});
});
</script>

<form:form action="room/player/edit.do" modelAttribute="room">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<p>
		<em><spring:message code="form.required.params" /></em>
	</p>
	
	<ul id="resources">
	</ul>
	<spring:message var="lang" code="master.page.current.lang" />
	
	<acme:select id="roomDesign" items="${roomDesigns}" itemLabel="name[${lang}]"
		code="room.roomDesign" path="roomDesign" />

	<acme:submit name="save" code="room.save" />

	<jstl:if test="${room.id!=0}">
		<acme:delete clickCode="room.delete.message" name="delete"
			code="room.delete" />
	</jstl:if>
	<acme:cancel url="forum/list.do" code="room.cancel" />

</form:form>