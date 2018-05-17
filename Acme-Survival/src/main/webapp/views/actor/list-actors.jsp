
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}?page="/>

<display:table name = "actors" id = "actor" 
	requestURI = "${requestURI}" pagesize = "${pagesize}" class = "displaytag">

	<spring:message code = "actor.name" var = "name"/>
	<display:column property = "name" title = "${name}" sortable = "true"/>
	
	<spring:message code = "actor.surname" var = "surname"/>
	<display:column property = "surname" title = "${surname}" sortable = "true"/>
	
	<spring:message code = "actor.email" var = "email"/>
	<display:column property = "email" title = "${email}" sortable = "true"/>

	<display:column>
	<jstl:if test="${!actor.userAccount.banned}">
		<a href = "actor/admin/ban.do?actorId=${actor.id}">
		<button class = "btn" onclick = "return confirm('<spring:message code='actor.confirm.ban' />') ">
			<spring:message code = "actor.ban"/>
		</button>
		</a>
	</jstl:if>
	<jstl:if test="${actor.userAccount.banned}">
		<a href = "actor/admin/unban.do?actorId=${actor.id}">
		<button class = "btn" onclick = "return confirm('<spring:message code='actor.confirm.unban' />') ">
			<spring:message code = "actor.unban"/>
		</button>
		</a>
	</jstl:if>
	</display:column>
	
</display:table>
