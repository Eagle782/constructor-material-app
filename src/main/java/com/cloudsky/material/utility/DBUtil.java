package com.cloudsky.material.utility;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.cloudsky.material.entity.Project;


public class DBUtil {
	
	public static JdbcTemplate getJdbcTemplate() {

		SimpleDriverDataSource ds = DataSource.getInstance().getDs();
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(ds);
		return template;
	}

	public static List<Map<String, Object>> Query(String sql) {
		List<Map<String, Object>> result = getJdbcTemplate().queryForList(sql);
		return result;
	}
	
	
	public static List<Project> Query(String sql, RowMapper<Project> rowMapper) {
		List<Project> result = getJdbcTemplate().query(sql, rowMapper);
		return result;
	}
	

	public static List<Map<String, Object>> Query(String sql, final Object[] args) {
			List<Map<String, Object>> result = getJdbcTemplate().queryForList(sql,args);
			return result;
	}
		

	
	public static Integer QueryForInt(String sql) {
		Integer result = getJdbcTemplate().queryForObject(sql, Integer.class);
		return result;
	}
	

	public static Integer QueryForInt(String sql,final Object[] args) {
			Integer result = getJdbcTemplate().queryForObject(sql, args, Integer.class);
			return result;
	}

	public static Map<String, Object> QueryForStr(String sql) {
		Map<String, Object> result = getJdbcTemplate().queryForMap(sql);
		return result;
	}
	
	public static String queryFirstCell(String sql, String cellName) {
		String result = "";
		List<Map<String, Object>> row = getJdbcTemplate().queryForList(sql);
		if (row.size() > 0) {
			Object cell = row.get(0).get(cellName);
			if (cell != null) {
				result = cell.toString();
			}

		}
		return result;
	}

	public static int update(String sql) {
		return getJdbcTemplate().update(sql);

	}
	
	public static int update(String sql, final Object[] args){
		return getJdbcTemplate().update(sql,args);
	}

	
	
	public static boolean batchUpdate(final String[] sqls) {
		SimpleDriverDataSource ds = DataSource.getInstance().getDs();
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(ds);
		transactionTemplate.setTransactionManager(transactionManager);
		return (Boolean) transactionTemplate .execute(new TransactionCallback<Boolean>() {
				public Boolean doInTransaction(TransactionStatus status) {
					boolean flag = false;
					try {
						// JdbcTemplate batchUpdate操作
						getJdbcTemplate().batchUpdate(sqls);
						flag = true;
					} catch (Exception e) {
						System.out.println("RuntimeException:-----" + e);
						status.setRollbackOnly(); // 回滚
						flag = false;
					}
					return flag;
				}
			});
	}
	
	
	public static boolean batchupdate(final String sql, final Object[] args) {
		SimpleDriverDataSource ds = DataSource.getInstance().getDs();
		TransactionTemplate transactionTemplate = new TransactionTemplate();
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(ds);
		transactionTemplate.setTransactionManager(transactionManager);
		return (Boolean) transactionTemplate.execute(new TransactionCallback<Boolean>() {
				public Boolean doInTransaction(TransactionStatus status) {
					boolean flag = false;
					try {
						getJdbcTemplate().batchUpdate(sql,
								new BatchPreparedStatementSetter() {
									public void setValues( PreparedStatement ps, int length)
											throws SQLException {
										for (int i = 0; i < args.length; i++) {
											ps.setString(i + 1, args[i].toString());
										}

									}
									@Override
									public int getBatchSize() {
										return 0;
									}
								});
						flag = true;
					} catch (Exception e) {
						System.out.println("RuntimeException:-----" + e);
						status.setRollbackOnly(); // 回滚
						flag = false;
					}
					return flag;
				}
			});
	}

}
