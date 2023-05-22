package integrationTest.helper;

import java.sql.Connection;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DbComparisonFailure;
import org.dbunit.assertion.DiffCollectingFailureHandler;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
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

      DiffCollectingFailureHandler failureHandler = new DiffCollectingFailureHandler();
      for (String path : paths) {
        IDataSet expected = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(path));
        for (String tableName : expected.getTableNames()) {
          ITable expectedTable = expected.getTable(tableName);
          ITable actualTable = actual.getTable(tableName);

          try {
            Assertion.assertEquals(expectedTable, actualTable, failureHandler);
          } catch (DbComparisonFailure e) {
//            StringBuilder sb = new StringBuilder();
//
//            for (int i = 0; i < actualTable.getRowCount(); i++) {
//              for (Column column : actualTable.getTableMetaData().getColumns()) {
//                sb.append(column.getColumnName());
//                sb.append("=");
//                sb.append(actualTable.getValue(i, column.getColumnName()));
//                sb.append(",");
//              }
//              sb.append("\n");
//            }
//            System.out.println(sb);
//            throw new RuntimeException(e);
            // 件数が一致しなかったときのエラーメッセージ.
//            failureHandler.createFailure("件数が合いませんでした");
            throw new RuntimeException(e);
          }
        }
        if (!failureHandler.getDiffList().isEmpty()) {
//          StringBuilder sb = new StringBuilder();
//          failureHandler.getDiffList().forEach(
//              diff -> sb.append(diff.toString())
//          );
//          Assertions.fail(sb.toString());
          // カラムの内容差異の場合のエラー
          Assertions.fail("テーブルの比較に失敗しました。");

        }
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  ;
}
