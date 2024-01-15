<script type="text/javascript" language="JavaScript">
Calendar.setup(
	{
	  <#list params?keys as key>    
      ${key} : ${params[key]}
      <#if key_has_next>,</#if>  
    </#list>
	}
);
</script>
                           
