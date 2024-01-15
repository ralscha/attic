<%@page import="ch.ess.sandbox.perf.TestBean"%> 
<html>
<head>
</head>
<body>
<table>
<%
  TestBean bean = new TestBean();
  for (int i=0; i<TestBean.ROWS; i++) {
%>
  <tr>
<%
    for (int j=0; j<TestBean.COLS; j++) {
%>
    <td>
      <select>
<%
      for (int k=0; k<TestBean.ITEMS; k++) {
        String data = bean.getData()
[i][j][k];
%>
        <option value="<%=i%>_<%=j%>_<%=k%>"><%=data%></option>
<%
      }
%>
      </select>
    </td>
<%
    }
%>
  </tr>
<%
  }
%>
</table>
</body>
</html>