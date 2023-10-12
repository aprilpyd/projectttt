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

        Shared.context = mockContext;
    }

    @Test
    public void testSaveTime() {
        int theme = 1;
        int difficulty = 2;
        int passedSecs = 30;

        // Mock the best time in SharedPreferences
        Mockito.when(mockSharedPreferences.getInt(String.format(Memory.bestTimeKey, theme, difficulty), -1))
                .thenReturn(40);

        Memory.saveTime(theme, difficulty, passedSecs);

        // Verify that the SharedPreferences were updated with the new best time
        Mockito.verify(mockEditor).putInt(String.format(Memory.bestTimeKey, theme, difficulty), passedSecs);
        Mockito.verify(mockEditor).commit();

        // Update the mocked value in SharedPreferences
        Mockito.when(mockSharedPreferences.getInt(String.format(Memory.bestTimeKey, theme, difficulty), -1))
                .thenReturn(passedSecs);

        // Retrieve the best time again and use assert to check
        int bestTime = Memory.getBestTime(theme, difficulty);
        assertEquals(passedSecs, bestTime);
    }



    @Test
    public void testGetBestTime() {
        int theme = 1;
        int difficulty = 1;
        int expectedBestTime = 25;
        String key = String.format(Memory.bestTimeKey, theme, difficulty);

        // Mock SharedPreferences behavior
        Mockito.when(mockSharedPreferences.getInt(key, -1)).thenReturn(expectedBestTime);

        // Test getBestTime for the current combination of theme and difficulty
        int bestTime = Memory.getBestTime(theme, difficulty);
        int expected = (theme >= 0 && difficulty >= 0) ? expectedBestTime : -1; // Expect -1 for negative inputs
        assertEquals(expected, bestTime);
    }

}





