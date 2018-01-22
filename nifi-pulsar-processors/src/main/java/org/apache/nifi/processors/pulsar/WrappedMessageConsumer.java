/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.processors.pulsar;

import org.apache.nifi.logging.ComponentLog;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.ConsumerStats;

public class WrappedMessageConsumer {
	
	private final Consumer consumer;
	private boolean closed = false;
	
	public WrappedMessageConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public void close(ComponentLog logger) {
		closed = true;
		
		try {
			consumer.close();
		} catch (PulsarClientException e) {
			logger.warn("Unable to close connection to Pulsar due to {}; resources may not be cleaned up appropriately", e);
		}
	}

	public boolean isClosed() {
        return closed;
    }
	
	public Consumer getConsumer() {
		return this.consumer;
	}
	
	public ConsumerStats getStats() {
		return this.consumer.getStats();
	}
}