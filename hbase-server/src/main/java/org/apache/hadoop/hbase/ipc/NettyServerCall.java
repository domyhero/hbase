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
package org.apache.hadoop.hbase.ipc;

import io.netty.channel.ChannelFutureListener;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.classification.InterfaceAudience;
import org.apache.hadoop.hbase.io.ByteBufferPool;
import org.apache.hadoop.hbase.ipc.RpcServer.CallCleanup;
import org.apache.hadoop.hbase.shaded.com.google.protobuf.BlockingService;
import org.apache.hadoop.hbase.shaded.com.google.protobuf.Descriptors.MethodDescriptor;
import org.apache.hadoop.hbase.shaded.com.google.protobuf.Message;
import org.apache.hadoop.hbase.shaded.protobuf.generated.RPCProtos.RequestHeader;
import org.apache.htrace.TraceInfo;

/**
 * Datastructure that holds all necessary to a method invocation and then afterward, carries the
 * result.
 */
@InterfaceAudience.Private
class NettyServerCall extends ServerCall<NettyServerRpcConnection> {

  NettyServerCall(int id, BlockingService service, MethodDescriptor md, RequestHeader header,
      Message param, CellScanner cellScanner, NettyServerRpcConnection connection, long size,
      TraceInfo tinfo, InetAddress remoteAddress, long receiveTime, int timeout,
      ByteBufferPool reservoir, CellBlockBuilder cellBlockBuilder, CallCleanup reqCleanup) {
    super(id, service, md, header, param, cellScanner, connection, size, tinfo, remoteAddress,
        receiveTime, timeout, reservoir, cellBlockBuilder, reqCleanup);
  }

  /**
   * If we have a response, and delay is not set, then respond immediately. Otherwise, do not
   * respond to client. This is called by the RPC code in the context of the Handler thread.
   */
  @Override
  public synchronized void sendResponseIfReady() throws IOException {
    connection.channel.writeAndFlush(this);
  }

  public synchronized void sendResponseIfReady(ChannelFutureListener listener) throws IOException {
    connection.channel.writeAndFlush(this).addListener(listener);
  }
}