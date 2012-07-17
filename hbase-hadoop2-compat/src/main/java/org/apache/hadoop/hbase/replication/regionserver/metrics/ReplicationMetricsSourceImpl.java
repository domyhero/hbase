/**
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
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hbase.replication.regionserver.metrics;

import org.apache.hadoop.hbase.metrics.BaseMetricsSourceImpl;
import org.apache.hadoop.metrics2.MetricsSource;

/**
 * Hadoop2 implementation of ReplicationMetricsSource. This provides access to metrics gauges and
 * counters.
 */
public class ReplicationMetricsSourceImpl extends BaseMetricsSourceImpl implements
    ReplicationMetricsSource {

  public static final String METRICS_NAME = "ReplicationMetrics";
  public static final String METRICS_CONTEXT = "replicationmetrics";
  public static final String METRICS_DESCRIPTION = "Metrics about HBase replication";

  public ReplicationMetricsSourceImpl() {
    this(METRICS_NAME, METRICS_DESCRIPTION, METRICS_CONTEXT);
  }

  ReplicationMetricsSourceImpl(String metricsName,
                               String metricsDescription, String metricsContext) {
    super(metricsName, metricsDescription, metricsContext);
  }
}
