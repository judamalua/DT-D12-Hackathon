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

<form:form action="thread/actor/edit.do" modelAttribute="thread">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<jstl:if test="${thread.forum!=null}">
		<form:hidden path="forum" />
	</jstl:if>
	<p>
		<em><spring:message code="form.required.params" /></em>
	</p>

	<acme:textbox code="thread.name" path="name" required="true" />

	<acme:textarea code="thread.tags" path="tags" />

	<jstl:if test="${thread.forum==null}">
		<acme:select items="${forums}" itemLabel="name" code="thread.forum"
			path="forum" />
	</jstl:if>
	<acme:submit name="save" code="thread.save" />

	<acme:delete clickCode="thread.delete.message" name="delete"
		code="thread.delete" />

	<acme:cancel url="thread/list.do" code="thread.cancel" />

</form:form>
