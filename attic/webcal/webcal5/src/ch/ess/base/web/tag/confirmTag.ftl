<script type="text/javascript" language="JavaScript">

  <#if (part2?length > 0)>    
    function ${functionName}(val) {
    var agree = confirm("${part1}"+val+"${part2}");
  <#else>    
    function ${functionName}() {
    var agree = confirm("${part1}");
  </#if>

  if (agree)
    return true;
  else
    return false; 
  }
</script>
