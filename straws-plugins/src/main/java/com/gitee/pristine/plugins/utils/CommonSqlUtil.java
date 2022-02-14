package com.gitee.pristine.plugins.utils;

import com.gitee.pristine.datasource.meta.ColumnMeta;
import com.gitee.pristine.datasource.meta.TableMeta;
import com.gitee.pristine.datasource.split.SplitPkRange;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * SQL 基础工具类
 * @author Pristine Xu
 * @date 2022/1/19 4:48
 * @description:
 */
public class CommonSqlUtil {

    /**
     * 生成插入sql参数占位符，？,？,？,？,？
     * @param columns 列, 逗号分割，示例：id,name,gender...
     * @return ？,？,？,？
     */
    public static String createInsertSqlParamsPlaceholder(String columns) {
        String[] split = columns.split(",");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i == split.length-1) {
                builder.append("?");
            } else {
                builder.append("?").append(",");
            }
        }
        return builder.toString();
    }

    /**
     * 生成更新sql参数占位符，update table set k=?, k2=?, k3=? where id = [idValue]
     * @param columns 列, 逗号分割，示例：id,name,gender...
     * @return
     */
    public static String createUpdateSqlParamsPlaceholder(String columns, String splitPk) {
        String[] split = columns.split(",");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            // TODO 更新语句不能将主键或自增列加进去
            if (!splitPk.equals(split[i])) {
                if (i == split.length-1) {
                    builder.append(split[i] + "=? ");
                } else {
                    builder.append(split[i] + "=?").append(", ");
                }
            }
        }
        return builder.toString();
    }

    /**
     * 创建范围参数，拼接 column >= ? and column < ?
     * @param splitPk
     * @param splitPkRange
     * @return
     */
    public static String createRange(String splitPk, SplitPkRange splitPkRange) {
        long bgnVal = splitPkRange.getStartValue().getPk().longValue();
        long endVal = splitPkRange.getFinishValue().getPk().longValue();
        return splitPk +" >= " + bgnVal + " and " + splitPk + " < " + endVal;
    }

    /**
     * 调整更新参数
     * @param tableMeta
     * @param columns
     * @param args
     * @return
     */
    public static List<Object[]> adjustUpdateParams(TableMeta tableMeta, String columns, List<Object[]> args) {
        String[] columnNames = columns.split(",");
        Map<String, Integer> _idxMap = new HashMap<>();
        for (int i = 0; i < columnNames.length; i++) {
            _idxMap.put(columnNames[i].trim(), i);
        }

        List<String> primaryKeys = tableMeta.getPrimaryKeys();
        List<Object[]> resData = new LinkedList<>();

        for (Object[] arg : args) {
            Object[] var = new Object[arg.length];
            int index = 0;

            // 非主键和非自增列
            for (int i = 0; i < columnNames.length; i++) {
                ColumnMeta col = tableMeta.getColumn(columnNames[i].trim());
                if (!col.isPrimaryKey() && !col.isAutoIncrement()) {
                    var[index] = arg[_idxMap.get(col.getName())];
                    index ++;
                }
            }

            // 主键
            for (int j = 0; j < primaryKeys.size(); j++) {
                var[index] = arg[_idxMap.get(primaryKeys.get(j))];
                index ++;
            }

            resData.add(var);
        }
        return resData;
    }


    /**
     * 调整删除参数
     * @param tableMeta
     * @param columns
     * @param args
     * @return
     */
    public static List<Object[]> adjustDeleteParams(TableMeta tableMeta, String columns, List<Object[]> args) {
        String[] columnNames = columns.split(",");
        Map<String, Integer> _idxMap = new HashMap<>();
        for (int i = 0; i < columnNames.length; i++) {
            _idxMap.put(columnNames[i].trim(), i);
        }

        List<String> primaryKeys = tableMeta.getPrimaryKeys();
        List<Object[]> resData = new LinkedList<>();

        for (Object[] arg : args) {
            Object[] var = new Object[primaryKeys.size()];
            int index = 0;

            // 主键
            for (int j = 0; j < primaryKeys.size(); j++) {
                var[index] = arg[_idxMap.get(primaryKeys.get(j))];
                index ++;
            }

            resData.add(var);
        }
        return resData;
    }

}
