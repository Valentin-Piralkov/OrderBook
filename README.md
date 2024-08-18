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

To use this project, you need to create a WebSocketClient with the URI of the Kraken API WebSocket server. Then, call the connect method to establish the WebSocket connection.