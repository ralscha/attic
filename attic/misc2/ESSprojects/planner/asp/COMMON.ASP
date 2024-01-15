<% 
 function showKind(color)      
     dim kindquery, kindrs
     set kindrs = Server.CreateObject("ADODB.RecordSet")
     kindquery = "select KindID, Description from DateKind where KindID = '" & color & "'"
     kindrs.open kindquery, cnn, adOpenStatic, adLockOptimistic     
     
     if (kindrs.RecordCount > 0) then
       showKind = kindrs("Description")
     else
       showKind = ""
     end if
     kindrs.close
   end function


   function formatDate(adate)
     dim d, m, y
     
     d = day(adate)
     m = month(adate)
     y = year(adate)
     
     if d < 10 then
       d = "0" & d
     end if
     
     if m < 10 then
       m = "0" & m
     end if
       
     formatDate = d & "." & m & "." & y
     
   end function
%>
