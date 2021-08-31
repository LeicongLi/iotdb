/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.iotdb.db.query.expression;

import org.apache.iotdb.db.exception.query.LogicalOptimizeException;
import org.apache.iotdb.db.metadata.PartialPath;
import org.apache.iotdb.db.qp.utils.WildcardsRemover;
import org.apache.iotdb.db.query.udf.core.builder.TransformerBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Expression {

  private String expressionString;

  public boolean isAggregationFunctionExpression() {
    return false;
  }

  public boolean isTimeSeriesGeneratingFunctionExpression() {
    return false;
  }

  public abstract void concat(List<PartialPath> prefixPaths, List<Expression> resultExpressions);

  public abstract void removeWildcards(
      WildcardsRemover wildcardsRemover, List<Expression> resultExpressions)
      throws LogicalOptimizeException;

  public abstract void collectPaths(Set<PartialPath> pathSet);

  public abstract void constructTransformerBuilder(
      Map<Expression, TransformerBuilder> expressionTransformerBuilderMap);

  public String getExpressionString() {
    if (expressionString == null) {
      expressionString = toString();
    }
    return expressionString;
  }

  @Override
  public int hashCode() {
    return getExpressionString().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Expression)) {
      return false;
    }

    return getExpressionString().equals(((Expression) o).getExpressionString());
  }
}
