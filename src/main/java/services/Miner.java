package main.java.services;

import main.java.models.Block;

public class Miner implements Runnable {
    private final BlockChain blockchain;
    private static final int BLOCKS_COUNT = 5;

    public Miner(BlockChain blockchain, String threadName) {
        this.blockchain = blockchain;
        Thread.currentThread().setName(threadName);
    }

    @Override
    public void run() {
        while (blockchain.getBlockChain().size() < BLOCKS_COUNT) {
            final Block block = new Block(
                    Thread.currentThread().getName(),
                    blockchain.getBlockChain().size() + 1,
                    getPreviousBlockHash());
            block.countTimeFindMagicNumber();
            blockchain.addBlock(block);
        }
    }

    private String getPreviousBlockHash(){
        return blockchain.getBlockChain().isEmpty()
                ? "0"
                : blockchain.getBlockChain().getLast().getCurrantHash();
    }
}
