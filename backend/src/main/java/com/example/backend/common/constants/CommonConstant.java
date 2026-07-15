package com.example.backend.common.constants;

public class CommonConstant {

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    
    public static final boolean IS_DELETED = true;
	
	public static final boolean IS_NOT_DELETED = false;

    public static final boolean RETRIEVING_ALL = true;
    public static final boolean NOT_RETRIEVING_ALL = false;
	
    public static final int OK = 200;
    public static final int CREATED = 201;

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;

    public static final int INTERNAL_SERVER_ERROR = 500;

    public static final String FILE_TIME_FORMAT = "yyyyMMdd_HHmmss";
}
