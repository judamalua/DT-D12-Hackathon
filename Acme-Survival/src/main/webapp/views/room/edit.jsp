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
	$(document).ready(function() {
		$('#resourceError').hide();
		$('#resources').hide();
		$('#roomDesign').change(function() {
			$.get('room/player/resources.do?roomDesignId=' + $('select option:selected').val(), function(result) {
				if (result == 'error') {
					$('#resourceError').show();
					$('#resources').hide();
				} else if (result.length > 0) {
					$('#wood').html(result.split(',')[0]);
					$('#metal').html(result.split(',')[1]);
					$('#resources').show();
					$('#resourceError').hide();
				} else {
					$('#resourceError').hide();
					$('#resources').hide();
				}
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

	<div id="resources">
		<spring:message code="room.wood" />:<div id="wood"></div>
		<spring:message code="room.metal" />:<div id="metal"></div>
	</div>
	<div id="resourceError" class="error">
		<spring:message code="room.resources.error" />
	</div>
	<spring:message var="lang" code="master.page.current.lang" />

	<acme:select id="roomDesign" items="${roomDesigns}"
		itemLabel="name[${lang}]" code="room.roomDesign" path="roomDesign" />

	<acme:submit name="save" code="room.save" />

	<jstl:if test="${room.id!=0}">
		<acme:delete clickCode="room.delete.message" name="delete"
			code="room.delete" />
	</jstl:if>
	<acme:cancel url="refuge/player/display.do" code="room.cancel" />

</form:form>