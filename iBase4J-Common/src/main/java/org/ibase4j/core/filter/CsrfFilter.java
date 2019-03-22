package org.ibase4j.core.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsrfFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(CsrfFilter.class);

	// 白名单
	private List<String> whiteUrls;

	private int _size = 0;

	@Override
    public void init(FilterConfig filterConfig) {
		// 读取文件
		String path = CsrfFilter.class.getResource("/").getFile();
		whiteUrls = readFile(path + "csrfWhite.txt");
		_size = null == whiteUrls ? 0 : whiteUrls.size();
	}

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			// 获取请求url地址
			String url = req.getRequestURL().toString();
            logger.debug("===获取请求url地址===", url);

			String referurl = req.getHeader("Referer");
            logger.debug("referurl----->" + referurl);
			if (isWhiteReq(referurl)) {
				chain.doFilter(request, response);
			} else {

				req.getRequestDispatcher("/").forward(req, res);

				// 记录跨站请求日志
				String log = "";
				String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String clientIp = WebUtil.getHost(req);

				log = clientIp + "||" + date + "||" + referurl + "||" + url;
				logger.warn(log);
				return;
			}

		} catch (Exception e) {
			logger.error("doFilter", e);
		}

	}

	/*
	 * 判断是否是白名单
	 */
	private boolean isWhiteReq(String referUrl) {
		if (referUrl == null || "".equals(referUrl) || _size == 0) {
			return true;
		} else {
			String refHost = "";
			referUrl = referUrl.toLowerCase();
			if (referUrl.startsWith("http://")) {
				refHost = referUrl.substring(7);
			} else if (referUrl.startsWith("https://")) {
				refHost = referUrl.substring(8);
			}
			
			for (String urlTemp : whiteUrls) {
				logger.info("check----->" + refHost+"-----"+urlTemp.toLowerCase());
				if(refHost.equals(urlTemp.toLowerCase())){
					return true;
				}else if (refHost.indexOf(urlTemp.toLowerCase()) > -1) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
    public void destroy() {
	}

	private List<String> readFile(String fileName) {
		List<String> list = new ArrayList<String>();
		BufferedReader reader = null;
		FileInputStream fis = null;
		try {
			File f = new File(fileName);
			if (f.isFile() && f.exists()) {
				fis = new FileInputStream(f);
                reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
				String line;
				while ((line = reader.readLine()) != null) {
					if (!"".equals(line)) {
						list.add(line);
					}
				}
			}
		} catch (Exception e) {
			logger.error("readFile", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.error("InputStream关闭异常", e);
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.error("FileInputStream关闭异常", e);
			}
		}
		return list;
	}
}
