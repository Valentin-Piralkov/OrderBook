# Kraken API Order Book

This project is a WebSocket client for the Kraken API. It establishes a WebSocket connection to a given URI and uses a WebSocketListener to handle incoming messages. It parses and updates order books for subscribed currency pairs (Like "BTH/USD"). Order books are updated in real-time as new messages are received from the WebSocket connection and are printed on the console for each update.

## Features

* Establishes a WebSocket connection to the Kraken API.
* Handles incoming messages using a WebSocketListener.
* Manages the state of the connection and subscriptions to different currency pairs.
* Parses and updates order books for subscribed currency pairs.

## Classes

* `WebSocketClient:` This class represents the WebSocket client. It establishes a WebSocket connection to a given URI and uses a WebSocketListener to handle incoming messages.
* `WebSocketListener:` This class handles incoming messages from the WebSocket connection. It manages the state of the connection and subscriptions to different currency pairs.
* `KrakenAPIState:` This class represents the state of the connection and subscriptions to different currency pairs.
* `OrderBook:` This class represents an order book for a specific currency pair. It can update the order book based on incoming messages from the WebSocket connection.

## Usage

To use this project, you simply need to run the `WebSocketClient` class. It will establish a WebSocket connection to the Kraken API and start listening for incoming messages. The order books for subscribed currency pairs will be updated in real-time as new messages are received from the WebSocket connection. You can change the currency pairs to subscribe to by modifying the `subscription.json` file to contain your desired pairs. You can also change the pairs that are stored in order books by modifying the `PAIRS` constant in the `EnvironmentGlobals` class. DO not change the WSS URI as different versions of KrakenAPI send different messages and the code won't work properly.

## Dependencies
* Java 22
* Maven 3.8
* Java Websocket 1.1
* Jackson 2.16
* JUnit 5
* Mockito 5
* ByteBuddy 1.14

