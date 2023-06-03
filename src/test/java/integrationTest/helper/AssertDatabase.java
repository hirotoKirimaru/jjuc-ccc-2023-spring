package integrationTest.helper;

import java.sql.Connection;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DbComparisonFailure;
import org.dbunit.assertion.DefaultFailureHandler;
import org.dbunit.assertion.DiffCollectingFailureHandler;
import org.dbunit.assertion.Difference;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.jetbrains.annotations.NotNull;

public interface AssertDatabase {

  @NotNull
  default DatabaseConnection getDbUnitConnection(Connection connection)
      throws DatabaseUnitException {
    return new DatabaseConnection(connection, "app");
  }

  default void setUpDatabase(DataSource dataSource, String... paths) {
    try (var connection = dataSource.getConnection()) {
      var dbUnitConnection = getDbUnitConnection(connection);

      for (String path : paths) {
        var dataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(path));
        DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnection, dataSet);
      }

    } catch (Exception e) {
      throw new RuntimeException("setupに失敗しました", e);
    }
  }

  default void assertDatabase(DataSource dataSource, String... paths) {
    try (var connection = dataSource.getConnection()) {
      DatabaseConnection dbUnitConnection = getDbUnitConnection(connection);
      IDataSet actual = dbUnitConnection.createDataSet();

      // 件数が一致しなかったときのエラーメッセージ用
      StringBuilder countErrorSb = new StringBuilder();
      DiffCollectingFailureHandler failureHandler = new DiffCollectingFailureHandler();
      for (String path : paths) {
        IDataSet loadExpectedXml = new FlatXmlDataSetBuilder().build(
            getClass().getResourceAsStream(path));
        ReplacementDataSet expected = new ReplacementDataSet(loadExpectedXml);
        expected.addReplacementObject("[null]", null);

        for (String tableName : expected.getTableNames()) {
          ITable expectedTable = expected.getTable(tableName);
          ITable actualTable = actual.getTable(tableName);

          try {
//            Assertion.assertEquals(expectedTable, actualTable, failureHandler);
            Assertion.assertEquals(expectedTable, actualTable, failureHandler);
          } catch (DbComparisonFailure e) {
            if (actualTable.getRowCount() == 0) {
              countErrorSb.append("件数不一致:データがありません[");
              countErrorSb.append("table=").append(tableName);
              countErrorSb.append("]");
              countErrorSb.append("\n");
            }
            for (int i = 0; i < actualTable.getRowCount(); i++) {
              countErrorSb.append("件数不一致:データ[");
              countErrorSb.append("table=").append(tableName);
              countErrorSb.append(",");
              for (Column column : actualTable.getTableMetaData().getColumns()) {
                countErrorSb.append(column.getColumnName());
                countErrorSb.append("=");
                countErrorSb.append(actualTable.getValue(i, column.getColumnName()));
                countErrorSb.append(",");
              }
              countErrorSb.append("]");
              countErrorSb.append("\n");
            }
          }
        }
        if (!countErrorSb.isEmpty() || !failureHandler.getDiffList().isEmpty()) {
          StringBuilder sb = new StringBuilder(countErrorSb);
          for (var d : failureHandler.getDiffList()) {
            if (d instanceof Difference diff) {
              sb.append("値不一致:[");
              sb.append("Table=").append(diff.getExpectedTable().getTableMetaData().getTableName());
              int i = 1;
              for (Column primaryKey : diff.getActualTable().getTableMetaData()
                  .getPrimaryKeys()) {
                sb.append(",pk").append(i++).append("=").append(primaryKey.getColumnName());
                sb.append("=").append(diff.getActualTable()
                    .getValue(diff.getRowIndex(), primaryKey.getColumnName()));
              }
//                sb.append(", rowIndex=").append(diff.getRowIndex());
              sb.append(", columnName=").append(diff.getColumnName());
              sb.append(", expectedValue=").append(diff.getExpectedValue());
              sb.append(", actualValue=").append(diff.getActualValue());
              sb.append("]");
              sb.append("\n");
//                sb.append(diff);

            }
          }

//              diff -> sb.append(diff.toString());
//          Assertions.fail(sb.toString());
          // カラムの内容差異の場合のエラー
          Assertions.fail("テーブルの比較に失敗しました。\n" + sb);

        }
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  ;
}
