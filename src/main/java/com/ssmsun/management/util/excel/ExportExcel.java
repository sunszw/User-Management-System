package com.ssmsun.management.util.excel;

import com.ssmsun.management.entity.User;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Component
public class ExportExcel {

    public void UserExcel(List<User> userData, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户信息表");
        sheet.setColumnWidth(0, 50);

        for (int i = 0; i < userData.size(); i++) {
            HSSFRow row = sheet.createRow(i);

            HSSFCell cell1 = row.createCell(1);
            HSSFRichTextString userid = new HSSFRichTextString(String.valueOf(userData.get(i).getUserid()));
            cell1.setCellValue(userid);

            HSSFCell cell2 = row.createCell(2);
            HSSFRichTextString username = new HSSFRichTextString(userData.get(i).getUsername());
            cell2.setCellValue(username);

            HSSFCell cell3 = row.createCell(3);
            HSSFRichTextString email = new HSSFRichTextString(userData.get(i).getEmail());
            cell3.setCellValue(email);

            HSSFCell cell4 = row.createCell(4);
            HSSFRichTextString phone = new HSSFRichTextString(userData.get(i).getPhone());
            cell4.setCellValue(phone);

            HSSFCell cell5 = row.createCell(5);
            HSSFRichTextString gender = new HSSFRichTextString(String.valueOf(userData.get(i).getGender()));
            cell5.setCellValue(gender);

            HSSFCell cell6 = row.createCell(6);
            HSSFRichTextString amount = new HSSFRichTextString(String.valueOf(userData.get(i).getAmount()));
            cell6.setCellValue(amount);

            HSSFCell cell7 = row.createCell(7);
            HSSFRichTextString vip = new HSSFRichTextString(String.valueOf(userData.get(i).getVip()));
            cell7.setCellValue(vip);

            HSSFCell cell8 = row.createCell(8);
            HSSFRichTextString credate = new HSSFRichTextString(String.valueOf(userData.get(i).getCredate()));
            cell8.setCellValue(credate);

            HSSFCell cell9 = row.createCell(9);
            HSSFRichTextString confirm = new HSSFRichTextString(String.valueOf(userData.get(i).getConfirm()));
            cell9.setCellValue(confirm);

        }

        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户信息.xls", "utf-8"));
        response.setContentType("application/octet-stream");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();

    }

}
