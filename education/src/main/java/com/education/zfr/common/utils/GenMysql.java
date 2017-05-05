package com.education.zfr.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GenMysql
{
    
    // 指定实体生成所在包的路径
    private String packageOutPath = "com.education.zfr.biz.entity";
    
    // 作者名字
    private String authorName = "大大泡泡糖";
    
    // 主键
    private String primaryKey = "";
    
    // 列名数组
    private List<String> colnames = new ArrayList<String>();
    
    // 列名类型数组
    private List<String> colTypes = new ArrayList<String>();
    
    // 列名大小数组
    private List<Integer> colSizes = new ArrayList<Integer>();
    
    // 注释
    private List<String> colComments = new ArrayList<String>();
    
    // 是否需要导入包java.util.*
    private boolean f_util = false;
    
    // 是否需要导入包java.sql.*
    private boolean f_sql = false;
    
    /**
     * 数据库连接
     */
    // 表名
    private static final String TABLENAME = "cpn_department";
    
    // 库名
    private static final String DATABASENAME = "company";
    
    // 数据库地址
    private static final String URL = "jdbc:mysql://localhost:3306/";
    
    // 数据库用户名
    private static final String NAME = "root";
    
    // 数据库密码
    private static final String PASS = "root";
    
    // 数据库驱动
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    /*
     * 构造函数
     */
    public GenMysql()
    {
        // 创建连接
        Connection con = null;
        // 查要生成实体类的表
        String sql =
            "select COLUMN_NAME,COLUMN_KEY,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,COLUMN_COMMENT from information_schema.COLUMNS where table_name = '"
                + TABLENAME + "' and table_schema = '" + DATABASENAME + "';";
        Statement pStemt = null;
        String content = null;
        ResultSet rs = null;
        try
        {
            try
            {
                Class.forName(DRIVER);
            }
            catch (ClassNotFoundException e1)
            {
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL + DATABASENAME, NAME, PASS);
            pStemt = con.createStatement();
            rs = pStemt.executeQuery(sql);
            while (rs.next())
            {
                // COLUMN_NAME,COLUMN_KEY,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,COLUMN_COMMENT
                colnames.add(rs.getString("COLUMN_NAME"));
                colTypes.add(rs.getString("DATA_TYPE"));
                colSizes.add(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
                colComments.add(rs.getString("COLUMN_COMMENT"));
                if ("PRI".equalsIgnoreCase(rs.getString("COLUMN_KEY")))
                {
                    primaryKey = rs.getString("COLUMN_NAME");
                }
                
                if (rs.getString("DATA_TYPE").equalsIgnoreCase("datetime"))
                {
                    f_util = true;
                }
                if (rs.getString("DATA_TYPE").equalsIgnoreCase("image")
                    || rs.getString("DATA_TYPE").equalsIgnoreCase("text"))
                {
                    f_sql = true;
                }
            }
            content = parse();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                rs.close();
            }
            catch (SQLException e1)
            {
            }
            try
            {
                pStemt.close();
            }
            catch (SQLException e)
            {
            }
            try
            {
                con.close();
            }
            catch (SQLException e)
            {
            }
            System.out.println(content);
        }
    }
    
    /**
     * 功能：生成实体类主体代码
     * 
     * @return
     */
    private String parse()
    {
        StringBuffer sb = new StringBuffer();
        
        // 判断是否导入工具包
        sb.append("package " + this.packageOutPath + ";\r\n\r\n");
        if (f_util)
        {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql)
        {
            sb.append("import java.sql.*;\r\n");
        }
        
        sb.append("import javax.persistence.Column;\r\n");
        sb.append("import javax.persistence.Entity;\r\n");
        sb.append("import javax.persistence.GeneratedValue;\r\n");
        sb.append("import javax.persistence.GenerationType;\r\n");
        sb.append("import javax.persistence.Id;\r\n");
        sb.append("import javax.persistence.Table;\r\n");
        
        sb.append("\r\n");
        // 实体部分
        sb.append("@Entity\r\n");
        sb.append("@Table(name = \"" + TABLENAME + "\")\r\n");
        sb.append("public class " + initcap(TABLENAME) + " extends BaseEntity {\r\n");
        sb.append("\r\n");
        // 自动生成 serialVersionUID
        Long id = (Long)UUID.randomUUID().getMostSignificantBits();
        sb.append("\tprivate static final long serialVersionUID = " + id + "l;\r\n");
        sb.append("\r\n");
        // 属性
        processAllAttrs(sb);
        // get set方法
        processAllMethod(sb);
        // 生成toString [根据情况添加]
        processToString(sb);
        sb.append("}\r\n");
        
        return sb.toString();
    }
    
    /**
     * 功能：生成toString
     * 
     * @param sb
     */
    private void processToString(StringBuffer sb)
    {
        sb.append("\t@Override\r\n");
        sb.append("\tpublic String toString()\r\n");
        sb.append("\t{\r\n");
        sb.append("\t   return ToStringBuilder.reflectionToString(this);\r\n");
        sb.append("\t}\r\n");
    }
    
    /**
     * 功能：生成所有属性
     * 
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb)
    {
        for (int i = 0; i < colnames.size(); i++)
        {
            String[] names = colnames.get(i).split("_");
            StringBuffer columnName = new StringBuffer();
            for (String n : names)
            {
                if (columnName.length() > 0)
                {
                    columnName.append(initcap(n.toLowerCase()));
                }
                else
                {
                    columnName.append(n.toLowerCase());
                }
            }
            sb.append("\t/**\r\n");
            sb.append("\t * ").append(colComments.get(i)).append("\r\n");
            sb.append("\t */\r\n");
            if (primaryKey.equals(colnames.get(i)))
            {
                sb.append("\t@Id\r\n");
                sb.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
            }
            sb.append("\t@Column(name=\"" + colnames.get(i) + "\")\r\n");
            sb.append("\tprivate " + sqlType2JavaType(colTypes.get(i)) + " " + columnName.toString() + ";\r\n");
            sb.append("\r\n");
        }
        
    }
    
    /**
     * 功能：生成所有方法
     * 
     * @param sb
     */
    private void processAllMethod(StringBuffer sb)
    {
        
        for (int i = 0; i < colnames.size(); i++)
        {
            String[] names = colnames.get(i).split("_");
            StringBuffer columnName = new StringBuffer();
            for (String n : names)
            {
                if (columnName.length() > 0)
                {
                    columnName.append(initcap(n.toLowerCase()));
                }
                else
                {
                    columnName.append(n.toLowerCase());
                }
            }
            sb.append("\tpublic void set" + initcap(columnName.toString()) + "(" + sqlType2JavaType(colTypes.get(i))
                + " " + columnName.toString() + "){\r\n");
            sb.append("\t   this." + columnName.toString() + "=" + columnName.toString() + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes.get(i)) + " get" + initcap(columnName.toString())
                + "(){\r\n");
            sb.append("\t   return " + columnName.toString() + ";\r\n");
            sb.append("\t}\r\n");
        }
        
    }
    
    /**
     * 功能：将输入字符串的首字母改成大写
     * 
     * @param str
     * @return
     */
    private String initcap(String str)
    {
        String result = "";
        String[] names = str.split("_");
        for (int i = 0; i < names.length; i++)
        {
            char[] ch = names[i].toCharArray();
            if (ch[0] >= 'a' && ch[0] <= 'z')
            {
                ch[0] = (char)(ch[0] - 32);
            }
            result = result + new String(ch);
        }
        return result;
    }
    
    /**
     * 功能：获得列的数据类型
     * 
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType)
    {
        
        if (sqlType.equalsIgnoreCase("bit"))
        {
            return "boolean";
        }
        else if (sqlType.equalsIgnoreCase("tinyint"))
        {
            return "byte";
        }
        else if (sqlType.equalsIgnoreCase("smallint"))
        {
            return "short";
        }
        else if (sqlType.equalsIgnoreCase("int"))
        {
            return "Integer";
        }
        else if (sqlType.equalsIgnoreCase("bigint"))
        {
            return "Long";
        }
        else if (sqlType.equalsIgnoreCase("float"))
        {
            return "Float";
        }
        else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
            || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
            || sqlType.equalsIgnoreCase("smallmoney"))
        {
            return "Double";
        }
        else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
            || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
            || sqlType.equalsIgnoreCase("text"))
        {
            return "String";
        }
        else if (sqlType.equalsIgnoreCase("datetime"))
        {
            return "Date";
        }
        else if (sqlType.equalsIgnoreCase("image"))
        {
            return "Blod";
        }
        
        return null;
    }
    
    /**
     * 出口 TODO
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        
        new GenMysql();
        
    }
    
}