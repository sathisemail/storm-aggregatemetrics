/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.apache.storm.metric.api;


public class AggregateReducerMetric implements IReducer<AggregatorReducerState> {

    @Override
    public AggregatorReducerState init() {
        return new AggregatorReducerState();
    }

    @Override
    public AggregatorReducerState reduce(AggregatorReducerState acc, Object input) {
        acc.count++;
        if (input instanceof Double) {
            acc.sum += (Double) input;

            if ((Double) input < acc.min) {
                acc.min = (Double) input;
            }

            if (acc.max < (Double) input) {
                acc.max = (Double) input;
            }
        } else if (input instanceof Long) {
            double inputVal = ((Long) input).doubleValue();
            acc.sum += inputVal;

            if (inputVal < acc.min) {
                acc.min = inputVal;
            }

            if (acc.max < inputVal) {
                acc.max = inputVal;
            }

        } else if (input instanceof Integer) {
            double inputVal = ((Integer) input).doubleValue();
            acc.sum += inputVal;

            if (inputVal < acc.min) {
                acc.min = inputVal;
            }

            if (acc.max < inputVal) {
                acc.max = inputVal;
            }

        } else {
            throw new RuntimeException(
                    "AggregateReducer::reduce called with unsupported input type `" + input.getClass()
                    + "`. Supported types are Double, Long, Integer.");
        }
        return acc;
    }

    @Override
    public Object extractResult(AggregatorReducerState acc) {
        return acc;
    }
}
