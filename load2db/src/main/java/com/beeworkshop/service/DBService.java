package com.beeworkshop.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.beeworkshop.model.Staff;
import com.beeworkshop.utils.impl.MybatisUtil;

public class DBService {
	public List<Staff> listStaff() {
		SqlSession session = null;

		try {
			session = MybatisUtil.getSqlSession();
			List<Staff> ls = session.selectList("com.beeworkshop.mapper.StaffMapper.listStaff");
			return ls;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public int insertBatch(List<Staff> staffs) {
		SqlSession session = null;
		try {
			session = MybatisUtil.getSqlSession();
			int rs = session.insert("com.beeworkshop.mapper.StaffMapper.insertBatch", staffs);
			session.commit();
			return rs;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
