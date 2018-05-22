<%--
 * display.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!-- Variable declaration -->

<spring:message code = "master.page.current.lang" var = "currentLang" />
<spring:message code="master.page.moment.format.out" var="format" />

<fmt:formatDate var="formatNotificationMoment"
	value="${notification.moment}" pattern="${format}" />

<h2>
	<jstl:out value="${notification.title[currentLang]}" />
</h2>
<br/>

<p>
	<jstl:out value="${formatNotificationMoment}"/>
</p>
<br/>
<p>
	<jstl:out value="${notification.body[currentLang]}" />
</p>

<jstl:if test="${attackId != null}">
	<acme:button url="attack/player/delete.do?attackId=${attackId}" code="notification.finish.result"/>
</jstl:if>

<jstl:if test="${gatherId != null}">
	<acme:button code="mission.summary" url="gather/player/foundItems.do?notificationId=${notification.id}"/>
</jstl:if>

<br/>

<jstl:if test="${notification.mission[\"class\"].simpleName eq \"Gather\"}">

<!-- Modal Trigger -->
  <a class="waves-effect waves-light btn modal-trigger" href="#modalEvents"><spring:message code = "notification.modal.events"/></a>

  <!-- Modal Structure -->
  <div id="modalEvents" class="modal">
    <div class="modal-content">
      <h4><spring:message code = "notification.modal.title"/></h4>
      <jstl:if test="${fn:length(notification.events) gt 0}">
      <p><spring:message code = "notification.modal.description"/></p>
      <jstl:forEach items="${notification.events}" var = "event">
      	<b><spring:message code = "notification.modal.event.title"/></b>
      	<jstl:out value="${event.name[currentLang]}"></jstl:out>
      	<br/>
      	<b><spring:message code = "notification.modal.event.desc"/></b>
      	<jstl:out value="${event.description[currentLang]}"></jstl:out>
      	<br/>
      	<spring:message code = "notification.modal.event.health"/> <jstl:out value="${event.health}"/>, <spring:message code = "notification.modal.event.food"/> <jstl:out value="${event.food}"/> <spring:message code = "notification.modal.event.water"/> <jstl:out value="${event.water}"/>
      	<jstl:if test="${event.findCharacter}">
      		<spring:message code = "notification.modal.event.findChar"/>
      	</jstl:if>
      </jstl:forEach>
      </jstl:if>
       <jstl:if test="${fn:length(notification.events) eq 0}">
       	<spring:message code = "notification.modal.event.noEvent"/>
       </jstl:if>
       <jstl:if test="${notification.foundRefuge}">
       <spring:message code = "notification.modal.foundRefuge"/>
       </jstl:if>
    </div>
    <div class="modal-footer">
      <a href="notification/player/display.do?notificationId=${notification.id}" class="modal-close waves-effect waves-green btn-flat"><spring:message code = "notification.modal.accept"/></a>
    </div>
  </div>
</jstl:if>
