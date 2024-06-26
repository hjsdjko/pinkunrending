package com.entity.model;

import com.entity.DanshengongdanErEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 二级单位审核
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class DanshengongdanErModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 贫困申请
     */
    private Integer pinkuhushenqingId;


    /**
     * 二级单位
     */
    private Integer erjidanweiId;


    /**
     * 审核编号
     */
    private String danshengongdanErUuidNumber;


    /**
     * 审核状态
     */
    private Integer danshengongdanErYesnoTypes;


    /**
     * 审核意见
     */
    private String danshengongdanErYesnoText;


    /**
     * 审核时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date danshengongdanErShenheTime;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：贫困申请
	 */
    public Integer getPinkuhushenqingId() {
        return pinkuhushenqingId;
    }


    /**
	 * 设置：贫困申请
	 */
    public void setPinkuhushenqingId(Integer pinkuhushenqingId) {
        this.pinkuhushenqingId = pinkuhushenqingId;
    }
    /**
	 * 获取：二级单位
	 */
    public Integer getErjidanweiId() {
        return erjidanweiId;
    }


    /**
	 * 设置：二级单位
	 */
    public void setErjidanweiId(Integer erjidanweiId) {
        this.erjidanweiId = erjidanweiId;
    }
    /**
	 * 获取：审核编号
	 */
    public String getDanshengongdanErUuidNumber() {
        return danshengongdanErUuidNumber;
    }


    /**
	 * 设置：审核编号
	 */
    public void setDanshengongdanErUuidNumber(String danshengongdanErUuidNumber) {
        this.danshengongdanErUuidNumber = danshengongdanErUuidNumber;
    }
    /**
	 * 获取：审核状态
	 */
    public Integer getDanshengongdanErYesnoTypes() {
        return danshengongdanErYesnoTypes;
    }


    /**
	 * 设置：审核状态
	 */
    public void setDanshengongdanErYesnoTypes(Integer danshengongdanErYesnoTypes) {
        this.danshengongdanErYesnoTypes = danshengongdanErYesnoTypes;
    }
    /**
	 * 获取：审核意见
	 */
    public String getDanshengongdanErYesnoText() {
        return danshengongdanErYesnoText;
    }


    /**
	 * 设置：审核意见
	 */
    public void setDanshengongdanErYesnoText(String danshengongdanErYesnoText) {
        this.danshengongdanErYesnoText = danshengongdanErYesnoText;
    }
    /**
	 * 获取：审核时间
	 */
    public Date getDanshengongdanErShenheTime() {
        return danshengongdanErShenheTime;
    }


    /**
	 * 设置：审核时间
	 */
    public void setDanshengongdanErShenheTime(Date danshengongdanErShenheTime) {
        this.danshengongdanErShenheTime = danshengongdanErShenheTime;
    }
    /**
	 * 获取：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：添加时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
