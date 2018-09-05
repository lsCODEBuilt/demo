package com.test.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
* @author SimonSun
* @create 2018-09-05 17:09:09
**/
@ApiModel(description="t_member")
@Data
@Entity
@Table(name="t_member")
public class Member implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "创建人名称")
    @Column(name = "create_name")
    private String createName;

    @ApiModelProperty(value = "创建人登录名称")
    @Column(name = "create_by")
    private String createBy;

    @ApiModelProperty(value = "创建日期")
    @Column(name = "create_date")
    private Date createDate;

    @ApiModelProperty(value = "更新人名称")
    @Column(name = "update_name")
    private String updateName;

    @ApiModelProperty(value = "更新人登录名称")
    @Column(name = "update_by")
    private String updateBy;

    @ApiModelProperty(value = "更新日期")
    @Column(name = "update_date")
    private Date updateDate;

    @ApiModelProperty(value = "账号")
    @Column(name = "account_no")
    private String accountNo;

    @ApiModelProperty(value = "密码")
    @Column(name = "password")
    private String password;

    @ApiModelProperty(value = "姓名")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "证件类型")
    @Column(name = "card_type")
    private String cardType;

    @ApiModelProperty(value = "证件号")
    @Column(name = "card_no")
    private String cardNo;

    @ApiModelProperty(value = "证据前面影像")
    @Column(name = "card_front_url")
    private String cardFrontUrl;

    @ApiModelProperty(value = "证件后面影像")
    @Column(name = "card_back_url")
    private String cardBackUrl;

    @ApiModelProperty(value = "头像")
    @Column(name = "head_url")
    private String headUrl;

    @ApiModelProperty(value = "背景图")
    @Column(name = "background_url")
    private String backgroundUrl;

    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name")
    private String nickName;

    @ApiModelProperty(value = "性别")
    @Column(name = "sex")
    private String sex;

    @ApiModelProperty(value = "地区")
    @Column(name = "region")
    private String region;

    @ApiModelProperty(value = "生日")
    @Column(name = "birthday")
    private Date birthday;

    @ApiModelProperty(value = "个人签名")
    @Column(name = "personal_signature")
    private String personalSignature;

    @ApiModelProperty(value = "职业")
    @Column(name = "profession")
    private String profession;

    @ApiModelProperty(value = "兴趣爱好")
    @Column(name = "interest")
    private String interest;

    @ApiModelProperty(value = "乐龄")
    @Column(name = "remix_days")
    private Integer remixDays;

    @ApiModelProperty(value = "年龄")
    @Column(name = "age_stage")
    private String ageStage;

    @ApiModelProperty(value = "星座")
    @Column(name = "constellation")
    private String constellation;

    @ApiModelProperty(value = "会员等级")
    @Column(name = "member_grade")
    private Integer memberGrade;

    @ApiModelProperty(value = "会员类型")
    @Column(name = "member_type")
    private Integer memberType;

    @ApiModelProperty(value = "完整百分比")
    @Column(name = "complete_percentage")
    private Double completePercentage;

    @ApiModelProperty(value = "登陆方式")
    @Column(name = "login_type")
    private String loginType;

    @ApiModelProperty(value = "状态")
    @Column(name = "status")
    private String status;

    @ApiModelProperty(value = "passwrod")
    @Column(name = "passwrod")
    private String passwrod;
}