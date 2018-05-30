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
					if (result.split(',')[2] > 0) {
						$('#resourceHealth').show();
						$('#plusHealth').html(result.split(',')[2]);
					} else {
						$('#resourceHealth').hide();
					}
					if (result.split(',')[3] > 0) {
						$('#resourceFood').show();
						$('#plusFood').html(result.split(',')[3]);
					} else {
						$('#resourceFood').hide();
					}
					if (result.split(',')[4] > 0) {
						$('#resourceWater').show();
						$('#plusWater').html(result.split(',')[4]);
					} else {
						$('#resourceWater').hide();
					}
					if (result.split(',')[5] > 0) {
						$('#resourceMetal').show();
						$('#plusMetal').html(result.split(',')[5]);
					} else {
						$('#resourceMetal').hide();
					}
					if (result.split(',')[6] > 0) {
						$('#resourceWood').show();
						$('#plusWood').html(result.split(',')[6]);
					} else {
						$('#resourceWood').hide();
					}
					if (result.split(',')[7] > 0) {
						$('#resourceCharacterCapacity').show();
						$('#plusCharacterCapacity').html(result.split(',')[7]);
					} else {
						$('#resourceCharacterCapacity').hide();
					}
					if (result.split(',')[8] > 0) {
						$('#resourceItemCapacity').show();
						$('#plusItemCapacity').html(result.split(',')[8]);
					} else {
						$('#resourceItemCapacity').hide();
					}
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
<div class="form-group">
	<div class="row">
		<form:form action="room/player/edit.do" modelAttribute="room">

			<form:hidden path="id" />
			<form:hidden path="version" />


			<p>
				<em><spring:message code="form.required.params" /></em>
			</p>

			<div id="resources">
				<div class="plusLabel">
					<spring:message code="room.wood" />
					:
				</div>
				<div class="plusElm">
					<div id="wood"></div>
				</div>
				<br />
				<div class="plusLabel">
					<spring:message code="room.metal" />
					:
				</div>
				<div class="plusElm">
					<div id="metal"></div>
				</div>
				<br />
				<div id="resourceHealth">

					<div class="plusLabel">
						<spring:message code="room.health" />
						+
					</div>
					<div class="plusElm">
						<div id="plusHealth"></div>
						/min
					</div>
					<br />
				</div>

				<div id="resourceFood">
					<div class="plusLabel">
						<spring:message code="room.food" />
						+
					</div>
					<div class="plusElm">
						<div id="plusFood"></div>
						/min
					</div>
					<br />
				</div>
				<div id="resourceWater">
					<div class="plusLabel">
						<spring:message code="room.water" />
						+
					</div>
					<div class="plusElm">
						<div id="plusWater"></div>/min
					</div>
					<br />
				</div>
				<div id="resourceMetal">
					<div class="plusLabel">
						<spring:message code="room.metal" />
						+
					</div>
					<div class="plusElm">
						<div id="plusMetal"></div>
						/min
					</div>
					<br />
				</div>
				<div id="resourceWood">
					<div class="plusLabel">
						<spring:message code="room.wood" />
						+
					</div>
					<div class="plusElm">
						<div id="plusWood"></div>
						/min
					</div>
					<br />
				</div>
				<div id="resourceCharacterCapacity">
					<div class="plusLabel">
						<spring:message code="room.characterCapacity" />
						+
					</div>

					<div class="plusElm" id="plusCharacterCapacity"></div>
				</div>
				<div id="resourceItemCapacity">
					<div class="plusLabel">
						<spring:message code="room.itemCapacity" /> + 
					</div>
					
					<div class="plusElm" id="plusItemCapacity"></div>
					<br />
				</div>
			</div>
			<br />
			<div id="resourceError" class="error">
				<spring:message code="room.resources.error" />
			</div>
			<spring:message var="lang" code="master.page.current.lang" />

			<acme:select id="roomDesign" items="${roomDesigns}"
				itemLabel="name[${lang}]" code="room.roomDesign" path="roomDesign" />
			<br />
			<div class="cleared-div">
				<acme:submit name="save" code="room.save" />

				<jstl:if test="${room.id!=0}">
					<acme:delete clickCode="room.delete.message" name="delete"
						code="room.delete" />
				</jstl:if>
				<acme:cancel url="shelter/player/display.do" code="room.cancel" />
			</div>
		</form:form>
	</div>
</div>