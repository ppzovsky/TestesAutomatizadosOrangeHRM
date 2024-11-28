package com.projetoiLAB.utils;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class ReadData {

    public static class Login {
        public String username;
        public String password;
    }

    public static class Logins {
        public Login loginValido;
        public Login loginInvalido;
    }

    public static Logins lerJson() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/test/resources/test-data/login-data.json")) {
            return gson.fromJson(reader, Logins.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao ler o arquivo JSON.");
        }
    }
}
