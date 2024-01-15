/*
 * menuDropdown.js - implements an dropdown menu based on a HTML list
 * Author: Dave Lindquist (dave@gazingus.org)
 */

var currentMenu = null;

if (!document.getElementById) {
    document.getElementById = function() { return null; }
}

function initializeMenu(menuId, actuatorId) {
    var menu = document.getElementById(menuId);
    var actuator = document.getElementById(actuatorId);

    if (menu == null || actuator == null) return;

    //if (window.opera) return; // I'm too tired

    actuator.onmouseover = function() {
        if (currentMenu) {
            currentMenu.style.visibility = "hidden";
            this.showMenu();
        }
    }
  
    actuator.onclick = function() {
        if (currentMenu == null) {
            this.showMenu();
        }
        else {
            currentMenu.style.visibility = "hidden";
            currentMenu = null;
        }

        return false;
    }

    actuator.showMenu = function() {   
        menu.style.left = (23 + this.offsetLeft) + "px";
        menu.style.top = (26 + this.offsetTop) + this.offsetHeight + "px";
        menu.style.visibility = "visible";
        currentMenu = menu;
    }
}

function expandMenus() {
    // empty method - this is here b/c the ListDisplayer
    // calls this method for expanding menus and the list
    // type is determined by JavaScript, rather than Java
}