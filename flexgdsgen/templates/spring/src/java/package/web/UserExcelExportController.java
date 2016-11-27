package @packageProject@.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import @packageProject@.entity.User;
import @packageProject@.service.UserService;

@Controller
public class UserExcelExportController {

  @Autowired
  private UserService userService;

  @RequestMapping("/userExcelExport.action")
  @Secured("ROLE_ADMIN")
  public void export(HttpServletResponse response) throws IOException {

    //access to logged in user
    //String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
    
    List<User> users = userService.findAll();

    response.setContentType("application/vnd.ms-excel");
    response.setHeader("extension", "xls");

    OutputStream out = response.getOutputStream();

    Workbook workbook = new HSSFWorkbook();
    CreationHelper createHelper = workbook.getCreationHelper();

    CellStyle headerStyle = workbook.createCellStyle();
    Font headerFont = workbook.createFont();
    headerFont.setFontHeightInPoints((short)10);
    headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
    headerStyle.setFont(headerFont);

    Sheet sheet = workbook.createSheet();

    sheet.setColumnWidth(0, 20 * 256);
    sheet.setColumnWidth(1, 20 * 256);
    sheet.setColumnWidth(2, 20 * 256);

    Row row = sheet.createRow(0);

    Cell c = row.createCell(0);
    c.setCellStyle(headerStyle);
    c.setCellValue(createHelper.createRichTextString("Username"));

    c = row.createCell(1);
    c.setCellStyle(headerStyle);
    c.setCellValue(createHelper.createRichTextString("First Name"));

    c = row.createCell(2);
    c.setCellStyle(headerStyle);
    c.setCellValue(createHelper.createRichTextString("Last Name"));

    int rowNo = 1;
    for (User user : users) {
      row = sheet.createRow(rowNo);

      c = row.createCell(0);
      c.setCellValue(createHelper.createRichTextString(user.getUserName()));

      c = row.createCell(1);
      c.setCellValue(createHelper.createRichTextString(user.getFirstName()));

      c = row.createCell(2);
      c.setCellValue(createHelper.createRichTextString(user.getName()));

      rowNo++;
    }

    workbook.write(out);

    out.close();

  }
}