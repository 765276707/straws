package com.gitee.pristine.datasource.constant;

/**
 * 数据库Meta的标签
 * @author Pristine Xu
 * @date 2021/12/25 19:22
 * @description: 可根据标签从Meta中获取所需要的信息
 */
public class MetaLabel {

    /**
     * 表Label
     */
    public static class Table {
        /** 表名 */
        public final static String TABLE_NAME = "TABLE_NAME";
        /** 表类型 */
        public final static String TABLE_TYPE = "TABLE_TYPE";
        /** 表备注 */
        public final static String REMARKS = "REMARKS";
    }

    /**
     * 字段Label
     */
    public static class Column {
        /** 表名 */
        public final static String TABLE_NAME = "TABLE_NAME";
        /** 字段名 */
        public final static String COLUMN_NAME = "COLUMN_NAME";
        /** 对应的java.sql.Types的SQL类型(列类型ID) */
        public final static String DATA_TYPE = "DATA_TYPE";
        /** java.sql.Types类型名称(列类型名称) */
        public final static String TYPE_NAME = "TYPE_NAME";
        /** 列大小 */
        public final static String COLUMN_SIZE = "COLUMN_SIZE";
        /** 小数位数 */
        public final static String DECIMAL_DIGITS = "DECIMAL_DIGITS";
        /** 基数（通常是10或2） */
        public final static String NUM_PREC_RADIX = "NUM_PREC_RADIX";
        /** 对于 char 类型，该长度是列中的最大字节数 */
        public final static String CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";
        /** 是否可空 */
        public final static String IS_NULLABLE = "IS_NULLABLE";
        /** 是否自增 */
        public final static String IS_AUTOINCREMENT = "IS_AUTOINCREMENT";
        /** 字段默认值 */
        public final static String COLUMN_DEF = "COLUMN_DEF";
        /** 表中列的索引（从1开始） */
        public final static String ORDINAL_POSITION = "ORDINAL_POSITION";
        /** 列描述 */
        public final static String REMARKS = "REMARKS";
    }

    /**
     * 主键Label
     */
    public static class PrimaryKey {
        /** 表名 */
        public final static String TABLE_NAME = "TABLE_NAME";
        /** 主键字段名 */
        public final static String COLUMN_NAME = "COLUMN_NAME";
        /** 序列号(主键内值1表示第一列的主键，值2代表主键内的第二列) */
        public final static String KEY_SEQ = "KEY_SEQ";
        /** 主键名称 */
        public final static String PK_NAME = "PK_NAME";
    }

    /**
     * 索引Label
     */
    public static class Index {
        /** 表名 */
        public final static String TABLE_NAME = "TABLE_NAME";
        /** 索引名称 */
        public static final String INDEX_NAME = "INDEX_NAME";
        /** 索引目录 */
        public static final String INDEX_QUALIFIER = "INDEX_QUALIFIER";
        /** 索引类型 */
        public static final String TYPE = "TYPE";
        /** 索引列名 */
        public static final String COLUMN_NAME = "COLUMN_NAME";
        /** 索引值是否可以不唯一 */
        public static final String NON_UNIQUE = "NON_UNIQUE";
        /** 排序 */
        public static final String ASC_OR_DESC = "ASC_OR_DESC";
    }

}
