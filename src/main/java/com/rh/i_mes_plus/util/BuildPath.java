package com.rh.i_mes_plus.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class BuildPath {
	public  static OutputStream  Manual_Saving(HttpServletResponse response, String fileName) throws IOException{
		
		//浏览器自选输出路径
		response.setHeader("Content-disposition", "attachment;filename="+fileName);
		response.setContentType("application/msexcel");

		OutputStream out=response.getOutputStream();
		return out;
	}
}
