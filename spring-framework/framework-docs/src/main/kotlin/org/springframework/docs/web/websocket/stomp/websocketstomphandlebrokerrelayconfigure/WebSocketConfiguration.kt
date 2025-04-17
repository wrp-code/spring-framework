/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("DEPRECATION")
package org.springframework.docs.web.websocket.stomp.websocketstomphandlebrokerrelayconfigure

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import java.net.InetSocketAddress

// tag::snippet[]
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfiguration : WebSocketMessageBrokerConfigurer {

	// ...

	override fun configureMessageBroker(registry: MessageBrokerRegistry) {
		registry.enableStompBrokerRelay("/queue/", "/topic/").setTcpClient(createTcpClient())
		registry.setApplicationDestinationPrefixes("/app")
	}

	private fun createTcpClient(): ReactorNettyTcpClient<ByteArray> {
		return ReactorNettyTcpClient({ it.addressSupplier { InetSocketAddress(0) } }, StompReactorNettyCodec())
	}
}
// end::snippet[]