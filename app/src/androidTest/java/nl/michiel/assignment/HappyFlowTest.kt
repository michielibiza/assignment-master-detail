package nl.michiel.assignment

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import nl.michiel.assignment.view.TopLevelNavigation
import org.junit.Rule
import org.junit.Test

class HappyFlowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * When opening the app
     * And I wait for the repos to load
     * And I tap on repo with name "ActionBarSherlock"
     * Then I should see "Details" (in the top bar) and "JakeWharton" (in the content)
     * When I wait for the events to load
     * Then I should see at least one "ForkEvent"
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun happyFlow(): Unit = with(composeTestRule) {
        setContent { TopLevelNavigation() }

        waitUntilAtLeastOneExists(hasText("ActionBarSherlock"))

        onNodeWithText("ActionBarSherlock").performClick()

        onNodeWithText("Details").assertExists()
        onNodeWithText("JakeWharton").assertExists()

        waitUntilAtLeastOneExists(hasText("History"))

        onAllNodesWithText("ForkEvent").onFirst().assertExists()

    }
}
