/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package net.hydromatic.optiq.rules.java;

import net.hydromatic.linq4j.expressions.BlockBuilder;
import net.hydromatic.linq4j.expressions.Expression;

import java.util.List;

/**
 * Information for a call to {@link AggImplementor#implementReset(AggContext, AggResetContext)}.
 * {@link AggResetContext} provides access to the accumulator variables
 * that should be reset.
 */
public class AggResetContext extends NestedBlockBuilder {
  private final List<Expression> accumulator;

  /**
   * Creates aggregate reset context
   * @param block code block that will contain the added initialization
   * @param accumulator accumulator variables that store the intermediate
   *                    aggregate state
   */
  public AggResetContext(BlockBuilder block, List<Expression> accumulator) {
    super(block);
    this.accumulator = accumulator;
  }

  /**
   * Returns accumulator variables that should be reset.
   * There MUST be an assignment even if you just assign the default value.
   * @return accumulator variables that should be reset or empty list when no
   *   accumulator variables are used by the aggregate implementation.
   * @see net.hydromatic.optiq.rules.java.AggImplementor#getStateType(AggContext)
   */
  public List<Expression> accumulator() {
    return accumulator;
  }
}

// End AggResetContext.java