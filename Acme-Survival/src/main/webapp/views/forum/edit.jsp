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
<div class="form-group">
	<div class="row">
		<form:form action="forum/actor/edit.do" modelAttribute="forumForm">

			<form:hidden path="id" />
			<form:hidden path="version" />

			<p>
				<em><spring:message code="form.required.params" /></em>
			</p>

			<acme:textbox code="forum.name" path="name" required="true" />

			<acme:textarea code="forum.description" path="description"
				required="true" />

			<acme:textbox code="forum.image" path="image" />

			<security:authorize
				access="hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('MANAGER') or hasRole('DESIGNER')">
				<acme:checkbox code="forum.staff" path="staff" id="staff" />

				<acme:checkbox code="forum.support" path="support" id="support" />
			</security:authorize>

			<acme:select items="${forums}" itemLabel="name"
				code="forum.fatherForum" path="forum" />
			<br/>
			<br/>
			<div class="cleared-div">
				<acme:submit name="save" code="forum.save" />

				<jstl:if test="${forumForm.id!=0}">
					<acme:delete clickCode="forum.delete.message" name="delete"
						code="forum.delete" />
				</jstl:if>
				<acme:cancel url="forum/list.do" code="forum.cancel" />
			</div>
		</form:form>
	</div>
</div>