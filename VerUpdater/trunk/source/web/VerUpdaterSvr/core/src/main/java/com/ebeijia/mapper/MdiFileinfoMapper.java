package com.ebeijia.mapper;

import com.ebeijia.models.MdiFileinfo;

public interface MdiFileinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mdi_fileinfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int deleteByPrimaryKey(Integer fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mdi_fileinfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int insert(MdiFileinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mdi_fileinfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int insertSelective(MdiFileinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mdi_fileinfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    MdiFileinfo selectByPrimaryKey(Integer fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mdi_fileinfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int updateByPrimaryKeySelective(MdiFileinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mdi_fileinfo
     *
     * @mbggenerated Fri Oct 14 17:02:13 CST 2016
     */
    int updateByPrimaryKey(MdiFileinfo record);
    
    
}