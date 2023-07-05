package pt.ulusofona.aed.rockindeisi2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;

public class TestMain {
    @Test
    public void test() {
        boolean read_success = Main.loadFiles(new File("test-files"));
        Assertions.assertTrue(read_success,"Erro ao ler ficheiros");
    }
}
