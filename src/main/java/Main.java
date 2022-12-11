package main.java;

import main.java.services.BlockChain;
import main.java.services.ChatEmulator;
import main.java.services.Miner;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        final var blockchain = new BlockChain();
        final var countOfMiners = Runtime.getRuntime().availableProcessors();
        final var executor = Executors.newFixedThreadPool(countOfMiners);

        IntStream.range(0, countOfMiners - 1)
                .mapToObj(i -> new Miner(blockchain, String.valueOf(i)))
                .forEach(executor::submit);

        ChatEmulator chatEmulator = new ChatEmulator();
        chatEmulator.haveChatAt(blockchain);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        chatEmulator.stop();

        executor.shutdown();
    }
}