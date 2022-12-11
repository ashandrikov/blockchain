package main.java.models;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static main.java.services.BlockChain.zeroes;
import static main.java.utils.StringUtil.applySha256;

public class Block {
    private final String minerId;
    private long id;
    private long timestamp;
    private long magicNumber;
    private String previousHash;
    private String currantHash;
    private List<String> messages;
    private int timeToGenerate;
    private Random rand = new Random();

    public Block(String minerId, int id, String prevBlockHash) {
        this.minerId = minerId;
        this.id = id;
        this.previousHash = prevBlockHash;
        this.timestamp = new Date().getTime();
    }

    public void countTimeFindMagicNumber() {
        final Instant startTime = Instant.now();
        findMagicNumber();
        timeToGenerate = Math.toIntExact(Duration.between(startTime, Instant.now()).toSeconds());
    }

    private void findMagicNumber() {
        String hash;
        do {
            magicNumber = rand.nextLong();
            hash = applySha256("" + id + magicNumber + previousHash);
        } while (!hash.startsWith("0".repeat(zeroes)));
        currantHash = hash;
    }

    @Override
    public String toString() {
        return String.format("""
                        Block:
                        Created by miner # %s
                        Id: %d
                        Timestamp: %d
                        Magic number: %d
                        Hash of the previous block:
                        %s
                        Hash of the block:
                        %s
                        Block data: %s
                        Block was generating for %d seconds""",
                minerId,
                id,
                timestamp,
                magicNumber,
                previousHash,
                currantHash,
                printMessages(),
                timeToGenerate);
    }

    public long getId() {
        return id;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getCurrantHash() {
        return currantHash;
    }

    public int getTimeToGenerate() {
        return timeToGenerate;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String printMessages(){
        if (getMessages().isEmpty()) {
            return "no messages";
        } else {
            String result = "";
            for (String s :getMessages()) {
                result = result + "\n" + s;
            }
            return result;
        }
    }
}
