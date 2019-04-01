package com.beeworkshop.mapper;

import java.util.List;

import com.beeworkshop.model.Staff;

public interface StaffMapper {
	// 查询数据库表中的所有数据
	List<Staff> listStaff();

	//批量插入数据
	int insertBatch(List<Staff> staffs);
}
