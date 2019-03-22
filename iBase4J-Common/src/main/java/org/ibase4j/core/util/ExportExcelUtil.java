package org.ibase4j.core.util;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.ibase4j.model.NameValuePair;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @Description: 导出Excel
 * @Author: dj
 * @CreateDate: 2018-09-11
 */
public class ExportExcelUtil {
    private static final Logger logger = LogManager.getLogger();
    /**
     * 生成excel
     * @param downloadFlag //是否下载  如果下载传true 否 传false
     * @param titleS	   //标题的文字数组
     * @param centerVal	   //内容的二维数组
     * @return
     */
    public static byte[] exportExcel(boolean downloadFlag,String[] titleS,String[][] centerVal,HttpServletResponse response) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            if(titleS != null && titleS.length > 0 && centerVal != null && centerVal.length > 0){
                float mus =65536; //65536
                float avg = centerVal.length / mus;
                int width = 20;
                // 第一步，创建一个webbook，对应一个Excel文件
                XSSFWorkbook wb = new XSSFWorkbook();
                //title设置样式
                XSSFCellStyle titleStyle = wb.createCellStyle();
                titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); //居中格式
                titleStyle.setWrapText(true);//自动换行
                // 标题加粗
                XSSFFont xssfFont = wb.createFont();
                xssfFont.setBold(true);
                titleStyle.setFont(xssfFont);
                //页面设置样式
                XSSFCellStyle centerStyle = wb.createCellStyle();
                centerStyle.setWrapText(true);//自动换行
                centerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
                //当前循环数
                int centerNum = 0;
                for(int sheetNum = 0; sheetNum < avg ; sheetNum++){
                    // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
                    XSSFSheet sheet= wb.createSheet("sheet1" + sheetNum);
                    //设置默认列宽
                    // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
                    XSSFRow row = sheet.createRow(0);
                    // 标题列
                    for(int i = 0; i < titleS.length; i++){
                        //设置列宽
                        sheet.setColumnWidth(i, 252*width+323);
                        //创建标题
                        XSSFCell cell  = row.createCell(i);
                        cell.setCellValue(titleS[i]);
                        cell.setCellStyle(titleStyle);
                    }
                    // 内容区域
                    int iNum = 0;
                    if(centerVal.length > mus * (sheetNum+1)){
                        iNum =  (int)mus;
                    }else{
                        iNum = centerVal.length - (int)mus*sheetNum;
                    }
                    for(int i = 0 ; i < iNum  ; i++){
                        row = sheet.createRow(i + 1);
                        for(int j = 0; j < centerVal[centerNum].length; j++){
                            XSSFCell cell  = row.createCell(j);
                            cell.setCellValue(centerVal[centerNum][j]);
                            cell.setCellStyle(centerStyle);
                        }
                        centerNum ++;
                    }
                }
                if(downloadFlag){
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    wb.write(os);
                    byte[] content = os.toByteArray();
                    InputStream is = new ByteArrayInputStream(content);
                    // 设置response参数
                    response.reset();
                    //response.setContentType("application/vnd.ms-excel;charset=utf-8");
                    response.setContentType("application/octet-stream;charset=utf-8");
                    response.setHeader("Content-Disposition", "attachment;filename="+ new String(("test" + ".xls").getBytes(), "iso-8859-1"));
                    ServletOutputStream out = response.getOutputStream();
                    response.setHeader("Content-Length", String.valueOf(is.available()));
                    bis = new BufferedInputStream(is);
                    bos = new BufferedOutputStream(out);
                    byte[] buff = new byte[2048];
                    int bytesRead;
                    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                        bos.write(buff, 0, bytesRead);
                    }
                  /*  ByteArrayOutputStream os = new ByteArrayOutputStream();
                    wb.write(os);
                    byte[] bytes = os.toByteArray();
                    os.close();
                    return bytes;*/
                }else{
                    FileOutputStream fout = new FileOutputStream("D:/zx.xlsx");
                    wb.write(fout);
                    fout.close();
                    return null;
                }
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        finally
        {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    /**
	 * 导出到excel文件，适应各种表单
	 * @param fileName 导出文件名
	 * @param titles 标题栏
	 * @param dataList 内容List
	 * @param response 
	 * @throws IOException
	 */
	public static void expExcel(String fileName, List<NameValuePair> titles,
			List<Map<String,Object>> dataList, HttpServletResponse response) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// createSheet(excel工作表名)
		HSSFSheet sheet = workbook.createSheet(fileName);
		
		 //下面是设置excel表中标题的样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont titleFont = workbook.createFont();
        titleFont.setColor(HSSFColor.VIOLET.index);
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleStyle.setFont(titleFont);
        
        //内容的样式
        HSSFCellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        contentStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont content_font = workbook.createFont();
        content_font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        contentStyle.setFont(content_font);
        //填充标题内容
        HSSFRow row = sheet.createRow(0);
        if(titles != null && titles.size()>0){
        	logger.debug("----------------标题开始------------------");
        	for (int i = 0; i < titles.size(); i++) 
        	{
        		NameValuePair pair = titles.get(i);
        		String value = StringUtil.objectToString(pair.getValue()) ;
                //设置标题的宽度自适应
                sheet.setColumnWidth(i, value.getBytes().length * 2 * 256);
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(titleStyle);
                HSSFRichTextString text = new HSSFRichTextString(value);
                cell.setCellValue(text);
            }
        	logger.debug("标题结束------------------");
			for (int i = 0; i < dataList.size(); i++) {
				logger.debug("--数据第"+i+"行------------------");
				row = sheet.createRow(i + 1);
				Map<String, Object> data = dataList.get(i);
				for (int j = 0; j < titles.size(); j++) {
					NameValuePair pair = titles.get(j);
	        		String key = pair.getName();
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(contentStyle);
					Object value = data.get(key);
					HSSFRichTextString richString = new HSSFRichTextString(getValueByType(value));
					cell.setCellValue(richString);
				}
				logger.debug("-数据结束------");
			}
        }else{//如果没有标题，则使用数据第一行作为标题行
        	if(dataList!=null && dataList.size()>0){
        		Map<String, Object> titleData = dataList.get(0);
        		Set<String> sets = titleData.keySet();
            	List<String> titleList = new ArrayList(sets);
            	for(int i=0;i<titleList.size();i++){
            		String key = titleList.get(i);
                    //设置标题的宽度自适应
                    sheet.setColumnWidth(i, key.getBytes().length * 2 * 256);
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(titleStyle);
                    HSSFRichTextString text = new HSSFRichTextString(key);
                    cell.setCellValue(text);
                    i++;
                }
            	if(dataList.size()>1){
            		for (int j = 1; j < dataList.size(); j++) {
        				row = sheet.createRow(j);
        				Map<String, Object> data = dataList.get(j);
        				for (String title: titleList) {
        					HSSFCell cell = row.createCell(j);
        					cell.setCellStyle(contentStyle);
        					Object value = data.get(title);
        					HSSFRichTextString richString = new HSSFRichTextString(getValueByType(value));
        					cell.setCellValue(richString);
        				}
        			}
            	}
        	}
        }
		//这里调用reset()因为我在别的代码中调用了response.getWriter();
        response.reset();
        ServletOutputStream ouputStream = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename="+  new String(fileName.getBytes("gb2312"), "ISO8859-1" ));
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
	}
	
	/**
	 * 根据值类型获取字符串
	 * @param obj
	 * @return
	 */
	private static String getValueByType(Object obj){
		if(obj != null){
			if(obj instanceof String){
				return String.valueOf(obj);
			}else if(obj instanceof Date){
				Date value = (Date)obj;
				return DateUtil.format(value, "yyyy-MM-dd");
			}else{
				return obj.toString();
			}
		}else{
			return "";
		}
	}
}

