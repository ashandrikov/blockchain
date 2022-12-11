package main.java.services;

import main.java.models.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockChain {
    public static int zeroes;
    LinkedBlockingDeque<Block> blockChain = new LinkedBlockingDeque<>();
    List<String> messages =
            Collections.synchronizedList(new ArrayList<>());

    public LinkedBlockingDeque<Block> getBlockChain() {
        return blockChain;
    }

    synchronized void addMessage(String author, String message) {
        messages.add(author + ": " + message);
    }

    public synchronized void addBlock(Block block) {
        if (isValid(block)) {
            block.setMessages(messages);
            blockChain.add(block);
            printBlock();
            updateZeroes();
        }
    }

    private void updateZeroes() {
        int timeSec = getLastBlock().getTimeToGenerate();
        if (timeSec < 1) {
            zeroes++;
            System.out.println(String.format("N was increased to %d%n", zeroes));
        } else if (timeSec > 4) {
            zeroes--;
            System.out.println("N was decreased by 1\n");
        } else {
            System.out.println("N stays the same\n");
        }
    }

    private boolean isValid(Block block) {
        return blockChain.size() + 1 == block.getId() &&
                Objects.equals(getLastBlockHash(), block.getPreviousHash());
    }

    public void printBlock() {
        System.out.println(getLastBlock());
    }

    public String getLastBlockHash() {
        return blockChain.isEmpty() ? "0" : getLastBlock().getCurrantHash();
    }

    public Block getLastBlock() {
        return blockChain.getLast();
    }
}
