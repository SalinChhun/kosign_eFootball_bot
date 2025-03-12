package com.salin.kosign_eFootball_bot.common.api;

public enum StatusCode {

    INVALID_TEAM_PAIR(404, "Home team and away team cannot be the same", 404),

    SUCCESS(200, "Success", 200),
    FORBIDDEN(403, "You are not authorized to perform this action", 403),
    NOT_FOUND(404, "Not Found", 404),
    BAD_REQUEST(400, "Bad Request", 400),
    USER_NOT_FOUND(453, "User is not found", 404),
    EMAIL_NOT_FOUND(453, "Email is not found", 404),
    INVALID_EMAIL_FORMAT(453, "Invalid email format", 404),
    BAD_CREDENTIALS(452, "Password is incorrect", 404),
    VERIFY_PASSWORD(452, "Confirm password must be the same with new password", 404),
    EMAIL_EXIST(409, "Email is already exist", 409),
    EMAIL_IS_NUll(404, "Email is null", 404),
    AUTHENTICATION_FAILED(453, "Authentication failed", 404),
    UNAUTHORIZED(401, "Unauthorized", 401),
    INVALID_PINCODE_FORMAT(404, "Pin code must be input only number 6 digit", 404),
    INVALID_PINCODE(404, "Invalid pin code", 404),
    EXPIRED_PINCODE(404, "PIN code has expired.", 404),
    PINCODE_REQUIRED(404, "You need to verify pin code first", 404),
    SEASON_CAN_NOT_DELETE(404, "This season is in use, cannot be deleted", 404),
    SEASON_NOT_FOUND(404, "Season is not found", 404),;

    private final String message;
    private final int code;
    private final int httpCode;

    StatusCode(final int code, final String message, int httpCode) {
        this.message = message;
        this.code = code;
        this.httpCode = httpCode;
    }

    public String getMessage() {

        return this.message;

    }

    public int getCode() {

        return code;

    }

    public int getHttpCode() {

        return httpCode;

    }
}
