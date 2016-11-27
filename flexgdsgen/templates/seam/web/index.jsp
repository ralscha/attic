<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <title>@projectName@</title>

    <style type="text/css" media="screen">
      html, body, #alternativeContent { height:100%; }
      body { margin:0; padding:0; overflow:hidden; }
      object { display:block; }
    </style>

    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <script type="text/javascript" src="assets/swfobject.js"></script>
    <script type="text/javascript">
      var flashvars = {contextPath: '<%= request.getContextPath() %>'};
      var params = {};
      var attributes = {};
      attributes.id = "@projectNameLowerCase@";
      swfobject.embedSWF("@projectNameLowerCase@.swf", "alternativeContent", "100%", "100%", "10", "expressInstall.swf", flashvars, params, attributes);
    </script>
</head>
  <body>
    <div id="alternativeContent">
      <a href="http://www.adobe.com/go/getflashplayer">
        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" />
      </a>
    </div>
  </body>
</html>