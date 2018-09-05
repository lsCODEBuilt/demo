package com.test.common.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.test.common.untils.DomainSerializable;

/**
 * 结果构建类<br>
 * <p/>
 * How to use:
 * <pre>
 * <p>方式一:</p>
 * <li>Result result = Result.newBuilder().result(Result.Code.SUCCESS).data(data).build();</li>
 * <p>方式二:</p>
 * <li>Result.newBuilder().result(Result.Code.SUCCESS ,data).build();</li>
 * <p>方式三:</p>
 * <li>Result result = Result.newResult(Code.SUCCESS).data(new String[]{"A" ,"B"}).msg("msg");</li>
 * </pre>
 *
 * @author Zero.zhao
 * @author Elve.xu
 * @version 1.2
 */
public final class Result<T> extends DomainSerializable {

    public static final Result FAIL = Result.newResult(Code.FAIL);
    public static final Result SUCCESS = Result.newResult(Code.SUCCESS);
    @JSONField(serialize = false)
    private static final long serialVersionUID = 64655331555509136L;
    /**
     * 结果状态码
     */
    @JSONField(serialize = true)
    private Integer code;

    /**
     * 消息描述
     */
    @JSONField(serialize = true)
    private String msg;

    /**
     * 数据结果
     */
    private T data;

    /**
     * 扩展字段
     */
    private Object ext;

    public Result() {
    }

    /**
     * Builder
     *
     * @param builder builder
     */
    private Result(Builder<T> builder) {
        this.code = builder.resultCode;
        this.msg = builder.resultMsg;
        this.data = builder.data;
        this.ext = builder.ext;
    }

    public static <T> Builder<T> newBuilder() {
        return new Builder<T>();
    }

    public static <T> Result<T> newResult(Code code, T data) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(code.code());
        tResult.setMsg(code.msg());
        tResult.setData(data);
        return tResult;
    }

    public static <T> Result<T> newResult(int code, String msg) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }

    public static <T> Result<T> newResult(int code, String msg, T data) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        tResult.setData(data);
        return tResult;
    }

    public static <T> Result<T> newResult(Code code, T data, Object ext) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(code.code());
        tResult.setMsg(code.msg());
        tResult.setData(data);
        tResult.setExt(ext);
        return tResult;
    }

    public static <T> Result<T> newResult(Code code) {
        return newResult(code, null);
    }

    public Result<T> clazz(Class<T> clazz) {
        return this;
    }

    public static Result fail(String message){
        Result<String> tResult = new Result<String>();
        tResult.setCode(Code.FAIL.code());
        tResult.setMsg(message);
        return tResult;
    }

    public static <T> Result<T> success(int code, String msg, T data) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        tResult.setData(data);
        return tResult;
    }

    public static <T> Result<T> success(int code, String msg) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }

    public static <T> Result<T> success(T data) {
        Result<T> tResult = new Result<T>();
        tResult.setCode(Code.SUCCESS.code());
        tResult.setData(data);
        return tResult;
    }
    public static  Result success() {
        Result tResult = new Result();
        tResult.setCode(Code.SUCCESS.code());
        return tResult;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * 获取编码
     *
     * @return return code
     */
    public Integer code() {
        return code;
    }

    /**
     * 获取 Message
     *
     * @return return message
     */
    public String msg() {
        return msg;
    }

    /**
     * 获取数据(泛型)
     *
     * @return 返回数据
     */
    public T data() {
        return data;
    }

    /**
     * 直接转化成JSON字符串
     *
     * @return JSON字符串
     */
    public String string() {
        return toString();
    }

    /**
     * 直接转化成JSON字符串
     *
     * @return JSON字符串
     */
    public String json() {
        return toJson();
    }

    /**
     * set code
     *
     * @param code code
     */
    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * set message
     *
     * @param msg message
     */
    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * set data
     *
     * @param data data
     */
    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * set ext field
     *
     * @param ext ext value
     */
    public Result<T> ext(Object ext) {
        this.ext = ext;
        return this;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

    /**
     * inner builder
     */
    public static final class Builder<T> {

        private Integer resultCode;
        private String resultMsg;
        private T data;
        private Object ext;

        /**
         * build builder with Code
         *
         * @param resultCode result code
         * @return builder
         */
        public Builder<T> result(Code resultCode) {
            this.resultCode = resultCode.code();
            this.resultMsg = resultCode.msg();
            return this;
        }

        public Builder<T> result(Code resultCode, T data) {
            this.resultCode = resultCode.code();
            this.resultMsg = resultCode.msg();
            this.data = data;
            return this;
        }

        public Builder<T> result(Code resultCode, T data, Object ext) {
            this.resultCode = resultCode.code();
            this.resultMsg = resultCode.msg();
            this.data = data;
            this.ext = ext;
            return this;
        }

        /**
         * build builder with Code
         *
         * @param code result code
         * @return builder
         */
        public Builder<T> result(Integer code, String msg) {
            return result(code, msg, null);
        }

        public Builder<T> code(Code code) {
            return result(code, null);
        }

        /**
         * build builder with Code
         *
         * @param code result code
         * @return builder
         */
        public Builder<T> result(Integer code, String msg, T data) {
            if (!Code.validate(code)) {
                throw new IllegalArgumentException("无效错误码:" + code + " ,@see Code");
            }
            this.resultCode = code;
            this.resultMsg = msg;
            this.data = data;
            return this;
        }

        /**
         * set data
         *
         * @param data data
         * @return return this
         */
        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder ext(Object ext) {
            this.ext = ext;
            return this;
        }

        /**
         * build result Object
         *
         * @return object instance
         */
        public Result<T> build() {
            return new Result<T>(this);
        }

        /**
         * 直接输出JSON String
         *
         * @return result -> json String
         */
        public String string() {
            return new Result<T>(this).toString();
        }

        /**
         * 直接输出JSON
         *
         * @return json
         */
        public String json() {
            return new Result<T>(this).toJson();
        }

    }
}

