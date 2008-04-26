<%@include file="../includes.jsp"%>

	<h:panelGrid styleClass="vertical-menu-cell" columnClasses="optionList"
		columns="1" cellspacing="0" cellpadding="0">

		<rich:dropDownMenu
			style="border:1px solid #{a4jSkin.panelBorderColor}" value="Adversiments"
			direction="bottom-right" jointPoint="tr">

			<rich:menuItem value="Insert"  />

			<rich:menuItem value="Search" action="index"/>

		</rich:dropDownMenu>
		
		<rich:dropDownMenu
			style="border:1px solid #{a4jSkin.panelBorderColor}" value="Sponsors"
			direction="bottom-right" jointPoint="tr">

			<rich:menuItem value="Login"/>

			<rich:menuItem value="How-to"/> 

		</rich:dropDownMenu>



	</h:panelGrid>


