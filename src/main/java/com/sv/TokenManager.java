package com.sv;

import java.util.ArrayList;
import java.util.List;

public class TokenManager {
    private static TokenManager instance;
    private List<String> tokens;

    private TokenManager() {
        this.tokens = new ArrayList<>();
    }

    public static TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }

    public void agregarToken(String token) {
        tokens.add(token);
    }

    public List<String> obtenerTokens() {
        return new ArrayList<>(tokens);
    }

    public void limpiarTokens() {
        tokens.clear();
    }

    public int obtenerCantidadTokens() {
         return tokens.size();
    }

    public String obtenerToken(int indice) {
        if (indice >= 0 && indice < tokens.size()) {
            return tokens.get(indice);
        }
        return null;
    }
}
