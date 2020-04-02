/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.bigtable.hbase.adapters.read;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.cloud.bigtable.data.v2.models.RowAdapter;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestResultRowAdapter {

  private static final ByteString ROW_KEY_ONE = ByteString.copyFromUtf8("one");
  private static final String COL_FAMILY = "cf";
  private static final ByteString QUAL_ONE = ByteString.copyFromUtf8("q1");
  private static final ByteString QUAL_TWO = ByteString.copyFromUtf8("q2");
  private static final List<String> LABELS = ImmutableList.of("label-a", "label-b");
  private static final ByteString VALUE = ByteString.copyFromUtf8("value");

  private ResultRowAdapter underTest = new ResultRowAdapter();

  @Test
  public void testWithSingleCellRow() {
    RowAdapter.RowBuilder<Result> rowBuilder = underTest.createRowBuilder();

    rowBuilder.startRow(ROW_KEY_ONE);
    rowBuilder.startCell(COL_FAMILY, QUAL_ONE, 10000L, Collections.<String>emptyList(), 0);
    rowBuilder.cellValue(VALUE);
    rowBuilder.finishCell();
    rowBuilder.startCell(COL_FAMILY, QUAL_TWO, 20000L, LABELS, 0);
    rowBuilder.cellValue(VALUE);
    rowBuilder.finishCell();

    Result expected =
        Result.create(
            ImmutableList.<Cell>of(
                new RowCell(
                    ROW_KEY_ONE.toByteArray(),
                    COL_FAMILY.getBytes(),
                    QUAL_ONE.toByteArray(),
                    10L,
                    VALUE.toByteArray()),
                new RowCell(
                    ROW_KEY_ONE.toByteArray(),
                    COL_FAMILY.getBytes(),
                    QUAL_TWO.toByteArray(),
                    20L,
                    VALUE.toByteArray(),
                    LABELS)));
    assertResult(expected, rowBuilder.finishRow());
    assertEquals(ROW_KEY_ONE, underTest.getKey(expected));
  }

  @Test
  public void testWhenSplitCell() {
    ByteString valuePart1 = ByteString.copyFromUtf8("part-1");
    ByteString valuePart2 = ByteString.copyFromUtf8("part-2");
    ByteString valuePart3 = ByteString.copyFromUtf8("part-3");

    RowAdapter.RowBuilder<Result> rowBuilder = underTest.createRowBuilder();
    rowBuilder.startRow(ROW_KEY_ONE);
    rowBuilder.startCell(COL_FAMILY, QUAL_ONE, 10000L, Collections.<String>emptyList(), 0);
    rowBuilder.cellValue(valuePart1);
    rowBuilder.cellValue(valuePart2);
    rowBuilder.cellValue(valuePart3);
    rowBuilder.finishCell();

    Result actualResult = rowBuilder.finishRow();
    Assert.assertArrayEquals(
        valuePart1.concat(valuePart2).concat(valuePart3).toByteArray(),
        actualResult.getValue(COL_FAMILY.getBytes(), QUAL_ONE.toByteArray()));
  }

  @Test
  public void testWithMarkerRow() {
    RowAdapter.RowBuilder<Result> rowBuilder = underTest.createRowBuilder();
    Result markerRow = rowBuilder.createScanMarkerRow(ROW_KEY_ONE);
    assertResult(Result.EMPTY_RESULT, markerRow);
    assertTrue(underTest.isScanMarkerRow(markerRow));

    rowBuilder.reset();
    rowBuilder.startRow(ROW_KEY_ONE);
    assertResult(Result.EMPTY_RESULT, rowBuilder.finishRow());

    Result resultWithOneCell =
        Result.create(
            ImmutableList.<Cell>of(
                new RowCell(
                    ROW_KEY_ONE.toByteArray(),
                    COL_FAMILY.getBytes(),
                    QUAL_ONE.toByteArray(),
                    10L,
                    VALUE.toByteArray())));
    assertFalse(underTest.isScanMarkerRow(resultWithOneCell));
  }

  @Test
  public void testFamilyOrdering() {
    RowAdapter.RowBuilder<Result> rowBuilder = underTest.createRowBuilder();

    rowBuilder.startRow(ROW_KEY_ONE);
    rowBuilder.startCell("cc", QUAL_ONE, 20000L, LABELS, 0);
    rowBuilder.cellValue(VALUE);
    rowBuilder.finishCell();
    rowBuilder.startCell("bb", QUAL_TWO, 40000L, LABELS, 0);
    rowBuilder.cellValue(VALUE);
    rowBuilder.finishCell();
    rowBuilder.startCell("aa", ByteString.copyFromUtf8("q3"), 20000L, LABELS, 0);
    rowBuilder.cellValue(VALUE);
    rowBuilder.finishCell();
    rowBuilder.startCell("zz", ByteString.copyFromUtf8("q4"), 80000L, LABELS, 0);
    rowBuilder.cellValue(VALUE);
    rowBuilder.finishCell();
    rowBuilder.startCell("b", ByteString.copyFromUtf8("q4"), 10000L, LABELS, 0);
    rowBuilder.cellValue(VALUE);
    rowBuilder.finishCell();
    Result actualResult = rowBuilder.finishRow();

    List<String> colFamilyInActualOrder = new ArrayList<>(5);
    for (Cell cell : actualResult.listCells()) {
      colFamilyInActualOrder.add(
          Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()));
    }
    assertEquals(ImmutableList.of("aa", "b", "bb", "cc", "zz"), colFamilyInActualOrder);
  }

  private void assertResult(Result expected, Result actual) {
    try {
      Result.compareResults(expected, actual);
    } catch (Throwable throwable) {
      throw new AssertionError("Result did not match", throwable);
    }
  }
}
