package vpiralkov.env;

import java.util.List;

/**
 * EnvironmentGlobals class contains global variables for the project.
 *
 * WSS - Kraken WebSocket URL
 * FILE_PATH - Path to the JSON file for the subscription message.
 * PAIRS - List of currency pairs. Change this list to store to different pairs.
 **/

public class EnvironmentGlobals {
    public static final String WSS = "wss://ws.kraken.com/v2";
    public static final String FILE_PATH = "src/main/resources/subscription.json";
    public static final List<String> PAIRS = List.of("BTC/USD", "ETH/USD");
}
