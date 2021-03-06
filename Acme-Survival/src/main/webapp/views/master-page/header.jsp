<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<link rel="stylesheet" type="text/css" href="./styles/newStyle.css">

<security:authorize access="hasRole('PLAYER')">
	<script>
// 		$(document).ready(function() {
// 			$.get("notification/player/numberNotifications.do", function(data, status) {
// 				$('#numNotifications').htm(data);
// 			});
// 		});
	</script>
</security:authorize>
<!-- <div class = "crop">
	<a href="welcome/index.do"> <img class="banner img-responsive" src="https://i.ytimg.com/vi/BzX6jMalUck/maxresdefault.jpg"
		alt="Acme Co., Inc." />
	</a>
</div> -->
<div class="navbar-fixed">
	<nav>
		<div class="nav-wrapper metal-wasted">
			<img width="240" style="position: absolute"
				src="images/bannerheader.png" /> <a class="brand-logo" href="#">&#160;&#160;Acme&#160;<img
				width="24" src="images/people.png" />&#160;Template
			</a>

			<ul id="nav-mobile" class="right hide-on-med-and-down">
				<security:authorize access="isAuthenticated()">

					<li class="li-separation"><a class="fNiv valign-wrapper"
						href="forum/list.do"> <img class="icon-image" width="48"
							src="images/icons/Items/enchanting-book.png" /> <spring:message
								code="master.page.forum.list" /></a></li>
					<li class="li-separation"><a class="fNiv valign-wrapper"
						href="product/list.do"> <img class="icon-image" width="48"
							src="images/icons/Currency/currency-large-gold-stack.png" /> <spring:message
								code="master.page.product.list" /></a></li>

					<security:authorize access="hasRole('ADMIN')">
						<!-- Dropdown Structure -->
						<ul id="dropdownAdminProfile" class="dropdown-content">
							<li><a href="actor/admin/edit.do"><spring:message
										code="master.page.actorEdit" /></a></li>
							<li class="divider"></li>
							<li><a href="actor/display.do"><spring:message
										code="master.page.actorProfile" /></a></li>
							<li class="divider"></li>
							<li><a href="j_spring_security_logout"><spring:message
										code="master.page.logout" /> </a></li>
						</ul>

						<!-- Dropdown Trigger -->
						<li class="valign-wrapper li-separation"><img
							class="icon-image-nomargin" width="48"
							src="images/icons/keysnew/key_007.png" /><a
							class="dropdown-button" href="#!"
							data-activates="dropdownAdminProfile"><security:authentication
									property="principal.username" /><i
								class="material-icons right">arrow_drop_down</i></a></li>

					</security:authorize>

					<security:authorize access="hasRole('MANAGER')">
						<!-- Dropdown Structure -->
						<ul id="dropdownManagerProfile" class="dropdown-content">
							<li><a href="actor/manager/edit.do"><spring:message
										code="master.page.actorEdit" /></a></li>
							<li class="divider"></li>
							<li><a href="actor/display.do"><spring:message
										code="master.page.actorProfile" /></a></li>
							<li class="divider"></li>
							<li><a href="j_spring_security_logout"><spring:message
										code="master.page.logout" /> </a></li>
						</ul>

						<!-- Dropdown Trigger -->
						<li class="valign-wrapper li-separation"><img
							class="icon-image-nomargin" width="48"
							src="images/icons/keysnew/key_007.png" /><a
							class="dropdown-button" href="#!"
							data-activates="dropdownManagerProfile"><security:authentication
									property="principal.username" /><i
								class="material-icons right">arrow_drop_down</i></a></li>

					</security:authorize>


					<security:authorize access="hasRole('PLAYER')">
						<!-- Dropdown Structure -->
						<ul id="dropdownPlayerProfile" class="dropdown-content">
							<li><a href="actor/player/edit.do"><spring:message
										code="master.page.actorEdit" /></a></li>
							<li class="divider"></li>
							<li><a href="actor/display.do"><spring:message
										code="master.page.actorProfile" /></a></li>
							<li class="divider"></li>
							<li><a href="j_spring_security_logout"><spring:message
										code="master.page.logout" /> </a></li>
						</ul>

						<!-- Dropdown Trigger -->
						<li class="valign-wrapper li-separation"><img
							class="icon-image-nomargin" width="48"
							src="images/icons/keysnew/key_007.png" /><a
							class="dropdown-button" href="#!"
							data-activates="dropdownPlayerProfile"><security:authentication
									property="principal.username" /><i
								class="material-icons right">arrow_drop_down</i></a></li>

					</security:authorize>


					<security:authorize access="hasRole('MODERATOR')">
						<!-- Dropdown Structure -->
						<ul id="dropdownModeratorProfile" class="dropdown-content">
							<li><a href="actor/moderator/edit.do"><spring:message
										code="master.page.actorEdit" /></a></li>
							<li class="divider"></li>
							<li><a href="actor/display.do"><spring:message
										code="master.page.actorProfile" /></a></li>
							<li class="divider"></li>
							<li><a href="j_spring_security_logout"><spring:message
										code="master.page.logout" /> </a></li>
						</ul>

						<!-- Dropdown Trigger -->
						<li class="valign-wrapper li-separation"><img
							class="icon-image-nomargin" width="48"
							src="images/icons/keysnew/key_007.png" /><a
							class="dropdown-button" href="#!"
							data-activates="dropdownModeratorProfile"><security:authentication
									property="principal.username" /><i
								class="material-icons right">arrow_drop_down</i></a></li>

					</security:authorize>


					<security:authorize access="hasRole('DESIGNER')">
						<!-- Dropdown Structure -->
						<ul id="dropdownDesignerProfile" class="dropdown-content">

							<li><a href="actor/designer/edit.do"><spring:message
										code="master.page.actorEdit" /></a></li>
							<li class="divider"></li>
							<li><a href="actor/display.do"><spring:message
										code="master.page.actorProfile" /></a></li>
							<li class="divider"></li>
							<li><a href="j_spring_security_logout"><spring:message
										code="master.page.logout" /> </a></li>

						</ul>

						<!-- Dropdown Trigger -->

						<li class="valign-wrapper li-separation"><img
							class="icon-image-nomargin" width="48"
							src="images/icons/keysnew/key_007.png" /><a
							class="dropdown-button" href="#!"
							data-activates="dropdownDesignerProfile"><security:authentication
									property="principal.username" /><i
								class="material-icons right">arrow_drop_down</i></a></li>

					</security:authorize>
				</security:authorize>
			</ul>

			<ul id="nav-mobile" class="right hide-on-med-and-down">
				<!-- id="jMenu" -->
				<!-- Do not forget the "fNiv" class for the first level links !! -->

				<security:authorize access="hasRole('ADMIN')">
					<!-- Dropdown Structure -->
					<ul id="dropdownAdminFunctions" class="dropdown-content">
						<li><a href="actor/admin/register.do"><spring:message
									code="master.page.registerAdmin" /></a></li>
						<li class="divider"></li>
						<li><a href="actor/admin/registerManager.do"><spring:message
									code="master.page.registerManager" /></a></li>
						<li class="divider"></li>
						<li><a href="actor/admin/registerModerator.do"><spring:message
									code="master.page.registerModerator" /></a></li>
						<li class="divider"></li>
						<li><a href="actor/admin/registerDesigner.do"><spring:message
									code="master.page.registerDesigner" /></a></li>
						<li class="divider"></li>
						<li><a href="dashboard/admin/list.do"><spring:message
									code="master.page.dashboardList" /></a></li>
						<li class="divider"></li>
						<li><a href="configuration/admin/list.do"><spring:message
									code="master.page.configuration" /></a></li>
						<li class="divider"></li>
						<li><a href="actor/admin/list-actors.do"><spring:message
									code="master.page.list.actors" /></a></li>
					</ul>

					<!-- Dropdown Trigger -->
					<li class="valign-wrapper"><img class="icon-image-nomargin"
						width="48" src="images/icons/wands/wands_003.png" /><a
						class="dropdown-button" href="#!"
						data-activates="dropdownAdminFunctions"><spring:message
								code="master.page.admin" /><i class="material-icons right">arrow_drop_down</i></a></li>
				</security:authorize>

				<security:authorize access="hasRole('PLAYER')">
					<!-- Dropdown Structure -->
					<ul id="dropdownPlayerFunctions" class="dropdown-content">
						<li><a href="shelter/player/display.do"><spring:message
									code="master.page.myShelter" /></a></li>
						<li class="divider"></li>
						<li><a href="notification/player/list.do"><spring:message
									code="master.page.myNotifications" /></a></li>
						<li class="divider"></li>
						<li><a href="item/player/armory.do"><spring:message
									code="master.page.armory" /></a></li>

					</ul>

					<!-- Dropdown Trigger -->
					<li class="valign-wrapper"><img class="icon-image-nomargin"
						width="48" src="images/icons/wands/wands_003.png" /><a
						class="dropdown-button" href="#!"
						data-activates="dropdownPlayerFunctions"><spring:message
								code="master.page.player" /><i class="material-icons right">arrow_drop_down</i></a></li>
				</security:authorize>

				<security:authorize access="hasRole('DESIGNER')">
					<!-- Dropdown Structure -->
					<ul id="dropdownDesignerFunctions" class="dropdown-content">
						<li><a href="location/designer/display.do"><spring:message
									code="master.page.displayLocation" /></a></li>
						<li class="divider"></li>
						<li><a href="roomDesign/list.do"><spring:message
									code="master.page.room.design.list.final" /></a></li>
						<li class="divider"></li>
						<li><a href="roomDesign/designer/list.do"><spring:message
									code="master.page.room.design.list.draft" /></a></li>
						<li class="divider"></li>
						<li><a
							href="itemDesign/designer/list.do?tool=true&finalMode=true"><spring:message
									code="master.page.designer.tools.final" /></a></li>
						<li class="divider"></li>
						<li><a
							href="itemDesign/designer/list.do?tool=true&finalMode=false"><spring:message
									code="master.page.designer.tools.notFinal" /></a></li>
						<li class="divider"></li>
						<li><a
							href="itemDesign/designer/list.do?tool=false&finalMode=true"><spring:message
									code="master.page.designer.resources.final" /></a></li>
						<li class="divider"></li>
						<li><a
							href="itemDesign/designer/list.do?tool=false&finalMode=false"><spring:message
									code="master.page.designer.resources.notFinal" /></a></li>
						<li class="divider"></li>
						<li><a href="event/designer/list.do"><spring:message
									code="master.page.events-notfinal" /> </a></li>
						<li class="divider"></li>
						<li><a href="event/designer/list-final.do"><spring:message
									code="master.page.events-final" /> </a></li>
						<li class="divider"></li>
						<li><a href="lootTable/designer/list.do"><spring:message
									code="master.page.lootTables" /> </a></li>
						<li class="divider"></li>
						<li><a href="designerConfiguration/designer/list.do"><spring:message
									code="master.page.designerConfiguration" /> </a></li>
					</ul>

					<!-- Dropdown Trigger -->
					<li class="valign-wrapper"><img class="icon-image-nomargin"
						width="48" src="images/icons/wands/wands_003.png" /><a
						class="dropdown-button" href="#!"
						data-activates="dropdownDesignerFunctions"><spring:message
								code="master.page.designer" /><i class="material-icons right">arrow_drop_down</i></a></li>
				</security:authorize>

				<security:authorize access="hasRole('MANAGER')">
					<!-- Dropdown Structure -->
					<ul id="dropdownManagerFunctions" class="dropdown-content">
						<li><a href="product/manager/list.do"><spring:message
									code="master.page.manager.products" /></a></li>
					</ul>

					<!-- Dropdown Trigger -->
					<li class="valign-wrapper"><img class="icon-image-nomargin"
						width="48" src="images/icons/wands/wands_003.png" /><a
						class="dropdown-button" href="#!"
						data-activates="dropdownManagerFunctions"><spring:message
								code="master.page.manager" /><i class="material-icons right">arrow_drop_down</i></a></li>
				</security:authorize>

				<security:authorize access="isAnonymous()">
					<li><a class="fNiv" href="security/login.do"> <spring:message
								code="master.page.login" /></a></li>
					<li><a class="fNiv" href="actor/registerAsPlayer.do"> <spring:message
								code="master.page.registerAsPlayer" /></a></li>
					<li class="li-separation"><a class="fNiv valign-wrapper"
						href="forum/list.do"> <img class="icon-image" width="48"
							src="images/icons/Items/enchanting-book.png" /> <spring:message
								code="master.page.forum.list" /></a></li>
					<li class="li-separation"><a class="fNiv valign-wrapper"
						href="product/list.do"> <img class="icon-image" width="48"
							src="images/icons/Currency/currency-large-gold-stack.png" /> <spring:message
								code="master.page.product.list" /></a></li>
				</security:authorize>
			</ul>
		</div>
	</nav>
</div>
<p></p>


