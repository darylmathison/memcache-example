/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.darylmathison.memcache;

import java.io.IOException;
import java.net.InetSocketAddress;
import net.spy.memcached.MemcachedClient;

/**
 *
 * @author Daryl
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MemcachedClient client = new MemcachedClient(new InetSocketAddress("localhost", 5701));
            for(int i = 2; i < 20; i++) {
                System.out.println("value of round " + i + " is " + fibonacci(i, client));
            }
            client.shutdown();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private static long fibonacci(int rounds, MemcachedClient client) {
        String cached = (String)client.get(String.valueOf(rounds));
        if(cached != null) {
            System.out.print("cached ");
            return Long.parseLong(cached);
        }
        
        long[] lastTwo = new long[] {1, 1};
        
        for(int i = 0; i < rounds; i++) {
            long last = lastTwo[1];
            lastTwo[1] = lastTwo[0] + lastTwo[1];
            lastTwo[0] = last;
        }
        
        client.set(String.valueOf(rounds), 360, String.valueOf(lastTwo[1]));
        return lastTwo[1];
     }
}
