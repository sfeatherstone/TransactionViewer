package com.simonfea.transactionviewer.model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by simonfea on 14/10/2016.
 */

public class Rates {

    private Map<String, GraphNode> currencyConnections = new HashMap<>();

    private HashMap<String, Double> currencyRates = new HashMap<>();

    @Nullable
    public Double getRate(String sourceCurrency, String destinationCurrency){
        if (sourceCurrency.equals(destinationCurrency)) {
            return 1.0;
        }
        String requiredKey = getKey(sourceCurrency, destinationCurrency);
        Double rate = currencyRates.get(requiredKey);
        if (rate==null) {
            rate = findRateAndAddToFastLookup(sourceCurrency,destinationCurrency);
        }
        return rate;
    }

    public boolean addRate(String sourceCurrency, String destinationCurrency, double rate) {
        String key = getKey(sourceCurrency, destinationCurrency);
        if (currencyRates.containsKey(key)) {
            return false;
        }
        currencyRates.put(key, rate);
        //Build nodes for unconnected currencies
        GraphNode sourceNode = getOrCreateNode(sourceCurrency);
        GraphNode destNode = getOrCreateNode(destinationCurrency);
        sourceNode.connectedCurrencies.add(destNode);
        return true;
    }

    private Double findRateAndAddToFastLookup(String sourceCurrency, String destinationCurrency) {
        Set<String> nodesVisited = new HashSet<>();
        GraphNode source = currencyConnections.get(sourceCurrency);
        GraphNode destination = currencyConnections.get(destinationCurrency);

        if (source==null || destination==null) return null;

        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(source);
        source.calculatedRate = 1.0;

        while(!queue.isEmpty()) {
            GraphNode current = queue.remove();
            nodesVisited.add(current.currency);
            if (current==destination) {
                addRate(sourceCurrency,destinationCurrency,current.calculatedRate);
                return current.calculatedRate;
            }
            for (GraphNode node: current.connectedCurrencies) {
                if (!nodesVisited.contains(node.currency)) {
                    queue.add(node);
                    node.calculatedRate = current.calculatedRate * getRate(current.currency,node.currency);
                }
            }
        }
        return null;
    }

    private GraphNode getOrCreateNode(String currency) {
        GraphNode currentNode = currencyConnections.get(currency);
        if (currentNode==null) {
            currentNode = new GraphNode(currency);
            currencyConnections.put(currency, currentNode);
        }
        return currentNode;
    }

    private String getKey(String sourceCurrency, String destinationCurrency) {
        return sourceCurrency + "+" + destinationCurrency;
    }

    private class GraphNode {
        public GraphNode(String currency) {
            this.currency = currency;
        }
        public String currency;
        public ArrayList<GraphNode> connectedCurrencies = new ArrayList<>();
        public double calculatedRate;
    }
}
