package com.ebeijia.mapper;

import com.ebeijia.models.UsrLogininfo;
import com.ebeijia.models.UsrLogininfoKey;

public interface UsrLogininfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr_logininfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int deleteByPrimaryKey(UsrLogininfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr_logininfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int insert(UsrLogininfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr_logininfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int insertSelective(UsrLogininfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr_logininfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    UsrLogininfo selectByPrimaryKey(UsrLogininfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr_logininfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int updateByPrimaryKeySelective(UsrLogininfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usr_logininfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int updateByPrimaryKey(UsrLogininfo record);
}