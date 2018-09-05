package com.test.common.dto;

/**
 * 错误码
 *
 * @author misselvexu
 */
public enum Code {

    /**
     * Success Code
     */
    SUCCESS(200, "成功"),

    /**
     * 业务处理失败的状态
     */
    EXECUTE_INVALID(201, "业务处理失败"),

    /**
     * 参数不合法
     **/
    ER_PARAMS(300, "参数不合法"),

    /**
     * 请求失败
     **/
    BAD_REQUEST(400, "请求失败"),

    /**
     * 用户名或密码错误
     **/
    ER_INVALID_PASSWORD(301, "用户名或密码错误"),

    /**
     * 无效Token值
     **/
    ER_INVALID_TOKEN(302, "非法Token"),

    ER_INVALID_REQUEST_HEADER_AUTH(303, "请求头信息缺少Token值[Authorization]"),

    ER_INVALID_REQUEST_HEADER_PUBLICKEY(304, "请求头信息缺少客户端公钥参数值[puk]"),

    ER_INVALID_REQUEST_HEADER_SIGN(305, "请求头信息缺少签名值[sign]"),

    ER_INVALID_SIGN(306, "签名校验错误"),

    ER_INVALID_RSA(307, "参数被篡改"),

    ER_GATEWAY(308, "网关异常"),

    ER_PASSWORD_MUST_NOT_BE_SAME(309, "修改的密码不能与原密码相同"),

    ER_PASSWORD_UPDATE_FAIL(310, "密码修改失败,原密码不正确"),

    ER_UID_NOT_EXIST(311, "无效UID"),

    ER_MOBILE_ALREADY_EXIST(312, "手机号已经存在"),

    ER_ACCOUNT_STATUS(313, "账号状态异常"),

    ER_RE_LOGIN(401, "登录状态已失效,重新登录"),

    /**
     * 请求失败
     **/
    FAIL(500, "请求失败"),

    BUSY(501, "服务器繁忙,稍后重试");

    private final int code;
    private final String msg;

    /**
     * default cont
     */
    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 校验状态代码是否是已知的代码
     *
     * @param code 代码
     * @return return true if code is right otherwise return false
     */
    public static boolean validate(Integer code) {
        Code[] codes = Code.values();
        for (Code temp : codes) {
            if (code == temp.code()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 转换 code
     *
     * @param code code 编码
     * @return return MemberCode instance
     */
    public static Code parseCode(int code) {
        Code[] codes = Code.values();
        for (Code temp : codes) {
            if (code == temp.code()) {
                return temp;
            }
        }
        throw new IllegalArgumentException("无效错误码:" + code + " ,@see cloudx.common.dto");
    }

    /**
     * 批量判断 code
     *
     * @param code input code
     * @param codes except code
     * @return is true
     */
    public static Boolean assertCode(int code, Code... codes) {
        try {
            if (codes != null && codes.length > 0) {
                Code input = Code.parseCode(code);
                for (Code temp : codes) {
                    if (input.equals(temp)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * get code
     *
     * @return return code
     */
    public int code() {
        return code;
    }

    /**
     * get message
     *
     * @return return message desc
     */
    public String msg() {
        return msg;
    }

}
