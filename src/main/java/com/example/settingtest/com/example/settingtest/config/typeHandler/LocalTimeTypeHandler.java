package com.example.settingtest.com.example.settingtest.config.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalTime;
import java.util.Objects;

public class LocalTimeTypeHandler extends BaseTypeHandler<LocalTime>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LocalTime parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setTime(i, Time.valueOf(parameter));
	}

	@Override
	public LocalTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Time time = rs.getTime(columnName);
		return Objects.nonNull(time) ? time.toLocalTime() : null;
	}

	@Override
	public LocalTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Time time = rs.getTime(columnIndex);
		return Objects.nonNull(time) ? time.toLocalTime() : null;
	}

	@Override
	public LocalTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Time time = cs.getTime(columnIndex);
		return Objects.nonNull(time) ? time.toLocalTime() : null;
	}

}
