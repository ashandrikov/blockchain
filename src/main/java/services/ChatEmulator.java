package main.java.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatEmulator {
    public static final int THREADS = 4;
    private final List<String> botNames = List.of("Kate", "Tom", "Nick", "Alex", "John");
    private final Set<MessageBot> bots = new HashSet<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

    public void haveChatAt(BlockChain blockChain) {
        botNames.forEach(bot -> bots.add(new MessageBot(bot, blockChain)));
        bots.forEach(executorService::submit);
    }

    public void stop() {
        bots.forEach(MessageBot::stop);
        executorService.shutdown();
    }
}
