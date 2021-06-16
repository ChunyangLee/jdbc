package com.lichunyang.blobtest;

import com.lichunyang.util.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author ChunyangLi
 * @create 2021-06-15-11:15
 */
public class BlobTest {
    //向customers表中插入一个Blob类型
    @Test
    public void testInsert() throws  Exception{
        Connection con = JDBCUtils.getConnection();
        String sql= "insert into customers(name,email,birth,photo) values(?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setObject(1,"徐男");
        ps.setObject(2,"39342@qq.com");
        ps.setObject(3,"1994-11-12");
        InputStream is = new FileInputStream(new File("s.jpeg"));
        ps.setBlob(4,is);

        ps.execute();
        JDBCUtils.closeResourses(con,ps);
    }
    @Test
    public void testQuery(){
        Connection con = null;
        PreparedStatement ps = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            con = JDBCUtils.getConnection();
            String sql= "select photo from customers where name=?";
            ps = con.prepareStatement(sql);
            ps.setString(1,"徐男");

            ResultSet rs = ps.executeQuery();
            Blob photo=null;
            while (rs.next()){
                photo = rs.getBlob(1);
            }
            //下载blob图片保存在本地
            is = photo.getBinaryStream();
            bis = new BufferedInputStream(is);
            byte[] bytes = new byte[1024];
            int len;
            fos = new FileOutputStream("s_copy.jpeg");
            while ((len=bis.read(bytes))!=-1){
                fos.write(bytes,0,len);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (fos!=null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis!=null) bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is!=null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResourses(con,ps);
        }

    }
}
