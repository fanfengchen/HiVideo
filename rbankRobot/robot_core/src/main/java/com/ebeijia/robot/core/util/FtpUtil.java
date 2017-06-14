package com.ebeijia.robot.core.util;
/*package com.edu.cmhmnscore.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	private static FTPClient ftpClient = new FTPClient();
	private static String encoding = System.getProperty("file.encoding");

	private static final String ftpIp = PropertiesUtils.getProperties("ftp.ip"); // 地址
	private static final String ftpUsr = PropertiesUtils
			.getProperties("ftp.user"); // 用户
	private static final String ftpPwd = PropertiesUtils
			.getProperties("ftp.pwd"); // 密码

	*//**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录,如果是根目录则为“/”
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            本地文件输入流
	 * @return 成功返回true，否则返回false
	 *//*
	public static boolean uploadFile(String ip, int port, String username,
			String password, String path, String filename, InputStream input) {
		boolean result = false;
		try {
			int reply;
			// ftpClient.connect(ip);
			ftpClient.connect(ip, port);// 连接FTP服务器
			// 登录
			boolean ist = ftpClient.login(username, password);
			ftpClient.setControlEncoding(encoding);
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return result;
			}

			// 转移工作目录至指定目录下
			boolean changed = ftpClient.changeWorkingDirectory(path);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//
			if (changed) {
				result = ftpClient.storeFile(filename, input);
			}
			ftpClient.logout();
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage(), e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	public static boolean uploadFile(File file, String removtePath) {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			return uploadFile(ftpIp, FTPClient.DEFAULT_PORT, ftpUsr, ftpPwd,
					removtePath, file.getName(), is);
		} catch (FileNotFoundException e) {
			LoggerUtil.error(e);
			return false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LoggerUtil.error(e);
				}
			}
		}

	}

}
*/