package main.java.services;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MessageBot implements Runnable {
    private final String name;
    private final BlockChain blockChain;
    private static final int MAX_SLEEP_MS = 350;
    private static final int MSG_LENGTH = 7;
    private boolean isStopped = false;
    private final Random random = new Random();

    public MessageBot(String name, BlockChain blockChain) {
        this.name = name;
        this.blockChain = blockChain;
    }

    private void sendRandomMessage() {
        blockChain.addMessage(name, generateRandomMessage());
    }

    private String generateRandomMessage() {
        StringBuilder resultMessage = new StringBuilder();
        for (int i = 0; i < MSG_LENGTH; i++) {
            resultMessage.append((char) ThreadLocalRandom.current().nextInt(97, 123));
        }
        return resultMessage + " " + new Date().getTime();
    }

    public void stop() {
        isStopped = true;
    }

    @Override
    public void run() {
        long sleepTimer = random.nextInt(MAX_SLEEP_MS);

        while (!isStopped) {
            sendRandomMessage();
            try {
                Thread.sleep(sleepTimer);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
