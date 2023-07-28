package com.example.omadachallengechenige.flickrimages

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.compose.AppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FullScreenPhotoScreenKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            AppTheme {
                FullScreenPhotoScreen(navController = rememberNavController(), state = ImagesScreenState(flickrPhotos = listOf(
                    Photo("Title 1", "url1"), Photo("Title 2", "url2"), Photo("Title 3", "url3")
                ), selectedPhoto = 1))
            }
        }
    }

    @Test
    fun numberOfImagesIsCorrect() {
        composeTestRule.onNodeWithText("2 / 3").assertExists()
    }

    @Test
    fun isDisplayingSecondImage() {
        composeTestRule.onNodeWithText("Title 1").assertDoesNotExist()
        composeTestRule.onNodeWithText("Title 2").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText("Title 3").assertDoesNotExist()
    }

    @Test
    fun swipingGoesToNextItem() {
        composeTestRule.onNodeWithText("Title 2").performTouchInput { swipeLeft() }
        composeTestRule.onNodeWithText("Title 2").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("Title 3").assertExists().assertIsDisplayed()
    }
}