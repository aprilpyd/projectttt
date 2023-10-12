package com.snatik.matches;

import android.content.Context;
import android.content.SharedPreferences;
import com.snatik.matches.common.Memory;
import com.snatik.matches.common.Shared;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class MemoryTest {

    @Mock
    private Context mockContext;
    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockContext.getSharedPreferences(Memory.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE))
                .thenReturn(mockSharedPreferences);
        Mockito.when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        // Set the test context to Shared.context
        Shared.context = mockContext;
    }

    @Test
    public void testGetHighStars() {
        int theme = 1;
        int difficulty = 1;
        int expectedHighStars = 3;

        // Mock the high stars in SharedPreferences
        Mockito.when(mockSharedPreferences.getInt(String.format(Memory.highStartKey, theme, difficulty), 0))
                .thenReturn(expectedHighStars);

        int highStars = Memory.getHighStars(theme, difficulty);

        // Verify that the method returns the expected high stars
        assertEquals(3, highStars);
    }

    @Test
    public void testGetBestTime() {
        int theme = 1;
        int difficulty = 2;
        int expectedBestTime = 25;

        // Mock the best time in SharedPreferences
        Mockito.when(mockSharedPreferences.getInt(String.format(Memory.bestTimeKey, theme, difficulty), -1))
                .thenReturn(expectedBestTime);

        int bestTime = Memory.getBestTime(theme, difficulty);

        // Use assert to check if the method returns the expected best time
        assertEquals(expectedBestTime, bestTime);
    }
}





