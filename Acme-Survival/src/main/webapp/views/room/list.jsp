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

<spring:message code="master.page.current.lang" var="lang" />

<acme:pagination requestURI="room/player/list.do?characterId=${characterId}&page="
	pageNum="${pageNum}" page="${page}" />

<display:table name="rooms" id="room"
	requestURI="room/player/list.do?characterId=${characterId}" class="displaytag">

	<spring:message code="refuge.room.name" var="nameRoomTitle" />
			<display:column title="${nameRoomTitle}">
				<jstl:out value="${room.roomDesign.name[lang]}" />
				<br />
				<jstl:out value="${room.roomDesign.description[lang]}" />
			</display:column>
	


	<display:column>
		<security:authorize access="hasRole('PLAYER')">
			
				<acme:button
					url="character/player/move.do?roomId=${room.id}&characterId=${characterId}"
					code="room.move" />
		
		</security:authorize>
	</display:column>

</display:table>


