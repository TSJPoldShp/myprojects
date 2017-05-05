package com.education.zfr.common.utils;

public final class Constants {

	private Constants() {
	}

	public static final String DELIMETER_COMMA = ",";

	public static final String HTTP_PARAMETER_ERROR = "101";

	public static final String HTTP_AUTH_ERROR = "201";

	public static final String HTTP_SYSTEM_ERROR = "300";

	public static final String HTTP_SYSTEM_OK = "200";

	public static final String HTTP_SYSTEM_WARN = "206";

	public static final String MESSAGE_UNKNOWN_ERROR = "未知错误！";

	public static final String MESSAGE_OPERATION_ERROR = "操作失败!";

	public static final String MESSAGE_OPERATION_OK = "操作成功!";

	public static final String UNKNOWN_STRING = "unknown";

	public static final String HASH_ALGORITHM = "SHA-1";

	public static final int HASH_INTERATIONS = 1024;

	public static final int SALT_SIZE = 8;

	public static final int DEFAULT_PRIORITY = 5;

	public static final int DEFAULT_POOLSIZE = 10;

	public static final String ROLE_NORMAL = "normal";

	public static final String ROLE_SYS = "sys";

	public static final String BUTTON_PREFIX = "button_";

	public static final String JOB_DATA_KEY = "jobDataKey";

	public static final String SESSION_LOGINNAME = "loginName";

	public static final String SESSION_MENUDISPLAY = "menuDisplay";

	public static final long PARENT = 0L;

	public static final int ENUM_YES = 1;

	public static final int ENUM_NO = 0;

	public static final String CODE_FAILED = "0";

	public static final String CODE_SUCCESS = "1";

	public static final String UTF_8 = "UTF-8";

	public static final String VIDEO_TYPE_TS_SUF = ".ts";

	public static final String SUCCESS = "success";

	/**
	 * 成功
	 */
	public static final String API_CODE_OK = "000";

	public static final String API_MESSAGE_OK = "成功";

	/**
	 * 参数错误
	 */
	public static final String API_CODE_PARAM_ERROR = "HV-310";

	public static final String API_MESSAGE_PARAM_ERROR = "参数错误";

	/**
	 * 数据不存在
	 */
	public static final String API_CODE_DATA_NOTEXIST = "HV-320";

	public static final String API_MESSAGE_DATA_NOT_EXIST = "不存在对应数据";

	public static final String API_CODE_PROCESS_ERROR = "HV-400";

	public static final String API_MESSGE_PROCESS_ERROR = "处理数据异常,请稍后重试";

	/**
	 * 数据库连接异常
	 */
	public static final String API_CODE_DB_ERROR = "HV-910";

	public static final String API_MESSAGE_DB_ERROR = "访问数据异常";

	public static final String INNER_CODE_OK = "INNER-HV-00000";

	public static final String INNER_CODE_COS_COMMUNICATION_ERROR = "INNER-HV-00100";

	public static final String INNER_MESSAGE_COS_COMMUNICATION_ERROR = "与COS交互异常";

	public static final String INNER_CODE_COS_DATA_ERROR = "INNER-HV-00101";

	public static final String INNER_MESSAGE_COS_DATA_ERROR = "COS返回数据结果错误";

	public static final String INNER_CODE_DMS_COMMUNICATION_ERROR = "INNER-HV-00200";

	public static final String INNER_MESSAGE_DMS_COMMUNICATION_ERROR = "与DMS交互异常";

	public static final String INNER_CODE_DMS_DATA_ERROR = "INNER-HV-00202";

	public static final String INNER_MESSAGE_DMS_DATA_ERROR = "DMS返回数据结果错误";

	public static final String INNER_CODE_MS_COMMUNICATION_ERROR = "INNER-HV-00300";

	public static final String INNER_MESSAGE_MS_COMMUNICATION_ERROR = "与视频服务交互异常";

	public static final String INNER_CODE_MS_PARAM_ERROR = "INNER-HV-00301";

	public static final String INNER_MESSAGE_MS_PARAM_ERROR = "参数错误";
	
	public static final String INNER_CODE_MS_DATA_ERROR = "INNER-HV-00302";

	public static final String INNER_MESSAGE_MS_DATA_ERROR = "视频服务返回数据结果错误";

	public static final String COS_RESULTCODE_OK = "000000";

	public static final String DMS_RESULTCODE_OK = "000";

	public static final String DMS_RESULTCODE_WARN = "350";

	public static final String DMS_RESULTCODE_ERROR_PARAM = "340";

	public static final String DMS_RESULTCODE_ERROR = "999";

	/**
	 * 文件路径分隔符：/
	 */
	public static final String FILE_SEPARATOR = "/";

	/**
	 * utf8编码格式
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * FTP连接模式 PORT
	 */
	public static final String FTP_CONN_MODE_PORT = "PORT";

	/**
	 * FTP连接模式 PASV
	 */
	public static final String FTP_CONN_MODE_PASV = "PASV";

	/**
	 * 上传海报存放相对路径
	 */
	public static final String UPLOAD_DIR = "static/upload";

	/**
	 * COS对接接口参数名称：seriesId
	 */
	public static final String COS_PARAMS_SERIES_ID = "seriesId";

	/**
	 * COS对接接口参数名称：seriesName
	 */
	public static final String COS_PARAMS_SERIES_NAME = "seriesName";

	/**
	 * COS对接接口参数名称：source
	 */
	public static final String COS_PARAMS_SOURCE = "source";

	/**
	 * COS对接接口参数名称：epgGroupId
	 */
	public static final String COS_PARAMS_EPGGROUPID = "epgGroupId";

	/**
	 * COS对接接口参数名称：catgId
	 */
	public static final String COS_PARAMS_CATG_ID = "catgId";

	/**
	 * COS对接接口参数名称：start
	 */
	public static final String COS_PARAMS_START = "start";

	/**
	 * COS对接接口参数名称：limit
	 */
	public static final String COS_PARAMS_LIMIT = "limit";

}
