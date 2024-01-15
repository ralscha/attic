
  <script language="JavaScript1.3">

    
    function clientDetect() {
        window.opener = null;

        form = document.forms[0];
            
        form.appName.value = navigator.appName;
        form.appVersion.value = navigator.appVersion;
        form.timeZoneOffset.value = 0 - parseInt((new Date()).getTimezoneOffset());

        if (window.body) {
            if (window.body.clientWidth) {
                form.initialWindowWidth.value = window.body.clientWidth;
                form.initialWindowHeight.value = window.body.clientHeight;
                form.chromeWidth.value = window.body.offsetWidth - window.body.clientWidth;
                form.chromeHeight.value = window.body.offsetHeight - window.body.clientHeight;
            }
        }

        if (window.innerWidth) {
            form.initialWindowWidth.value = window.innerWidth;
            form.initialWindowHeight.value = window.innerHeight;
            form.chromeWidth.value = window.outerWidth - window.innerWidth;
            form.chromeHeight.value = window.outerHeight - window.innerHeight;
        }

        if (window.screen) {
            form.screenWidth.value = window.screen.width;
            form.screenHeight.value = window.screen.height;
            form.colorDepth.value = window.screen.colorDepth;
        }
        
        if (window.menubar) {
            form.initialWindowHasMenuBar.value = window.menubar.visible ? "1" : "0";
        }
        if (window.toolbar) {
            form.initialWindowHasToolBar.value = window.toolbar.visible ? "1" : "0";
        }
        if (window.statusbar) {
            form.initialWindowHasStatusBar.value = window.statusbar.visible ? "1" : "0";
        }
        
        form.jsVersion.value = getJavaScriptVersion();

    }
    

    
  </script>

  <script language="JavaScript1.3">
  
    function getJavaScriptVersion() {
        return "1.3";
    }

  </script>
    
  <script language="JavaScript1.4">
  
    function getJavaScriptVersion() {
        return "1.4";
    }
    
  </script>

  <script language="JavaScript1.5">
  
    function getJavaScriptVersion() {
        return "1.5";
    }
    
  </script>