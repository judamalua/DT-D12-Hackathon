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

<form:form action="message/actor/edit.do" modelAttribute="messageForm">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<p>
		<em><spring:message code="form.required.params" /></em>
	</p>

	<acme:textbox code="message.text" path="text" required="true" />

	<acme:textarea code="message.image" path="image" />

	<acme:submit name="save" code="message.save" />

	<jstl:if test="${messageForm.id!=0}">
		<acme:delete clickCode="message.delete.message" name="delete"
			code="message.delete" />
	</jstl:if>
	<jstl:if test="${thread!=null and thread.forum.forum!=null }">
		<acme:cancel url="form/list.do?forumId=${thread.forum.forum}" code="thread.cancel" />
	</jstl:if>
	<jstl:if test="${thread==null or thread.forum.forum==null }">
		<acme:cancel url="form/list.do" code="thread.cancel" />
	</jstl:if>

</form:form>
