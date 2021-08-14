package util

class CommentConverter {

    /// Format the text so that it doesn't interfere with the translation.
    fun extractTextFromComment(commentText: String): String {
        return commentText
            // For multiline comments, remove "//", "///" and "*" at line start.
            .replace(Regex("(^|\n) *?(///?|\\*)"), "")
            .replace("\n", " ")
            .replace(Regex(" +"), " ")
    }
}
