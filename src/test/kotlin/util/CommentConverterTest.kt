package util

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class CommentConverterTest: BehaviorSpec({

    val converter = CommentConverter()

    Given("Give complete comment") {

        And("Start with //") {
            When("single line comment") {
                val comment = "// This is a single line comment."
                val result = converter.extractTextFromComment(comment)
                Then("Remove \"// \"") {
                    result shouldBe " This is a single line comment."
                }
            }

            When("2 lines comment") {
                val comment =
                    """
                    // This is first line and
                    // this is second line."""
                val result = converter.extractTextFromComment(comment)
                Then("Remove \"// \"") {
                    result shouldBe " This is first line and this is second line."
                }
            }

            When("3 lines comment") {
                val comment =
                    """
                    // This is first line,
                    // this is second line and
                    // this is third line."""
                val result = converter.extractTextFromComment(comment)
                Then("Remove \"// \"") {
                    result shouldBe " This is first line, this is second line and this is third line."
                }
            }
        }

        And("Start with ///") {
            When("single line comment") {
                val comment = "/// This is a single line comment."
                val result = converter.extractTextFromComment(comment)
                Then("Remove \"/// \"") {
                    result shouldBe " This is a single line comment."
                }
            }

            When("2 lines comment") {
                val comment =
                    """
                    /// This is first line and
                    /// this is second line."""
                val result = converter.extractTextFromComment(comment)
                Then("Remove \"/// \"") {
                    result shouldBe " This is first line and this is second line."
                }
            }

            When("3 lines comment") {
                val comment =
                    """
                    /// This is first line,
                    /// this is second line and
                    /// this is third line."""
                val result = converter.extractTextFromComment(comment)
                Then("Remove \"/// \"") {
                    result shouldBe " This is first line, this is second line and this is third line."
                }
            }
        }
    }
})
