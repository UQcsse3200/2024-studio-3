package com.csse3200.game.files;

import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.files.FileHandle;
import com.csse3200.game.files.FileLoader.Location;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class FileLoaderTest {

    @Test
    void loadFromValidFile() {
        TestStats test = FileLoader.readClass(TestStats.class, "test/files/valid.json");
        assertNotNull(test);
        assertEquals( 3, test.stat1);
        assertEquals(4, test.stat2);
    }

    @Test
    void loadFromEmptyFile() {
        TestStats test =
                FileLoader.readClass(
                  TestStats.class, "test/files/empty.json");
        assertNotNull(test);
        assertEquals(1, test.stat1);
        assertEquals(2, test.stat2);
    }

    @Test
    void loadDirectory() {
        FileHandle[] files = FileLoader.getFiles("test/files/", Location.LOCAL);
        assertEquals(10, files.length);
    }

    @Test
    void loadFromMissingFile() {
        TestStats test =
                FileLoader.readClass(
                  TestStats.class, "test/files/missing.json");
        assertNull(test);
    }

    @Test
    void loadFromInvalidFile() {
        TestStats test =
                FileLoader.readClass(
                  TestStats.class, "test/files/invalid.json");
        assertNull(test);
    }
}
