package com.bj.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bj.domain.MemCodeCacher;
import com.bj.util.JdbcUtil;

public class LoadCache {
	
	public LoadCache(String host_id,String thread_id){
		loadHostParm(host_id);
		loadFtpParm(thread_id);
	}
	
	private void loadHostParm(String host_id){
		
		/**
		 * 加载主机信息sql
		 */
		String host_para_sql = "select host_id, host_ip, user_name  from conf_host_para where host_id = ? ";
		
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(host_para_sql);
			ps.setString(1, host_id);
			rs = ps.executeQuery();
			while(rs.next()){
				MemCodeCacher.host_parm.put(MemCodeCacher.HOST_ID, rs.getString(1));
				MemCodeCacher.host_parm.put(MemCodeCacher.HOST_IP, rs.getString(2));
				MemCodeCacher.host_parm.put(MemCodeCacher.USER_NAME,rs.getString(3));
				/*MemCodeCacher.host_parm.put(MemCodeCacher.TCP_IP,rs.getString(3));
				MemCodeCacher.host_parm.put(MemCodeCacher.TCP_PORT,rs.getInt(4));
				MemCodeCacher.host_parm.put(MemCodeCacher.UDP_PORT,rs.getInt(5));
				MemCodeCacher.host_parm.put(MemCodeCacher.LOG_PATH,rs.getString(6));
				MemCodeCacher.host_parm.put(MemCodeCacher.SWITCH_MODE,rs.getInt(7));*/
				//MemCodeCacher.host_parm.put(MemCodeCacher.CONNECT_STRING,rs.getString(9));
			}
			if(MemCodeCacher.host_parm.size()<=0){
				throw new RuntimeException("don't find the host info,the host_id is [ " + host_id + "] , please check DB!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(conn != null){
					conn.close();
				}
				if(ps != null){
					ps.close();
				}
				if(rs != null){
					rs.close();
				}
			} catch (Exception e2) {
				
			}
		}
	}
	
	private void loadFtpParm(String thread_id){
		/**
		 * 加载线程采集信息数据的sql
		 * 
		 * select a.src_id,a.begin_range,a.end_range,a.reserve_files,a.max_deal_files,a.sleep_time,
		 * a.alarm_level,a.stop_flag,b.log_table_name,c.host_id,c.ip_address,c.login_user,
		 * c.login_passwd,c.host_type,c.trans_mode,b.src_dir,b.pattern,
		 * d.host_id,d.ip_address,d.login_user,d.login_passwd,d.host_type,d.trans_mode,b.rule_tmp_dir,
		 * b.rule_dst_dir,b.rule_dst_file,b.bak_flag,b.rule_bak_dir,b.bak_suffix,
		 * b.dup_flag,b.rule_dup_dir,b.rule_file_id,b.rule_file_seq,b.file_sn_flag,
		 * b.rule_file_sn,b.qc_flag,b.rule_qc_file,b.md5_flag,b.rule_md5_dir,
		 * b.rule_md5_file from ft_exec_plan a, ft_info_data b, ft_info_host c, 
		 * ft_info_host d where a.thread_id = 11160122 and a.src_id = b.src_id 
		 * and b.src_host_id = c.host_id and b.dst_host_id = d.host_id  order by a.seq_no
		 * 
		 */
		
		/*StringBuilder loadDataSql = new StringBuilder();
		loadDataSql.append("select a.src_id,a.begin_range,a.end_range,a.reserve_files,a.max_deal_files,");
		loadDataSql.append("a.sleep_time,a.alarm_level,a.stop_flag,b.log_table_name,c.host_id,");
		loadDataSql.append("c.ip_address,c.login_user,c.login_passwd,c.host_type,c.trans_mode,b.src_dir,");
		loadDataSql.append("b.pattern,d.host_id,d.ip_address,d.login_user,d.login_passwd,d.host_type,");
		loadDataSql.append("d.trans_mode,b.rule_tmp_dir,b.rule_dst_dir,b.rule_dst_file,b.bak_flag,");
		loadDataSql.append("b.rule_bak_dir,b.bak_suffix,b.dup_flag,b.rule_dup_dir,b.rule_file_id,");
		loadDataSql.append("b.rule_file_seq,b.file_sn_flag,b.rule_file_sn,b.qc_flag,b.rule_qc_file,");
		loadDataSql.append("b.md5_flag,b.rule_md5_dir,b.rule_md5_file ");
		loadDataSql.append(" from FT_EXEC_PLAN a,FT_INFO_DATA b, FT_INFO_HOST c, FT_INFO_HOST d");
		loadDataSql.append(" where a.thread_id = ? and a.src_id = b.src_id ");
		loadDataSql.append(" and b.src_host_id = c.host_id and b.dst_host_id = d.host_id ");
		loadDataSql.append(" order by a.seq_no ");*/
		
		StringBuilder loadDataSql = new StringBuilder();
		loadDataSql.append("select a.src_id,b.log_table_name,");
		loadDataSql.append(" c.host_id,c.ip_address,c.login_user,c.login_passwd,c.host_type,b.src_dir,b.pattern,");
		loadDataSql.append(" d.host_id,d.ip_address,d.login_user,d.login_passwd,d.host_type,b.dst_dir");
		loadDataSql.append(" from ft_exec_plan a,ft_info_data b,ft_info_host c,ft_info_host d");
		loadDataSql.append(" where a.thread_id = ? and a.src_id = b.src_id");
		loadDataSql.append(" and b.src_host_id = c.host_id and b.dst_host_id = d.host_id");
		
		Connection conn = JdbcUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(loadDataSql.toString());
			ps.setString(1, thread_id);
			rs = ps.executeQuery();
			while(rs.next()){
				MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_ID, rs.getString(1));
				MemCodeCacher.ft_parm.put(MemCodeCacher.LOG_TABLE_NAME, rs.getString(2));
				MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_HOST_ID, rs.getString(3));
				MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_LOGIN_USER, rs.getString(5));
				MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_LOGIN_PASSWORD, rs.getString(6));
				MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_HOST_TYPE, rs.getString(7));
				//MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_TRANS_MODE, rs.getString(15));
				MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_DIR, rs.getString(8));
				MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_PATTERN, rs.getString(9));
				MemCodeCacher.ft_parm.put(MemCodeCacher.DST_HOST_ID, rs.getString(10));
				MemCodeCacher.ft_parm.put(MemCodeCacher.DST_LOGIN_USER, rs.getString(12));
				MemCodeCacher.ft_parm.put(MemCodeCacher.DST_LOGIN_PASSWORD, rs.getString(13));
				MemCodeCacher.ft_parm.put(MemCodeCacher.DST_HOST_TYPE, rs.getString(14));
				//MemCodeCacher.ft_parm.put(MemCodeCacher.DST_TRANS_MODE, rs.getString(23));
				MemCodeCacher.ft_parm.put(MemCodeCacher.RULE_DST_DIR, rs.getString(15));
				
				String[] srcHost = rs.getString(4).split(":");
				String[] dstHost = rs.getString(11).split(":");
				
				String src_host_ip = srcHost[0];
				String dst_host_ip = dstHost[0];
				
				MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_HOST_ADDRESS, src_host_ip);
				MemCodeCacher.ft_parm.put(MemCodeCacher.DST_HOST_ADDRESS, dst_host_ip);
				
				if(srcHost.length>1){
					MemCodeCacher.ft_parm.put(MemCodeCacher.SRC_HOST_PORT, srcHost[1]);
				}
				
				if(dstHost.length>1){
					MemCodeCacher.ft_parm.put(MemCodeCacher.DST_HOST_PORT, dstHost[1]);
				}
				
				String host_ip = (String) MemCodeCacher.host_parm.get(MemCodeCacher.HOST_IP);
				
				if(!host_ip.equals(dst_host_ip)){   // Destination is not a local address.
					if(!host_ip.equals(src_host_ip)){ // Source is not a local address.
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_PUT, false);
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_GET, false);
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_GET_PUT, true);
					}else{   // Source is a local address.
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_PUT, true);
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_GET, false);
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_GET_PUT, false);
					}
				}else{ // Destination is a local address.
					if(!host_ip.equals(src_host_ip)){  // Source is not a local address. 
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_PUT, false);
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_GET, true);
						MemCodeCacher.ft_parm.put(MemCodeCacher.FTP_GET_PUT, false);
					}else{ // Source is a local address.
						//源主机以及目标主机与应用主机在同一台服务器，暂不做业务处理
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn!=null){
					conn.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(rs!=null){
					rs.close();
				}
			} catch (Exception e2) {
				
			}
		}
	}

}
